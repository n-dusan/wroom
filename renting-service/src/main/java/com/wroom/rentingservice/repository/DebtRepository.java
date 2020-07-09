package com.wroom.rentingservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wroom.rentingservice.domain.Debt;


public interface DebtRepository extends JpaRepository<Debt, Long>{

	List<Debt> findByUserId(Long id);
	
}
