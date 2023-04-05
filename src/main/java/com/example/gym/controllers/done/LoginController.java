package com.example.gym.controllers.done;


import animatefx.animation.FadeOut;
import com.example.gym.dto.services.UsersService;
import com.example.gym.entities.service.Users;
import com.example.gym.helpers.CommonClass;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginController extends CommonClass implements Initializable {
    public AnchorPane loginPane;
    @FXML
    private JFXButton loginBtn;

    @FXML
    private PasswordField password;

    @FXML
    private ComboBox<Users> userNameChooser;

    private ObservableList<Users> users;
    private Stage currentStage;


    public LoginController() throws SQLException {
        try {
            users = UsersService.users();
        } catch (SQLException e) {
            errorMessage("Khalad ba ka dhacat " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            currentStage = (Stage) userNameChooser.getScene().getWindow();
            userNameChooser.setItems(users);
        });


        service.setOnSucceeded(e -> {
            loginBtn.setGraphic(null);

            if (error) {
                errorMessage("Fadlan hubi username ka ama passwordka aad gelisay");
            } else {
                closeLogin();
                try {
                    openSplash();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


    }

    @FXML
    void loginHandler(ActionEvent event) {

        loginBtn.setContentDisplay(ContentDisplay.RIGHT);
        if (start) {
            service.restart();
            loginBtn.setGraphic(getLoadingImageView());

        } else {
            service.start();
            loginBtn.setGraphic(getLoadingImageView());
            start = true;
        }

    }

    @FXML
    void exitHandler(MouseEvent event) {
        closeLogin();
    }

    private final Service<Void> service = new Service<>() {
        @Override
        protected Task<Void> createTask() {

            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(1000);
                    Users activeUser = userNameChooser.getValue();
                    error = !password.getText().equals(activeUser.getPassword());
                    return null;
                }
            };
        }
    };


    private void openSplash() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gym/views2/done/splash-screen.fxml"));
        Scene scene = new Scene(loader.load());
        SplashScreenController controller = loader.getController();
        controller.setActiveUser(userNameChooser.getValue());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    private void closeLogin() {
        FadeOut fadeOut = new FadeOut(loginPane);
        fadeOut.play();
        fadeOut.setOnFinished(e -> {
            currentStage.close();
        });
    }
}
