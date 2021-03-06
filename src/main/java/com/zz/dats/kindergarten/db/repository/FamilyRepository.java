package com.zz.dats.kindergarten.db.repository;

import com.zz.dats.kindergarten.db.entity.FamilyNameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyRepository extends JpaRepository<FamilyNameEntity, Integer> {
}
