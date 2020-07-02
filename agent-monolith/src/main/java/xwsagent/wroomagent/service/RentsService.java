package xwsagent.wroomagent.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.converter.AdConverter;
import xwsagent.wroomagent.converter.RentConverter;
import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.BundledRequests;
import xwsagent.wroomagent.domain.Debt;
import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.domain.dto.RentRequestDTO;
import xwsagent.wroomagent.domain.enums.DebtStatus;
import xwsagent.wroomagent.domain.enums.RequestStatus;
import xwsagent.wroomagent.exception.InvalidReferenceException;
import xwsagent.wroomagent.jwt.UserPrincipal;
import xwsagent.wroomagent.repository.AdRepository;
import xwsagent.wroomagent.repository.RentRequestRepository;
import xwsagent.wroomagent.repository.rbac.UserRepository;
import xwsagent.wroomagent.soap.clients.RentsClient;
import xwsagent.wroomagent.soap.xsd.OperationRents;

@Service
public class RentsService {

	private final RentRequestRepository rentRepository;
	private final AdRepository adRepository;
	private final AdService adService;
	private final UserRepository userRepository;
	private final RentsClient rentsClient;

	@Autowired
	private BundleService bundleService;

	public RentsService(RentRequestRepository rr, AdService a, AdRepository adRepository, UserRepository userRepository,
			RentsClient rentsClient) {
		this.rentRepository = rr;
		this.adService = a;
		this.adRepository = adRepository;
		this.userRepository = userRepository;
		this.rentsClient = rentsClient;
	}

	public List<RentRequest> findAll() {
		return this.rentRepository.findAll();
	}

	public List<RentRequest> findByAd(Ad ad) {
		return this.rentRepository.findByAd(ad);
	}

	public RentRequest findById(Long id) {
		return this.rentRepository.findById(id).orElseThrow(
				() -> new InvalidReferenceException("Unable to find reference to " + id.toString() + " rent request"));
	}

	public RentRequest sendRequest(RentRequestDTO dto, Long requestedUserID) {
		User user = this.userRepository.findById(requestedUserID).get();
		List<Debt> debts = new ArrayList<Debt>();
		for(Debt debt : user.getDebts()) {
			if(debt.getStatus() == DebtStatus.UNPAID) {
				debts.add(debt);
			}
		}
		
		if(debts.isEmpty()) {
			RentRequest entity = RentConverter.toEntity(dto);
			// entity.setRequestedUserId(dto.getRequestedUserId());
			entity.setRequestedUser(this.userRepository.findById(requestedUserID).get());
			entity.setStatus(RequestStatus.PENDING);
			entity.setAd(this.adService.findById(dto.getAd().getId()));
			
			RentRequest saved = this.rentRepository.save(entity);
			
	//		No need to send this to wroom
	//		try {
	//			this.rentsClient.send(entity, OperationRents.);
	//		} catch(Exception e) {
	//			System.out.println("Error during soap sending");
	//			e.printStackTrace();
	//		}
			return saved;
		}else {
			return null;
		}
		
	}

	public BundledRequests sendBundleRequest(RentRequestDTO[] dtos, Long requestedUserID) {
		User user = this.userRepository.findById(requestedUserID).get();
		List<Debt> debts = new ArrayList<Debt>();
		for(Debt debt : user.getDebts()) {
			if(debt.getStatus() == DebtStatus.UNPAID) {
				debts.add(debt);
			}
		}
		
		if(debts.isEmpty()) {
			BundledRequests bundle = new BundledRequests();
			BundledRequests saved = this.bundleService.save(bundle);
	
			Set<RentRequest> requests = new HashSet<RentRequest>();
			for (RentRequestDTO requestDTO : dtos) {
				RentRequest newRequest = RentConverter.toEntity(requestDTO);
				newRequest.setStatus(RequestStatus.PENDING);
	
				// newRequest.setRequestedUserId(requestDTO.getRequestedUserId());
				newRequest.setRequestedUser(this.userRepository.findById(requestedUserID).get());
				newRequest.setAd(this.adService.findById(requestDTO.getAd().getId()));
	
				newRequest.setBundle(saved);
				requests.add(newRequest);
			}
			bundle.setRequests(requests);
	
			BundledRequests entity = this.bundleService.save(bundle);
			try {
				this.rentsClient.sendBundle(entity);
			} catch(Exception e) {
				System.out.println("Error during soap sending");
				e.printStackTrace();
			}
			
			return entity;
		}else {
			return null;
		}
	}

	public boolean occupy(RentRequestDTO rentRequestDTO, Authentication auth) {
		RentRequest rentRequest = RentConverter.toEntity(rentRequestDTO);
		rentRequest.setStatus(RequestStatus.PHYSICALLY_RESERVED);
		rentRequest.setFromDate(rentRequestDTO.getFromDate());
		rentRequest.setToDate(rentRequestDTO.getToDate());

		
		//rentRequest.setRequestedUser((this.userRepository.findById(user.getId(),.)));

		UserPrincipal user = (UserPrincipal) auth.getPrincipal();
		Optional<User> loggedInUser = userRepository.findByEmail(user.getUsername());
		if (loggedInUser.isPresent()) {
			rentRequest.setRequestedUser(loggedInUser.get());
		}

		Ad ad = this.adService.findById(rentRequestDTO.getAd().getId());
		List<Ad> list = this.adService.findAll();

		for(Ad a : list) {
			System.out.println(a);
		}

		rentRequest.setAd(ad);

		if (ad.getAvailableFrom().after(rentRequest.getFromDate())
				&& ad.getAvailableTo().before(rentRequest.getToDate())) {
			return false;
		}
		if (ad.getAvailableFrom().after(rentRequest.getFromDate())
				&& ad.getAvailableTo().after(rentRequest.getToDate())) {
			return false;
		}

		if (ad.getAvailableFrom().before(rentRequest.getFromDate())
				&& ad.getAvailableTo().before(rentRequest.getToDate())) {
			return false;
		}

		List<RentRequest> rentList = rentRepository.findByAd(ad);

		if(rentList.size() == 0) {
			RentRequest saved = rentRepository.save(rentRequest);
			
			try {
				this.rentsClient.send(saved, OperationRents.OCCUPY);		
			} catch(Exception e) {
				System.out.println("Error during soap sending");
				e.printStackTrace();
			}
			
			return true;
		}

		for(RentRequest r : rentList) {
			if(r.getFromDate().after(rentRequest.getFromDate()) && r.getToDate().before(rentRequest.getToDate()) ) {
				if(r.getStatus() == RequestStatus.PENDING) {
					r.setStatus(RequestStatus.CANCELED);
					this.rentRepository.saveAndFlush(r);
				} else {
					return false;
				}
			}
			if(r.getFromDate().after(rentRequest.getFromDate()) && r.getToDate().after(rentRequest.getToDate())&& r.getFromDate().before(rentRequest.getToDate())) {
				if(r.getStatus() == RequestStatus.PENDING) {
					r.setStatus(RequestStatus.CANCELED);
					this.rentRepository.saveAndFlush(r);
				}else {
					return false;
				}
			}

			if(r.getFromDate().before(rentRequest.getFromDate()) && r.getToDate().before(rentRequest.getToDate()) && r.getToDate().after(rentRequest.getFromDate())) {
				if(r.getStatus() == RequestStatus.PENDING) {
					r.setStatus(RequestStatus.CANCELED);
					this.rentRepository.saveAndFlush(r);
				}else {
					return false;
				}
			}

			if(r.getFromDate().before(rentRequest.getFromDate()) && r.getToDate().after(rentRequest.getToDate()) ) {
				if(r.getStatus() == RequestStatus.PENDING) {
					r.setStatus(RequestStatus.CANCELED);
					this.rentRepository.saveAndFlush(r);
				}else {
					return false;
				}
			}

			if(rentRequest.getFromDate().equals(r.getFromDate()) || rentRequest.getToDate().equals(r.getToDate())) {
				if(r.getStatus() == RequestStatus.PENDING) {
					r.setStatus(RequestStatus.CANCELED);
					this.rentRepository.saveAndFlush(r);
				}else {
					return false;
				}
			}
		}
		
		// Send rent request to wroom
		RentRequest saved = rentRepository.save(rentRequest);
		try {
			this.rentsClient.send(saved, OperationRents.OCCUPY);		
		} catch(Exception e) {
			System.err.println("Error during soap sending");
			e.printStackTrace();
		}
		
		return true;
	}

	public List<RentRequest> findByRequestedUser(Long userId) {
		return this.rentRepository.findByRequestedUser(userId);
	}

	public List<RentRequestDTO> occupyList(Long userId) {
		List<Ad> adList = adRepository.findAllActiveUser(userId);
		List<RentRequestDTO> retList = new ArrayList<RentRequestDTO>();
		for (Ad ad : adList) {
			List<RentRequest> requestList = rentRepository.findByAd(ad);
			for (RentRequest r : requestList) {
				retList.add(RentConverter.fromEntity(r));
			}
		}
		return retList;
	}

	public List<RentRequestDTO> findByAd(Long adId) {
		Ad ad = this.adService.findById(adId);
		return RentConverter.fromEntityList(this.findByAd(ad), RentConverter::fromEntity);
	}

	public RentRequest decline(Long id) {
		RentRequest rentRequest = findById(id);
		rentRequest.setStatus(RequestStatus.CANCELED);
		RentRequest saved = this.rentRepository.save(rentRequest);
		
		try {
			RentRequest sending = saved;
			
			// Kada budemo imali sinhronizovane baze, u monolitnoj cemo imati podatak
			// o tome koji id dati entitet ima na wroom-u. Zato se ovde vrsi promena id-ja
//			sending.setId(sending.getWroomId());
			this.rentsClient.send(sending, OperationRents.DECLINE);
		} catch(Exception e) {
			System.err.println("Did not sync");
		}
		
		return saved;
	}

	public RentRequest accept(Long id) {
		RentRequest rentRequest = findById(id);
		rentRequest.setStatus(RequestStatus.RESERVED);
		
		RentRequest saved = this.rentRepository.save(rentRequest);
		
		try {
			RentRequest sending = saved;
			
			// Kada budemo imali sinhronizovane baze, u monolitnoj cemo imati podatak
			// o tome koji id dati entitet ima na wroom-u.
			// Zato se ovde za request.id stavlja request.wroomId (za sada jos ne)
			sending.setId(saved.getId());
			
			this.rentsClient.send(sending, OperationRents.ACCEPT);
		} catch(Exception e) {
			System.err.println("Did not sync");
		}
		
		return saved;
	}

	public RentRequest complete(Long id) {
		RentRequest rentRequest = findById(id);

		rentRequest.setStatus(RequestStatus.COMPLETED);
		return this.rentRepository.save(rentRequest);
	}

	public List<RentRequest> getPending(Long userId) {

		List<Ad> adList = adRepository.findAllActiveUser(userId);
		List<RentRequest> pendingList = new ArrayList<RentRequest>();

		boolean flag = false;

		for (Ad ad : adList) {
			List<RentRequest> requestList = rentRepository.findByAd(ad);
			for (RentRequest rentRequest : requestList) {
				// if flag is true, pending request is overlapped by a reserved one and should
				// not be shown
				flag = false;

				if (rentRequest.getStatus() == RequestStatus.PENDING) {
					System.out.println("Uso u pending");
					for (RentRequest request : requestList) {

						if (request.getId() != rentRequest.getId()) {
							System.out.println("Uso ovde");
							Integer count = rentRepository.findValidPendingRequests(userId, ad.getId(),
									rentRequest.getFromDate(), rentRequest.getToDate());
							if (count != null) {
								if (count > 0) {
									flag = true;
									System.out.println(">>Rent request with ID" + rentRequest
											+ "overlaps with a reserved one." + request);
								}
							}
						}
					}

					if (!flag) {
						System.out.println("Flag je false");
						pendingList.add(rentRequest);
					}

				}
			}
		}

		System.out.println("Pending list " + pendingList);
		return pendingList;
	}

	public RentRequest pay(Long id) {

		RentRequest rentRequest = findById(id);

		Ad ad = adService.findById(rentRequest.getAd().getId());
		List<RentRequest> otherRequests = rentRepository.findByAd(ad);

		for (RentRequest r : otherRequests) {

			if (r.getFromDate().after(rentRequest.getFromDate()) && r.getToDate().before(rentRequest.getToDate())) {
				if (r.getStatus() == RequestStatus.PENDING && r.getId() != rentRequest.getId()) {
					r.setStatus(RequestStatus.CANCELED);

					clearBundles(r);

					this.rentRepository.saveAndFlush(r);
				}
			}

			if (r.getFromDate().after(rentRequest.getFromDate()) && r.getToDate().after(rentRequest.getToDate())
					&& r.getFromDate().before(rentRequest.getToDate())) {
				if (r.getStatus() == RequestStatus.PENDING && r.getId() != rentRequest.getId()) {
					r.setStatus(RequestStatus.CANCELED);
					clearBundles(r);
					this.rentRepository.saveAndFlush(r);
				}
			}

			if (r.getFromDate().before(rentRequest.getFromDate()) && r.getToDate().before(rentRequest.getToDate())
					&& r.getToDate().after(rentRequest.getFromDate())) {
				if (r.getStatus() == RequestStatus.PENDING && r.getId() != rentRequest.getId()) {
					r.setStatus(RequestStatus.CANCELED);
					clearBundles(r);
					this.rentRepository.saveAndFlush(r);
				}
			}

			if (r.getFromDate().before(rentRequest.getFromDate()) && r.getToDate().after(rentRequest.getToDate())) {
				if (r.getStatus() == RequestStatus.PENDING && r.getId() != rentRequest.getId()) {
					r.setStatus(RequestStatus.CANCELED);
					this.rentRepository.saveAndFlush(r);
				}
			}

			if (rentRequest.getFromDate().equals(r.getFromDate()) || rentRequest.getToDate().equals(r.getToDate())) {
				if (r.getStatus() == RequestStatus.PENDING && r.getId() != rentRequest.getId()) {
					r.setStatus(RequestStatus.CANCELED);
					clearBundles(r);
					this.rentRepository.saveAndFlush(r);
				}
			}
		}

		rentRequest.setStatus(RequestStatus.PAID);
		rentRepository.save(rentRequest);
		return rentRequest;
	}

	public void clearBundles(RentRequest r) {

		BundledRequests bundle = r.getBundle();

		if (bundle != null) {
			List<RentRequest> bundledRequests = bundleService.findBundledRentRequests(r.getBundle().getId());
			for (RentRequest bundledRequest : bundledRequests) {
				decline(bundledRequest.getId());
			}
		}
	}

}
