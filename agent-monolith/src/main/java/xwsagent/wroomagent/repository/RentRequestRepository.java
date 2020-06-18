package xwsagent.wroomagent.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.RentRequest;

public interface RentRequestRepository extends JpaRepository<RentRequest, Long> {

	List<RentRequest> findByAd(Ad ad);

	@Query(value = "select rr.* from rent_request rr where rr.requested_user_id = ?1", nativeQuery=true)
	List<RentRequest> findByRequestedUser(Long requestedUserId);

	@Query(value = "select rr.* from rent_request rr where rr.requested_user_id = ?1 and rr.ad_id = ?2 " +
			"and exists " +
			"(select rr2.* from rent_request rr2 where (rr2.status = 'RESERVED' or rr2.status = 'PHYSICALLY_RESERVED') " +
			"and rr2.from_date > ?3 and rr2.to_date <= ?4 and rr.id = rr2.id)", nativeQuery=true)
	Integer findValidPendingRequests(Long userId, Long adId, Date fromDate, Date toDate);

}
