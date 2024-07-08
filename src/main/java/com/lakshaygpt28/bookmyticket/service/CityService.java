package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.model.City;
import com.lakshaygpt28.bookmyticket.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private static final Logger LOG = LoggerFactory.getLogger(CityService.class);

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City addCity(City city) {
        LOG.info("Adding city: {}", city.getName());
        City savedCity = cityRepository.save(city);
        LOG.info("City added successfully: {}", savedCity.getName());
        return savedCity;
    }

    public List<City> getAllCities() {
        LOG.info("Fetching all cities");
        List<City> cities = cityRepository.findAll();
        LOG.info("All cities fetched successfully");
        return cities;
    }

    public Optional<City> getCityById(Long id) {
        LOG.info("Fetching city with id: {}", id);
        Optional<City> city = cityRepository.findById(id);

        if (city.isPresent()) {
            LOG.info("City found: {}", city.get().getName());
        } else {
            LOG.info("City not found with id: {}", id);
        }

        return city;
    }
}
