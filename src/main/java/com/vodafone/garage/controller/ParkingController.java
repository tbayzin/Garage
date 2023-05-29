package com.vodafone.garage.controller;

import com.vodafone.garage.model.Ticket;
import com.vodafone.garage.model.Vehicle;
import com.vodafone.garage.service.ParkingLot;
import com.vodafone.garage.validation.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/")
public class ParkingController {

    ValidationService validationService;
    private ParkingLot parkingLot;

    public ParkingController() {
        parkingLot = new ParkingLot(10);
    }

    @PostMapping("/park")
    public ResponseEntity<String> park(@RequestBody Vehicle vehicle) {
        String response = parkingLot.park(vehicle);
        LocalDateTime now = LocalDateTime.now();
        Ticket ticket = new Ticket(vehicle.getPlateNumber(), vehicle.getColor(), vehicle.getType(), now);
        System.out.println("Ticket information of the parked car: " + "color: " + ticket.getColor() + "plate: "
                + ticket.getPlateNumber() + " Ticket given at: "
                + ticket.getCreationTime());
        HttpStatus status = response.equals("No slots available") ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return new ResponseEntity<>(parkingLot.getStatus(), HttpStatus.OK);
    }

    @DeleteMapping("/leave/{plateNumber}")
    public ResponseEntity<String> leave(@PathVariable String plateNumber) {
        String response = parkingLot.leave(plateNumber);
        LocalDateTime now = LocalDateTime.now();
        Ticket ticket = new Ticket(plateNumber, now);
        System.out.println("Ticket information of the leaving car: " +
                " " + plateNumber + " left time: " + now);
        HttpStatus status = response.equals("Vehicle not found") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(response, status);
    }
}

