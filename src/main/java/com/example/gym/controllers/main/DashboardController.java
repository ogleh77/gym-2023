package com.example.gym.controllers.main;

import com.example.gym.helpers.CommonClass;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController extends CommonClass implements Initializable {
    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox sidePane;

    @FXML
    private HBox topProfile;

    @FXML
    private HBox menuHBox;

    @FXML
    private StackPane notificationsHBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> {
            borderPane.setLeft(null);
        });
    }


    @FXML
    void registrationHandlerClick(MouseEvent event) throws IOException {
        FXMLLoader loader = openWindow(URL + "/registrations.fxml", borderPane, sidePane, menuHBox, notificationsHBox);
        RegistrationController controller = loader.getController();
        controller.setBorderPane(borderPane);
    }


    @FXML
    void notificationClickHandler(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(URL + "/outdated.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

    }

    @FXML
    void outdatedClickHandler(MouseEvent event) throws IOException {
        FXMLLoader loader = openWindow(URL + "/outdated.fxml", borderPane, sidePane, menuHBox, notificationsHBox);
    }

}
