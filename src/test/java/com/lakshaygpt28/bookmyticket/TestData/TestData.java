package com.lakshaygpt28.bookmyticket.TestData;

import com.lakshaygpt28.bookmyticket.model.City;
import com.lakshaygpt28.bookmyticket.model.Movie;
import com.lakshaygpt28.bookmyticket.model.Theatre;
import com.lakshaygpt28.bookmyticket.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestData {

    public static City getDummyCity1() {
        City city = new City();
        city.setId(1L);
        city.setName("New York");
        return city;
    }

    public static City getDummyCity2() {
        City city = new City();
        city.setId(2L);
        city.setName("Los Angeles");
        return city;
    }

    public static List<City> getDummyCities() {
        return Arrays.asList(getDummyCity1(), getDummyCity2());
    }

    public static City getDummyCity() {
        return new City(1L, "Hyderabad", null);
    }

    public static Theatre getDummyTheatre() {
        return new Theatre(1L, "PVR Cinemas", getDummyCity(), null);
    }

    public static List<Theatre> getDummyTheatres() {
        List<Theatre> theatres = new ArrayList<>();
        theatres.add(new Theatre(1L, "PVR Cinemas", getDummyCity(), null));
        theatres.add(new Theatre(2L, "INOX", getDummyCity(), null));
        return theatres;
    }

    public static List<Theatre> createTheatresForCity(City city) {
        List<Theatre> theatres = new ArrayList<>();
        theatres.add(new Theatre(1L, "PVR Cinemas", city, null));
        theatres.add(new Theatre(2L, "INOX", city, null));
        return theatres;
    }
}