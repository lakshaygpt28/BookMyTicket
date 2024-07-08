package com.lakshaygpt28.bookmyticket.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seatNumber;

    @ManyToOne
    @JoinColumn(name = "screen_id", nullable = false)
    @JsonIgnoreProperties("seats")
    private Screen screen;

    private boolean isAvailable = true;
}
