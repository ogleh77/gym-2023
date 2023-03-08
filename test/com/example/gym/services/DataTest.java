package com.example.gym.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {

    @Test
    void customers() {
        System.out.println(DataService.customers().hashCode());
        System.out.println(DataService.customers().hashCode());

        System.out.println(DataService.customers().hashCode());

     }
}