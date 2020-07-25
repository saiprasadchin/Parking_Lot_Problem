package com.bridgelabz.parkinglot.model;


import java.util.Objects;

public class ParkingVehicleDetails {

   private Object vehicle;
   private VehicleSize vehicleSize;
   private DriverType driverType;

    public ParkingVehicleDetails(Object vehicle, VehicleSize vehicleSize, DriverType driverType) {
        this.vehicle = vehicle;
        this.vehicleSize = vehicleSize;
        this.driverType = driverType;
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingVehicleDetails vehicle1 = (ParkingVehicleDetails) o;
        return vehicle.equals(vehicle1.vehicle) &&
                vehicleSize == vehicle1.vehicleSize &&
                driverType == vehicle1.driverType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicle, vehicleSize, driverType);
    }
}
