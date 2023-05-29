package com.vodafone.garage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Slot {
    private int slotNumber;
    private Vehicle vehicle;


    public Slot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.vehicle = null;
    }


    public boolean isAvailable() {
        return vehicle == null;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void assignVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void removeVehicle() {
        this.vehicle = null;
    }
}