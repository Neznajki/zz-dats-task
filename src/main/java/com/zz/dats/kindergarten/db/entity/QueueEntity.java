package com.zz.dats.kindergarten.db.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "queue", schema = "kindergarten")
public class QueueEntity {
    private int id;
    private Timestamp added;
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
    @Column(name = "added")
    public Timestamp getAdded() {
        return added;
    }

    public void setAdded(Timestamp added) {
        this.added = added;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueueEntity that = (QueueEntity) o;
        return id == that.id &&
            Objects.equals(added, that.added);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, added);
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
