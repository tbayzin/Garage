package com.vodafone.garage.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

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
        int requiredSlots = getRequiredSlots(vehicle.getType());
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

    public String leave(String registrationNumber) {
        Vehicle vehicle = null;
        for (Vehicle v : vehicles) {
            if (v.getPlateNumber().equals(registrationNumber)) {
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
        return "Vehicle with registration number " + registrationNumber + " has left the parking lot.";
    }

    public String getStatus() {
        StringBuilder status = new StringBuilder();
        for (Slot slot : slots) {
            if (!slot.isAvailable()) {
                status.append(slot.getVehicle().getPlateNumber()).append(" ")
                        .append(slot.getVehicle().getColor()).append(" [").append(slot.getSlotNumber()).append("]\n");
            }
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
        for (int i = 0; i < slots.length - 1 && index < requiredSlots; i++) { // Son slotu kontrol etmiyoruz
            if (slots[i + 1].isAvailable()) {
                // Arada bir slot boşluk bırakılıyor
                if (index == 0 || slots[i].isAvailable() && slots[i+1].isAvailable()) { // Hem mevcut hem de bir sonraki slotun boş olması gerekiyor
                    availableSlots[index] = slots[i + 1].getSlotNumber();
                    index++;
                }
            } else {
                // Eğer aradaki slot dolu ise, yeni bir slot dizisi başlatılıyor
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