package com.bridgelabz.model;

import com.bridgelabz.model.IParkingLotListener;

public class Owner implements IParkingLotListener {

    private String parkingLotStatus;

    public String getParkingLotStatus() {
        return parkingLotStatus;
    }

    @Override
    public void inform(String parkingLotStatus) {
        this.parkingLotStatus = parkingLotStatus;
    }
}
