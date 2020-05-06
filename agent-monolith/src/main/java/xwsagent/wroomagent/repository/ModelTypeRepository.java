package xwsagent.wroomagent.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xwsagent.wroomagent.domain.ModelType;

@Repository
public interface ModelTypeRepository extends JpaRepository<ModelType, Long> {

	ModelType findByName(String name);
}
