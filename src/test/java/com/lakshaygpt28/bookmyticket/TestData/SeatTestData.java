package com.lakshaygpt28.bookmyticket.TestData;

import com.lakshaygpt28.bookmyticket.model.Seat;

import java.util.ArrayList;
import java.util.List;

public class SeatTestData {

    public static Seat getDummySeat() {
        Seat seat = new Seat();
        seat.setId(1L); // Replace with appropriate values for your test case
        seat.setSeatNumber("A1");
        seat.setScreen(ScreenTestData.getDummyScreen1()); // Example screen data
        return seat;
    }

    public static List<Seat> dummySeats() {
        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat(1L, "A1", ScreenTestData.getDummyScreen1()));
        seats.add(new Seat(2L, "A2", ScreenTestData.getDummyScreen1()));
        return seats;
    }

    public static List<Seat> dummySeats(List<Long> ids) {
        List<Seat> seats = new ArrayList<>();
        for (Long id : ids) {
            seats.add(new Seat(id, "A" + id, ScreenTestData.getDummyScreen1()));
        }
        return seats;
    }
}