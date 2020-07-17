package com.bridgelabz.model;

import java.util.Objects;

public class Vehicle {

    private String vehicleType;
    private String vehicleOwner;
    private String vehicleNumber;

    public Vehicle(String vehicleType, String vehicleOwner, String vehicleNumber) {
        this.vehicleType = vehicleType;
        this.vehicleOwner = vehicleOwner;
        this.vehicleNumber = vehicleNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(vehicleType, vehicle.vehicleType) &&
                Objects.equals(vehicleOwner, vehicle.vehicleOwner) &&
                Objects.equals(vehicleNumber, vehicle.vehicleNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleType, vehicleOwner, vehicleNumber);
    }
}
