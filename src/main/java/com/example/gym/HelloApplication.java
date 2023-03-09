package com.example.gym;

import com.example.gym.controllers.main.RegistrationController;
import com.example.gym.dto.CustomerService;
import com.example.gym.dto.UsersService;
import com.example.gym.entities.Customers;
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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/gym/views/main/registrations.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        RegistrationController controller = fxmlLoader.getController();
        Customers customer = CustomerService.fetchAllCustomer(UsersService.users().get(0)).get(0);
        controller.setActiveUser(UsersService.users().get(0));
        controller.setCustomer(customer);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}