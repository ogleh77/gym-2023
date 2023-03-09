package com.example.gym.controllers.working;

import com.example.gym.dto.services.UsersService;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserCreationHandler extends CommonClass implements Initializable {
    @FXML
    private JFXRadioButton admin;

    @FXML
    private ComboBox<Users> chooseUser;

    @FXML
    private JFXButton createBtn;

    @FXML
    private JFXRadioButton female;

    @FXML
    private TextField firstname;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField lastname;

    @FXML
    private JFXRadioButton male;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField oldPassword;

    @FXML
    private TextField phone;

    @FXML
    private ComboBox<String> shift;

    @FXML
    private JFXRadioButton superAdmin;

    @FXML
    private Label topText;

    @FXML
    private TextField username;
    private boolean isUserNew = true;
    private boolean imageUploaded = false;
    private Users activeUser;
    private final ToggleGroup roleToggle = new ToggleGroup();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            initFields();
            checkIfTheUserIsSuperAdmin();
            if (!isUserNew) {
                topText.setText("UPDATING USER");
                createBtn.setText("Update");
            }
        });

        service.setOnSucceeded(e -> {
            createBtn.setGraphic(null);
            System.out.println(users());
        });
    }

    @FXML
    void createUser(ActionEvent event) {
        if (isValid(getMandatoryFields(), genderGroup)) {
            if (!imageUploaded) {
                checkImage();
            }
            if (start) {
                service.restart();
                createBtn.setGraphic(getLoadingImageView());
                createBtn.setText(isUserNew ? "Creating" : "Updating");
            } else {
                service.start();
                createBtn.setGraphic(getLoadingImageView());
                createBtn.setText(isUserNew ? "Creating" : "Updating");
                start = true;
            }

        }
    }

    @FXML
    void choiceUserHandler(ActionEvent event) {
        activeUser = chooseUser.getValue();

        System.out.println(activeUser);
        firstname.setText(activeUser.getFirstName());
        lastname.setText(activeUser.getLastName());
        phone.setText(activeUser.getPhone());
        shift.setValue(activeUser.getShift());
        username.setText(activeUser.getUsername());
        oldPassword.setText(activeUser.getPassword());

        if (activeUser.getGender().equals("Male")) {
            male.setSelected(true);
        } else if (activeUser.getGender().equals("Female")) {
            female.setSelected(true);
        }

        if (activeUser.getRole().equals("super_admin")) {
            admin.setSelected(true);
        } else if (activeUser.getRole().equals("admin")) {
            superAdmin.setSelected(true);
        }


        if (activeUser.getImage() != null) {
            try {
                if (activeUser.getImage() != null) {
                    imageView.setImage(new Image(new FileInputStream(activeUser.getImage())));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        createBtn.setDisable(false);
    }

    @FXML
    void imageUploader(ActionEvent event) {
        uploadImage();
    }


    //----------------------Helpers--------------------
    private void uploadImage() {
        try {
            if (selectedFile() != null) {
                Image image = new Image(new FileInputStream(selectedFile.getAbsolutePath()));
                imageView.setImage(image);
                imageView.setX(50);
                imageView.setY(25);
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
        Alert alert = new Alert(Alert.AlertType.WARNING, "Sawirku wuxuu kuu qurxinyaa profile kaga", ok, cancel);
        alert.setTitle("Sawir lama helin");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ok) {
            uploadImage();
        } else {
            imageUploaded = true;
        }
    }

    private Users users() {
        String image = selectedFile != null ? selectedFile.getAbsolutePath() : null;
        String gander = male.isSelected() ? "Male" : "Female";
        String role = superAdmin.isSelected() ? "super_admin" : "admin";

        return new Users(firstname.getText().trim(), lastname.getText().trim(), phone.getText().trim(), gander,
                shift.getValue(), username.getText().trim(), oldPassword.getText().trim(), image, role);


    }

    private void checkIfTheUserIsSuperAdmin() {
        if (activeUser.getRole().equals("super_admin")) {
            try {
                chooseUser.setItems(UsersService.users());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            male.setDisable(true);
            female.setDisable(true);
            superAdmin.setDisable(true);
            admin.setDisable(true);
            chooseUser.setDisable(true);
        }
    }

    private void initFields() {
        admin.setSelected(true);
        admin.setToggleGroup(roleToggle);
        superAdmin.setToggleGroup(roleToggle);
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        shift.setItems(super.getShift());

        getMandatoryFields().addAll(firstname, lastname, phone, shift, username, oldPassword);
    }

    @Override
    public void setActiveUser(Users activeUser) {
        this.activeUser = activeUser;
        if (activeUser != null) {
            firstname.setText(activeUser.getFirstName());
            lastname.setText(activeUser.getLastName());
            phone.setText(activeUser.getPhone());
            shift.setValue(activeUser.getShift());
            username.setText(activeUser.getUsername());
            oldPassword.setText(activeUser.getPassword());

            if (activeUser.getGender().equals("Male")) {
                male.setSelected(true);
            } else if (activeUser.getGender().equals("Female")) {
                female.setSelected(true);
            }

            if (activeUser.getImage() != null) {
                try {
                    if (activeUser.getImage() != null) {
                        imageUploaded = true;
                        imageView.setImage(new Image(new FileInputStream(activeUser.getImage())));
                        selectedFile = new File(activeUser.getImage());
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
        isUserNew = false;


    }

    private final Service<Void> service = new Service<>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() {
                    try {
                        if (isUserNew) {
                            UsersService.insertUser(users());
                        } else {
                            UsersService.update(users());
                        }
                        Platform.runLater(() -> {
                            informationAlert(isUserNew ? "Wxad samaysay user cusub" : "Waxaad update garaysay user ka " + activeUser.getUsername());
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
