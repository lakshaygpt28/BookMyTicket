package com.lakshaygpt28.bookmyticket.controller;

import com.lakshaygpt28.bookmyticket.model.Seat;
import com.lakshaygpt28.bookmyticket.service.SeatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SeatController {
    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping("/seats")
    public ResponseEntity<Seat> addSeat(@RequestBody @Valid Seat seat) {
        Seat savedSeat = seatService.addSeat(seat);
        return ResponseEntity.ok(savedSeat);
    }

    @GetMapping("screens/{screenId}/seats")
    public ResponseEntity<List<Seat>> getSeatsByScreenId(@PathVariable Long screenId) {
        List<Seat> seats = seatService.getSeatsByScreenId(screenId);
        return ResponseEntity.ok(seats);
    }

    @PostMapping("/screens/{screenId}/seats")
    public ResponseEntity<List<Seat>> addSeatsByScreenId(@PathVariable Long screenId, @RequestBody @Valid List<Seat> seats) {
        List<Seat> savedSeats = seatService.addSeatsByScreenId(screenId, seats);
        return new ResponseEntity<>(savedSeats, HttpStatus.CREATED);
    }

}
