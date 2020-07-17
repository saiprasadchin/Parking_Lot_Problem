package com.bridgelabz.servicetest;

import com.bridgelabz.exception.ParkingLotServiceException;
import com.bridgelabz.model.Vehicle;
import com.bridgelabz.service.ParkingLotService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotServiceTest {

    ParkingLotService parkingLotService = null;
    @Before
    public void init(){
        parkingLotService = new ParkingLotService();
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        Vehicle vehicle = new Vehicle("Car","Sai","MH0404");
        boolean isParked = false;
        try {
            isParked = parkingLotService.parkVehicle(vehicle);
        } catch (ParkingLotServiceException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenAVehicle_WhenAlreadyParked_ShouldReturnFalse() {
        Vehicle vehicle = new Vehicle("Car","Sai","MH0404");
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
        Vehicle vehicle = new Vehicle("Car","Sai","MH0404");

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
    public void givenCapacity_WhenAvailableShould_InformToOWner() {
        Vehicle vehicle = new Vehicle("Car","Sai","MH0404");

        boolean isUnParked = false;
        try {
            parkingLotService.parkVehicle(vehicle);
            isUnParked = parkingLotService.unParkVehicle(vehicle);
        } catch (ParkingLotServiceException e) {
            e.printStackTrace();
        }
        Assert.assertFalse(parkingLotService.isCapicityFull);
    }

    @Test
    public void givenAVehicles_WhenParkingLotIsFull_ShouldThrowException() {
        parkingLotService.parkingCapacity = 1;
        Vehicle vehicle = new Vehicle("Car","Sai","MH0404");
        try {
            parkingLotService.parkVehicle(vehicle);
        } catch (ParkingLotServiceException e) {
            Assert.assertEquals(ParkingLotServiceException.ExceptionType.PARKING_LOT_IS_FULL,e.exceptionType);
        }
    }
}
