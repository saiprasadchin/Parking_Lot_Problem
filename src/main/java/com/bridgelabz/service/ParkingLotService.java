package com.bridgelabz.service;

import com.bridgelabz.exception.ParkingLotServiceException;
import com.bridgelabz.model.Vehicle;
import com.bridgelabz.observer.AirportSecurityService;
import com.bridgelabz.observer.IObserver;
import com.bridgelabz.observer.Owner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotService {

    public int parkingCapacity = 100;
    public List<Vehicle> parkedVehicles = new ArrayList<Vehicle>();
    public Map<String, IObserver> parkingLotListeners = new HashMap<String, IObserver>();
    public final static String OWNER = "OWNER";
    public final static String SECURITY = "SECURITY";

    public ParkingLotService() {
        parkingLotListeners.put(OWNER, new Owner());
        parkingLotListeners.put(SECURITY, new AirportSecurityService());
    }

    public void registerListeners(String capacityStatus) {
        parkingLotListeners.get(OWNER).update(capacityStatus);
        parkingLotListeners.get(SECURITY).update(capacityStatus);

    }

    public void parkVehicle(Vehicle vehicle) throws ParkingLotServiceException {
        if (vehicle == null)
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.INVALID_VEHICLE, "Invalid Vehicle");

        if (parkedVehicles.size() == parkingCapacity)
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.PARKING_LOT_IS_FULL, "Parking Full");

        if (parkedVehicles.contains(vehicle))
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.VEHICLE_ALREADY_PRESENT, "Already present");

        parkedVehicles.add(vehicle);
        if (parkedVehicles.size() == parkingCapacity)
            registerListeners("Capacity is Full");

    }

    public void unParkVehicle(Vehicle vehicle) throws ParkingLotServiceException {
        if (vehicle == null)
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.INVALID_VEHICLE, "Invalid Vehicle");

        if (!parkedVehicles.contains(vehicle))
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.NO_SUCH_A_VEHICLE, "No Vehicle Found");

        parkedVehicles.remove(vehicle);
        registerListeners("Capacity Available");
    }

    public boolean isPresent(Vehicle vehicle) {
        return parkedVehicles.contains(vehicle);
    }
}
