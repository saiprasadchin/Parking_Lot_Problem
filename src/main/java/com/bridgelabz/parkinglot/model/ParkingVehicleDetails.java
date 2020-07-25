package com.bridgelabz.parkinglot.model;


import com.bridgelabz.parkinglot.enums.DriverType;
import com.bridgelabz.parkinglot.enums.VehicleColour;
import com.bridgelabz.parkinglot.enums.VehicleSize;

import java.util.Objects;

public class ParkingVehicleDetails {

    private Object vehicle;
    private VehicleSize vehicleSize;
    private DriverType driverType;
    private VehicleColour vehicleColour;

    public ParkingVehicleDetails(Object vehicle, VehicleSize vehicleSize, DriverType driverType, VehicleColour vehicleColour) {
        this.vehicle = vehicle;
        this.vehicleSize = vehicleSize;
        this.driverType = driverType;
        this.vehicleColour = vehicleColour;
    }

    public Object getVehicle() {
        return vehicle;
    }

    public VehicleSize getVehicleSize() {
        return vehicleSize;
    }

    public DriverType getDriverType() {
        return driverType;
    }

    public VehicleColour getVehicleColour() {
        return vehicleColour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingVehicleDetails vehicle1 = (ParkingVehicleDetails) o;
        return vehicle.equals(vehicle1.vehicle) &&
                vehicleSize == vehicle1.vehicleSize &&
                driverType == vehicle1.driverType &&
                vehicleColour == vehicle1.vehicleColour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicle, vehicleSize, driverType, vehicleColour);
    }
}
