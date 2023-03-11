package com.example.gym;

import com.example.gym.controllers.working.HomeController;
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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/gym/views2/working/home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HomeController controller = fxmlLoader.getController();
        controller.setActiveUser(UsersService.users().get(2));
        // controller.setCustomer(CustomerService.fetchAllCustomer(UsersService.users().get(0)).get(0));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}