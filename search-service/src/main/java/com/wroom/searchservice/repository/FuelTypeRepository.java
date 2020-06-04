package com.wroom.searchservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wroom.searchservice.domain.FuelType;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Long>{
	
	FuelType findByName(String name);
}
