package com.bridgelabz.service;

import com.bridgelabz.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotService {

    public int parkingCapacity = 100;
    public boolean isCapicityFull = false;
    public List<Vehicle> parkedVehicles = new ArrayList<Vehicle>();

    public boolean parkVehicle(Vehicle vehicle) {
        if(vehicle == null)
            return false;
        if(isCapicityFull){
           return false;
        }
        if(parkedVehicles.contains(vehicle)){
            return false;
        }
        parkedVehicles.add(vehicle);
        if(parkedVehicles.size() == parkingCapacity)
            isCapicityFull = true;
        return true;
    }

    public boolean unParkVehicle(Vehicle vehicle) {
        if(vehicle == null)
            return false;
        return true;
    }
}
