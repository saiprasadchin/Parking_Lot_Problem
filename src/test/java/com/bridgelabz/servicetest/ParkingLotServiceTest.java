package com.bridgelabz.servicetest;

import com.bridgelabz.model.Vehicle;
import com.bridgelabz.service.ParkingLotService;
import org.junit.Assert;
import org.junit.Test;

public class ParkingLotServiceTest {

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        Vehicle vehicle = new Vehicle();
        ParkingLotService parkingLotService = new ParkingLotService();
        boolean isParked = parkingLotService.parkVehicle(vehicle);
        Assert.assertTrue(isParked);
    }
}
