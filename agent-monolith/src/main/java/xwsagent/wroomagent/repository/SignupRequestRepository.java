package xwsagent.wroomagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xwsagent.wroomagent.domain.SignupRequest;

@Repository
public interface SignupRequestRepository extends JpaRepository<SignupRequest, Long> {

}
