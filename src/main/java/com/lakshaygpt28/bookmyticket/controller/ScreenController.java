package com.lakshaygpt28.bookmyticket.controller;

import com.lakshaygpt28.bookmyticket.model.Screen;
import com.lakshaygpt28.bookmyticket.service.ScreenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ScreenController {
    private final ScreenService screenService;

    @Autowired
    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @PostMapping("/screens")
    public ResponseEntity<Screen> addScreen(@RequestBody @Valid Screen newScreen) {
        Screen savedScreen = screenService.addScreen(newScreen);
        return new ResponseEntity<>(savedScreen, HttpStatus.CREATED);
    }

    @GetMapping("/screens")
    public ResponseEntity<List<Screen>> getAllScreens() {
        List<Screen> screens = screenService.getAllScreens();
        return ResponseEntity.ok(screens);
    }

    @PostMapping("/theatres/{theatreId}/screens")
    public ResponseEntity<List<Screen>> addScreensByTheatreId(@PathVariable Long theatreId, @RequestBody @Valid List<Screen> screens) {
        List<Screen> savedScreens = screenService.addScreensByTheatreId(theatreId, screens);
        return new ResponseEntity<>(savedScreens, HttpStatus.CREATED);
    }

    @GetMapping("/theatre/{theatreId}/screens")
    public ResponseEntity<List<Screen>> getScreensByTheatreId(@PathVariable Long theatreId) {
        List<Screen> screens = screenService.getScreensByTheatreId(theatreId);
        return ResponseEntity.ok(screens);
    }

    @GetMapping("/screens/{id}")
    public ResponseEntity<Screen> getScreenById(@PathVariable Long id) {
        Screen screen = screenService.getScreenById(id)
                .orElseThrow(() -> new RuntimeException("Screen not found"));
        return ResponseEntity.ok(screen);
    }

    @DeleteMapping("/screens/{id}")
    public ResponseEntity<Void> deleteScreen(@PathVariable Long id) {
        screenService.deleteScreen(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/screens/{id}")
    public ResponseEntity<Screen> updateScreen(@PathVariable Long id, @RequestBody Screen screen) {
        Screen updatedScreen = screenService.updateScreen(id, screen);
        return ResponseEntity.ok(updatedScreen);
    }
}
