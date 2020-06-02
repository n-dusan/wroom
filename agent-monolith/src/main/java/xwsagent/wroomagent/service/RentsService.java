package xwsagent.wroomagent.service;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.converter.AdConverter;
import xwsagent.wroomagent.converter.RentConverter;
import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.ModelType;
import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.domain.auth.User;
import xwsagent.wroomagent.domain.dto.AdDTO;
import xwsagent.wroomagent.domain.dto.RentRequestDTO;
import xwsagent.wroomagent.domain.enums.RequestStatus;
import xwsagent.wroomagent.jwt.UserPrincipal;
import xwsagent.wroomagent.repository.RentRequestRepository;
import xwsagent.wroomagent.repository.rbac.UserRepository;

@Service
public class RentsService {

	private final RentRequestRepository rentRepository;
	private final UserRepository userRepository;
	private final VehicleService vehicleService;
	private final AdService adService;

	public RentsService(RentRequestRepository rr, UserRepository u, VehicleService v, AdService a) {
		this.rentRepository = rr;
		this.userRepository = u;
		this.vehicleService = v;
		this.adService = a;
	}

	public List<RentRequest> findAll() {
		return this.rentRepository.findAll();
	}

	public List<RentRequest> findByAd(Ad ad) {
		return this.rentRepository.findByAd(ad);
	}

	public RentRequest sendRequest(RentRequestDTO dto, Long requestedUserID) {
		RentRequest entity = RentConverter.toEntity(dto);
		entity.setRequestedUser(this.userRepository.findById(requestedUserID).get());
		entity.setStatus(RequestStatus.PENDING);
		entity.setAd(AdConverter.toEntity(dto.getAd()));
		return this.rentRepository.save(entity);
	}

	public boolean occupy(RentRequestDTO rentRequestDTO, Authentication auth) {
		RentRequest rentRequest = RentConverter.toEntity(rentRequestDTO);
		rentRequest.setStatus(RequestStatus.PHYSICALLY_RESERVED);
		rentRequest.setFromDate(rentRequestDTO.getFromDate());
		rentRequest.setToDate(rentRequestDTO.getToDate());

		UserPrincipal user = (UserPrincipal) auth.getPrincipal();
		Optional<User> loggedInUser = userRepository.findByEmail(user.getUsername());
		if (loggedInUser.isPresent()) {
			rentRequest.setRequestedUser(loggedInUser.get());
		}

		Ad ad = this.adService.findById(rentRequestDTO.getAd().getId());
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
		for (RentRequest r : rentList) {
			if (r.getFromDate().after(rentRequest.getFromDate()) && r.getToDate().before(rentRequest.getToDate())) {
				return false;
			}
			if (r.getFromDate().after(rentRequest.getFromDate()) && r.getToDate().after(rentRequest.getToDate())
					&& r.getFromDate().before(rentRequest.getToDate())) {
				return false;
			}

			if (r.getFromDate().before(rentRequest.getFromDate()) && r.getToDate().before(rentRequest.getToDate())
					&& r.getToDate().after(rentRequest.getFromDate())) {
				return false;
			}

			if (r.getFromDate().before(rentRequest.getFromDate()) && r.getToDate().after(rentRequest.getToDate())) {
				return false;
			}

			if (rentRequest.getFromDate().equals(r.getFromDate()) || rentRequest.getToDate().equals(r.getToDate())) {
				return false;
			}
		}

		rentRepository.save(rentRequest);
		return true;
	}

}
