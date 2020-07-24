package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.Vehicle;

import java.util.*;

public class ParkingLotSystem {


    public List<ParkingLot> parkingLots;

    public ParkingLotSystem(ParkingLot... parkingLot) {
        this.parkingLots = new ArrayList<>(Arrays.asList(parkingLot));
    }

    public void park(Vehicle vehicle) throws ParkingLotException {
        for (ParkingLot parkingLot : this.parkingLots) {
            parkingLot.isVehicleAlreadyPresent(vehicle);
        }
        parkingLots.sort(Comparator.comparing(ParkingLot::getCountOfVehicles));
        parkingLots.get(0).parkVehicle(vehicle);
    }

    public void unPark(Vehicle vehicle) throws ParkingLotException {
        ParkingLot parkingLot = this.getParkingLotInWhichVehicleIsParked(vehicle);
        parkingLot.unParkVehicle(vehicle);
    }

    public ParkingLot getParkingLotInWhichVehicleIsParked(Vehicle vehicle) throws ParkingLotException {
        for (ParkingLot parkingLot : this.parkingLots) {
            if (parkingLot.isPresent(vehicle)) {
                return parkingLot;
            }
        }
        throw new ParkingLotException(ParkingLotException.ExceptionType.NO_SUCH_A_VEHICLE, "No Vehicle Found");
    }
}
