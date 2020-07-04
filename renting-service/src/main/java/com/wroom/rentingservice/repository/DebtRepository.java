package com.wroom.rentingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wroom.rentingservice.domain.Debt;


public interface DebtRepository extends JpaRepository<Debt, Long>{

}
