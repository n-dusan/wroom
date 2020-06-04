package com.wroom.searchservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wroom.searchservice.domain.BodyType;

@Repository
public interface BodyTypeRepository extends JpaRepository<BodyType, Long>{
	
	BodyType findByName(String name);
}
