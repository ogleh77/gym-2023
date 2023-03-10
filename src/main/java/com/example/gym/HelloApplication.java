package com.example.gym;

import com.example.gym.controllers.working.UpdateUserController;
import com.example.gym.dto.services.UsersService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/gym/views2/working/update-user.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        UpdateUserController controller = fxmlLoader.getController();
        controller.setActiveUser(UsersService.users().get(0));

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}