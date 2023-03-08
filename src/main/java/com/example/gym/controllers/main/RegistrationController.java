package com.example.gym.controllers.main;

import com.example.gym.helpers.CommonClass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController extends CommonClass implements Initializable {
    @FXML
    private TextField address;

    @FXML
    private Label customerId;

    @FXML
    private JFXRadioButton female;

    @FXML
    private TextField firstName;

    @FXML
    private Label gymTitle;

    @FXML
    private Label headerInfo;

    @FXML
    private ImageView imgView;

    @FXML
    private TextField lastName;

    @FXML
    private JFXRadioButton male;

    @FXML
    private Label messageValidation;

    @FXML
    private TextField middleName;

    @FXML
    private TextField phone;

    @FXML
    private JFXButton registerBtn;

    @FXML
    private ComboBox<String> shift;

    @FXML
    private TextField weight;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void customerSaveHandler(ActionEvent event) throws IOException {
        openPaymentWindow();
    }

    @Override
    public void setBorderPane(BorderPane borderPane) {
        super.setBorderPane(borderPane);
    }


    private void openPaymentWindow() throws IOException {
        FXMLLoader loader = openWindow(URL + "/payments.fxml", borderPane, null, null, null);
        PaymentController controller = loader.getController();
        controller.setBorderPane(borderPane);
    }



}
