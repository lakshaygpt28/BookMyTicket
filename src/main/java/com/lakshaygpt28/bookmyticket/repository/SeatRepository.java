package com.lakshaygpt28.bookmyticket.repository;

import com.lakshaygpt28.bookmyticket.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByScreenId(Long screenId);
}
