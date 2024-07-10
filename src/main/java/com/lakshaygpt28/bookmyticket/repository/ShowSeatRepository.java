package com.lakshaygpt28.bookmyticket.repository;

import com.lakshaygpt28.bookmyticket.model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    List<ShowSeat> findByShowIdAndSeatIdIn(Long showId, List<Long> seatIds);

    List<ShowSeat> findByShowIdAndIsAvailableTrue(Long showId);

    List<ShowSeat> findByShowId(Long showId);
}
