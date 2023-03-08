package com.example.gym.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private TableColumn<?, ?> customerId;

    @FXML
    private TableColumn<?, ?> fullName;

    @FXML
    private TableColumn<?, ?> gander;

    @FXML
    private Label gymTitle;

    @FXML
    private TableColumn<?, ?> information;

    @FXML
    private TableColumn<?, ?> payments;

    @FXML
    private TableColumn<?, ?> phone;

    @FXML
    private TableColumn<?, ?> shift;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TableColumn<?, ?> update;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



}
