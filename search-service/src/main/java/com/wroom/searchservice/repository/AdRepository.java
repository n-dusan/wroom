package com.wroom.searchservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wroom.searchservice.domain.Ad;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    @Query(value = "select a.* from ad a inner join vehicle v on a.vehicle_id = v.id where v.owner_id=:id and a.deleted=false", nativeQuery=true)
    List<Ad> findAllActiveUser(@Param("id") Long userId);

    @Query(value="select count(*) from ad a inner join vehicle v on a.vehicle_id = v.id where v.owner_id=:id and a.deleted=false", nativeQuery=true)
    Integer checkAdCountForUser(@Param("id") Long userId);

}
