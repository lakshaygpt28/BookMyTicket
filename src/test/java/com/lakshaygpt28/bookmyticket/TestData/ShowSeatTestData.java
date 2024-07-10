package com.lakshaygpt28.bookmyticket.TestData;

import com.lakshaygpt28.bookmyticket.model.Seat;
import com.lakshaygpt28.bookmyticket.model.Show;
import com.lakshaygpt28.bookmyticket.model.ShowSeat;

import java.util.ArrayList;
import java.util.List;

public class ShowSeatTestData {

    public static List<ShowSeat> createShowSeats(List<Show> shows, List<Seat> seats) {
        List<ShowSeat> showSeats = new ArrayList<>();
        for (Show show : shows) {
            for (Seat seat : seats) {
                showSeats.add(new ShowSeat(show, seat));
            }
        }
        return showSeats;
    }

    public static List<ShowSeat> createAvailableShowSeats(List<ShowSeat> showSeats) {
        List<ShowSeat> availableShowSeats = new ArrayList<>();
        for (ShowSeat showSeat : showSeats) {
            if (showSeat.isAvailable()) {
                availableShowSeats.add(showSeat);
            }
        }
        return availableShowSeats;
    }

    public static List<ShowSeat> getDummyShowSeats() {
        List<ShowSeat> showSeats = new ArrayList<>();
        Show dummyShow = ShowTestData.getDummyShow1(ScreenTestData.getDummyScreen1(), MovieTestData.getDummyMovie1());
        Seat dummySeat1 = new Seat(1L, "A1", dummyShow.getScreen());
        Seat dummySeat2 = new Seat(2L, "B1", dummyShow.getScreen());
        showSeats.add(new ShowSeat(dummyShow, dummySeat1));
        showSeats.add(new ShowSeat(dummyShow, dummySeat2));
        return showSeats;
    }
}