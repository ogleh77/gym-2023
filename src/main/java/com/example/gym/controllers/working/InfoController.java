package com.example.gym.controllers.working;

import com.example.gym.dto.main.PaymentService;
import com.example.gym.dto.services.GymService;
import com.example.gym.entities.main.Customers;
import com.example.gym.entities.main.Payments;
import com.example.gym.helpers.CommonClass;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.ResourceBundle;

public class InfoController extends CommonClass implements Initializable {
    @FXML
    private TableColumn<Payments, Double> amountPaid;

    @FXML
    private TableColumn<Payments, Double> discount;

    @FXML
    private TableColumn<Payments, LocalDate> expDate;

    @FXML
    private TableColumn<Payments, String> month;

    @FXML
    private TableColumn<Payments, String> paidBy;

    @FXML
    private TableColumn<Payments, String> paymentDate;

    @FXML
    private TableColumn<Payments, JFXButton> pendingBtn;
    @FXML
    private TableColumn<Payments, String> poxing;
    @FXML
    private TableColumn<Payments, String> running;
    @FXML
    private TableView<Payments> tableView;
    @FXML
    private TableColumn<Payments, String> vipBox;
    @FXML
    private TableColumn<Payments, String> year;
    @FXML
    private ImageView imgView;
    @FXML
    private Label fullName;
    @FXML
    private Label address;
    @FXML
    private Label phone;
    @FXML
    private Label shift;
    @FXML
    private Label weight;
    @FXML
    private Label gander;
    @FXML
    private Label whoAdded;
    @FXML
    private Label titile;

    private ButtonType ok;
    private ButtonType cancel;
    private ObservableList<Payments> payments;
    private final String pendStyle = "-fx-background-color: #1e6e66;-fx-text-fill: white;-fx-pref-width: 100;-fx-font-size: 15";
    private final String unPendStyle = "-fx-background-color: #red;-fx-text-fill: white;-fx-pref-width: 100;-fx-font-size: 15";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            initTable();
            getBtnFunction();
        });


    }


    private void initTable() {
        if (payments.isEmpty()) {
            tableView.setPlaceholder(new Label("NO PAYMENTS WERE FOUND.."));
        } else {
            amountPaid.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
            discount.setCellValueFactory(new PropertyValueFactory<>("discount"));
            expDate.setCellValueFactory(new PropertyValueFactory<>("expDate"));
            month.setCellValueFactory(new PropertyValueFactory<>("month"));
            paidBy.setCellValueFactory(new PropertyValueFactory<>("paidBy"));
            paymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
            pendingBtn.setCellValueFactory(new PropertyValueFactory<>("pendingBtn"));
            poxing.setCellValueFactory(payment -> new SimpleStringProperty(payment.getValue().isPoxing() ? "Yes" : "No"));
            running.setCellValueFactory(payment -> new SimpleStringProperty(payment.getValue().isOnline() ? "Yes" : "No"));
            vipBox.setCellValueFactory(payment -> new SimpleStringProperty(payment.getValue().getBox() != null ? "Yes" : "No"));
            year.setCellValueFactory(new PropertyValueFactory<>("year"));

            tableView.setItems(payments);
        }

    }

    private void getBtnFunction() {

        for (Payments payment : customer.getPayments()) {

            EventHandler<MouseEvent> pendingHandler = event -> {
                System.out.println("We ok");

                payment.setPending(!payment.isPending());
                payment.setOnline(!payment.isOnline());
                payment.getPendingBtn().setStyle(payment.isPending() ? unPendStyle : pendStyle);
                if (payment.isPending()) {
                   // try {
                  //      checkPayment(payment);
//                    } catch (SQLException e) {
//                        errorMessage(e.getMessage());
//                    }
                }else{

                }


            };

            payment.getPendingBtn().addEventFilter(MouseEvent.MOUSE_CLICKED, pendingHandler);
        }
    }

    @Override
    public void setCustomer(Customers customer) {
        super.setCustomer(customer);
        fullName.setText(customer.getFirstName() + " " + customer.getMiddleName() + " " + customer.getLastName());
        phone.setText(customer.getPhone());
        gander.setText(customer.getGander());
        address.setText(customer.getAddress() == null ? " no address " : customer.getAddress());
        shift.setText(customer.getShift());
        weight.setText(customer.getWeight() + "");
        whoAdded.setText(customer.getWhoAdded());

        payments = customer.getPayments();

        try {
            if (customer.getImage() != null) {
                imgView.setImage(new Image(new FileInputStream(customer.getImage())));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void pendPaymentChecker(Payments payment) throws SQLException {
        LocalDate exp = payment.getExpDate();
        LocalDate pendingDate = LocalDate.now();
        int daysRemind = Period.between(pendingDate, exp).getDays();
        if (ok == null && cancel == null) {
            ok = new ButtonType("Haa", ButtonBar.ButtonData.OK_DONE);
            cancel = new ButtonType("Maya!", ButtonBar.ButtonData.CANCEL_CLOSE);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ma hubtaa inaad hakisto paymentkan oo ay u hadhay \n" + "Wakhtigiisa dhicitaanka " + daysRemind + " malmood", ok, cancel);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ok) {
            PaymentService.pendPayment(payment, GymService.getGym().getPendingDate());
        } else {
            System.out.println("Canceled");
            alert.close();
        }
    }

    private void unPendPaymentChecker(Payments payment) throws SQLException {
        LocalDate exp = payment.getExpDate();
        LocalDate pendingDate = LocalDate.now();
        int daysRemind = Period.between(pendingDate, exp).getDays();
        if (ok == null && cancel == null) {
            ok = new ButtonType("Haa", ButtonBar.ButtonData.OK_DONE);
            cancel = new ButtonType("Maya!", ButtonBar.ButtonData.CANCEL_CLOSE);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ma hubtaa inaad hakisto paymentkan oo ay u hadhay \n" + "Wakhtigiisa dhicitaanka " + daysRemind + " malmood", ok, cancel);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ok) {
            PaymentService.pendPayment(payment, GymService.getGym().getPendingDate());
        } else {
            System.out.println("Canceled");
            alert.close();
        }
    }


}
