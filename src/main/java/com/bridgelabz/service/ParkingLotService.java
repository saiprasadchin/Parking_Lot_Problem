package com.bridgelabz.service;

import com.bridgelabz.exception.ParkingLotServiceException;
import com.bridgelabz.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotService {

    public int parkingCapacity = 100;
    public boolean isCapicityFull = false;
    public List<Vehicle> parkedVehicles = new ArrayList<Vehicle>();
    public AirportSecurityService airportSecurityService = new AirportSecurityService();


    public boolean parkVehicle(Vehicle vehicle)throws ParkingLotServiceException {
        if(vehicle == null)
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.INVALID_VEHICLE, "Invalid Vehicle");
        if(isCapicityFull){
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.PARKING_LOT_IS_FULL, "Parking Full");
        }
        if(parkedVehicles.contains(vehicle)){
            return false;
        }
        parkedVehicles.add(vehicle);
        if(parkedVehicles.size() == parkingCapacity){
            isCapicityFull = true;
            airportSecurityService.setFull(isCapicityFull);
        }
        return true;
    }

    public boolean unParkVehicle(Vehicle vehicle)throws ParkingLotServiceException {
        if(vehicle == null)
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.INVALID_VEHICLE, "Invalid Vehicle");
        return true;
    }
}
