package com.lakshaygpt28.bookmyticket.controller;

import com.lakshaygpt28.bookmyticket.dto.response.ApiResponse;
import com.lakshaygpt28.bookmyticket.dto.TheatreDTO;
import com.lakshaygpt28.bookmyticket.mapper.TheatreMapper;
import com.lakshaygpt28.bookmyticket.model.City;
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
    public ResponseEntity<ApiResponse<List<TheatreDTO>>> getTheatresByCityId(@PathVariable Long cityId) {
        List<Theatre> theatres =  theatreService.getTheatresByCityId(cityId);
        List<TheatreDTO> theatreDTOs = theatres.stream().map(TheatreMapper.INSTANCE::toDto).toList();
        ApiResponse<List<TheatreDTO>> response = ApiResponse.<List<TheatreDTO>>builder()
                .success(true)
                .message("Theatres fetched successfully for cityId: " + cityId)
                .response(theatreDTOs)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cities/{cityId}/theatres")
    public ResponseEntity<List<Theatre>> addTheatresByCityId(@PathVariable Long cityId, @RequestBody @Valid List<Theatre> theatres) {
        List<Theatre> savedTheatres = theatreService.addTheatresByCityId(cityId, theatres);
        List<TheatreDTO> theatreDTOS = savedTheatres.stream().map(TheatreMapper.INSTANCE::toDto).toList();
        ApiResponse<List<TheatreDTO>> response = ApiResponse.<List<TheatreDTO>>builder()
                .success(true)
                .message("Theatres added successfully for cityId: " + cityId)
                .response(theatreDTOS)
                .build();
        return new ResponseEntity<>(savedTheatres, HttpStatus.CREATED);
    }

    @GetMapping("/cities/{cityId}/theatres/{id}")
    public ResponseEntity<ApiResponse<TheatreDTO>> getTheatreById(
            @PathVariable Long cityId,
            @PathVariable Long id) {
        Theatre theatre = theatreService.getTheatreByCityAndId(cityId, id);
        TheatreDTO theatreDTO = TheatreMapper.INSTANCE.toDto(theatre);
        ApiResponse<TheatreDTO> response = ApiResponse.<TheatreDTO>builder()
                .success(true)
                .message("Theatre retrieved successfully")
                .response(theatreDTO)
                .build();
        return ResponseEntity.ok(response);
    }
}
