package xwsagent.wroomagent.repository.rbac;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import xwsagent.wroomagent.domain.auth.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@EntityGraph(value = "User.Roles.Permissions")
	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String username);
	
	@Query("select u from User u where u.id = ?#{principal.id}")
	User findCurrentUser();
	
	@Query("select u from User u where u.id != ?#{principal.id}")
	List<User> findUsersExceptSelf();
	
	@Query(value = "select u.* from users u where u.enabled = 1", nativeQuery=true)
	List<User> findAllEnabled();
	
}
