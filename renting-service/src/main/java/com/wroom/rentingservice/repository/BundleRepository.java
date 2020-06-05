package com.wroom.rentingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wroom.rentingservice.domain.BundledRequests;

public interface BundleRepository extends JpaRepository<BundledRequests, Long> {

}
