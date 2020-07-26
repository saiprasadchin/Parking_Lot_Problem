package com.bridgelabz.parkinglot.servicetest;

import com.bridgelabz.parkinglot.enums.VehicleColour;
import com.bridgelabz.parkinglot.enums.VehicleCompany;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.enums.DriverType;
import com.bridgelabz.parkinglot.model.ParkingVehicleDetails;
import com.bridgelabz.parkinglot.enums.VehicleSize;
import com.bridgelabz.parkinglot.model.Vehicle;
import com.bridgelabz.parkinglot.observer.AirportSecurityService;
import com.bridgelabz.parkinglot.observer.Owner;
import com.bridgelabz.parkinglot.service.ParkingLot;
import com.bridgelabz.parkinglot.service.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParkingLotSystemTest {

    ParkingLotSystem parkingLotSystem;
    ParkingLot parkingLot;
    ParkingLot firstParkingLot;
    ParkingLot secondParkingLot;
    ParkingLot thirdParkingLot;
    Vehicle firstVehicle;
    Vehicle secondVehicle;
    Vehicle thirdVehicle;
    ParkingVehicleDetails firstVehicleDetails;
    ParkingVehicleDetails secondVehicleDetails;
    ParkingVehicleDetails thirdVehicleDetails;

    @Before
    public void init() {
        this.parkingLot = new ParkingLot(2);
        this.firstParkingLot = new ParkingLot(3);
        this.secondParkingLot = new ParkingLot(3);
        this.thirdParkingLot = new ParkingLot(3);
        this.firstVehicle = new Vehicle("MH04 A 4444", VehicleCompany.TOYOTA, VehicleColour.WHITE);
        this.secondVehicle = new Vehicle("MH05 Y 5555", VehicleCompany.TOYOTA, VehicleColour.WHITE);
        //this.thirdVehicle = new Vehicle("MH06 AD 6666", VehicleCompany.TOYOTA, VehicleColour.WHITE);
        this.firstVehicleDetails = new ParkingVehicleDetails(firstVehicle, VehicleSize.SMALL, DriverType.NORMAL, "Saiprasad");
        this.secondVehicleDetails = new ParkingVehicleDetails(secondVehicle, VehicleSize.SMALL, DriverType.NORMAL, "Sagar");
        //this.thirdVehicleDetails = new ParkingVehicleDetails(thirdVehicle, VehicleSize.SMALL, DriverType.NORMAL, VehicleColour.WHITE, "Arjun");
        this.parkingLotSystem = new ParkingLotSystem(firstParkingLot, secondParkingLot, thirdParkingLot);
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        boolean isParked;
        try {
            parkingLot.parkVehicle(firstVehicleDetails);
            isParked = parkingLot.isPresent(firstVehicleDetails);
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
    public void givenVehicleToUnPark_WhenVehicleNotFound_ShouldThrowException() {
        try {
            parkingLot.unParkVehicle(firstVehicleDetails);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_A_VEHICLE, e.exceptionType);
        }
    }

    @Test
    public void givenAVehicle_WhenAlreadyParked_ShouldThrowException() {
        try {
            parkingLot.parkVehicle(firstVehicleDetails);
            parkingLot.parkVehicle(firstVehicleDetails);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PRESENT, e.exceptionType);
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        boolean isUnParked;
        try {
            parkingLot.parkVehicle(firstVehicleDetails);
            parkingLot.unParkVehicle(firstVehicleDetails);
            isUnParked = parkingLot.isPresent(firstVehicleDetails);
            Assert.assertFalse(isUnParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void givenVehicles_WhenParkingLotIsFull_ShouldInformSecurity() {
        AirportSecurityService airportSecurityService = new AirportSecurityService();
        parkingLot.registerParkingLotObserver(airportSecurityService);
        try {
            parkingLot.parkVehicle(firstVehicleDetails);
            parkingLot.parkVehicle(secondVehicleDetails);
            Assert.assertEquals("Capacity is Full", airportSecurityService.getParkingLotStatus());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenNotParkedVehicle_WhenUnParked_ShouldThrowException() {
        try {
            parkingLot.unParkVehicle(firstVehicleDetails);
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
            parkingLot.parkVehicle(firstVehicleDetails);
            parkingLot.parkVehicle(secondVehicleDetails);
            //parkingLot.parkVehicle(thirdVehicleDetails);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, e.exceptionType);
        }
    }

    @Test
    public void givenParkingLotVacancy_WhenFull_ShouldInformTheOwner() {
        Owner owner = new Owner();
        parkingLot.registerParkingLotObserver(owner);
        try {
            parkingLot.parkVehicle(firstVehicleDetails);
            parkingLot.unParkVehicle(firstVehicleDetails);
            Assert.assertEquals("Capacity Available", owner.getParkingLotStatus());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicle_WhenParkedAsPerProvidedSlot_ShouldReturnTrue() {
        try {
            parkingLot.parkVehicle(1, firstVehicleDetails);
            boolean isPresent = parkingLot.isPresent(firstVehicleDetails);
            Assert.assertTrue(isPresent);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicle_WhenVehicleFound_ShouldReturnVehicleSlotNumber() {
        try {
            parkingLot.parkVehicle(4, firstVehicleDetails);
            Integer slotNumber = parkingLot.findVehicle(firstVehicleDetails);
            Assert.assertEquals((Integer) 4, slotNumber);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicle_WhenParked_ShouldReturnParkingTime() {
        try {
            LocalDateTime localDateTime = LocalDateTime.now().withNano(0);
            parkingLot.parkVehicle(firstVehicleDetails);
            LocalDateTime localDateTime1 = parkingLot.getParkingTime(firstVehicleDetails);
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
            parkingLotSystem.park(firstVehicleDetails);
            ParkingLot actualResult = parkingLotSystem.getParkingLotInWhichVehicleIsParked(firstVehicleDetails);
            Assert.assertEquals(expectedResult, actualResult);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenSecondVehicle_ShouldGetParkedInSecondParkingLot() {
        ParkingLot expectedResult = secondParkingLot;
        try {
            parkingLotSystem.park(firstVehicleDetails);
            parkingLotSystem.park(secondVehicleDetails);
            ParkingLot actualResult = parkingLotSystem.getParkingLotInWhichVehicleIsParked(secondVehicleDetails);
            Assert.assertEquals(expectedResult, actualResult);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicle_isAlreadyPresentInAnyParkingLot_ShouldThrowException() {
        try {
            parkingLotSystem.park(firstVehicleDetails);
            parkingLotSystem.park(firstVehicleDetails);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PRESENT, e.exceptionType);
        }
    }

    @Test
    public void givenARequestToUnParkAVehicle_ShouldGetUnParked() {
        try {
            parkingLotSystem.park(firstVehicleDetails);
            parkingLotSystem.unPark(firstVehicleDetails);
            ParkingLot parkingLot = parkingLotSystem.getParkingLotInWhichVehicleIsParked(firstVehicleDetails);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_A_VEHICLE, e.exceptionType);
        }
    }

    @Test
    public void givenVehicle_whenParkingLotsAreEmpty_ShouldParkedInFirstParkingLotAndFirstSlot() {
        ParkingLot expectedResult = firstParkingLot;
        try {
            parkingLotSystem.park(firstVehicleDetails);
            ParkingLot parkingLot = parkingLotSystem.getParkingLotInWhichVehicleIsParked(firstVehicleDetails);
            Integer actualResult = parkingLotSystem.getParkingSlot(firstVehicleDetails);
            Assert.assertEquals(expectedResult, parkingLot);
            Assert.assertEquals((Integer) 1, actualResult);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicleWithHandicappedDriver_IfFirstLotHasEmptySlotsTheVehicle_ShouldParkedInTheFirstParkingLot() {
        try {
            ParkingVehicleDetails vehicle1 = new ParkingVehicleDetails(firstVehicle, VehicleSize.SMALL, DriverType.HANDICAPPED, "sagar");
            ParkingVehicleDetails vehicle2 = new ParkingVehicleDetails(secondVehicle, VehicleSize.SMALL, DriverType.HANDICAPPED, "arjun");
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
            ParkingVehicleDetails vehicle1 = new ParkingVehicleDetails(firstVehicle, VehicleSize.LARGE, DriverType.NORMAL, "Sagar");
            parkingLotSystem.park(firstVehicleDetails);
            parkingLotSystem.park(secondVehicleDetails);
            parkingLotSystem.park(vehicle1);
            ParkingLot highestNumOfFreeSpace = parkingLotSystem.getParkingLotInWhichVehicleIsParked(vehicle1);
            Assert.assertEquals(thirdParkingLot, highestNumOfFreeSpace);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehiclesColour_WhenFound_ShouldReturnListOfSlotNumber() {
        try {
            parkingLotSystem.park(firstVehicleDetails);
            parkingLotSystem.park(secondVehicleDetails);
            Map<ParkingLot, List<Integer>> slotNumbersByColor =
                    parkingLotSystem.getLotAndSlotListOfVehiclesByColor(VehicleColour.WHITE);
            Assert.assertEquals(1, slotNumbersByColor.get(firstParkingLot).get(0).intValue());
            Assert.assertEquals(1, slotNumbersByColor.get(secondParkingLot).get(0).intValue());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void givenBlueToyotaVehicle_WhenFound_ShouldReturnListOfSlotNumber() {
        Vehicle vehicle = new Vehicle("MH04 AB 9999", VehicleCompany.TOYOTA, VehicleColour.BLUE);
        ParkingVehicleDetails vehicleDetails = new ParkingVehicleDetails(vehicle, VehicleSize.LARGE, DriverType.NORMAL, "Sagar");
        try {
            parkingLotSystem.park(firstVehicleDetails);
            parkingLotSystem.park(vehicleDetails);
            Map<ParkingLot, List<String>> slotNumbersByCompanyAndColor =
                    parkingLotSystem.getLotAndSlotNumberByCompanyAndColor(VehicleCompany.TOYOTA, VehicleColour.BLUE);
            Assert.assertEquals("MH04 AB 9999 Sagar", slotNumbersByCompanyAndColor.get(secondParkingLot).get(0));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenBMWVehicle_WhenFound_ShouldReturnListOfSlotNumber() {
        Vehicle vehicle = new Vehicle("MH04 AB 9999", VehicleCompany.BMW, VehicleColour.WHITE);
        ParkingVehicleDetails vehicleDetails = new ParkingVehicleDetails(vehicle, VehicleSize.LARGE, DriverType.NORMAL, "Sagar");
        try {
            parkingLotSystem.park(firstVehicleDetails);
            parkingLotSystem.park(vehicleDetails);
            Map<ParkingLot, List<Integer>> slotNumbersByCompany =
                    parkingLotSystem.getSlotNumbersOfVehiclesByCompany(VehicleCompany.BMW);
            Assert.assertEquals(1, slotNumbersByCompany.get(secondParkingLot).get(0).intValue());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestToGetSlotsOfAllVehicleParkedBefore30Min_WhenFound_ShouldReturnListOfSimilarVehiclesSlotNumber() {
        try {
            parkingLotSystem.park(firstVehicleDetails);
            parkingLotSystem.park(secondVehicleDetails);
            Map<ParkingLot, List<Integer>> slotNumbersVehiclesByTime =
                    parkingLotSystem.getVehiclesParkedFromTime(30);
            Assert.assertEquals(1, slotNumbersVehiclesByTime.get(firstParkingLot).get(0).intValue());
            Assert.assertEquals(1, slotNumbersVehiclesByTime.get(secondParkingLot).get(0).intValue());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestToGetSlotsOfAllSmallHandicapped_WhenFound_ShouldReturnListOfSimilarVehiclesSlotNumber() {
        ParkingVehicleDetails vehicle1 = new ParkingVehicleDetails(firstVehicle, VehicleSize.SMALL, DriverType.HANDICAPPED, "sagar");
        ParkingVehicleDetails vehicle2 = new ParkingVehicleDetails(secondVehicle, VehicleSize.SMALL, DriverType.HANDICAPPED, "arjun");
        try {
            parkingLotSystem.park(vehicle1);
            parkingLotSystem.park(vehicle2);
            Map<ParkingLot, List<Integer>> slotNumberBySizeAndDriverType =
                    parkingLotSystem.getSlotNumbersBySizeAndDriverType(DriverType.HANDICAPPED, VehicleSize.SMALL);
            Assert.assertEquals(1, slotNumberBySizeAndDriverType.get(firstParkingLot).get(0).intValue());
            Assert.assertEquals(2, slotNumberBySizeAndDriverType.get(firstParkingLot).get(1).intValue());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}
