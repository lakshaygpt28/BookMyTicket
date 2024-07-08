package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.model.City;
import com.lakshaygpt28.bookmyticket.model.Theatre;
import com.lakshaygpt28.bookmyticket.repository.CityRepository;
import com.lakshaygpt28.bookmyticket.repository.TheatreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheatreService {
    
    private static final Logger LOG = LoggerFactory.getLogger(TheatreService.class);

    private final TheatreRepository theatreRepository;
    private final CityService cityService;

    @Autowired
    public TheatreService(TheatreRepository theatreRepository, CityService cityService) {
        this.theatreRepository = theatreRepository;
        this.cityService = cityService;
    }

    public List<Theatre> getAllTheatres() {
        LOG.info("Fetching all theatres");
        List<Theatre> theatres = theatreRepository.findAll();
        LOG.info("Retrieved {} theatres", theatres.size());
        return theatres;
    }

    public List<Theatre> getTheatresByCityId(Long cityId) {
        LOG.info("Fetching theatres for cityId: {}", cityId);
        List<Theatre> theatres = theatreRepository.findByCityId(cityId);
        LOG.info("Retrieved {} theatres for cityId: {}", theatres.size(), cityId);
        return theatres;
    }

    public Theatre addTheatre(Theatre newTheatre) {
        LOG.info("Adding theatre: {}", newTheatre.getName());
        City city = cityService.getCityById(newTheatre.getCity().getId())
                .orElseThrow(() -> new RuntimeException("City not found with id: " + newTheatre.getCity().getId()));
        newTheatre.setCity(city);
        LOG.info("City found. Saving theatre: {}", newTheatre.getName());
        Theatre savedTheatre = theatreRepository.save(newTheatre);
        LOG.info("Theatre saved successfully: {}", savedTheatre.getName());
        return savedTheatre;
    }

    public void deleteTheatre(Long id) {
        LOG.info("Deleting theatre with id: {}", id);
        theatreRepository.deleteById(id);
        LOG.info("Theatre with id {} deleted successfully", id);
    }

    public Optional<Theatre> getTheatreById(Long id) {
        LOG.info("Fetching theatre with id: {}", id);
        Optional<Theatre> theatre = theatreRepository.findById(id);

        if (theatre.isPresent()) {
            LOG.info("Theatre with id {} found: {}", id, theatre.get());
        } else {
            LOG.info("Theatre with id {} not found", id);
        }

        return theatre;
    }

    public List<Theatre> addTheatresByCityId(Long cityId, List<Theatre> theatres) {
        LOG.info("Adding {} theatres for cityId: {}", theatres.size(), cityId);
        City city = cityService.getCityById(cityId).
                orElseThrow(() -> new RuntimeException("City not found with id: " + cityId));
        theatres.forEach(theatre -> theatre.setCity(city));
        LOG.info("City found. Saving {} theatres for cityId: {}", theatres.size(), cityId);
        List<Theatre> savedTheatres = theatreRepository.saveAll(theatres);
        LOG.info("Theatres added successfully: {}", savedTheatres.size());
        return savedTheatres;
    }
}
