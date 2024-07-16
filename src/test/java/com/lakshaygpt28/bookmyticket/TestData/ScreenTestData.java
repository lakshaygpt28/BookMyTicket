package com.lakshaygpt28.bookmyticket.TestData;

import com.lakshaygpt28.bookmyticket.model.Screen;
import com.lakshaygpt28.bookmyticket.model.Seat;
import com.lakshaygpt28.bookmyticket.model.Theatre;

import java.util.ArrayList;
import java.util.List;

public class ScreenTestData {
    public static Screen getDummyScreen(Theatre theatre) {
        Screen screen = new Screen();
        screen.setId(1L);
        screen.setName("Screen 1");
        screen.setTheatre(theatre);
        screen.setSeats(getDummySeats(screen));
        return screen;
    }

    public static Screen getDummyScreen1() {
        Screen screen = new Screen();
        screen.setId(2L);
        screen.setName("Screen 2");
        screen.setTheatre(getDummyTheatre());
        screen.setSeats(getDummySeats(screen));
        return screen;
    }

    public static List<Screen> createScreens() {
        Theatre theatre = getDummyTheatre();
        Screen screen1 = new Screen(1L, "Screen 1", theatre, getDummySeats(null));
        Screen screen2 = new Screen(2L, "Screen 2", theatre, getDummySeats(null));
        return List.of(screen1, screen2);
    }

    private static List<Seat> getDummySeats(Screen screen) {
        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat(1L, "A1", screen));
        seats.add(new Seat(2L, "A2", screen));
        seats.add(new Seat(3L, "B1", screen));
        seats.add(new Seat(4L, "B2", screen));
        return seats;
    }

    public static Theatre getDummyTheatre() {
        Theatre theatre = new Theatre();
        theatre.setId(1L);
        theatre.setName("PVR Cinemas");
        theatre.setCity(TestData.getDummyCity());
        return theatre;
    }
}
