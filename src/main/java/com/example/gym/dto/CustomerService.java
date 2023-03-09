package com.example.gym.dto;

import com.example.gym.entities.Customers;
import com.example.gym.entities.service.Users;
import com.example.gym.helpers.CustomException;
import com.example.gym.models.CustomerModel;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class CustomerService {
    private static final CustomerModel customerModel = new CustomerModel();
    private static ObservableList<Customers> customers;

    public static void insertOrUpdateCustomer(Customers customer, boolean newCustomer, Users activeUser) throws SQLException, InterruptedException {
        if (customers == null) {
            fetchAllCustomer(activeUser);
        }
        try {
            if (newCustomer) {
                insertCustomer(customer);
            } else {
                updateCustomer(customer);
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("(UNIQUE constraint failed: customers.phone)")) {
                throw new CustomException("Lanbarka " + customer.getPhone() + " hore ayaa loo diwaan geshay fadlan dooro lanbarkale");
            } else {
                throw new CustomException("Khalad ayaaa ka dhacay " + e.getMessage());
            }
        }

        Thread.sleep(1000);
    }


    private static void insertCustomer(Customers customer) throws SQLException {
        customerModel.insert(customer);
        customers.add(customer);
    }

    private static void updateCustomer(Customers customer) throws SQLException {
        customerModel.update(customer);
        //Update after sorting
        //  int index = customerIndex(0, customers.size(), customer);
        //   customers.set(index, customer);
    }

    public static ObservableList<Customers> fetchAllCustomer(Users activeUser) throws SQLException {
        if (customers == null) {
            customers = customerModel.fetchAllCustomers(activeUser);
        }
        return customers;
    }
}
