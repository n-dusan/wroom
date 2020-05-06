package xwsagent.wroomagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xwsagent.wroomagent.domain.BodyType;

@Repository
public interface BodyTypeRepository extends JpaRepository<BodyType, Long>{
	
	BodyType findByName(String name);
}
