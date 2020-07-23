package com.bridgelabz.parkinglot.observer;

public interface IObserver {

    String getParkingLotStatus();
    void update(String parkingLotStatus);
}
