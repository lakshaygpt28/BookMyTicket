package com.lakshaygpt28.bookmyticket.controller;

import com.lakshaygpt28.bookmyticket.model.ShowSeat;
import com.lakshaygpt28.bookmyticket.service.ShowSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ShowSeatController {
    private final ShowSeatService showSeatService;

    @Autowired
    public ShowSeatController(ShowSeatService showSeatService) {
        this.showSeatService = showSeatService;
    }

    @GetMapping("shows/{showId}/seats/available")
    public ResponseEntity<List<ShowSeat>> getAvailableShowSeats(@PathVariable Long showId) {
        List<ShowSeat> showSeats = showSeatService.getAvailableShowSeats(showId);
        return ResponseEntity.ok(showSeats);
    }

    @GetMapping("shows/{showId}/seats")
    public ResponseEntity<List<ShowSeat>> getShowSeats(@PathVariable Long showId) {
        List<ShowSeat> showSeats = showSeatService.getShowSeats(showId);
        return ResponseEntity.ok(showSeats);
    }
}
