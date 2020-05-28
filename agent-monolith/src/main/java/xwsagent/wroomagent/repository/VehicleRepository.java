package xwsagent.wroomagent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import xwsagent.wroomagent.domain.Image;
import xwsagent.wroomagent.domain.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

	@Query(value = "SELECT i FROM Image i WHERE (i.vehicle = :vehicle)")
	List<Image> findByVehicle(@Param("vehicle") Vehicle vehicle);
}
