package com.bridgelabz.parkinglot.servicetest;

import com.bridgelabz.parkinglot.exception.ParkingLotServiceException;
import com.bridgelabz.parkinglot.observer.Owner;
import com.bridgelabz.parkinglot.model.Vehicle;
import com.bridgelabz.parkinglot.observer.AirportSecurityService;
import com.bridgelabz.parkinglot.service.ParkingLotService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class ParkingLotServiceTest {

    ParkingLotService parkingLotService = null;

    @Before
    public void init() {
        parkingLotService = new ParkingLotService(2);

    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        Vehicle vehicle = new Vehicle();
        boolean isParked;
        try {
            parkingLotService.parkVehicle(vehicle);
            isParked = parkingLotService.isPresent(vehicle);
            Assert.assertTrue(isParked);
        } catch (ParkingLotServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenANullVehicle_WhenParked_ShouldThrowException() {
        Vehicle vehicle = null;
        try {
            parkingLotService.parkVehicle(vehicle);
        } catch (ParkingLotServiceException e) {
            Assert.assertEquals(ParkingLotServiceException.ExceptionType.INVALID_VEHICLE, e.exceptionType);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleNotFound_ShouldThrowException() {
        Vehicle vehicle = new Vehicle();
        try {
            parkingLotService.unParkVehicle(vehicle);
            parkingLotService.getSlot(vehicle);
        } catch (ParkingLotServiceException e) {
            Assert.assertEquals(ParkingLotServiceException.ExceptionType.NO_SUCH_A_VEHICLE, e.exceptionType);
        }
    }

    @Test
    public void givenAVehicle_WhenAlreadyParked_ShouldThrowException() {
        Vehicle vehicle = new Vehicle();
        try {
            parkingLotService.parkVehicle(vehicle);
            parkingLotService.parkVehicle(vehicle);
        } catch (ParkingLotServiceException e) {
            Assert.assertEquals(ParkingLotServiceException.ExceptionType.VEHICLE_ALREADY_PRESENT, e.exceptionType);
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        Vehicle vehicle = new Vehicle();
        boolean isUnParked;
        try {
            parkingLotService.parkVehicle(vehicle);
            parkingLotService.unParkVehicle(vehicle);
            isUnParked = parkingLotService.isPresent(vehicle);
            Assert.assertFalse(isUnParked);
        } catch (ParkingLotServiceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenVehicles_WhenParkingLotIsFull_ShouldInformSecurity() {
        AirportSecurityService airportSecurityService = new AirportSecurityService();
        parkingLotService.registerParkingLotObserver(airportSecurityService);
        Vehicle vehicle = new Vehicle();
        try {
            parkingLotService.parkVehicle(vehicle);
            parkingLotService.parkVehicle(new Vehicle());
            Assert.assertEquals("Capacity is Full", airportSecurityService.getParkingLotStatus());
        } catch (ParkingLotServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenNotParkedVehicle_WhenUnParked_ShouldThrowException() {
        Vehicle vehicle = new Vehicle();
        try {
            parkingLotService.unParkVehicle(vehicle);
        } catch (ParkingLotServiceException e) {
            Assert.assertEquals(ParkingLotServiceException.ExceptionType.NO_SUCH_A_VEHICLE, e.exceptionType);
        }
    }

    @Test
    public void givenANullVehicle_WhenUnParked_ShouldThrowException() {
        Vehicle vehicle = null;
        try {
            parkingLotService.unParkVehicle(vehicle);
        } catch (ParkingLotServiceException e) {
            Assert.assertEquals(ParkingLotServiceException.ExceptionType.INVALID_VEHICLE, e.exceptionType);
        }
    }

    @Test
    public void givenVehicles_WhenParkingLotIsFull_ShouldThrowException() {
        try {
            parkingLotService.parkVehicle(new Vehicle());
            parkingLotService.parkVehicle(new Vehicle());
            parkingLotService.parkVehicle(new Vehicle());
        } catch (ParkingLotServiceException e) {
            Assert.assertEquals(ParkingLotServiceException.ExceptionType.PARKING_LOT_IS_FULL, e.exceptionType);
        }
    }

    @Test
    public void givenParkingLotVacancy_WhenFull_ShouldInformTheOwner() {
        Owner owner = new Owner();
        parkingLotService.registerParkingLotObserver(owner);
        try {
            Vehicle vehicle = new Vehicle();
            parkingLotService.parkVehicle(vehicle);
            parkingLotService.unParkVehicle(vehicle);
            Assert.assertEquals("Capacity Available", owner.getParkingLotStatus());
        } catch (ParkingLotServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicleToAttendant_WhenParkedAsPerProvidedSlot_ShouldReturnTrue() {
        Vehicle vehicle = new Vehicle();
        try {
            parkingLotService.parkVehicle(1, vehicle);
            boolean isPresent = parkingLotService.isPresent(vehicle);
            Assert.assertTrue(isPresent);
        } catch (ParkingLotServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleFound_ShouldReturnVehicleSlot() {
        Vehicle vehicle = new Vehicle();
        try {
            parkingLotService.parkVehicle(4, vehicle);
            Integer slotNumber = parkingLotService.findVehicle(vehicle);
            Assert.assertEquals((Integer) 4, slotNumber);
        } catch (ParkingLotServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicle_WhenParked_ShouldReturnParkingTime() {
        Vehicle vehicle = new Vehicle();
        try {
            LocalDateTime localDateTime = LocalDateTime.now().withNano(0);
            parkingLotService.parkVehicle(vehicle);
            LocalDateTime localDateTime1 = parkingLotService.getParkingTime(vehicle);
            Assert.assertEquals(localDateTime, localDateTime1);
        } catch (ParkingLotServiceException e) {
            e.printStackTrace();
        }
    }
}
