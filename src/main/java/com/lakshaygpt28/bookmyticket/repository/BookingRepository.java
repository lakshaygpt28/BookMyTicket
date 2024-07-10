package com.lakshaygpt28.bookmyticket.repository;

import com.lakshaygpt28.bookmyticket.model.Booking;
import com.lakshaygpt28.bookmyticket.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Modifying
    @Query("UPDATE Booking b SET b.bookingStatus = :bookingStatus WHERE b.id = :bookingId")
    void updateBookingStatus(@Param("bookingId") Long bookingId, @Param("bookingStatus") BookingStatus bookingStatus);

    @Query("SELECT b from Booking b WHERE b.bookingStatus = :bookingStatus AND b.bookingTime < :expirationTime")
    List<Booking> getUnpaidBookings(@Param("bookingStatus") BookingStatus bookingStatus, @Param("expirationTime") LocalDateTime expirationTime);
}
