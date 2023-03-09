package com.example.gym.dto;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    @Test
    void fetchAllCustomer() throws SQLException {
        System.out.println(CustomerService.fetchAllCustomer(UsersService.users().get(0)).get(0));
    }
}