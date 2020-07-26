package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.enums.VehicleColour;
import com.bridgelabz.parkinglot.enums.VehicleCompany;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.observer.IObserver;
import com.bridgelabz.parkinglot.model.ParkingVehicleDetails;
import com.bridgelabz.parkinglot.model.Slot;
import com.bridgelabz.parkinglot.utility.SlotAllotment;

import java.time.LocalDateTime;
import java.util.*;

public class ParkingLot {

    public int parkingCapacity;
    public SlotAllotment slotAllotment;
    public HashMap<Integer, Slot> parkedVehicles;
    public List<IObserver> observers;

    public ParkingLot(int parkingLotCapacity) {
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


    public int getCountOfVehicles() {
        return parkedVehicles.size();
    }

    public int getParkingCapacity() {
        return parkingCapacity;
    }


    public boolean isPresent(ParkingVehicleDetails vehicle) {
        return parkedVehicles.entrySet().stream().anyMatch(entry -> vehicle.equals(entry.getValue().getVehicle()));
    }

    public void parkVehicle(ParkingVehicleDetails vehicle) throws ParkingLotException {
        if (vehicle == null)
            throw new ParkingLotException(ParkingLotException.ExceptionType.INVALID_VEHICLE, "Invalid Vehicle");

        if (isPresent(vehicle))
            throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PRESENT, "Already present");
        parkedVehicles.put(slotAllotment.getNearestParkingSlot(), new Slot(vehicle, LocalDateTime.now().withNano(0)));
        if (this.parkingCapacity == this.parkedVehicles.size()) {
            informListeners("Capacity is Full");
        }
    }

    public void unParkVehicle(ParkingVehicleDetails vehicle) throws ParkingLotException {
        if (vehicle == null)
            throw new ParkingLotException(ParkingLotException.ExceptionType.INVALID_VEHICLE, "Invalid Vehicle");

        if (!isPresent(vehicle)) {
            throw new ParkingLotException(ParkingLotException.ExceptionType.NO_SUCH_A_VEHICLE, "No Vehicle Found");
        }
        parkedVehicles.remove(getSlot(vehicle));
        informListeners("Capacity Available");
    }

    public Integer getSlot(ParkingVehicleDetails vehicle) {
        Integer slot = -1;
        for (Map.Entry<Integer, Slot> entry : parkedVehicles.entrySet()) {
            if (vehicle.equals(entry.getValue().getVehicle())) {
                slot = entry.getKey();
                slotAllotment.unParkUpdate(slot);
            }
        }
        return slot;
    }

    public Integer findVehicle(ParkingVehicleDetails vehicle) throws ParkingLotException {
        Integer slot = -1;
        for (Map.Entry<Integer, Slot> entry : parkedVehicles.entrySet()) {
            if (vehicle.equals(entry.getValue().getVehicle())) {
                slot = entry.getKey();
                this.unParkVehicle(vehicle);
            }
        }
        return slot;
    }

    public Integer getPositionOfVehicle(ParkingVehicleDetails vehicle) {
        Integer slot = -1;
        for (Map.Entry<Integer, Slot> entry : parkedVehicles.entrySet()) {
            if (vehicle.equals(entry.getValue().getVehicle())) {
                slot = entry.getKey();
            }
        }
        return slot;
    }

    public void parkVehicle(int slot, ParkingVehicleDetails vehicle) throws ParkingLotException {
        if (isPresent(vehicle))
            throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PRESENT, "Vehicle Already present");
        this.parkedVehicles.put(slot, new Slot(vehicle, LocalDateTime.now().withNano(0)));
        slotAllotment.parkUpdate(slot);
    }

    public LocalDateTime getParkingTime(ParkingVehicleDetails vehicle) {
        Slot slot = parkedVehicles.get(getPositionOfVehicle(vehicle));
        return slot.getParkingTime();
    }

    public void checkVehicleAlreadyPresent(ParkingVehicleDetails vehicle) throws ParkingLotException {
        if (isPresent(vehicle)) {
            throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PRESENT, "Vehicle Already present");
        }
    }

    public List<Integer> getListOfSlotsByColour(VehicleColour vehicleColour) {
        List<Integer> slotNumbers = new ArrayList<>();
        for (Integer slotNumber : parkedVehicles.keySet()) {
            if (parkedVehicles.get(slotNumber).getVehicle().getVehicle().getVehicleColour().equals(vehicleColour)) {
                slotNumbers.add(slotNumber);
            }
        }
        return slotNumbers;
    }

    public List<Integer> getSlotNumbersByCompanyAndColour(VehicleCompany vehicleCompany, VehicleColour vehicleColour) {
        List<Integer> slotNumbers = new ArrayList<>();
        for (Integer slotNumber : parkedVehicles.keySet()) {
            if (parkedVehicles.get(slotNumber).getVehicle().getVehicle().getVehicleColour().equals(vehicleColour) &&
                    parkedVehicles.get(slotNumber).getVehicle().getVehicle().getCompany().equals(vehicleCompany)) {
                slotNumbers.add(slotNumber);
            }
        }
        return slotNumbers;
    }

    public List<Integer> getSlotNumbersByCompany(VehicleCompany vehicleCompany) {
        List<Integer> slotNumbers = new ArrayList<>();
        for (Integer slotNumber : parkedVehicles.keySet()) {
            if (parkedVehicles.get(slotNumber).getVehicle().getVehicle().getCompany().equals(vehicleCompany)) {
                slotNumbers.add(slotNumber);
            }
        }
        return slotNumbers;
    }
}
