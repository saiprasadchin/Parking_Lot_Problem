package com.bridgelabz.model;

import java.time.LocalTime;
import java.util.Objects;

public class SlotDetails {
    private Vehicle vehicle;
    private LocalTime parkingTime;

    public SlotDetails(Vehicle vehicle, LocalTime parkingTime) {
        this.vehicle = vehicle;
        this.parkingTime = parkingTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalTime getParkingTime() {
        return parkingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlotDetails that = (SlotDetails) o;
        return Objects.equals(vehicle, that.vehicle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicle);
    }
}
