package com.example.gym.controllers.done;

import com.example.gym.dto.services.UsersService;
import com.example.gym.entities.service.Users;
import com.example.gym.helpers.CommonClass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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

public class UpdateUserController extends CommonClass implements Initializable {
    @FXML
    private JFXRadioButton superAdmin;

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
    private PasswordField password;

    @FXML
    private TextField phone;

    @FXML
    private ComboBox<String> shift;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private JFXRadioButton admin;

    @FXML
    private TextField username;
    private Users updatingUser;
    @FXML
    private ComboBox<Users> chooseUser;
    @FXML
    private JFXButton uploadImageBtn;
    @FXML
    private TextField idFeild;
    private boolean imageUploaded = false;

    private final ToggleGroup roleToggle = new ToggleGroup();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            initFields();
            if (!activeUser.getRole().equals("super_admin")) {
                chooseUser.setDisable(true);
                female.setDisable(true);
                male.setDisable(true);
                superAdmin.setDisable(true);
                admin.setSelected(true);
            } else {
                try {
                    chooseUser.setItems(UsersService.users());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        service.setOnSucceeded(e -> {
            updateBtn.setGraphic(null);
            if (chooseUser.getValue() == null) {
                System.out.println(updatingUser);
            }
            System.out.println(updatingUser);
        });
    }

    @FXML
    void updateHandler() {
        if (isValid(getMandatoryFields(), genderGroup)) {
            if (!imageUploaded) {
                checkImage();
            }
            if (start) {
                service.restart();
                updateBtn.setGraphic(getLoadingImageView());
                updateBtn.setText("Updating");
            } else {
                service.start();
                updateBtn.setGraphic(getLoadingImageView());
                updateBtn.setText("Updating");
                start = true;
            }

        }

    }

    @FXML
    void choiceUserHandler() {
        updatingUser = chooseUser.getValue();
        if (!activeUser.getUsername().equals(chooseUser.getValue().getUsername())) {
            System.out.println(activeUser.getUsername() + " not same " + chooseUser.getValue().getUsername());
            firstname.setDisable(true);
            lastname.setDisable(true);
            phone.setDisable(true);
            username.setDisable(true);
            password.setDisable(true);
            uploadImageBtn.setVisible(false);
        } else if (activeUser.getUsername().equals(chooseUser.getValue().getUsername())) {
            System.out.println(activeUser.getUsername() + " are same " + chooseUser.getValue().getUsername());
            firstname.setDisable(false);
            lastname.setDisable(false);
            phone.setDisable(false);
            username.setDisable(false);
            password.setDisable(false);
            uploadImageBtn.setVisible(true);



        }
        setUser(updatingUser, true);

    }

    @FXML
    void imageUploadHandler() {
        uploadImage();
    }


    @Override
    public void setActiveUser(Users activeUser) {
        super.setActiveUser(activeUser);
        setUser(activeUser, false);
    }

    private final Service<Void> service = new Service<>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() {
                    try {
                        Platform.runLater(() -> {
                            try {
                                if (chooseUser.getValue() != null) {
                                    updateUser();
                                } else {
                                    updateUser();
                                }
                                System.out.println(updatingUser);
                                UsersService.update(updatingUser);

                                Platform.runLater(() -> informationAlert("Waxaad Update garaysay user-ka " + updatingUser.getUsername()));
                            } catch (SQLException e) {
                                Platform.runLater(() -> errorMessage(e.getMessage()));
                            }

                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Platform.runLater(() -> errorMessage(e.getMessage()));
                    }
                    return null;
                }
            };
        }
    };


    private void setUser(Users activeUser, boolean isFromCombo) {
        idFeild.setText(String.valueOf(activeUser.getUserId()));
        firstname.setText(activeUser.getFirstName());
        lastname.setText(activeUser.getLastName());
        phone.setText(activeUser.getPhone());
        shift.setValue(activeUser.getShift());
        username.setText(activeUser.getUsername());
        password.setText(activeUser.getPassword());
        if (activeUser.getGender().equals("Male")) {
            male.setSelected(true);
        } else if (activeUser.getGender().equals("Female")) {
            female.setSelected(true);
        }

        if (activeUser.getRole().equals("super_admin")) {
            superAdmin.setSelected(true);
        } else if (activeUser.getRole().equals("admin")) {
            admin.setSelected(true);
        }


        if (activeUser.getImage() != null) {
            try {
                if (activeUser.getImage() != null) {
                    imageView.setImage(new Image(new FileInputStream(
                            isFromCombo ? chooseUser.getValue().getImage() : activeUser.getImage())));
                    selectedFile = new File(activeUser.getImage());
                    imageUploaded = true;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    ///---------------------Helpers--------------

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

    private void initFields() {
        admin.setToggleGroup(roleToggle);
        superAdmin.setToggleGroup(roleToggle);
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        shift.setItems(super.getShift());

        getMandatoryFields().addAll(firstname, lastname, phone, shift, username, password);
    }

    private void updateUser() {
        String image = selectedFile != null ? selectedFile.getAbsolutePath() : null;
        String gander = male.isSelected() ? "Male" : "Female";
        String role = superAdmin.isSelected() ? "super_admin" : "admin";
        updatingUser = new Users(Integer.parseInt(idFeild.getText()), firstname.getText().trim(), lastname.getText().trim(), phone.getText().trim(), gander,
                shift.getValue(), username.getText().trim(), password.getText().trim(), image, role);


    }
}
