package com.wroom.vehicleservice.repository;

import com.wroom.vehicleservice.domain.BodyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyTypeRepository extends JpaRepository<BodyType, Long> {

    BodyType findOneByName(String name);
}
