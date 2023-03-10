package com.example.gym.controllers.working;

import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideOutLeft;
import com.example.gym.helpers.CommonClass;
import javafx.event.ActionEvent;
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
    private HBox menuHBox;

    @FXML
    private StackPane notificationsHBox;

    @FXML
    private VBox sidePane;

    @FXML
    private HBox topProfile;

    private boolean fullSize = false;
    private boolean visible = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void homeClickHandler(ActionEvent event) {

    }

    @FXML
    void addUserHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gym/views2/done/user-creation.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

    }

    @FXML
    void menuToggle(MouseEvent event) {
        if (visible) {
            SlideOutLeft slideOutLeft = new SlideOutLeft();
            slideOutLeft.setNode(sidePane);
            slideOutLeft.play();
            slideOutLeft.setOnFinished(e -> {
                borderPane.setLeft(null);
            });
        } else {
            new SlideInLeft(sidePane).play();
            borderPane.setLeft(sidePane);
        }
        visible = !visible;
    }

    @FXML
    void maximizeHandler(MouseEvent event) {
        Stage stage = (Stage) sidePane.getScene().getWindow();
        System.out.println("Pressed..");
        fullSize = !fullSize;
        stage.setFullScreen(fullSize);
    }

}
