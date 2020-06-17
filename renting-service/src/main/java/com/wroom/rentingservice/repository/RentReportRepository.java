package com.wroom.rentingservice.repository;

import com.wroom.rentingservice.domain.RentReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentReportRepository extends JpaRepository<RentReport, Long>  {
}
