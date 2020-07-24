package com.bridgelabz.parkinglot.servicetest;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.observer.Owner;
import com.bridgelabz.parkinglot.model.Vehicle;
import com.bridgelabz.parkinglot.observer.AirportSecurityService;
import com.bridgelabz.parkinglot.service.ParkingLot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class ParkingLotTest {

    ParkingLot parkingLot = null;

    @Before
    public void init() {
        parkingLot = new ParkingLot(2);

    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        Vehicle vehicle = new Vehicle();
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
        Vehicle vehicle = null;
        try {
            parkingLot.parkVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.INVALID_VEHICLE, e.exceptionType);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleNotFound_ShouldThrowException() {
        Vehicle vehicle = new Vehicle();
        try {
            parkingLot.unParkVehicle(vehicle);
            parkingLot.getSlot(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_A_VEHICLE, e.exceptionType);
        }
    }

    @Test
    public void givenAVehicle_WhenAlreadyParked_ShouldThrowException() {
        Vehicle vehicle = new Vehicle();
        try {
            parkingLot.parkVehicle(vehicle);
            parkingLot.parkVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PRESENT, e.exceptionType);
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        Vehicle vehicle = new Vehicle();
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
        Vehicle vehicle = new Vehicle();
        try {
            parkingLot.parkVehicle(vehicle);
            parkingLot.parkVehicle(new Vehicle());
            Assert.assertEquals("Capacity is Full", airportSecurityService.getParkingLotStatus());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenNotParkedVehicle_WhenUnParked_ShouldThrowException() {
        Vehicle vehicle = new Vehicle();
        try {
            parkingLot.unParkVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_A_VEHICLE, e.exceptionType);
        }
    }

    @Test
    public void givenANullVehicle_WhenUnParked_ShouldThrowException() {
        Vehicle vehicle = null;
        try {
            parkingLot.unParkVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.INVALID_VEHICLE, e.exceptionType);
        }
    }

    @Test
    public void givenVehicles_WhenParkingLotIsFull_ShouldThrowException() {
        try {
            parkingLot.parkVehicle(new Vehicle());
            parkingLot.parkVehicle(new Vehicle());
            parkingLot.parkVehicle(new Vehicle());
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, e.exceptionType);
        }
    }

    @Test
    public void givenParkingLotVacancy_WhenFull_ShouldInformTheOwner() {
        Owner owner = new Owner();
        parkingLot.registerParkingLotObserver(owner);
        try {
            Vehicle vehicle = new Vehicle();
            parkingLot.parkVehicle(vehicle);
            parkingLot.unParkVehicle(vehicle);
            Assert.assertEquals("Capacity Available", owner.getParkingLotStatus());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicleToAttendant_WhenParkedAsPerProvidedSlot_ShouldReturnTrue() {
        Vehicle vehicle = new Vehicle();
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
        Vehicle vehicle = new Vehicle();
        try {
            parkingLot.parkVehicle(4, vehicle);
            Integer slotNumber = parkingLot.findVehicle(vehicle);
            Assert.assertEquals((Integer) 4, slotNumber);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicle_WhenParked_ShouldReturnParkingTime() {
        Vehicle vehicle = new Vehicle();
        try {
            LocalDateTime localDateTime = LocalDateTime.now().withNano(0);
            parkingLot.parkVehicle(vehicle);
            LocalDateTime localDateTime1 = parkingLot.getParkingTime(vehicle);
            Assert.assertEquals(localDateTime, localDateTime1);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}
