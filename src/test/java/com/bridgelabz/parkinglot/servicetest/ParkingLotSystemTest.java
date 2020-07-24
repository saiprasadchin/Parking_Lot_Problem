package com.bridgelabz.parkinglot.servicetest;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.Vehicle;
import com.bridgelabz.parkinglot.service.ParkingLot;
import com.bridgelabz.parkinglot.service.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotSystemTest {

    ParkingLotSystem parkingLotSystem;
    ParkingLot firstLot;
    ParkingLot secondLot;
    Vehicle firstVehicle;
    Vehicle secondVehicle;

    @Before
    public void init() {
        this.firstLot = new ParkingLot(3);
        this.secondLot = new ParkingLot(3);
        this.firstVehicle = new Vehicle();
        this.secondVehicle = new Vehicle();
        this.parkingLotSystem = new ParkingLotSystem(firstLot, secondLot);
    }

    @Test
    public void givenVehicle_whenParkingLotsAreEmpty_ShouldParkedInFirstParkingLot() {
        ParkingLot expectedResult = firstLot;
        try {
            parkingLotSystem.park(firstVehicle);
            ParkingLot actualResult = parkingLotSystem.getParkingLotInWhichVehicleIsParked(firstVehicle);
            Assert.assertEquals(expectedResult,actualResult);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenSecondVehicle_ShouldGetParkedInSecondParkingLot() {
        ParkingLot expectedResult = secondLot;
        try {
            parkingLotSystem.park(firstVehicle);
            parkingLotSystem.park(secondVehicle);
            ParkingLot actualResult = parkingLotSystem.getParkingLotInWhichVehicleIsParked(secondVehicle);
            Assert.assertEquals(expectedResult,actualResult);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicle_isAlreadyPresentInAnyParkingLot_ShouldThrowException() {
        try {
            parkingLotSystem.park(firstVehicle);
            parkingLotSystem.park(firstVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PRESENT, e.exceptionType);
        }
    }

    @Test
    public void givenARequestToUnParkAVehicle_ShouldGetUnParked() {
        try {
            parkingLotSystem.park(firstVehicle);
            parkingLotSystem.unPark(firstVehicle);
            ParkingLot parkingLot = parkingLotSystem.getParkingLotInWhichVehicleIsParked(firstVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_A_VEHICLE, e.exceptionType);
        }
    }
}
