package xwsagent.wroomagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xwsagent.wroomagent.domain.GearboxType;

@Repository
public interface GearboxTypeRepository extends JpaRepository<GearboxType, Long> {

	GearboxType findByName(String name);
}
