package com.example.gym.services;

import com.example.gym.entities.Customers;
import javafx.collections.ObservableList;

public class DataService {
    private static final Data data = new Data();
    private static ObservableList<Customers> customers;

    public static ObservableList<Customers> customers() {

        if (customers == null) {
            customers = data.customers();
        }

        return customers;
    }
}
