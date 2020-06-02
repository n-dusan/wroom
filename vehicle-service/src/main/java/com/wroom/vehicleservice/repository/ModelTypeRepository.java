package com.wroom.vehicleservice.repository;

import com.wroom.vehicleservice.domain.ModelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelTypeRepository extends JpaRepository<ModelType, Long> {

    ModelType findByName(String name);
}

