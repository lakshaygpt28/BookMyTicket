package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.exception.TheatreNotFoundException;
import com.lakshaygpt28.bookmyticket.model.City;
import com.lakshaygpt28.bookmyticket.model.Theatre;
import com.lakshaygpt28.bookmyticket.repository.TheatreRepository;
import com.lakshaygpt28.bookmyticket.util.ErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        City city = cityService.getCityById(newTheatre.getCity().getId());
        newTheatre.setCity(city);
        LOG.info("City found. Saving theatre: {}", newTheatre.getName());
        Theatre savedTheatre = theatreRepository.save(newTheatre);
        LOG.info("Theatre saved successfully: {}", savedTheatre.getName());
        return savedTheatre;
    }

    public Theatre getTheatreById(Long id) {
        LOG.info("Fetching theatre with id: {}", id);
        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() -> new TheatreNotFoundException(String.format(ErrorMessages.THEATRE_NOT_FOUND, id)));
        LOG.info("Theatre fetched successfully: {}", theatre.getName());
        return theatre;
    }

    public List<Theatre> addTheatresByCityId(Long cityId, List<Theatre> theatres) {
        LOG.info("Adding {} theatres for cityId: {}", theatres.size(), cityId);
        City city = cityService.getCityById(cityId);
        theatres.forEach(theatre -> theatre.setCity(city));
        LOG.info("City found. Saving {} theatres for cityId: {}", theatres.size(), cityId);
        List<Theatre> savedTheatres = theatreRepository.saveAll(theatres);
        LOG.info("Theatres added successfully: {}", savedTheatres.size());
        return savedTheatres;
    }

    public Theatre getTheatreByCityAndId(Long cityId, Long id) {
        LOG.info("Fetching theatre with cityId: {} and id: {}", cityId, id);
        Theatre theatre = theatreRepository.findByCityIdAndId(cityId, id);
        if (theatre == null) {
            throw new TheatreNotFoundException(String.format(ErrorMessages.THEATRE_NOT_FOUND, id));
        }
        LOG.info("Theatre: {} fetched successfully with cityId: {} and id: {}", theatre.getName(), cityId, id);
        return theatre;
    }
}
