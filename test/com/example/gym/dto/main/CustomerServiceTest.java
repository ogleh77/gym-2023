package com.example.gym.dto.main;

import com.example.gym.dto.services.UsersService;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class CustomerServiceTest {

    @Test
    void fetchAllCustomer() throws SQLException {
        System.out.println(CustomerService.fetchAllCustomer(UsersService.users().get(0)));
    }

    @Test
    void fetchOfflineCustomer() throws SQLException {
        System.out.println(CustomerService.fetchOfflineCustomer(UsersService.users().get(0)));

    }

    @Test
    void fetchOnlineCustomer() throws SQLException {
        System.out.println(CustomerService.fetchOnlineCustomer(UsersService.users().get(0)));
    }



}