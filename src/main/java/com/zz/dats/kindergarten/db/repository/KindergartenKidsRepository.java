package com.zz.dats.kindergarten.db.repository;

import com.zz.dats.kindergarten.db.entity.KindergartenKidsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface KindergartenKidsRepository extends JpaRepository<KindergartenKidsEntity, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO kindergarten_kids (kindergarten_id, kid_id) SELECT kindergarten_id, kid_id FROM `queue` WHERE kindergarten_id = :kindergartenId", nativeQuery = true)
    void addKidsFromQueue(
        @Param("kindergartenId") Integer kindergartenId
    );


//    @Modifying
//    @Transactional
//    @Query(value="DELETE FROM `queue` WHERE kindergarten_id = :kindergartenId AND kid_id = :kidId", nativeQuery = true)
//    void deleteByKindergartenAndKid(
//        @Param("kindergartenId") Integer kindergartenId,
//        @Param("kidId") Integer kidId
//    );
}
