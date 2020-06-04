package com.wroom.searchservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wroom.searchservice.domain.ModelType;

@Repository
public interface ModelTypeRepository extends JpaRepository<ModelType, Long> {

	ModelType findByName(String name);
}
