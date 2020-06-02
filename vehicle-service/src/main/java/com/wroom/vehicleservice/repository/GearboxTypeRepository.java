package com.wroom.vehicleservice.repository;

import com.wroom.vehicleservice.domain.GearboxType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GearboxTypeRepository extends JpaRepository<GearboxType, Long> {

    GearboxType findByName(String name);
}