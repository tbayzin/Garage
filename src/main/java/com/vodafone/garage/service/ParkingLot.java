package com.vodafone.garage.service;



import com.vodafone.garage.model.Slot;
import com.vodafone.garage.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLot {
    private ArrayList<Vehicle> vehicles;
    private Slot[] slots;
    private int totalSlots;

    public ParkingLot(int totalSlots) {
        this.totalSlots = totalSlots;
        vehicles = new ArrayList<>();
        slots = new Slot[totalSlots];
        for (int i = 0; i < totalSlots; i++) {
            slots[i] = new Slot(i + 1);
        }
    }

    public String park(Vehicle vehicle) {
        if (vehicles.equals(vehicle.getPlateNumber())) {
            return "This vehicle is already in the garage";
        }
        int requiredSlots = getRequiredSlots(vehicle.getType());
        if (vehicle.getPlateNumber().isEmpty()) {
            return "Plate number section is empty";
        }
        if (requiredSlots == -1) {
            return "Unknown vehicle type";
        }
        int[] availableSlots = findAvailableSlots(requiredSlots);
        if (availableSlots == null) {
            return "No slots available";
        }
        for (int slotNumber : availableSlots) {
            slots[slotNumber - 1].assignVehicle(vehicle);
        }
        vehicles.add(vehicle);
        return "Allocated " + requiredSlots + " slots.";
    }

    public String leave(String plateNumber) {
        Vehicle vehicle = null;
        for (Vehicle v : vehicles) {
            if (v.getPlateNumber().equals(plateNumber)) {
                vehicle = v;
                break;
            }
        }
        if (vehicle == null) {
            return "Vehicle not found";
        }
        for (Slot slot : slots) {
            if (slot.getVehicle() == vehicle) {
                slot.removeVehicle();
            }
        }
        vehicles.remove(vehicle);
        return "Vehicle with plate number " + plateNumber + " has left the parking lot.";
    }

    public String getStatus() {
        StringBuilder status = new StringBuilder();

        Map<Vehicle, List<Integer>> vehicleSlotMap = new HashMap<>();
        for (Slot slot : slots) {
            if (!slot.isAvailable()) {
                Vehicle vehicle = slot.getVehicle();
                int slotNumber = slot.getSlotNumber();

                List<Integer> slotNumbers = vehicleSlotMap.getOrDefault(vehicle, new ArrayList<>());
                slotNumbers.add(slotNumber);
                vehicleSlotMap.put(vehicle, slotNumbers);
            }
        }

        for (Map.Entry<Vehicle, List<Integer>> entry : vehicleSlotMap.entrySet()) {
            Vehicle vehicle = entry.getKey();
            List<Integer> slotNumbers = entry.getValue();

            status.append(vehicle.getPlateNumber()).append(" ")
                    .append(vehicle.getColor()).append(" ").append(String.join(",", slotNumbers.toString())).append("\n");
        }

        return status.toString();
    }

    private int getRequiredSlots(String vehicleType) {
        switch (vehicleType.toLowerCase()) {
            case "car":
                return 1;
            case "jeep":
                return 2;
            case "truck":
                return 4;
            default:
                return -1;
        }
    }

    private int[] findAvailableSlots(int requiredSlots) {
        int[] availableSlots = new int[requiredSlots];
        int index = 0;
        for (int i = 0; i < slots.length - 1 && index < requiredSlots; i++) {
            if (slots[i + 1].isAvailable()) {
                if (index == 0 || slots[i].isAvailable() && slots[i+1].isAvailable()) {
                    availableSlots[index] = slots[i + 1].getSlotNumber();
                    index++;
                }
            } else {
                index = 0;
            }
        }
        if (index == requiredSlots) {
            return availableSlots;
        } else {
            return null;
        }
    }
}