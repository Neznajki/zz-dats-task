package com.zz.dats.kindergarten.db.repository;

import com.zz.dats.kindergarten.db.entity.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface QueueRepository extends JpaRepository<QueueEntity, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM QueueEntity qe WHERE qe.kindergartenByKindergartenId.id = :kindergartenId AND qe.kidByKidId.id = :kidId")
    void deleteByKindergartenAndKid(
        @Param("kindergartenId") Integer kindergartenId,
        @Param("kidId") Integer kidId
    );

    @Modifying
    @Transactional
    @Query(value ="DELETE q FROM queue as q, kindergarten_kids as kk WHERE kk.kindergarten_id = q.kindergarten_id AND kk.kid_id = q.kid_id AND q.kindergarten_id = :kindergartenId", nativeQuery = true)
    void removeAddedKids(@Param("kindergartenId") Integer kindergartenId);


//    @Modifying
//    @Transactional
//    @Query(value="DELETE FROM `queue` WHERE kindergarten_id = :kindergartenId AND kid_id = :kidId", nativeQuery = true)
//    void deleteByKindergartenAndKid(
//        @Param("kindergartenId") Integer kindergartenId,
//        @Param("kidId") Integer kidId
//    );
}
