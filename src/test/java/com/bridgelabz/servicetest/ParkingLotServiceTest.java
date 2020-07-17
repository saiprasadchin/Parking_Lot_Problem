package com.bridgelabz.servicetest;

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
        boolean isParked = parkingLotService.parkVehicle(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        Vehicle vehicle = new Vehicle("Car","Sai","MH0404");
        boolean isUnParked = parkingLotService.unParkVehicle(vehicle);
        Assert.assertTrue(isUnParked);
    }

    @Test
    public void givenAVehicles_WhenParkingLotIsFull_ShouldReturnFalse() {
        parkingLotService.parkingCapacity = 1;
        Vehicle vehicle = new Vehicle("Car","Sai","MH0404");
        parkingLotService.parkVehicle(vehicle);
        Assert.assertTrue(parkingLotService.isCapicityFull);
    }
}
