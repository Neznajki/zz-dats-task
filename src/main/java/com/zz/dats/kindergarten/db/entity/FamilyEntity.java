package com.zz.dats.kindergarten.db.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "family", schema = "kindergarten")
public class FamilyEntity {
    private int id;
    private KidEntity kidByKidId;
    private KidEntity kidBySecondKidId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FamilyEntity that = (FamilyEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "kid_id", referencedColumnName = "id")
    public KidEntity getKidByKidId() {
        return kidByKidId;
    }

    public void setKidByKidId(KidEntity kidByKidId) {
        this.kidByKidId = kidByKidId;
    }

    @ManyToOne
    @JoinColumn(name = "second_kid_id", referencedColumnName = "id")
    public KidEntity getKidBySecondKidId() {
        return kidBySecondKidId;
    }

    public void setKidBySecondKidId(KidEntity kidBySecondKidId) {
        this.kidBySecondKidId = kidBySecondKidId;
    }
}
