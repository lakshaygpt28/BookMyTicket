package com.lakshaygpt28.bookmyticket.repository;

import com.lakshaygpt28.bookmyticket.model.City;
import com.lakshaygpt28.bookmyticket.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    List<Theatre> findByCityId(Long cityId);

    Theatre findByCityIdAndId(Long cityId, Long id);
}
