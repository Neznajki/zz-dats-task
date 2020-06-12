package com.zz.dats.kindergarten.db.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "family_name", schema = "kindergarten")
public class FamilyNameEntity {
    private int id;
    private String name;
    private Collection<FamilyKidsEntity> familyKidsById;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FamilyNameEntity that = (FamilyNameEntity) o;
        return id == that.id &&
            Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "familyNamesByFamilyId")
    public Collection<FamilyKidsEntity> getFamilyKidsById() {
        return familyKidsById;
    }

    public void setFamilyKidsById(Collection<FamilyKidsEntity> familyKidsById) {
        this.familyKidsById = familyKidsById;
    }

    @Override
    public String toString()
    {
        return String.format("%s (%d)", this.name, this.id);
    }
}
