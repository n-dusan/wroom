package com.wroom.rentingservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wroom.rentingservice.domain.Ad;
import com.wroom.rentingservice.domain.RentRequest;

public interface RentRequestRepository extends JpaRepository<RentRequest, Long> {

	List<RentRequest> findByAd(Ad ad);
	
	List<RentRequest> findByRequestedUserId(Long requestedUserId);

	@Query(value = "select rr.* from rent_request rr where rr.requested_user_id = ?1 and rr.ad_id = ?2 " +
			"and exists " +
			"(select rr2.* from rent_request rr2 where (rr2.status = 'RESERVED' or rr2.status = 'PHYSICALLY_RESERVED') " +
			"and rr2.from_date > ?3 and rr2.to_date <= ?4 and rr.id = rr2.id)", nativeQuery=true)
	Integer findValidPendingRequests(Long userId, Long adId, Date fromDate, Date toDate);

	@Query(value="select r.* from rent_request r where r.local_id=:id and r.owner_username=:username", nativeQuery=true)
    RentRequest findByLocalId(Long id, String username);
	
	@Query(value = "SELECT r FROM RentRequest r WHERE (r.rentReport.id = :id)")
	RentRequest findByReportId(@Param("id") Long id);
	
	@Query(value = "SELECT r FROM RentRequest r WHERE (r.ad.id = :id)")
	Ad findByAdId(@Param("id") Long id);
	
//	@Query(value = "", nativeQuery=true)
//	List<RentRequest> findReservedRequests();
}
