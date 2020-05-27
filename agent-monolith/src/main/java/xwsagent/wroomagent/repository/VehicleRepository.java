package xwsagent.wroomagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import xwsagent.wroomagent.domain.Vehicle;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

    @Query(value="select v.* from vehicle v where deleted=false and v.owner_id=?1", nativeQuery=true)
    List<Vehicle> findAllActiveForUser(Long ownerId);

    Vehicle findOneById(Long id);
}
