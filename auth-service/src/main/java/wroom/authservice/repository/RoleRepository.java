package wroom.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import wroom.authservice.domain.Role;
import wroom.authservice.domain.RoleName;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(RoleName name);
	
	boolean existsByName(RoleName name);
	
}
