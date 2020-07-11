package xwsagent.wroomagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import xwsagent.wroomagent.domain.Ad;
import xwsagent.wroomagent.domain.RentReport;
import xwsagent.wroomagent.domain.Vehicle;

import java.util.List;

public interface RentReportRepository extends JpaRepository<RentReport, Long> {

    @Query(value = "select rp.* from rent_request rr inner join rent_report rp on rr.rent_report_id = rp.id inner join ad a on rr.ad_id = a.id where a.vehicle_id = ?1 and a.deleted=false", nativeQuery=true)
    List<RentReport> findAllByVehicle(Long vehicleId);
    
    @Query(value = "SELECT r FROM RentReport r WHERE (r.user.id = :id)")
	List<RentReport> findByUserId(@Param("id") Long id);
}
