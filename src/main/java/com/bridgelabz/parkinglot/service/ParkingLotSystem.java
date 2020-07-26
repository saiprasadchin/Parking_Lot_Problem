package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.enums.VehicleColour;
import com.bridgelabz.parkinglot.enums.VehicleCompany;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.enums.DriverType;
import com.bridgelabz.parkinglot.model.ParkingVehicleDetails;
import com.bridgelabz.parkinglot.enums.VehicleSize;

import java.util.*;

public class ParkingLotSystem {


    public List<ParkingLot> parkingLots;

    public ParkingLotSystem(ParkingLot... parkingLot) {
        this.parkingLots = new ArrayList<>(Arrays.asList(parkingLot));
    }

    public void addParking(ParkingLot parkingLot) {
        this.parkingLots.add(parkingLot);
    }

    public int getNumberOfParkingLots() {
        return this.parkingLots.size();
    }

    public void park(ParkingVehicleDetails vehicle) throws ParkingLotException {
        ParkingLot parkingLotAlLot = null;
        List<ParkingLot> lots = this.parkingLots;
        for (ParkingLot parkingLot : lots) {
            parkingLot.checkVehicleAlreadyPresent(vehicle);
        }
        if (vehicle.getVehicleSize().equals(VehicleSize.LARGE)) {
            parkingLotAlLot = LotAllotmentService.getLotForLarge(this.parkingLots);
        }
        if (vehicle.getDriverType().equals(DriverType.HANDICAPPED)) {
            parkingLotAlLot = LotAllotmentService.getLotForHandicapped(this.parkingLots);
        }
        if (vehicle.getDriverType().equals(DriverType.NORMAL)) {
            parkingLotAlLot = LotAllotmentService.getLotForNormal(this.parkingLots);
        }
        parkingLotAlLot.parkVehicle(vehicle);
    }

    public void unPark(ParkingVehicleDetails vehicle) throws ParkingLotException {
        ParkingLot parkingLot = this.getParkingLotInWhichVehicleIsParked(vehicle);
        parkingLot.unParkVehicle(vehicle);
    }

    public ParkingLot getParkingLotInWhichVehicleIsParked(ParkingVehicleDetails vehicle) throws ParkingLotException {
        for (ParkingLot parkingLot : this.parkingLots) {
            if (parkingLot.isPresent(vehicle)) {
                return parkingLot;
            }
        }
        throw new ParkingLotException(ParkingLotException.ExceptionType.NO_SUCH_A_VEHICLE, "No Vehicle Found");
    }

    public Integer getParkingSlot(ParkingVehicleDetails vehicle) throws ParkingLotException {
        ParkingLot parkingLot = this.getParkingLotInWhichVehicleIsParked(vehicle);
        return parkingLot.getPositionOfVehicle(vehicle);
    }

    public Map<ParkingLot, List<Integer>> getLotAndSlotListOfVehiclesByColor(VehicleColour vehicleColour) {
        Map<ParkingLot, List<Integer>> vehiclesWithSpecificColor = new HashMap<>();
        for (ParkingLot parkingLot : this.parkingLots) {
            List<Integer> slotNumbers = parkingLot.getListOfSlotsByColour(vehicleColour);
            if (slotNumbers.size() > 0) {
                vehiclesWithSpecificColor.put(parkingLot, slotNumbers);
            }
        }
        return vehiclesWithSpecificColor;
    }

    public Map<ParkingLot, List<Integer>> getLotAndSlotNumberByCompanyAndColor(VehicleCompany vehicleCompany, VehicleColour vehicleColour) {
        Map<ParkingLot, List<Integer>> vehicleByCompanyAndColour = new HashMap<>();
        for (ParkingLot parkingLot : this.parkingLots) {
            List<Integer> slotNumbers = parkingLot.getSlotNumbersByCompanyAndColour(vehicleCompany, vehicleColour);
            if (slotNumbers.size() > 0) {
                vehicleByCompanyAndColour.put(parkingLot, slotNumbers);
            }
        }
        return vehicleByCompanyAndColour;
    }

    public Map<ParkingLot, List<Integer>> getSlotNumbersOfVehiclesByCompany(VehicleCompany vehicleCompany) {
        Map<ParkingLot, List<Integer>> vehicleByCompany = new HashMap<>();
        for (ParkingLot parkingLot : this.parkingLots) {
            List<Integer> slotNumbers = parkingLot.getSlotNumbersByCompany(vehicleCompany);
            if (slotNumbers.size() > 0) {
                vehicleByCompany.put(parkingLot, slotNumbers);
            }
        }
        return vehicleByCompany;
    }
}
