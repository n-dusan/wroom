package xwsagent.wroomagent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.BundledRequests;
import xwsagent.wroomagent.domain.RentRequest;
import xwsagent.wroomagent.domain.enums.RequestStatus;
import xwsagent.wroomagent.exception.InvalidReferenceException;
import xwsagent.wroomagent.repository.BundleRepository;
import xwsagent.wroomagent.repository.RentRequestRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BundleService {

	//neophodan autowired ovde zbog circular bean dependencies
	@Autowired
	private BundleRepository bundleRepository;

	@Autowired
	private RentsService rentsService;

	@Autowired
	private AdService adService;

	@Autowired
	private RentRequestRepository rentRepository;


	public BundledRequests save(BundledRequests b) {
		return this.bundleRepository.save(b);
	}

	public BundledRequests findById(Long id) {
		return bundleRepository.findById(id).orElseThrow(
				() -> new InvalidReferenceException("Unable to find reference to " + id.toString() + " bundle"));
	}


	public List<RentRequest> findBundledRentRequests(Long bundleId) {
		BundledRequests bundledRequests = findById(bundleId);

		List<RentRequest> requestList  = new ArrayList<>(bundledRequests.getRequests());

		return requestList;
	}


	public List<RentRequest> decline(Long bundleId) {
		List<RentRequest> requestList = findBundledRentRequests(bundleId);

		for (RentRequest rentRequest : requestList) {
			this.rentsService.decline(rentRequest.getId());
		}

		return requestList;
	}


	public List<RentRequest> accept(Long bundleId) {
		List<RentRequest> requestList = findBundledRentRequests(bundleId);

		for (RentRequest rentRequest : requestList) {
			this.rentsService.accept(rentRequest.getId());
		}

		return requestList;
	}


	public List<RentRequest> pay(Long id) {

		List<RentRequest> requestList = findBundledRentRequests(id);

		for (RentRequest rentRequest : requestList) {
			Ad ad = adService.findById(rentRequest.getAd().getId());

			List<RentRequest> otherRequests = rentRepository.findByAd(ad);

			for(RentRequest r : otherRequests) {

				if(r.getFromDate().after(rentRequest.getFromDate()) && r.getToDate().before(rentRequest.getToDate())) {
					if(r.getStatus() == RequestStatus.PENDING && r.getId() != rentRequest.getId()) {
						r.setStatus(RequestStatus.CANCELED);
						this.rentsService.clearBundles(r);
						this.rentRepository.saveAndFlush(r);

					}
				}

				if(r.getFromDate().after(rentRequest.getFromDate()) && r.getToDate().after(rentRequest.getToDate()) && r.getFromDate().before(rentRequest.getToDate())) {
					if(r.getStatus() == RequestStatus.PENDING && r.getId() != rentRequest.getId()) {
						r.setStatus(RequestStatus.CANCELED);
						this.rentsService.clearBundles(r);
						this.rentRepository.saveAndFlush(r);

					}
				}

				if(r.getFromDate().before(rentRequest.getFromDate()) && r.getToDate().before(rentRequest.getToDate()) && r.getToDate().after(rentRequest.getFromDate())) {
					if(r.getStatus() == RequestStatus.PENDING && r.getId() != rentRequest.getId()) {
						r.setStatus(RequestStatus.CANCELED);
						this.rentsService.clearBundles(r);
						this.rentRepository.saveAndFlush(r);

					}
				}

				if(r.getFromDate().before(rentRequest.getFromDate()) && r.getToDate().after(rentRequest.getToDate()) ) {
					if(r.getStatus() == RequestStatus.PENDING && r.getId() != rentRequest.getId()) {
						r.setStatus(RequestStatus.CANCELED);
						this.rentsService.clearBundles(r);
						this.rentRepository.saveAndFlush(r);

					}
				}

				if(rentRequest.getFromDate().equals(r.getFromDate()) || rentRequest.getToDate().equals(r.getToDate())) {
					if(r.getStatus() == RequestStatus.PENDING && r.getId() != rentRequest.getId()) {
						r.setStatus(RequestStatus.CANCELED);
						this.rentsService.clearBundles(r);
						this.rentRepository.saveAndFlush(r);

					}
				}
			}

			rentRequest.setStatus(RequestStatus.PAID);
			rentRepository.save(rentRequest);
		}

		return requestList;
	}
}
