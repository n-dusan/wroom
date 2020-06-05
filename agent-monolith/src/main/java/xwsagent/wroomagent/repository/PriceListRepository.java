package xwsagent.wroomagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xwsagent.wroomagent.domain.PriceList;

import java.util.List;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {

    @Query(value="select p.* from price_list p where p.deleted = false", nativeQuery=true)
    List<PriceList> findAllActive();

    PriceList findOneById(Long id);
}
