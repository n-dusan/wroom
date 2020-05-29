package xwsagent.wroomagent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.Image;
import xwsagent.wroomagent.domain.Vehicle;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{


    @Query(value="select v.* from vehicle v where deleted=false and v.owner_id=?1", nativeQuery=true)
    List<Vehicle> findAllActiveForUser(Long ownerId);

	@Query(value = "SELECT i FROM Image i WHERE (i.vehicle = :vehicle)")
	List<Image> findByVehicle(@Param("vehicle") Vehicle vehicle);
	
	@Query(value = "SELECT a FROM Ad a WHERE (a.vehicle = :vehicle)")
	List<Ad> findByVehicleId(@Param("vehicle") Vehicle vehicle);

}
