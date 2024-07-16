package com.lakshaygpt28.bookmyticket.repository;

import com.lakshaygpt28.bookmyticket.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreenRepository extends JpaRepository<Screen, Long> {

    List<Screen> findByTheatreId(Long theatreId);

    Screen findByTheatreIdAndId(Long id, Long id1);
}
