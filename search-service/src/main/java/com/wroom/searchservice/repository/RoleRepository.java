package com.wroom.searchservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wroom.searchservice.domain.Role;
import com.wroom.searchservice.domain.RoleName;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(RoleName name);
	
	boolean existsByName(RoleName name);
	
}
