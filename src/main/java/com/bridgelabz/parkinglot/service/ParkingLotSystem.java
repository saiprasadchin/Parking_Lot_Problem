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
        } else if (vehicle.getDriverType().equals(DriverType.HANDICAPPED)) {
            parkingLotAlLot = LotAllotmentService.getLotForHandicapped(this.parkingLots);
        } else if (vehicle.getDriverType().equals(DriverType.NORMAL)) {
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

        this.parkingLots.stream().forEach(p -> {
            if (p.getListOfSlotsByColour(vehicleColour).size() > 0) {
                vehiclesWithSpecificColor.put(p, p.getListOfSlotsByColour(vehicleColour));
            }
        });
        return vehiclesWithSpecificColor;
    }

    public Map<ParkingLot, List<String>> getLotAndSlotNumberByCompanyAndColor(VehicleCompany vehicleCompany, VehicleColour vehicleColour) {
        Map<ParkingLot, List<String>> vehicleByCompanyAndColour = new HashMap<>();
        for (ParkingLot parkingLot : this.parkingLots) {
            List<String> slotNumbers = parkingLot.getSlotNumbersByCompanyAndColour(vehicleCompany, vehicleColour);
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

    public Map<ParkingLot, List<Integer>> getVehiclesParkedFromTime(int time) {
        Map<ParkingLot, List<Integer>> slotNumbersByTime = new HashMap<>();
        for (ParkingLot parkingLot : this.parkingLots) {
            List<Integer> slotNumbers = parkingLot.getVehiclesParkedFromTime(30);
            if (slotNumbers.size() > 0) {
                slotNumbersByTime.put(parkingLot, slotNumbers);
            }
        }
        return slotNumbersByTime;
    }

    public Map<ParkingLot, List<String>> getSlotNumbersBySizeAndDriverType(DriverType driverType, VehicleSize vehicleSize) {
        Map<ParkingLot, List<String>> lotAndSlotNumbers = new HashMap<>();
        for (ParkingLot parkingLot : this.parkingLots) {
            List<String> slotNumbers = parkingLot.getCompleteVehiclesList(driverType,vehicleSize);
            if (slotNumbers.size() > 0) {
                lotAndSlotNumbers.put(parkingLot, slotNumbers);
            }
        }
        return lotAndSlotNumbers;
    }

    public Map<ParkingLot, List<Integer>> getAllVehiclesParkedInParkingLot() {
        Map<ParkingLot, List<Integer>> lotAndSlotNumbers = new HashMap<>();
        for (ParkingLot parkingLot : this.parkingLots) {
            List<Integer> slotNumbers = parkingLot.getAllVehiclesParkedInParkingLot();
            if (slotNumbers.size() > 0) {
                lotAndSlotNumbers.put(parkingLot, slotNumbers);
            }
        }
        return lotAndSlotNumbers;
    }
}
