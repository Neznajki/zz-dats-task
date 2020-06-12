package com.zz.dats.kindergarten.db.repository;

import com.zz.dats.kindergarten.db.entity.KidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface KidRepository extends JpaRepository<KidEntity, Integer> {
    @Query("FROM KidEntity AS k LEFT JOIN k.kindergartenKidsById AS kk LEFT JOIN k.queuesById as q WHERE (q.id IS NULL OR q.kindergartenByKindergartenId.id <> ?1) AND kk.id IS NULL")    //This is using a named query method
    Collection<KidEntity> getKidsAvailableFor(Integer kindergartenId);
}
