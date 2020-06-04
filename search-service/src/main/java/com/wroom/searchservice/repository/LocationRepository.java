package com.wroom.searchservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wroom.searchservice.domain.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findOneById(Long id);
}
