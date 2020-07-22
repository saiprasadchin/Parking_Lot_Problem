package com.bridgelabz.service;

import com.bridgelabz.exception.ParkingLotServiceException;
import com.bridgelabz.model.SlotDetails;
import com.bridgelabz.model.Vehicle;
import com.bridgelabz.observer.AirportSecurityService;
import com.bridgelabz.observer.IObserver;
import com.bridgelabz.observer.Owner;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class ParkingLotService {

    public int parkingCapacity;
    public int currentParkedStatus = 0;
    public HashMap<Integer, SlotDetails> parkedVehicles;
    public Map<String, IObserver> parkingLotListeners = new HashMap<>();
    public final static String OWNER = "OWNER";
    public final static String SECURITY = "SECURITY";

    public ParkingLotService(int parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
        parkingLotListeners.put(OWNER, new Owner());
        parkingLotListeners.put(SECURITY, new AirportSecurityService());
        this.parkedVehicles = new HashMap<>(100);
        this.initializeParkingLot();
    }

    public void initializeParkingLot() {
        IntStream.range(0, this.parkingCapacity).forEachOrdered(slots -> parkedVehicles.put(slots, null));
    }

    public boolean isPresent(Vehicle vehicle) {
        return parkedVehicles.containsValue(new SlotDetails(vehicle, null));
    }

    public void registerListeners(String capacityStatus) {
        parkingLotListeners.get(OWNER).update(capacityStatus);
        parkingLotListeners.get(SECURITY).update(capacityStatus);

    }

    public void parkVehicle(Vehicle vehicle) throws ParkingLotServiceException {
        if (vehicle == null)
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.INVALID_VEHICLE, "Invalid Vehicle");

        if (currentParkedStatus == parkingCapacity)
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.PARKING_LOT_IS_FULL, "Parking Full");

        if (isPresent(vehicle))
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.VEHICLE_ALREADY_PRESENT, "Already present");


        parkedVehicles.put(getAvailableSlot(), new SlotDetails(vehicle, LocalTime.now().withNano(0)));
        currentParkedStatus++;
        if (currentParkedStatus == parkingCapacity)
            registerListeners("Capacity is Full");

    }

    public void unParkVehicle(Vehicle vehicle) throws ParkingLotServiceException {
        int spot = 0;
        if (vehicle == null)
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.INVALID_VEHICLE, "Invalid Vehicle");

        if (!isPresent(vehicle))
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.NO_SUCH_A_VEHICLE, "No Vehicle Found");

        for (Map.Entry<Integer, SlotDetails> entry : parkedVehicles.entrySet()) {
            if (vehicle.equals(entry.getValue())) {
                spot = entry.getKey();
            }
        }
        parkedVehicles.put(spot, null);
        currentParkedStatus--;
        registerListeners("Capacity Available");
    }

    public int getAvailableSlot() {
        for (int slot = 0; slot < this.parkedVehicles.size(); slot++) {
            if (this.parkedVehicles.get(slot) == null)
                return slot;
        }
        return -1;
    }

    public void parkVehicle(int slot, Vehicle vehicle) throws ParkingLotServiceException {
        if (isPresent(vehicle))
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.VEHICLE_ALREADY_PRESENT, "Already present");
        this.parkedVehicles.put(slot, new SlotDetails(vehicle, LocalTime.now().withNano(0)));
    }

    public int findVehicle(Vehicle vehicle) throws ParkingLotServiceException {
        if (isPresent(vehicle)) {
            int spot = 0;
            for (Integer slots : this.parkedVehicles.keySet()) {
                if (this.parkedVehicles.get(slots) != null) {
                    if (vehicle.equals(this.parkedVehicles.get(slots).getVehicle())) {
                        spot = slots;
                    }
                }
            }
            return spot;
        }
        throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.NO_SUCH_A_VEHICLE, "No Vehicle Found");
    }
}
