package com.lakshaygpt28.bookmyticket.controller;

import com.lakshaygpt28.bookmyticket.model.Theatre;
import com.lakshaygpt28.bookmyticket.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;



import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TheatreController {

    private final TheatreService theatreService;

    @Autowired
    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @GetMapping("/cities/{cityId}/theatres")
    public ResponseEntity<List<Theatre>> getTheatresByCityId(@PathVariable Long cityId) {
        List<Theatre> theatres =  theatreService.getTheatresByCityId(cityId);
        return ResponseEntity.ok(theatres);
    }

    @PostMapping("/cities/{cityId}/theatres")
    public ResponseEntity<List<Theatre>> addTheatresByCityId(@PathVariable Long cityId, @RequestBody @Valid List<Theatre> theatres) {
        List<Theatre> savedTheatres = theatreService.addTheatresByCityId(cityId, theatres);
        return new ResponseEntity<>(savedTheatres, HttpStatus.CREATED);
    }

    @GetMapping("/theatres")
    public ResponseEntity<List<Theatre>> getAllTheatres() {
        List<Theatre> theatres = theatreService.getAllTheatres();
        return ResponseEntity.ok(theatres);
    }

    @PostMapping("/theatres")
    public ResponseEntity<Theatre> addTheatre(@RequestBody @Valid Theatre theatre) {
        Theatre savedTheatre = theatreService.addTheatre(theatre);
        return new ResponseEntity<>(savedTheatre, HttpStatus.CREATED);
    }

    @GetMapping("/theatres/{id}")
    public ResponseEntity<Theatre> getTheatreById(@PathVariable Long id) {
        Theatre theatre = theatreService.getTheatreById(id)
                .orElseThrow(() -> new RuntimeException("Theatre not found"));
        return ResponseEntity.ok(theatre);
    }

}
