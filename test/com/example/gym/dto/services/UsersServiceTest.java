package com.example.gym.dto.services;

import com.example.gym.entities.service.Users;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class UsersServiceTest {

    @Test
    void insertUser() {


    }

    @Test
    void updateUser() throws SQLException {

        Users user = UsersService.users().get(0);

        System.out.println("Before\n " + user);

        Users user2 = new Users("Hibaaq", "Ali", "4303666", "Female", "Night",
                user.getUsername(), user.getPassword(), null, user.getRole());

      //  UsersService.update(user2, prevUserName);
        System.out.println("After\n " + UsersService.users());
    }
}