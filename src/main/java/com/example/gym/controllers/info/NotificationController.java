package com.example.gym.controllers.info;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NotificationController implements Initializable {
    @FXML
    private VBox vbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void load() throws IOException {
        AnchorPane anchorPane = null;
        FXMLLoader loader;
        for (int i = 0; i < 10; i++) {
            loader = new FXMLLoader(getClass().getResource("/com/example/gym/views/info/customer-card.fxml"));

            anchorPane = loader.load();

            vbox.getChildren().add(anchorPane);
        }
    }
}
