package com.wroom.rentingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wroom.rentingservice.domain.BundledRequests;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BundleRepository extends JpaRepository<BundledRequests, Long> {

    @Query(value = "select distinct b.* from rent_request rr " +
            "inner join bundled_requests b on rr.bundle_id = b.id " +
            "inner join ad a on rr.ad_id = a.id " +
            "inner join vehicle v on a.vehicle_id = v.id where v.owner_username = ?1", nativeQuery=true)
    List<BundledRequests> findBundledRequestsByVehicleOwnerEmail(String email);
}
