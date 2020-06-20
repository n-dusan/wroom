package xwsagent.wroomagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xwsagent.wroomagent.domain.Ad;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    @Query(value = "select a.* from ad a inner join vehicle v on a.vehicle_id = v.id where v.owner_id=:id and a.deleted=false", nativeQuery=true)
    List<Ad> findAllActiveUser(@Param("id") Long userId);

    @Query(value="select count(*) from ad a inner join vehicle v on a.vehicle_id = v.id where v.owner_id=:id and a.deleted=false", nativeQuery=true)
    Integer checkAdCountForUser(@Param("id") Long userId);

}
