package com.example.gym.controllers.info;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerCardController implements Initializable {
    @FXML
    private ImageView customerPhoto;

    @FXML
    private Label fullName;

    @FXML
    private Label lastPaid;

    @FXML
    private Label outDated;

    @FXML
    private Label phone;

    @FXML
    private Label shift;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



}
