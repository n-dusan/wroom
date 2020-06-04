package com.wroom.searchservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wroom.searchservice.domain.PriceList;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {

    @Query(value="select p.* from price_list p where p.deleted = false", nativeQuery=true)
    List<PriceList> findAllActive();

    PriceList findOneById(Long id);
}
