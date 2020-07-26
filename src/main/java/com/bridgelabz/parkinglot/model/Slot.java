package com.bridgelabz.parkinglot.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Slot {
    private ParkingVehicleDetails vehicle;
    private LocalDateTime parkingTime;
    Integer slotNumber ;

    public Slot(ParkingVehicleDetails vehicle, LocalDateTime parkingTime, Integer slotNumber) {
        this.vehicle = vehicle;
        this.parkingTime = parkingTime;
        this.slotNumber = slotNumber;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public ParkingVehicleDetails getVehicle() {
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
