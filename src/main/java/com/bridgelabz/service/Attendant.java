package com.bridgelabz.service;

import com.bridgelabz.model.Vehicle;

import java.util.HashMap;

public class Attendant {
    public HashMap<Integer, Vehicle> attendantParkedVehicle(int slot, Vehicle vehicle, HashMap<Integer, Vehicle> parkedVehicles) {
                if (parkedVehicles.get(slot) == null)
                    parkedVehicles.put(slot,vehicle);
        return parkedVehicles;
    }
}
