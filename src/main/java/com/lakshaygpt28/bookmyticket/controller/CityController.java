package com.lakshaygpt28.bookmyticket.controller;

import com.lakshaygpt28.bookmyticket.dto.CityDTO;
import com.lakshaygpt28.bookmyticket.dto.response.ApiResponse;
import com.lakshaygpt28.bookmyticket.mapper.CityMapper;
import com.lakshaygpt28.bookmyticket.model.City;
import com.lakshaygpt28.bookmyticket.service.CityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CityController {
    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/cities")
    public ResponseEntity<ApiResponse<City>> addCity(@RequestBody @Valid City city) {
        City savedCity = cityService.addCity(city);
        ApiResponse<City> response = ApiResponse.<City>builder()
                .success(true)
                .message("City added successfully")
                .response(savedCity)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/cities")
    public ResponseEntity<ApiResponse<List<CityDTO>>> getAllCities() {
        List<City> cities = cityService.getAllCities();
        List<CityDTO> cityDTOs = cities.stream().map(CityMapper.INSTANCE::toDto).toList();
        ApiResponse<List<CityDTO>> response = ApiResponse.<List<CityDTO>>builder()
                .success(true)
                .message("All cities fetched successfully")
                .response(cityDTOs)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<ApiResponse<CityDTO>> getCityById(@PathVariable Long id) {
        City city = cityService.getCityById(id);
        CityDTO cityDTO = CityMapper.INSTANCE.toDto(city);
        ApiResponse<CityDTO> response = ApiResponse.<CityDTO>builder()
                .success(true)
                .message("City fetched successfully")
                .response(cityDTO)
                .build();
        return ResponseEntity.ok(response);
    }
}
