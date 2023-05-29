package com.vodafone.garage.model;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@RequiredArgsConstructor
public class Ticket {
    private String plateNumber;
    private String color;
    private String type;
    private LocalDateTime creationTime;
    public Ticket(String plateNumber, String color, String type, LocalDateTime creationTime) {
        this.plateNumber = plateNumber;
        this.color = color;
        this.type = type;
        this.creationTime = creationTime;
    }

    public Ticket(String plateNumber, LocalDateTime now) {
    }

    public Ticket(String plateNumber, boolean contains, LocalDateTime now) {
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }
}