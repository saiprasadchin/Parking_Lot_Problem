package com.bridgelabz.model;

public class AirportSecurityService implements IParkingLotListener {

    private String parkingLotStatus;

    public String getParkingLotStatus() {
        return parkingLotStatus;
    }

    @Override
    public void inform(String parkingLotStatus) {
        this.parkingLotStatus = parkingLotStatus;
    }
}
