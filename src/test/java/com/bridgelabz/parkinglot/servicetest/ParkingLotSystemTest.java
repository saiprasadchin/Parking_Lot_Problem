package com.bridgelabz.parkinglot.servicetest;

import com.bridgelabz.parkinglot.enums.VehicleColour;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.enums.DriverType;
import com.bridgelabz.parkinglot.model.ParkingVehicleDetails;
import com.bridgelabz.parkinglot.enums.VehicleSize;
import com.bridgelabz.parkinglot.observer.AirportSecurityService;
import com.bridgelabz.parkinglot.observer.Owner;
import com.bridgelabz.parkinglot.service.ParkingLot;
import com.bridgelabz.parkinglot.service.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ParkingLotSystemTest {

    ParkingLotSystem parkingLotSystem;
    ParkingLot parkingLot;
    ParkingLot firstParkingLot;
    ParkingLot secondParkingLot;
    ParkingLot thirdParkingLot;
    ParkingVehicleDetails firstVehicle;
    ParkingVehicleDetails secondVehicle;
    ParkingVehicleDetails thirdVehicle;

    @Before
    public void init() {
        this.parkingLot = new ParkingLot(2);
        this.firstParkingLot = new ParkingLot(3);
        this.secondParkingLot = new ParkingLot(3);
        this.thirdParkingLot = new ParkingLot(3);
        this.firstVehicle = new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.NORMAL, VehicleColour.WHITE);
        this.secondVehicle = new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.NORMAL, VehicleColour.WHITE);
        this.thirdVehicle = new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.NORMAL, VehicleColour.WHITE);
        this.parkingLotSystem = new ParkingLotSystem(firstParkingLot, secondParkingLot, thirdParkingLot);
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        ParkingVehicleDetails vehicle = new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.NORMAL, VehicleColour.WHITE);
        boolean isParked;
        try {
            parkingLot.parkVehicle(vehicle);
            isParked = parkingLot.isPresent(vehicle);
            Assert.assertTrue(isParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenANullVehicle_WhenParked_ShouldThrowException() {
        ParkingVehicleDetails vehicle = null;
        try {
            parkingLot.parkVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.INVALID_VEHICLE, e.exceptionType);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleNotFound_ShouldThrowException() {
        ParkingVehicleDetails vehicle = new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.NORMAL, VehicleColour.WHITE);
        try {
            parkingLot.unParkVehicle(vehicle);
            parkingLot.getSlot(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_A_VEHICLE, e.exceptionType);
        }
    }

    @Test
    public void givenAVehicle_WhenAlreadyParked_ShouldThrowException() {
        ParkingVehicleDetails vehicle = new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.NORMAL, VehicleColour.WHITE);
        try {
            parkingLot.parkVehicle(vehicle);
            parkingLot.parkVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PRESENT, e.exceptionType);
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        ParkingVehicleDetails vehicle = new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.NORMAL, VehicleColour.WHITE);
        boolean isUnParked;
        try {
            parkingLot.parkVehicle(vehicle);
            parkingLot.unParkVehicle(vehicle);
            isUnParked = parkingLot.isPresent(vehicle);
            Assert.assertFalse(isUnParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenVehicles_WhenParkingLotIsFull_ShouldInformSecurity() {
        AirportSecurityService airportSecurityService = new AirportSecurityService();
        parkingLot.registerParkingLotObserver(airportSecurityService);
        ParkingVehicleDetails vehicle = new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.NORMAL, VehicleColour.WHITE);
        try {
            parkingLot.parkVehicle(vehicle);
            parkingLot.parkVehicle(new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.NORMAL, VehicleColour.WHITE));
            Assert.assertEquals("Capacity is Full", airportSecurityService.getParkingLotStatus());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenNotParkedVehicle_WhenUnParked_ShouldThrowException() {
        ParkingVehicleDetails vehicle = new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.NORMAL, VehicleColour.WHITE);
        try {
            parkingLot.unParkVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_A_VEHICLE, e.exceptionType);
        }
    }

    @Test
    public void givenANullVehicle_WhenUnParked_ShouldThrowException() {
        ParkingVehicleDetails vehicle = null;
        try {
            parkingLot.unParkVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.INVALID_VEHICLE, e.exceptionType);
        }
    }

    @Test
    public void givenVehicles_WhenParkingLotIsFull_ShouldThrowException() {
        try {
            parkingLot.parkVehicle(new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.NORMAL, VehicleColour.WHITE));
            parkingLot.parkVehicle(new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.NORMAL, VehicleColour.WHITE));
            parkingLot.parkVehicle(new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.NORMAL, VehicleColour.WHITE));
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, e.exceptionType);
        }
    }

    @Test
    public void givenParkingLotVacancy_WhenFull_ShouldInformTheOwner() {
        Owner owner = new Owner();
        parkingLot.registerParkingLotObserver(owner);
        try {
            ParkingVehicleDetails vehicle = new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.NORMAL, VehicleColour.WHITE);
            parkingLot.parkVehicle(vehicle);
            parkingLot.unParkVehicle(vehicle);
            Assert.assertEquals("Capacity Available", owner.getParkingLotStatus());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicleToAttendant_WhenParkedAsPerProvidedSlot_ShouldReturnTrue() {
        ParkingVehicleDetails vehicle = new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.NORMAL, VehicleColour.WHITE);
        try {
            parkingLot.parkVehicle(1, vehicle);
            boolean isPresent = parkingLot.isPresent(vehicle);
            Assert.assertTrue(isPresent);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleFound_ShouldReturnVehicleSlot() {
        ParkingVehicleDetails vehicle = new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.NORMAL, VehicleColour.WHITE);
        try {
            parkingLot.parkVehicle(4, vehicle);
            Integer slotNumber = parkingLot.findVehicle(vehicle);
            parkingLot.parkVehicle(5, vehicle);
            Assert.assertEquals((Integer) 4, slotNumber);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicle_WhenParked_ShouldReturnParkingTime() {
        ParkingVehicleDetails vehicle = new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.NORMAL, VehicleColour.WHITE);
        try {
            LocalDateTime localDateTime = LocalDateTime.now().withNano(0);
            parkingLot.parkVehicle(vehicle);
            LocalDateTime localDateTime1 = parkingLot.getParkingTime(vehicle);
            Assert.assertEquals(localDateTime, localDateTime1);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAParkingLot_ShouldGetAddedToTheParkingLotsManagedByTheSystem() {
        ParkingLot parkingLot3 = new ParkingLot(5);
        ParkingLot parkingLot4 = new ParkingLot(5);
        parkingLotSystem.addParking(parkingLot3);
        parkingLotSystem.addParking(parkingLot4);
        int numberOfParkingLots = parkingLotSystem.getNumberOfParkingLots();
        Assert.assertEquals(5, numberOfParkingLots);
    }

    @Test
    public void givenVehicle_whenParkingLotsAreEmpty_ShouldParkedInFirstParkingLot() {
        ParkingLot expectedResult = firstParkingLot;
        try {
            parkingLotSystem.park(firstVehicle);
            ParkingLot actualResult = parkingLotSystem.getParkingLotInWhichVehicleIsParked(firstVehicle);
            Assert.assertEquals(expectedResult, actualResult);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenSecondVehicle_ShouldGetParkedInSecondParkingLot() {
        ParkingLot expectedResult = secondParkingLot;
        try {
            parkingLotSystem.park(firstVehicle);
            parkingLotSystem.park(secondVehicle);
            ParkingLot actualResult = parkingLotSystem.getParkingLotInWhichVehicleIsParked(secondVehicle);
            Assert.assertEquals(expectedResult, actualResult);
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

    @Test
    public void givenVehicle_whenParkingLotsAreEmpty_ShouldParkedInFirstParkingLotAndFirstSlot() {
        ParkingLot expectedResult = firstParkingLot;
        try {
            parkingLotSystem.park(firstVehicle);
            ParkingLot parkingLot = parkingLotSystem.getParkingLotInWhichVehicleIsParked(firstVehicle);
            Integer actualResult = parkingLotSystem.getParkingSlot(firstVehicle);
            Assert.assertEquals(expectedResult, parkingLot);
            Assert.assertEquals((Integer) 1, actualResult);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicleWithHandicappedDriver_IfFirstLotHasEmptySlotsTheVehicle_ShouldParkedInTheFirstParkingLot() {
        try {
            ParkingVehicleDetails vehicle1 = new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.HANDICAPPED, VehicleColour.WHITE);
            ParkingVehicleDetails vehicle2 = new ParkingVehicleDetails(new Object(), VehicleSize.SMALL, DriverType.HANDICAPPED, VehicleColour.WHITE);
            parkingLotSystem.park(vehicle1);
            parkingLotSystem.park(vehicle2);
            ParkingLot presentLot1 = parkingLotSystem.getParkingLotInWhichVehicleIsParked(vehicle1);
            ParkingLot presentLot2 = parkingLotSystem.getParkingLotInWhichVehicleIsParked(vehicle2);
            Assert.assertEquals(firstParkingLot, presentLot1);
            Assert.assertEquals(firstParkingLot, presentLot2);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenLargeVehicle_ShouldGetParkedInTheLotWithMostEmptySlots() {
        try {
            ParkingVehicleDetails vehicle1 = new ParkingVehicleDetails(new Object(), VehicleSize.LARGE, DriverType.NORMAL, VehicleColour.WHITE);
            parkingLotSystem.park(firstVehicle);
            parkingLotSystem.park(secondVehicle);
            parkingLotSystem.park(vehicle1);
            ParkingLot highestNumOfFreeSpace = parkingLotSystem.getParkingLotInWhichVehicleIsParked(vehicle1);
            Assert.assertEquals(thirdParkingLot, highestNumOfFreeSpace);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehiclesColour_WhenFound_ShouldReturnListOfWhitVehicleWithSlotNumber() {
        try {
            parkingLotSystem.park(firstVehicle);
            parkingLotSystem.park(secondVehicle);
            Map<ParkingLot, List<Integer>> slotNumberListOfVehiclesByColor = parkingLotSystem.getSlotListOfVehiclesByColor(VehicleColour.WHITE);
            Assert.assertEquals(1, slotNumberListOfVehiclesByColor.get(firstParkingLot).get(0).intValue());
            Assert.assertEquals(1, slotNumberListOfVehiclesByColor.get(secondParkingLot).get(0).intValue());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}
