package xwsagent.wroomagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xwsagent.wroomagent.domain.BrandType;

@Repository
public interface BrandTypeRepository extends JpaRepository<BrandType, Long>{
	
	BrandType findByName(String name);
}
