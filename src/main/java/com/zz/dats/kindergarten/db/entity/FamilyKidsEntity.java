package com.zz.dats.kindergarten.db.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "family_kids", schema = "kindergarten")
public class FamilyKidsEntity {
    private int id;
    private KidEntity kidByKidId;
    private FamilyNameEntity familyNamesByFamilyId;

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
        FamilyKidsEntity that = (FamilyKidsEntity) o;
        return id == that.id &&
            Objects.equals(kidByKidId.getId(), that.kidByKidId.getId()) &&
            Objects.equals(familyNamesByFamilyId.getId(), that.familyNamesByFamilyId.getId());
    }

    @OneToOne
    @JoinColumn(name = "kid_id", referencedColumnName = "id")
    public KidEntity getKidByKidId() {
        return kidByKidId;
    }

    public void setKidByKidId(KidEntity kidByKidId) {
        this.kidByKidId = kidByKidId;
    }

    @ManyToOne
    @JoinColumn(name = "family_id", referencedColumnName = "id")
    public FamilyNameEntity getFamilyNamesByFamilyId() {
        return familyNamesByFamilyId;
    }

    public void setFamilyNamesByFamilyId(FamilyNameEntity familyNamesByFamilyId) {
        this.familyNamesByFamilyId = familyNamesByFamilyId;
    }
}
