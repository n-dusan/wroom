package com.wroom.vehicleservice.repository;

import com.wroom.vehicleservice.domain.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {

    FuelType findByName(String name);
}

