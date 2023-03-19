package com.example.gym.controllers.done;

import com.example.gym.dto.main.CustomerService;
import com.example.gym.entities.main.Customers;
import com.example.gym.entities.service.Users;
import com.example.gym.helpers.CommonClass;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController extends CommonClass implements Initializable {

    @FXML
    private TableColumn<Customers, Integer> customerId;

    @FXML
    private TableColumn<Customers, String> fullName;

    @FXML
    private TableColumn<Customers, String> gander;

    @FXML
    private Label headerInfo;

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

    private ObservableList<Customers> customersList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            try {
                initTable();
                userButtons();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        });
    }


    private void initTable() throws SQLException {
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        fullName.setCellValueFactory(customers -> new SimpleStringProperty(
                customers.getValue().getFirstName() + "   " + customers.getValue().getMiddleName()
                        + "   " + customers.getValue().getLastName()));

        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        gander.setCellValueFactory(new PropertyValueFactory<>("gander"));
        shift.setCellValueFactory(new PropertyValueFactory<>("shift"));

        information.setCellValueFactory(new PropertyValueFactory<>("information"));
        update.setCellValueFactory(new PropertyValueFactory<>("update"));
        payments.setCellValueFactory(new PropertyValueFactory<>("paymentBtn"));
        tableView.setItems(customersList);
    }

    @Override
    public void setActiveUser(Users activeUser) {
        super.setActiveUser(activeUser);
        try {
            this.customersList = CustomerService.fetchAllCustomer(activeUser);
            System.out.println("Have " + customersList.hashCode());
        } catch (SQLException e) {
            errorMessage("Khald baa  dhacay" + e.getMessage());
        }
    }


    //-----------------------HELPERS-------------------

    private void userButtons() throws SQLException {
        for (Customers customer : customersList) {

            EventHandler<MouseEvent> updateHandler = event -> {
                System.out.println("Update pressed");
            };


            customer.getUpdate().addEventFilter(MouseEvent.MOUSE_CLICKED, updateHandler);

            EventHandler<MouseEvent> paymentHandler = event -> {
                System.out.println("Update pressed");
            };


            customer.getPaymentBtn().addEventFilter(MouseEvent.MOUSE_CLICKED, paymentHandler);

            EventHandler<MouseEvent> informationHandler = event -> {
                System.out.println("Update pressed");
            };


            customer.getInformation().addEventFilter(MouseEvent.MOUSE_CLICKED, informationHandler);
        }
    }
}
