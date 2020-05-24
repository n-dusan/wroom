package xwsagent.wroomagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xwsagent.wroomagent.domain.auth.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

}
