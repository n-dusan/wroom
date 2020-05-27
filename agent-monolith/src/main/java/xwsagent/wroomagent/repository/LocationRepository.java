package xwsagent.wroomagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xwsagent.wroomagent.domain.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findOneById(Long id);
}
