package wroom.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import wroom.authservice.domain.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{

	public PasswordResetToken findByEmail(String email);
	public PasswordResetToken findByToken(String token);	
	
}
