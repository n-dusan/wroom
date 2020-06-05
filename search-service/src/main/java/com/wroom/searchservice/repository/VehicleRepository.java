package com.wroom.searchservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wroom.searchservice.domain.Ad;
import com.wroom.searchservice.domain.Image;
import com.wroom.searchservice.domain.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{


    @Query(value="select v.* from vehicle v where deleted=false and v.owner_id=:id", nativeQuery=true)
    List<Vehicle> findAllActiveForUser(@Param("id") Long ownerId);

	@Query(value = "SELECT i FROM Image i WHERE (i.vehicle = :vehicle)")
	List<Image> findByVehicle(@Param("vehicle") Vehicle vehicle);
	
	@Query(value = "SELECT a FROM Ad a WHERE (a.vehicle = :vehicle)")
	List<Ad> findByVehicleId(@Param("vehicle") Vehicle vehicle);

}
