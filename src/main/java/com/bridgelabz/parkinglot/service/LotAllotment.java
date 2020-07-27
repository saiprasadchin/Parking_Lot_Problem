package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;

import java.util.Comparator;
import java.util.List;

public class LotAllotment {

    public static ParkingLot getLotForNormal(List<ParkingLot> parkingLots) {
        parkingLots.sort(Comparator.comparing(ParkingLot::getCountOfVehicles));
        return parkingLots.get(0);
    }

    public static ParkingLot getLotForHandicapped(List<ParkingLot> parkingLots) throws ParkingLotException {
        return parkingLots.stream()
                .filter(parkingLot -> parkingLot.getCountOfVehicles() != parkingLot.getParkingCapacity())
                .findFirst()
                .orElseThrow(() -> new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, "Parking Full"));
    }

    public static ParkingLot getLotForLarge(List<ParkingLot> parkingLots) throws ParkingLotException {
        return parkingLots.stream()
                .sorted(Comparator.comparing(parkingLot -> (parkingLot.getParkingCapacity() - parkingLot.getCountOfVehicles()),
                        Comparator.reverseOrder()))
                .filter(parkingLot -> parkingLot.getCountOfVehicles() != parkingLot.getParkingCapacity())
                .findFirst()
                .orElseThrow(() -> new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, "Parking Full"));
    }
}
