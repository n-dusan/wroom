package com.wroom.adsservice.repository;

import com.wroom.adsservice.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findOneById(Long id);
}

