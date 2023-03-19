package com.example.gym;

import com.example.gym.controllers.working.InfoController;
import com.example.gym.dto.main.CustomerService;
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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/gym/views2/working/customer-info.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        InfoController controller = fxmlLoader.getController();
        controller.setActiveUser(UsersService.users().get(0));
        controller.setCustomer(CustomerService.fetchAllCustomer(UsersService.users().get(1)).get(0));
        // controller.setCustomer(CustomerService.fetchAllCustomer(UsersService.users().get(0)).get(0));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}