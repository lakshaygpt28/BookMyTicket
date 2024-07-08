package com.lakshaygpt28.bookmyticket.repository;

import com.lakshaygpt28.bookmyticket.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

}
