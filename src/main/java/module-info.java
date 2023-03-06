module com.example.gym {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.gym to javafx.fxml;
    exports com.example.gym;
}