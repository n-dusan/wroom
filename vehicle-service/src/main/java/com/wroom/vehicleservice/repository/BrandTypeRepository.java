package com.wroom.vehicleservice.repository;

import com.wroom.vehicleservice.domain.BrandType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandTypeRepository extends JpaRepository<BrandType, Long> {

    BrandType findByName(String name);
}

