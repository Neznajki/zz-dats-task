package com.zz.dats.kindergarten.db.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "kindergarten", schema = "kindergarten")
public class KindergartenEntity {
    private int id;
    private String name;
    private String address;
    private Integer maxKids;

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
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "max_kids")
    public Integer getMaxKids() {
        return maxKids;
    }

    public void setMaxKids(Integer maxKids) {
        this.maxKids = maxKids;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KindergartenEntity that = (KindergartenEntity) o;
        return id == that.id &&
            Objects.equals(name, that.name) &&
            Objects.equals(address, that.address) &&
            Objects.equals(maxKids, that.maxKids);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, maxKids);
    }
}
