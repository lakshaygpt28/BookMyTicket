package com.lakshaygpt28.bookmyticket.repository;

import com.lakshaygpt28.bookmyticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
