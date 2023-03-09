module com.example.gym {
    requires javafx.controls;
    requires javafx.fxml;
    requires AnimateFX;
    requires com.jfoenix;
    requires junit;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    opens com.example.gym to javafx.fxml;
    opens com.example.gym.controllers.main to javafx.fxml;
    opens com.example.gym.controllers.info to javafx.fxml;
    opens com.example.gym.dto.services to javafx.fxml;
    opens com.example.gym.dto.main to javafx.fxml;
    opens com.example.gym.models.main to javafx.fxml;
    opens com.example.gym.models.services to javafx.fxml;
    opens com.example.gym.controllers.working to javafx.fxml;


    exports com.example.gym;
    exports com.example.gym.entities.service;
    exports com.example.gym.entities.main;

}