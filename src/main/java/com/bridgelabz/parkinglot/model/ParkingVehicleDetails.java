package com.bridgelabz.parkinglot.model;


import com.bridgelabz.parkinglot.enums.DriverType;
import com.bridgelabz.parkinglot.enums.VehicleColour;
import com.bridgelabz.parkinglot.enums.VehicleSize;

import java.util.Objects;

public class ParkingVehicleDetails {

    private Vehicle vehicle;
    private VehicleSize vehicleSize;
    private DriverType driverType;
    private String attendantName;

    public ParkingVehicleDetails(Vehicle vehicle, VehicleSize vehicleSize, DriverType driverType, String attendantName) {
        this.vehicle = vehicle;
        this.vehicleSize = vehicleSize;
        this.driverType = driverType;
        this.attendantName = attendantName;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public VehicleSize getVehicleSize() {
        return vehicleSize;
    }

    public DriverType getDriverType() {
        return driverType;
    }

    public String getAttendantName() {
        return attendantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingVehicleDetails that = (ParkingVehicleDetails) o;
        return Objects.equals(vehicle, that.vehicle) &&
                vehicleSize == that.vehicleSize &&
                driverType == that.driverType &&
                Objects.equals(attendantName, that.attendantName);
    }
}
