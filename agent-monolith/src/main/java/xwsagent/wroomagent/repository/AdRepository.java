package xwsagent.wroomagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xwsagent.wroomagent.domain.Ad;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    @Query(value = "select a.* from ad a inner join vehicle v on a.vehicle_id = v.id where v.owner_id = ?1 and a.deleted=false", nativeQuery=true)
    List<Ad> findAllActiveUser(Long userId);
}
