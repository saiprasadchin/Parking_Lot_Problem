package com.bridgelabz.parkinglot.model;

import com.bridgelabz.parkinglot.enums.VehicleColour;
import com.bridgelabz.parkinglot.enums.VehicleCompany;

import java.util.Objects;

public class Vehicle {

    private final String vehicleNumber;
    private final VehicleCompany company;
    private final VehicleColour vehicleColour;

    public Vehicle(String vehicleNumber, VehicleCompany company, VehicleColour vehicleColour) {
        this.vehicleNumber = vehicleNumber;
        this.company = company;
        this.vehicleColour = vehicleColour;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public VehicleCompany getCompany() {
        return company;
    }

    public VehicleColour getVehicleColour() {
        return vehicleColour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(vehicleNumber, vehicle.vehicleNumber) &&
                company == vehicle.company &&
                vehicleColour == vehicle.vehicleColour;
    }
}
