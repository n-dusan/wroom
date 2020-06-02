package com.wroom.vehicleservice.repository;

import com.wroom.vehicleservice.domain.Image;
import com.wroom.vehicleservice.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    public List<Image> findByVehicle(Vehicle vehicle);

}

