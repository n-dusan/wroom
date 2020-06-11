package com.wroom.adsservice.repository;

import com.wroom.adsservice.domain.Ad;
import com.wroom.adsservice.domain.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    @Query(value = "select a.* from ad a where a.deleted=false", nativeQuery=true)
    List<Ad> findAllActive();

    @Query(value = "select a.* from ad a inner join vehicle v on a.vehicle_id = v.id where v.owner_id=:id and a.deleted=false", nativeQuery=true)
    List<Ad> findAllActiveUser(@Param("id") Long userId);

    @Query(value="select count(*) from ad a inner join vehicle v on a.vehicle_id = v.id where v.owner_id=:id and a.deleted=false", nativeQuery=true)
    Integer checkAdCountForUser(@Param("id") Long userId);

    @Query(value = "SELECT a FROM Ad a WHERE (a.vehicleId = :vehicle)")
    List<Ad> findByVehicleId(@Param("vehicle") Long vehicle);
    
    @Query(value = "select c.* from comment c where c.deleted=false", nativeQuery=true)
    List<Comment> findComments();
}

