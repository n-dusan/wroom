package wroom.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import wroom.authservice.domain.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	public VerificationToken findByToken(String token);
	
}
