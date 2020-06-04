package com.wroom.searchservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wroom.searchservice.domain.GearboxType;

@Repository
public interface GearboxTypeRepository extends JpaRepository<GearboxType, Long> {

	GearboxType findByName(String name);
}
