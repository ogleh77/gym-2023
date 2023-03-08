package com.example.gym.services;

import com.example.gym.entities.CustomerBuilder;
import com.example.gym.entities.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Data {

    private ObservableList<Customers> customers;


    public ObservableList<Customers> customers() {
        if (customers == null) {
            customers = FXCollections.observableArrayList();
        }
        Customers customer = new CustomerBuilder()
                .setImage(null)
                .setAddress("Actober")
                .setPhone("4303924")
                .setGander("Male")
                .setMiddleName("Ali")
                .setFirstName("Jama")
                .setLastName("Muuse")
                .setShift("Afternoon")
                .setWeight(65)
                .setWhoAdded("Ogleh")
                .build();

        customer.getInformation();
        customer.getUpdate();
        customer.getPaymentBtn();
        Customers customer2 = new CustomerBuilder()
                .setImage(null)
                .setAddress("Tuurta")
                .setPhone("4303923")
                .setGander("Female")
                .setMiddleName("Ali")
                .setFirstName("Luul")
                .setLastName("Muuse")
                .setShift("Afternoon")
                .setWeight(65)
                .setWhoAdded("Muuse")
                .build();
        customer2.getInformation();
        customer2.getUpdate();
        customer2.getPaymentBtn();
        customers.addAll(customer, customer2);


        return customers;
    }


}
