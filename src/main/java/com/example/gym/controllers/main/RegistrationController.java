package com.example.gym.controllers.main;

import com.example.gym.dto.CustomerService;
import com.example.gym.entities.Customers;
import com.example.gym.entities.service.Users;
import com.example.gym.helpers.CommonClass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegistrationController extends CommonClass implements Initializable {
    @FXML
    private TextField address;

    @FXML
    private Label customerId;

    @FXML
    private JFXRadioButton female;

    @FXML
    private TextField firstName;

    @FXML
    private Label gymTitle;

    @FXML
    private Label headerInfo;

    @FXML
    private ImageView imgView;

    @FXML
    private TextField lastName;

    @FXML
    private JFXRadioButton male;

    @FXML
    private Label messageValidation;

    @FXML
    private TextField middleName;

    @FXML
    private TextField phone;

    @FXML
    private JFXButton registerBtn;

    @FXML
    private ComboBox<String> shift;

    @FXML
    private TextField weight;

    private boolean imageUploaded = false;
    private boolean isCustomerNew = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            shift.setItems(getShift());
            male.setToggleGroup(genderGroup);
            female.setToggleGroup(genderGroup);

            getMandatoryFields().addAll(firstName, middleName, lastName, phone, shift);

        });
        service.setOnSucceeded(e -> {
            registerBtn.setGraphic(null);
            try {
                System.out.println("\n");
                System.out.println(CustomerService.fetchAllCustomer(activeUser));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @FXML
    void closeHandler(MouseEvent event) {

    }

    @FXML
    void resetHandler(ActionEvent event) {

    }

    @FXML
    void customerSaveHandler(ActionEvent event) {

        if (isValid(getMandatoryFields(), genderGroup) && (phoneCheck() == null)) {
            //Check if the image uploaded....
            if (!imageUploaded) {
                checkImage();
            }
            if (start) {
                service.restart();
                registerBtn.setGraphic(getLoadingImageView());
                registerBtn.setText(isCustomerNew ? "Saving" : "Updating");
            } else {
                service.start();
                registerBtn.setGraphic(getLoadingImageView());
                registerBtn.setText(isCustomerNew ? "Saving" : "Updating");
                start = true;
            }
        }


    }

    @FXML
    void uploadImageHandler(ActionEvent event) {
        uploadImage();
    }

    private void uploadImage() {
        try {
            if (selectedFile() != null) {
                Image image = new Image(new FileInputStream(selectedFile.getAbsolutePath()));
                imgView.setImage(image);
                imgView.setX(50);
                imgView.setY(25);
                imgView.setFitHeight(166);
                imgView.setFitWidth(200);
                imageUploaded = true;
            }
        } catch (FileNotFoundException e) {
            errorMessage("Fadlan sawirka lama helin isku day mar kale");
            imageUploaded = false;
        }
    }

    private void checkImage() {
        ButtonType ok = new ButtonType("Hada soo upload-garee", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Ogaan baan u dhaafay.", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING, "Fadlan sawirku wuu kaa cawinayaa inaad wejiga \n" + "macmiilka ka dhex garan kartid macamisha kle", ok, cancel);
        alert.setTitle("Sawir lama helin");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ok) {
            uploadImage();
        } else {
            imageUploaded = true;
        }
    }

    private String phoneCheck() {
        if (!phone.getText().matches("[0-9]*")) {
            messageValidation.setVisible(true);
            messageValidation.setText("Fadlan lanbarka xarfo looma ogola");
            return "error";
        } else if (!phone.getText().matches("^\\d{7}")) {
            messageValidation.setVisible(true);
            messageValidation.setText("Fadlan lanbarku kama yaraan karo ama kama badan karo 7 lanbar");
            return "error";
        }
        messageValidation.setVisible(false);
        return null;

    }

    private Customers savingCustomer() {
        String gander = male.isSelected() ? "Male" : "Female";
        String _address = address.getText() != null ? address.getText().trim() : null;
        double _weight = ((!weight.getText().isEmpty() || !weight.getText().isBlank())) ? Double.parseDouble(weight.getText().trim()) : 65.0;
        String image = selectedFile != null ? selectedFile.getAbsolutePath() : null;
        int customerId = super.customer == null ? 0 : customer.getCustomerId();


        return new Customers(customerId, firstName.getText(), middleName.getText(), lastName.getText(), phone.getText(), gander, shift.getValue(), _address, image, _weight, activeUser.getUsername());
    }

    @Override
    public void setCustomer(Customers customer) {
        super.customer = customer;
        if (customer != null) {
            firstName.setText(customer.getFirstName());
            middleName.setText(customer.getMiddleName());
            lastName.setText(customer.getLastName());

            phone.setText(customer.getPhone());
            weight.setText(String.valueOf(customer.getWeight()));
            shift.setValue(customer.getShift());
            address.setText(customer.getAddress() != null ? customer.getAddress() : "No address");

            if (customer.getGander().equals("Male")) {
                male.setSelected(true);
            } else {
                female.setSelected(true);
            }
            weight.setText(String.valueOf(customer.getWeight()));
            shift.setValue(customer.getShift());
            address.setText(customer.getAddress() != null ? customer.getAddress() : "No address");

            try {
                if (customer.getImage() != null) {
                    imageUploaded = true;
                    imgView.setImage(new Image(new FileInputStream(customer.getImage())));
                    selectedFile = new File(customer.getImage());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            headerInfo.setText("UPDATING EXISTED CUSTOMER ");
            isCustomerNew = false;
            registerBtn.setText("Update");
        }
    }

    @Override
    public void setActiveUser(Users activeUser) {
        super.setActiveUser(activeUser);

        System.out.println(activeUser);
    }

    private final Service<Void> service = new Service<>() {
        @Override
        protected Task<Void> createTask() {

            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(1000);
                    try {
                        CustomerService.insertOrUpdateCustomer(savingCustomer(), isCustomerNew, activeUser);
                        Platform.runLater(() -> {
                            informationAlert(isCustomerNew ? "Waxaad diwaan gelisay macmiil cusub" :
                                    "Waxaad update garaysay macmiilka ah " + firstName.getText() + " " + customer.getMiddleName());
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                        Platform.runLater(() -> {
                            errorMessage(e.getMessage());
                        });
                    }


                    return null;
                }
            };
        }
    };


}
