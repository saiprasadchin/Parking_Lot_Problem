package com.bridgelabz.observer;

public interface IObserver {

    String getParkingLotStatus();
    void update(String parkingLotStatus);
}
