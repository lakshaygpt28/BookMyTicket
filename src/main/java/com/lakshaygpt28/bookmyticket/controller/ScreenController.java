package com.lakshaygpt28.bookmyticket.controller;

import com.lakshaygpt28.bookmyticket.dto.ScreenDTO;
import com.lakshaygpt28.bookmyticket.dto.response.ApiResponse;
import com.lakshaygpt28.bookmyticket.mapper.ScreenMapper;
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

    @PostMapping("/cities/{cityId}/theatres/{theatreId}/screens")
    public ResponseEntity<ApiResponse<List<ScreenDTO>>> addScreensByTheatreId(@PathVariable Long cityId, @PathVariable Long theatreId, @RequestBody @Valid List<Screen> screens) {
        List<Screen> savedScreens = screenService.addScreensByTheatreIdAndCityId(cityId, theatreId, screens);
        List<ScreenDTO> screenDTOs = savedScreens.stream().map(ScreenMapper.INSTANCE::toDto).toList();
        ApiResponse<List<ScreenDTO>> response = ApiResponse.<List<ScreenDTO>>builder()
                .success(true)
                .message("Screens added successfully for theatreId: " + theatreId)
                .response(screenDTOs)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/cities/{cityId}/theatres/{theatreId}/screens")
    public ResponseEntity<ApiResponse<List<ScreenDTO>>> getScreensByTheatreId(@PathVariable Long cityId, @PathVariable Long theatreId) {
        List<Screen> screens = screenService.getScreensByTheatreIdAndCityId(cityId, theatreId);
        List<ScreenDTO> screenDTOs = screens.stream().map(ScreenMapper.INSTANCE::toDto).toList();
        ApiResponse<List<ScreenDTO>> response = ApiResponse.<List<ScreenDTO>>builder()
                .success(true)
                .message("Screens retrieved successfully for theatreId: " + theatreId)
                .response(screenDTOs)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cities/{cityId}/theatres/{theatreId}/screens/{id}")
    public ResponseEntity<ApiResponse<ScreenDTO>> getScreenByIdAndTheatreIdAndCityId(@PathVariable Long cityId, @PathVariable Long theatreId, @PathVariable Long id) {
        Screen screen = screenService.getScreenByIdAndTheatreIdAndCityId(cityId, theatreId, id);
        ScreenDTO screenDTO = ScreenMapper.INSTANCE.toDto(screen);
        ApiResponse<ScreenDTO> response = ApiResponse.<ScreenDTO>builder()
                .success(true)
                .message("Screen retrieved successfully")
                .response(screenDTO)
                .build();
        return ResponseEntity.ok(response);
    }
}
