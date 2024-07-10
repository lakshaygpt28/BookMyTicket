package com.lakshaygpt28.bookmyticket.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("bookings")
    private User user;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ShowSeat> showSeats;
    private LocalDateTime bookingTime;
    private BookingStatus bookingStatus;
    private BigDecimal totalAmount;

    public Booking(User user, Show show, List<ShowSeat> showSeats, BigDecimal totalAmount) {
        this.user = user;
        this.show = show;
        this.showSeats = showSeats;
        this.bookingTime = LocalDateTime.now();
        this.bookingStatus = BookingStatus.RESERVED;
        this.totalAmount = totalAmount;
    }
}
