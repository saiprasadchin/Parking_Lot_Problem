package com.bridgelabz.servicetest;

import com.bridgelabz.exception.ParkingLotServiceException;
import com.bridgelabz.model.Vehicle;
import com.bridgelabz.service.AirportSecurityService;
import com.bridgelabz.service.Owner;
import com.bridgelabz.service.ParkingLotService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotServiceTest {

    ParkingLotService parkingLotService = null;

    @Before
    public void init() {
        parkingLotService = new ParkingLotService();
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        Vehicle vehicle = new Vehicle("Car", "Sai", "MH0404");
        boolean isParked = false;
        try {
            isParked = parkingLotService.parkVehicle(vehicle);
        } catch (ParkingLotServiceException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenANullVehicle_WhenParked_ShouldReturnTrue() {
        Vehicle vehicle = null;
        boolean isParked = false;
        try {
            isParked = parkingLotService.parkVehicle(vehicle);
        } catch (ParkingLotServiceException e) {
            Assert.assertEquals(ParkingLotServiceException.ExceptionType.INVALID_VEHICLE, e.exceptionType);
        }
    }

    @Test
    public void givenAVehicle_WhenAlreadyParked_ShouldReturnFalse() {
        Vehicle vehicle = new Vehicle("Car", "Sai", "MH0404");
        boolean isParked = false;
        try {
            isParked = parkingLotService.parkVehicle(vehicle);
            isParked = parkingLotService.parkVehicle(vehicle);
        } catch (ParkingLotServiceException e) {
            e.printStackTrace();
        }
        Assert.assertFalse(isParked);
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        Vehicle vehicle = new Vehicle("Car", "Sai", "MH0404");
        boolean isUnParked = false;
        try {
            parkingLotService.parkVehicle(vehicle);
            isUnParked = parkingLotService.unParkVehicle(vehicle);
        } catch (ParkingLotServiceException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(isUnParked);
    }

    @Test
    public void givenVehicles_WhenParkingLotIsFull_ShouldInformSecurity() {
        parkingLotService.parkingCapacity = 1;
        Vehicle vehicle = new Vehicle("Car", "Sai", "MH0404");
        try {
            parkingLotService.parkVehicle(vehicle);
            AirportSecurityService security = (AirportSecurityService)parkingLotService.parkingLotListeners.get(ParkingLotService.SECURITY);
            Assert.assertEquals("Capacity is Full",security.getParkingLotStatus());
        } catch (ParkingLotServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenNotParkedVehicle_WhenUnParked_ShouldThrowException() {
        Vehicle vehicle = new Vehicle("Car", "Sai", "MH0404");
        boolean isUnParked = false;
        try {
            isUnParked = parkingLotService.unParkVehicle(vehicle);
        } catch (ParkingLotServiceException e) {
            Assert.assertEquals(ParkingLotServiceException.ExceptionType.NO_SUCH_A_VEHICLE, e.exceptionType);
        }
    }

    @Test
    public void givenANullVehicle_WhenUnParked_ShouldThrowException() {
        Vehicle vehicle = null;
        boolean isUnParked = false;
        try {
            isUnParked = parkingLotService.unParkVehicle(vehicle);
        } catch (ParkingLotServiceException e) {
            Assert.assertEquals(ParkingLotServiceException.ExceptionType.INVALID_VEHICLE, e.exceptionType);
        }
    }

    @Test
    public void givenVehicles_WhenParkingLotIsFull_ShouldThrowException() {
        parkingLotService.parkingCapacity = 1;
        Vehicle vehicle = new Vehicle("Car", "Sai", "MH0404");
        try {
            parkingLotService.parkVehicle(vehicle);
        } catch (ParkingLotServiceException e) {
            Assert.assertEquals(ParkingLotServiceException.ExceptionType.PARKING_LOT_IS_FULL, e.exceptionType);
        }
    }

    @Test
    public void givenCapacity_WhenAvailableShould_InformToOWner() {
        Vehicle vehicle = new Vehicle("Car", "Saiprasad", "MH0405");
        try {
            parkingLotService.parkVehicle(vehicle);
            parkingLotService.unParkVehicle(vehicle);
            Owner owner = (Owner) parkingLotService.parkingLotListeners.get(ParkingLotService.OWNER);
            Assert.assertEquals("Capacity Available",owner.getParkingLotStatus());
        } catch (ParkingLotServiceException e) {
            e.printStackTrace();
        }
    }
}
