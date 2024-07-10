package com.lakshaygpt28.bookmyticket.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Show show;

    @ManyToOne
    private Seat seat;
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    @JsonIgnoreProperties("showSeats")
    private Booking booking;

    public ShowSeat(Show show, Seat seat) {
        this.show = show;
        this.seat = seat;
        this.isAvailable = true;
    }
}
