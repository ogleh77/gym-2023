package com.example.gym.entities.main;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customers implements Comparable<Customers> {
    private final int customerId;
    private String firstName;
    private String lastName;
    private final String middleName;
    private final String phone;
    private final String gander;
    private final String shift;
    private final String address;
    private final String image;
    private final double weight;
    private final String whoAdded;
    private ObservableList<Payments> payments;

    private JFXButton update;
    private JFXButton paymentBtn;
    private JFXButton information;

    public void setPayments(ObservableList<Payments> payments) {
        this.payments = payments;
    }

    public Customers(int customerId, String firstName, String middleName, String lastName, String phone, String gander, String shift, String address, String image, double weight, String whoAdded) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
        this.gander = gander;
        this.shift = shift;
        this.address = address;
        this.image = image;
        this.weight = weight;
        this.whoAdded = whoAdded;
        this.payments = FXCollections.observableArrayList();
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getPhone() {
        return phone;
    }

    public String getGander() {
        return gander;
    }

    public String getShift() {
        return shift;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public double getWeight() {
        return weight;
    }

    public String getWhoAdded() {
        return whoAdded;
    }

    public ObservableList<Payments> getPayments() {
        return payments;
    }

    public JFXButton getInformation() {
        if (information == null) {
            information = new JFXButton("information");
            information.setStyle("-fx-background-color: #1e6e66;-fx-text-fill: white;-fx-pref-width: 130;-fx-font-size: 14");
        }
        return information;
    }

    public JFXButton getPaymentBtn() {
        if (paymentBtn == null) {
            this.paymentBtn = new JFXButton("payment");
            paymentBtn.setStyle("-fx-background-color: #145ab6;-fx-text-fill: white;-fx-pref-width: 80;-fx-font-size: 14");
        }
        return paymentBtn;
    }

    public JFXButton getUpdate() {
        if (update == null) {
            update = new JFXButton("update");
            this.update = new JFXButton("update");
            update.setStyle("-fx-background-color: dodgerblue;-fx-text-fill: white;-fx-pref-width: 80;-fx-font-size: 14");
        }
        return update;
    }

    @Override
    public String toString() {
        return "\n [customerId: " +
                customerId + " firstname: " +
                firstName + "  lastname: " +
                lastName + " gander " + gander + " phone: " + phone + "\n payments: " + payments + "]\n\n";
    }

    @Override
    public int compareTo(Customers o) {
        if (this.customerId < o.customerId)
            return 1;
        return 0;
    }
}
