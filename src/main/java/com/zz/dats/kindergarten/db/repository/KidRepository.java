package com.zz.dats.kindergarten.db.repository;

import com.zz.dats.kindergarten.db.entity.KidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface KidRepository extends JpaRepository<KidEntity, Integer> {
    @Query(
        value="SELECT DISTINCT k.* FROM kid as k LEFT JOIN queue q on k.id = q.kid_id LEFT JOIN kindergarten_kids kk on k.id = kk.kid_id " +
            "WHERE q.kid_id NOT IN (SELECT kid_id FROM queue WHERE kindergarten_id = ?1) AND kk.id IS NULL", nativeQuery=true
//        "FROM KidEntity AS k LEFT JOIN k.kindergartenKidsById AS kk LEFT JOIN k.queuesById as q WHERE q.kidByKidId.id NOT IN (SELECT kidByKidId.id FROM QueueEntity WHERE q.kindergartenByKindergartenId.id = ?1 ) AND kk.id IS NULL"
    )
    Collection<KidEntity> getKidsAvailableFor(Integer kindergartenId);
}
