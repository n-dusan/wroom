package com.wroom.searchservice.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wroom.searchservice.domain.Image;
import com.wroom.searchservice.domain.Vehicle;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

	public List<Image> findByVehicle(Vehicle vehicle);

}
