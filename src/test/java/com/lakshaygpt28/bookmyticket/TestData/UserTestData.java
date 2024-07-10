package com.lakshaygpt28.bookmyticket.TestData;

import com.lakshaygpt28.bookmyticket.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserTestData {
    public static User getDummyUser1() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        return user;
    }

    public static User getDummyUser2() {
        User user = new User();
        user.setId(2L);
        user.setName("Jane Smith");
        return user;
    }

    public static List<User> getDummyUsers() {
        List<User> users = new ArrayList<>();
        users.add(getDummyUser1());
        users.add(getDummyUser2());
        return users;
    }
}
