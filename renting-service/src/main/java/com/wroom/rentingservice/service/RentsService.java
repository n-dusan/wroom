package com.wroom.rentingservice.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.wroom.rentingservice.converter.RentConverter;
import com.wroom.rentingservice.domain.Ad;
import com.wroom.rentingservice.domain.BundledRequests;
import com.wroom.rentingservice.domain.Debt;
import com.wroom.rentingservice.domain.RentRequest;
import com.wroom.rentingservice.domain.dto.RentRequestDTO;
import com.wroom.rentingservice.domain.enums.DebtStatus;
import com.wroom.rentingservice.domain.enums.RequestStatus;
import com.wroom.rentingservice.exception.GeneralException;
import com.wroom.rentingservice.jwt.UserPrincipal;
import com.wroom.rentingservice.repository.AdRepository;
import com.wroom.rentingservice.repository.DebtRepository;
import com.wroom.rentingservice.repository.RentRequestRepository;

@Service
public class RentsService {

	private final RentRequestRepository rentRepository;
	private final AdRepository adRepository;
	private final AdService adService;
	private final DebtRepository debtRepository;

	@Autowired
	private BundleService bundleService;

	public RentsService(RentRequestRepository rr, AdService a, AdRepository adRepository,
			DebtRepository debtRepository) {
		this.rentRepository = rr;
		this.adService = a;
		this.adRepository = adRepository;
		this.debtRepository = debtRepository;
	}

	public List<RentRequest> findAll() {
		return this.rentRepository.findAll();
	}

	public List<RentRequest> findByAd(Ad ad) {
		return this.rentRepository.findByAd(ad);
	}

	public RentRequest findById(Long id) {
		return this.rentRepository.findById(id).orElseThrow(
				() -> new GeneralException("Unable to find reference to " + id.toString() + " rent request"));
	}

	public RentRequest sendRequest(RentRequestDTO dto, Long requestedUserID) {
		List<Debt> byUser = this.debtRepository.findByUserId(requestedUserID);
		List<Debt> debts = new ArrayList<Debt>();
		
		for (Debt d : byUser) {
			if (d.getStatus() == DebtStatus.UNPAID) {
				debts.add(d);
			}
		}
		
		if (debts.isEmpty()) {
			RentRequest entity = RentConverter.toEntity(dto);
			entity.setRequestedUserId(dto.getRequestedUserId());
//			entity.setRequestedUser(this.userRepository.findById(requestedUserID).get());
			entity.setStatus(RequestStatus.PENDING);
			entity.setAd(this.adService.findById(dto.getAd().getId()));
			return this.rentRepository.save(entity);
		}

		return null;

	}

	public BundledRequests sendBundleRequest(RentRequestDTO[] dtos, Long requestedUserID) {

		List<Debt> debts = this.debtRepository.findByUserId(requestedUserID);
		for (Debt d : debts) {
			if (d.getStatus() == DebtStatus.UNPAID) {
				debts.add(d);
			}
		}

		if (debts.isEmpty()) {

			BundledRequests bundle = new BundledRequests();
			BundledRequests saved = this.bundleService.save(bundle);

			Set<RentRequest> requests = new HashSet<RentRequest>();
			for (RentRequestDTO requestDTO : dtos) {
				RentRequest newRequest = RentConverter.toEntity(requestDTO);
				newRequest.setStatus(RequestStatus.PENDING);

				newRequest.setRequestedUserId(requestDTO.getRequestedUserId());
//			newRequest.setRequestedUser(this.userRepository.findById(requestedUserID).get());
				newRequest.setAd(this.adService.findById(requestDTO.getAd().getId()));

				newRequest.setBundle(saved);
				requests.add(newRequest);
			}
			bundle.setRequests(requests);

			return this.bundleService.save(bundle);
		}

		return null;
	}

	RentRequest findByLocalId(Long id) {
		try {
			return rentRepository.findByLocalId(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Used when the request comes from monolith
	 * 
	 * @param * @return
	 */
	public boolean occupy(RentRequest rentRequest) {
//		RentRequest rentRequest = findByLocalId(entity.getLocalId(), entity.getOwnerUsername());
		Ad ad = rentRequest.getAd();

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

		if (rentList.size() == 0) {
			rentRepository.save(rentRequest);
			return true;
		}

		for (RentRequest r : rentList) {
			if (r.getFromDate().after(rentRequest.getFromDate()) && r.getToDate().before(rentRequest.getToDate())) {
				if (r.getStatus() == RequestStatus.PENDING) {
					r.setStatus(RequestStatus.CANCELED);
					this.rentRepository.saveAndFlush(r);
				} else {
					return false;
				}
			}
			if (r.getFromDate().after(rentRequest.getFromDate()) && r.getToDate().after(rentRequest.getToDate())
					&& r.getFromDate().before(rentRequest.getToDate())) {
				if (r.getStatus() == RequestStatus.PENDING) {
					r.setStatus(RequestStatus.CANCELED);
					this.rentRepository.saveAndFlush(r);
				} else {
					return false;
				}
			}

			if (r.getFromDate().before(rentRequest.getFromDate()) && r.getToDate().before(rentRequest.getToDate())
					&& r.getToDate().after(rentRequest.getFromDate())) {
				if (r.getStatus() == RequestStatus.PENDING) {
					r.setStatus(RequestStatus.CANCELED);
					this.rentRepository.saveAndFlush(r);
				} else {
					return false;
				}
			}

			if (r.getFromDate().before(rentRequest.getFromDate()) && r.getToDate().after(rentRequest.getToDate())) {
				if (r.getStatus() == RequestStatus.PENDING) {
					r.setStatus(RequestStatus.CANCELED);
					this.rentRepository.saveAndFlush(r);
				} else {
					return false;
				}
			}

			if (rentRequest.getFromDate().equals(r.getFromDate()) || rentRequest.getToDate().equals(r.getToDate())) {
				if (r.getStatus() == RequestStatus.PENDING) {
					r.setStatus(RequestStatus.CANCELED);
					this.rentRepository.saveAndFlush(r);
				} else {
					return false;
				}
			}
		}
		rentRepository.save(rentRequest);
		return true;
	}

	public boolean occupy(RentRequestDTO rentRequestDTO, Authentication auth) {
		RentRequest rentRequest = RentConverter.toEntity(rentRequestDTO);
		rentRequest.setStatus(RequestStatus.PHYSICALLY_RESERVED);
		rentRequest.setFromDate(rentRequestDTO.getFromDate());
		rentRequest.setToDate(rentRequestDTO.getToDate());

		if (auth != null) {
			rentRequest.setRequestedUserId(((UserPrincipal) auth.getPrincipal()).getId());
		}

//		UserPrincipal user = (UserPrincipal) auth.getPrincipal();
//		Optional<User> loggedInUser = userRepository.findByEmail(user.getUsername());
//		if (loggedInUser.isPresent()) {
//			rentRequest.setRequestedUser(loggedInUser.get());
//		}

		Ad ad = this.adService.findById(rentRequestDTO.getAd().getId());
		List<Ad> list = this.adService.findAll();

		for (Ad a : list) {
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

		if (rentList.size() == 0) {
			rentRepository.save(rentRequest);
			return true;
		}

		for (RentRequest r : rentList) {
			if (r.getFromDate().after(rentRequest.getFromDate()) && r.getToDate().before(rentRequest.getToDate())) {
				if (r.getStatus() == RequestStatus.PENDING) {
					r.setStatus(RequestStatus.CANCELED);
					this.rentRepository.saveAndFlush(r);
				} else {
					return false;
				}
			}
			if (r.getFromDate().after(rentRequest.getFromDate()) && r.getToDate().after(rentRequest.getToDate())
					&& r.getFromDate().before(rentRequest.getToDate())) {
				if (r.getStatus() == RequestStatus.PENDING) {
					r.setStatus(RequestStatus.CANCELED);
					this.rentRepository.saveAndFlush(r);
				} else {
					return false;
				}
			}

			if (r.getFromDate().before(rentRequest.getFromDate()) && r.getToDate().before(rentRequest.getToDate())
					&& r.getToDate().after(rentRequest.getFromDate())) {
				if (r.getStatus() == RequestStatus.PENDING) {
					r.setStatus(RequestStatus.CANCELED);
					this.rentRepository.saveAndFlush(r);
				} else {
					return false;
				}
			}

			if (r.getFromDate().before(rentRequest.getFromDate()) && r.getToDate().after(rentRequest.getToDate())) {
				if (r.getStatus() == RequestStatus.PENDING) {
					r.setStatus(RequestStatus.CANCELED);
					this.rentRepository.saveAndFlush(r);
				} else {
					return false;
				}
			}

			if (rentRequest.getFromDate().equals(r.getFromDate()) || rentRequest.getToDate().equals(r.getToDate())) {
				if (r.getStatus() == RequestStatus.PENDING) {
					r.setStatus(RequestStatus.CANCELED);
					this.rentRepository.saveAndFlush(r);
				} else {
					return false;
				}
			}
		}
		rentRepository.save(rentRequest);
		return true;
	}

	public List<RentRequest> findByRequestedUser(Long userId) {
		return this.rentRepository.findByRequestedUserId(userId);
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
		return this.rentRepository.save(rentRequest);
	}

	public RentRequest decline(RentRequest entity) {
		RentRequest rentRequest = findByLocalId(entity.getLocalId());
		rentRequest.setStatus(RequestStatus.CANCELED);
		return this.rentRepository.save(rentRequest);
	}

	public RentRequest accept(Long id) {
		RentRequest rentRequest = findById(id);
		rentRequest.setStatus(RequestStatus.RESERVED);
		return this.rentRepository.save(rentRequest);
	}

	public RentRequest accept(RentRequest entity) {
		RentRequest rentRequest = findByLocalId(entity.getLocalId());
		rentRequest.setStatus(RequestStatus.RESERVED);
		return this.rentRepository.save(rentRequest);
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

	public RentRequest updateLocalId(Long requestId, Long localId) {

		RentRequest forUpdate = findById(requestId);
		forUpdate.setLocalId(localId);

		return this.rentRepository.save(forUpdate);

	}

}
