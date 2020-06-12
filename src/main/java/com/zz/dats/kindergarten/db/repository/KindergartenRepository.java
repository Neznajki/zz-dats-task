package com.zz.dats.kindergarten.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.zz.dats.kindergarten.db.entity.KindergartenEntity;

public interface KindergartenRepository extends JpaRepository<KindergartenEntity, Integer> {
}
