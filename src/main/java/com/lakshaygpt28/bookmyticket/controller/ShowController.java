package com.lakshaygpt28.bookmyticket.controller;

import com.lakshaygpt28.bookmyticket.model.Show;
import com.lakshaygpt28.bookmyticket.service.ShowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ShowController {
    private final ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }


    @GetMapping("/shows/{id}")
    public ResponseEntity<Show> getShowById(@PathVariable Long id) {
        Show show = showService.getShowById(id)
                .orElseThrow(() -> new RuntimeException("Show not found with id: " + id));
        return ResponseEntity.ok(show);
    }


    @PostMapping("/shows")
    public ResponseEntity<List<Show>> addShows(@RequestBody @Valid List<Show> newShows) {
        List<Show> savedShow = showService.addShows(newShows);
        return ResponseEntity.ok(savedShow);
    }

    @GetMapping("/shows")
    public ResponseEntity<List<Show>> getAllShows() {
        List<Show> shows = showService.getAllShows();
        return ResponseEntity.ok(shows);
    }

    @GetMapping("/theatres/{theatreId}/shows")
    public ResponseEntity<List<Show>> getShowsByTheatreId(@PathVariable Long theatreId) {
        List<Show> shows = showService.getShowsByTheatreId(theatreId);
        return ResponseEntity.ok(shows);
    }
}
