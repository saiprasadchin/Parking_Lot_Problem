package com.bridgelabz.parkinglot.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Slot {
    private Vehicle vehicle;
    private LocalDateTime parkingTime;

    public Slot(Vehicle vehicle, LocalDateTime parkingTime) {
        this.vehicle = vehicle;
        this.parkingTime = parkingTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDateTime getParkingTime() {
        return parkingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot that = (Slot) o;
        return Objects.equals(vehicle, that.vehicle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicle);
    }
}
