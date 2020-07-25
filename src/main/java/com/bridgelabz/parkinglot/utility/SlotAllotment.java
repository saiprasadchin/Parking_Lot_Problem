package com.bridgelabz.parkinglot.utility;

import com.bridgelabz.parkinglot.exception.ParkingLotException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class SlotAllotment {

    public Integer parkingLotCapacity;
    public List<Integer> availableParkingSlots;

    public SlotAllotment(int parkingLotCapacity) {
        this.parkingLotCapacity = parkingLotCapacity;
        this.setInitialParkingStatus(parkingLotCapacity);
    }

    public void setInitialParkingStatus(Integer parkingLotCapacity) {
        this.availableParkingSlots = new ArrayList<>();
        IntStream.range(1, parkingLotCapacity + 1).forEachOrdered(slots -> this.availableParkingSlots.add(slots));
    }

    public void parkUpdate(Integer slot) {
        this.availableParkingSlots.remove(slot);
    }

    public void unParkUpdate(Integer slot) {
        this.availableParkingSlots.add(slot);
        Collections.sort(this.availableParkingSlots);
    }

    public Integer getNearestParkingSlot() throws ParkingLotException {
        try {
            return this.availableParkingSlots.remove(0);
        } catch (IndexOutOfBoundsException e) {
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, "Parking Full");
        }
    }
}
