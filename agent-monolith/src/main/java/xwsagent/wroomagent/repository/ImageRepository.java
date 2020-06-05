package xwsagent.wroomagent.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xwsagent.wroomagent.domain.Image;
import xwsagent.wroomagent.domain.Vehicle;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

	public List<Image> findByVehicle(Vehicle vehicle);

}
