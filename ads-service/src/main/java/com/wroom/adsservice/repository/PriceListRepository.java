package com.wroom.adsservice.repository;

import com.wroom.adsservice.domain.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {

    @Query(value="select p.* from price_list p where p.deleted = false", nativeQuery=true)
    List<PriceList> findAllActive();

    PriceList findOneById(Long id);

    @Query(value = "select p.* from price_list p where p.user_id=:id and p.deleted=false", nativeQuery=true)
    List<PriceList> findAllActiveUser(@Param("id") Long userId);

}
