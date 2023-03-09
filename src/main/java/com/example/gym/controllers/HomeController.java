package com.example.gym.controllers;

import com.example.gym.entities.Customers;
import com.example.gym.helpers.CommonClass;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends CommonClass implements Initializable {
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
   // private final ObservableList<Customers> customers = DataService.customers();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> {
            initTable();
           // buttonActions();

            // TODO: 08/03/2023 Registration and  
        });

    }

    public HomeController() {

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

      //  tableView.setItems(customers);
    }


    @Override
    public void setBorderPane(BorderPane borderPane) {
        super.setBorderPane(borderPane);
    }


//    private void buttonActions() {
//
//        for (Customers customer : customers) {
//
//            EventHandler<MouseEvent> updateHandler = event -> {
//                try {
//                    FXMLLoader loader = openWindow(URL + "/registrations.fxml", borderPane, null, null, null);
//                    RegistrationController controller = loader.getController();
//                    controller.setBorderPane(borderPane);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//
//            };
//            customer.getUpdate().addEventFilter(MouseEvent.MOUSE_CLICKED, updateHandler);
//
//            EventHandler<MouseEvent> infoHandler = event -> {
//                try {
//                    FXMLLoader loader = openWindow("/com/example/gym/views/info/customer-info.fxml", borderPane, null, null, null);
//                    CustomerInfoController controller = loader.getController();
//                    controller.setCustomers(customer);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//
//            };
//            customer.getInformation().addEventFilter(MouseEvent.MOUSE_CLICKED, infoHandler);
//
//        }
//    }
}