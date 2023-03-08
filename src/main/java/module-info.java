module com.example.gym {
    requires javafx.controls;
    requires javafx.fxml;
    requires AnimateFX;
    requires com.jfoenix;
    requires junit;
    opens com.example.gym to javafx.fxml;
    exports com.example.gym;
    opens com.example.gym.controllers to javafx.fxml;
    opens com.example.gym.services to javafx.fxml;
    opens com.example.gym.controllers.main to javafx.fxml;
    opens com.example.gym.controllers.info to javafx.fxml;
}