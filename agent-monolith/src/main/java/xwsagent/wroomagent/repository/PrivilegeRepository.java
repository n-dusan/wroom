package xwsagent.wroomagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xwsagent.wroomagent.domain.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

}
