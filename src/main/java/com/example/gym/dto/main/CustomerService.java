package com.example.gym.dto.main;

import com.example.gym.entities.main.Customers;
import com.example.gym.entities.service.Users;
import com.example.gym.helpers.CustomException;
import com.example.gym.models.main.CustomerModel;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class CustomerService {
    private static final CustomerModel customerModel = new CustomerModel();
    private static ObservableList<Customers> allCustomersList;
    private static ObservableList<Customers> offlineCustomers;
    private static ObservableList<Customers> onlineCustomers;

    public static void insertOrUpdateCustomer(Customers customer, boolean newCustomer, Users activeUser) throws SQLException, InterruptedException {
        if (allCustomersList == null)
            fetchAllCustomer(activeUser);

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
        allCustomersList.add(customer);
    }

    private static void updateCustomer(Customers customer) throws SQLException {
        customerModel.update(customer);
        //Update after sorting
        //   int index = customerIndex(0, allCustomersList.size(), customer);
        //   allCustomersList.customer);
    }

    public static ObservableList<Customers> fetchAllCustomer(Users activeUser) throws SQLException {
        if (allCustomersList == null) {
            allCustomersList = customerModel.fetchAllCustomers(activeUser);
            // Collections.sort(allCustomersList);
        }
        return allCustomersList;
    }

    public static ObservableList<Customers> fetchOfflineCustomer(Users activeUser) throws SQLException {
        if (offlineCustomers == null) {
            offlineCustomers = customerModel.fetchOfflineCustomers(activeUser);
            // Collections.sort(allCustomersList);
        }
        return offlineCustomers;
    }

    public static ObservableList<Customers> fetchOnlineCustomer(Users activeUser) throws SQLException {
        System.out.println("Called");
        if (onlineCustomers == null) {
            onlineCustomers = customerModel.fetchOnlineCustomers(activeUser);

            System.out.println("Outs init...");
            // Collections.sort(allCustomersList);
        }
        return onlineCustomers;
    }

    //---------–--------------------Helpers---------–--------------------
    private static int customerIndex(int low, int high, Customers customer) {
        //if array is in order then perform binary search on the array
        if (high >= low) {
            //calculate mid
            int mid = low + (high - low) / 2;
            //if key =intArray[mid] return mid
            if (allCustomersList.get(mid).getCustomerId() == customer.getCustomerId()) {
                return mid;
            }
            //if intArray[mid] > key then key is in left half of array
            if (allCustomersList.get(mid).getCustomerId() > customer.getCustomerId()) {
                return customerIndex(low, mid - 1, customer);//recursively search for key
            } else       //key is in right half of the array
            {
                return customerIndex(mid + 1, high, customer);//recursively search for key
            }
        }
        return -1;
    }

}
