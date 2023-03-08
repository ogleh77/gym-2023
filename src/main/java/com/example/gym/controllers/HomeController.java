package com.example.gym.controllers;

import com.example.gym.entities.Customers;
import com.example.gym.services.DataService;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private TableColumn<Customers, Integer> customerId;

    @FXML
    private TableColumn<Customers, String> fullName;

    @FXML
    private TableColumn<Customers, String> gander;
    @FXML
    private TableColumn<Customers, JFXButton> information;

    @FXML
    private TableColumn<Customers, JFXButton> payments;

    @FXML
    private TableColumn<Customers, String> phone;

    @FXML
    private TableColumn<Customers, String> shift;

    @FXML
    private TableView<Customers> tableView;

    @FXML
    private TableColumn<Customers, JFXButton> update;
    @FXML
    private Label gymTitle;

    @FXML
    private Label activeCount;


    @FXML
    private Label outDatedCount;

    @FXML
    private TextField search;


    @FXML
    private Label usersCount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(this::initTable);
    }


    private void initTable() {
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        fullName.setCellValueFactory(customers -> new SimpleStringProperty(
                customers.getValue().getFirstName() + "    " + customers.getValue().getMiddleName()
                        + "    " + customers.getValue().getLastName()));

        shift.setCellValueFactory(new PropertyValueFactory<>("shift"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        update.setCellValueFactory(new PropertyValueFactory<>("update"));
        gander.setCellValueFactory(new PropertyValueFactory<>("gander"));
        payments.setCellValueFactory(new PropertyValueFactory<>("paymentBtn"));
        information.setCellValueFactory(new PropertyValueFactory<>("information"));

        tableView.setItems(DataService.customers());
    }
}
