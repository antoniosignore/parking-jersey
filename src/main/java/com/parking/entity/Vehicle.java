package com.parking.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@javax.persistence.Entity
public class Vehicle implements Entity {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @Size(min = 2, max = 14)
    private String licensePlate;

    @NotNull
    private Date created_at;

    @ManyToOne
    private Account owner;

    public Vehicle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        if (!id.equals(vehicle.id)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", owner=" + owner +
                '}';
    }
}
