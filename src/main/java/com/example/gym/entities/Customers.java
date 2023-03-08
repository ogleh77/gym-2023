package com.example.gym.entities;

import com.jfoenix.controls.JFXButton;

public class Customers {

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
    private JFXButton update;
    private JFXButton paymentBtn;
    private JFXButton information;

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

    public JFXButton getUpdate() {
        this.update = new JFXButton("Update");
        this.update.setStyle("-fx-background-color: #1e6e66;-fx-text-fill: white;-fx-pref-width: 100;-fx-font-size: 15");
        return update;
    }

    public JFXButton getPaymentBtn() {
        this.paymentBtn = new JFXButton("Payment");
        this.paymentBtn.setStyle("-fx-background-color: dodgerblue;-fx-text-fill: white;-fx-pref-width: 100;-fx-font-size: 15");
        return paymentBtn;
    }

    public JFXButton getInformation() {
        this.information = new JFXButton("Information");
        this.information.setStyle("-fx-background-color: #328ca8;-fx-text-fill: white;-fx-pref-width: 100;-fx-font-size: 15");
        return information;
    }


    @Override
    public String toString() {
        return "\n [customerId: " +
                customerId + " firstname: " +
                firstName + "  lastname: " +
                lastName + " gander " + gander + " phone: " + phone + "\n";
    }


}
