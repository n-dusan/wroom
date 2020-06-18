package xwsagent.wroomagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xwsagent.wroomagent.domain.RentReport;

public interface RentReportRepository extends JpaRepository<RentReport, Long> {
}
