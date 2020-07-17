package com.bridgelabz.service;

import com.bridgelabz.model.Vehicle;

public class ParkingLotService {

    public boolean parkVehicle(Vehicle vehicle) {
        if(vehicle == null)
            return false;
        return true;
    }

    public boolean unParkVehicle(Vehicle vehicle) {
        if(vehicle == null)
            return false;
        return true;
    }
}
