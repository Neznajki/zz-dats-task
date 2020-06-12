package com.zz.dats.kindergarten.db.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "kid", schema = "kindergarten")
public class KidEntity {
    private int id;
    private String name;
    private String lastName;
    private String personalCode;
    private FamilyKidsEntity familyEntity;
    private Collection<KindergartenKidsEntity> kindergartenKidsById;
    private Collection<QueueEntity> queuesById;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "personal_code")
    public String getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(String personalCode) {
        this.personalCode = personalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KidEntity kidEntity = (KidEntity) o;
        return id == kidEntity.id &&
            Objects.equals(name, kidEntity.name) &&
            Objects.equals(lastName, kidEntity.lastName) &&
            Objects.equals(personalCode, kidEntity.personalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, personalCode);
    }

    @OneToOne(mappedBy = "kidByKidId")
    public FamilyKidsEntity getFamilyEntity() {
        return familyEntity;
    }

    public void setFamilyEntity(FamilyKidsEntity familyKidsEntity) {
        this.familyEntity = familyKidsEntity;
    }

    @OneToMany(mappedBy = "kidByKidId")
    public Collection<KindergartenKidsEntity> getKindergartenKidsById() {
        return kindergartenKidsById;
    }

    public void setKindergartenKidsById(Collection<KindergartenKidsEntity> kindergartenKidsById) {
        this.kindergartenKidsById = kindergartenKidsById;
    }

    @OneToMany(mappedBy = "kidByKidId")
    public Collection<QueueEntity> getQueuesById() {
        return queuesById;
    }

    public void setQueuesById(Collection<QueueEntity> queuesById) {
        this.queuesById = queuesById;
    }

    @Override
    public String toString()
    {
        String result = String.format("%s %s (%s)", this.name, this.lastName, this.personalCode);

        if (this.familyEntity != null) {
            result = String.format("%s@%s", result, this.familyEntity.getFamilyNamesByFamilyId().toString());
        }

        return result;
    }
}
