package xwsagent.wroomagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xwsagent.wroomagent.domain.FuelType;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Long>{
	
	FuelType findByName(String name);
}
