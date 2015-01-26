package com.parking.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@javax.persistence.Entity
public class Parking implements com.parking.entity.Entity {

    @NotNull
    Date parkingDate;

    @OneToOne
    Account pickedBy;

    @OneToOne

    Vehicle vehicle;

    Date whenPicked;

    Long latitude;

    Long longitude;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Account account;

    @Enumerated(EnumType.STRING)
    private ParkingStatusEnum status;

    public Parking() {
    }

    public Date getParkingDate() {
        return parkingDate;
    }

    public void setParkingDate(Date parkingDate) {
        this.parkingDate = parkingDate;
    }

    public Account getPickedBy() {
        return pickedBy;
    }

    public void setPickedBy(Account pickedBy) {
        this.pickedBy = pickedBy;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Date getWhenPicked() {
        return whenPicked;
    }

    public void setWhenPicked(Date whenPicked) {
        this.whenPicked = whenPicked;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ParkingStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ParkingStatusEnum status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parking)) return false;

        Parking parking = (Parking) o;

        if (!account.equals(parking.account)) return false;
        if (!id.equals(parking.id)) return false;
        if (!latitude.equals(parking.latitude)) return false;
        if (!longitude.equals(parking.longitude)) return false;
        if (!parkingDate.equals(parking.parkingDate)) return false;
        if (!pickedBy.equals(parking.pickedBy)) return false;
        if (status != parking.status) return false;
        if (!vehicle.equals(parking.vehicle)) return false;
        if (!whenPicked.equals(parking.whenPicked)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = parkingDate.hashCode();
        result = 31 * result + pickedBy.hashCode();
        result = 31 * result + vehicle.hashCode();
        result = 31 * result + whenPicked.hashCode();
        result = 31 * result + latitude.hashCode();
        result = 31 * result + longitude.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + account.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Parking{" +
                "parkingDate=" + parkingDate +
                ", pickedBy=" + pickedBy +
                ", vehicle=" + vehicle +
                ", whenPicked=" + whenPicked +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", id=" + id +
                ", account=" + account +
                ", status=" + status +
                '}';
    }
}
