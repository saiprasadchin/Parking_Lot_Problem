package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.Vehicle;

import java.util.*;

public class ParkingLotSystem {


    public List<ParkingLot> parkingLots;

    private static final String  DRIVER_TYPE_HANDICAPPED = "DRIVER_TYPE_HANDICAPPED";

    public ParkingLotSystem(ParkingLot... parkingLot) {
        this.parkingLots = new ArrayList<>(Arrays.asList(parkingLot));
    }

    public void addParking(ParkingLot parkingLot) {
        this.parkingLots.add(parkingLot);
    }
    public int getNumberOfParkingLots() {
        return this.parkingLots.size();
    }

    public void park(Vehicle vehicle,String driverType) throws ParkingLotException {
        for (ParkingLot parkingLot : this.parkingLots) {
            parkingLot.isVehicleAlreadyPresent(vehicle);
        }
        List<ParkingLot> listOfLots = new ArrayList(this.parkingLots);
        if(driverType.equals(DRIVER_TYPE_HANDICAPPED)){
            getLotForHandicap().parkVehicle(vehicle);
        }else {
            listOfLots.sort(Comparator.comparing(ParkingLot::getCountOfVehicles));
            listOfLots.get(0).parkVehicle(vehicle);
        }
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

    public Integer getParkingSlot(Vehicle vehicle) throws ParkingLotException {
        ParkingLot parkingLot = this.getParkingLotInWhichVehicleIsParked(vehicle);
        return parkingLot.getPositionOfVehicle(vehicle);
    }

    public ParkingLot getLotForHandicap() throws ParkingLotException {
        return this.parkingLots.stream()
                .filter(lot -> lot.getCountOfVehicles() != lot.getParkingCapacity())
                .findFirst()
                .orElseThrow(() -> new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, "Parking Full"));
    }
}
