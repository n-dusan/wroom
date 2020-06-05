package com.wroom.searchservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wroom.searchservice.domain.BundledRequests;

public interface BundleRepository extends JpaRepository<BundledRequests, Long> {

}
