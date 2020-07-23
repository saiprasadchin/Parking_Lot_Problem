package com.bridgelabz.parkinglot.observer;


public class AirportSecurityService implements IObserver {

    private String parkingLotStatus;

    @Override
    public String getParkingLotStatus() {
        return parkingLotStatus;
    }

    @Override
    public void update(String parkingLotStatus) {
        this.parkingLotStatus = parkingLotStatus;
    }
}
