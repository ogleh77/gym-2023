package com.example.gym.dto.services;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class BoxServiceTest {

    @Test
    void fetchBoxes() throws SQLException {
        System.out.println(BoxService.fetchBoxes());
        System.out.println(BoxService.fetchBoxes().hashCode());

        System.out.println(BoxService.fetchBoxes().hashCode());

    }
}