package xwsagent.wroomagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xwsagent.wroomagent.domain.BundledRequests;

public interface BundleRepository extends JpaRepository<BundledRequests, Long> {

}
