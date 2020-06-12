package com.zz.dats.kindergarten.db.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "kindergarten_kids", schema = "kindergarten")
public class KindergartenKidsEntity {
    private int id;
    private Timestamp joined;
    private KidEntity kidByKidId;
    private KindergartenEntity kindergartenByKindergartenId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "joined")
    public Timestamp getJoined() {
        return joined;
    }

    public void setJoined(Timestamp joined) {
        this.joined = joined;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KindergartenKidsEntity that = (KindergartenKidsEntity) o;
        return id == that.id &&
            Objects.equals(joined, that.joined);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, joined);
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
    @JoinColumn(name = "kindergarten_id", referencedColumnName = "id")
    public KindergartenEntity getKindergartenByKindergartenId() {
        return kindergartenByKindergartenId;
    }

    public void setKindergartenByKindergartenId(KindergartenEntity kindergartenByKindergartenId) {
        this.kindergartenByKindergartenId = kindergartenByKindergartenId;
    }
}
