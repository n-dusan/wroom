package xwsagent.wroomagent.repository.rbac;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xwsagent.wroomagent.domain.auth.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

	Permission findByName(String name);

	boolean existsByName(String name);

}
