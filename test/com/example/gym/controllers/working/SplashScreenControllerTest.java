package com.example.gym.controllers.working;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class SplashScreenControllerTest {
    @Test
    void check() {

        LocalDate now = LocalDate.now();
        LocalDate future = LocalDate.now().plusDays(3);


        System.out.println("Now " + now + " Future " + future);
        System.out.println(now.isBefore(future));
        System.out.println(now.isAfter(future));

    }
}