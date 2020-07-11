package com.wroom.rentingservice.repository;

import com.wroom.rentingservice.domain.BundledRequests;
import com.wroom.rentingservice.domain.RentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentReportRepository extends JpaRepository<RentReport, Long>  {

    @Query(value = "select rp.* from rent_request rr inner join rent_report rp on rr.rent_report_id = rp.id inner join ad a on rr.ad_id = a.id where a.vehicle_id = ?1 and a.deleted=false", nativeQuery=true)
    List<RentReport> findAllByVehicle(Long vehicleId);


    @Query(value = "select distinct rp.* from rent_request rr " +
            "inner join rent_report rp on rr.rent_report_id = rp.id " +
            "inner join ad a on rr.ad_id = a.id " +
            "inner join vehicle v on a.vehicle_id = v.id where v.owner_username = ?1", nativeQuery=true)
    List<RentReport> findRentReportByVehicleOwnerEmail(String email);
}
