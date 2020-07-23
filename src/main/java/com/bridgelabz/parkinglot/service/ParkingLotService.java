package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotServiceException;
import com.bridgelabz.parkinglot.observer.IObserver;
import com.bridgelabz.parkinglot.model.Vehicle;
import com.bridgelabz.parkinglot.utility.Slot;
import com.bridgelabz.parkinglot.utility.SlotAllotment;

import java.time.LocalDateTime;
import java.util.*;

public class ParkingLotService {

    public int parkingCapacity;
    public SlotAllotment slotAllotment;
    public HashMap<Integer, Slot> parkedVehicles;
    public List<IObserver> observers;

    public ParkingLotService(int parkingLotCapacity) {
        this.parkingCapacity = parkingLotCapacity;
        this.observers = new ArrayList<>();
        this.parkedVehicles = new HashMap<>();
        this.slotAllotment = new SlotAllotment(parkingLotCapacity);
    }

    public void registerParkingLotObserver(IObserver observer) {
        this.observers.add(observer);
    }

    public void informListeners(String capacityStatus) {
        for (IObserver entry : observers) {
            entry.update(capacityStatus);
        }
    }

    public boolean isPresent(Vehicle vehicle) {
        return (parkedVehicles.containsValue(new Slot(vehicle, LocalDateTime.now().withNano(0))));
    }

    public void parkVehicle(Vehicle vehicle) throws ParkingLotServiceException {
        if (vehicle == null)
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.INVALID_VEHICLE, "Invalid Vehicle");

        if (isPresent(vehicle))
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.VEHICLE_ALREADY_PRESENT, "Already present");

        parkedVehicles.put(slotAllotment.getNearestParkingSlot(), new Slot(vehicle, LocalDateTime.now().withNano(0)));
        if (this.parkingCapacity == this.parkedVehicles.size()) {
            informListeners("Capacity is Full");
        }
    }

    public void unParkVehicle(Vehicle vehicle) throws ParkingLotServiceException {
        if (vehicle == null)
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.INVALID_VEHICLE, "Invalid Vehicle");

        if (!isPresent(vehicle))
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.NO_SUCH_A_VEHICLE, "No Vehicle Found");

        parkedVehicles.remove(getSlot(vehicle));
        informListeners("Capacity Available");
    }

    public Integer getSlot(Vehicle vehicle) {
        Integer slot = -1;
        for (Map.Entry<Integer, Slot> entry : parkedVehicles.entrySet()) {
            if (vehicle.equals(entry.getValue().getVehicle())) {
                slot = entry.getKey();
                slotAllotment.unParkUpdate(slot);
                informListeners("Capacity Available");
            }
        }
        return slot;
    }

    public Integer findVehicle(Vehicle vehicle) {
        Integer slot = -1;
        for (Map.Entry<Integer, Slot> entry : parkedVehicles.entrySet()) {
            if (vehicle.equals(entry.getValue().getVehicle())) {
                slot = entry.getKey();
                slotAllotment.unParkUpdate(slot);
                informListeners("Capacity Available");
            }
        }
        return slot;
    }

    public void parkVehicle(int slot, Vehicle vehicle) throws ParkingLotServiceException {
        if (isPresent(vehicle))
            throw new ParkingLotServiceException(ParkingLotServiceException.ExceptionType.VEHICLE_ALREADY_PRESENT, "Already present");
        this.parkedVehicles.put(slot, new Slot(vehicle, LocalDateTime.now().withNano(0)));
        slotAllotment.parkUpdate(slot);

    }

    public LocalDateTime getParkingTime(Vehicle vehicle) {
        Slot slot = parkedVehicles.get(findVehicle(vehicle));
        return slot.getParkingTime();
    }
}
