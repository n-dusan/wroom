package xwsagent.wroomagent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.RentRequest;

public interface RentRequestRepository extends JpaRepository<RentRequest, Long> {

	@Query("select distinct r from RentRequest r join r.ads a where a = :ad")
	List<RentRequest> findByAd(Ad ad);
}
