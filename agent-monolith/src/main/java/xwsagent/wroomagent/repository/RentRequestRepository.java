package xwsagent.wroomagent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.RentRequest;

public interface RentRequestRepository extends JpaRepository<RentRequest, Long> {

	List<RentRequest> findByAd(Ad ad);
}
