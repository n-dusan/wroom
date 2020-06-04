package com.wroom.searchservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wroom.searchservice.domain.BrandType;

@Repository
public interface BrandTypeRepository extends JpaRepository<BrandType, Long>{
	
	BrandType findByName(String name);
}
