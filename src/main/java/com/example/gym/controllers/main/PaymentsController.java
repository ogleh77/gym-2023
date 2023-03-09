package com.example.gym.controllers.main;

import animatefx.animation.FadeIn;
import com.example.gym.dto.main.PaymentService;
import com.example.gym.dto.services.GymService;
import com.example.gym.entities.main.Customers;
import com.example.gym.entities.main.PaymentBuilder;
import com.example.gym.entities.main.Payments;
import com.example.gym.entities.service.Box;
import com.example.gym.helpers.CommonClass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentsController extends CommonClass implements Initializable {
    @FXML
    private TextField amountPaid;

    @FXML
    private ComboBox<Box> boxChooser;

    @FXML
    private JFXButton createBtn;

    @FXML
    private TextField discount;

    @FXML
    private Label discountValidtaion;

    @FXML
    private DatePicker expDate;

    @FXML
    private JFXRadioButton female;

    @FXML
    private TextField firstName;

    @FXML
    private ImageView imgView;

    @FXML
    private TextField lastName;

    @FXML
    private JFXRadioButton male;

    @FXML
    private TextField middleName;

    @FXML
    private ComboBox<String> paidBy;

    @FXML
    private TextField phone;

    @FXML
    private JFXCheckBox poxing;
    @FXML
    private Label title;
    private boolean paymentIsGoing = false;
    private boolean finished = false;

    private double fitnessCost;
    private double poxingCost;
    private double vipBoxCost;
    private double currentCost = 0;

    @FXML
    private Label paymentInfo;
    private ButtonType ok = new ButtonType("Finish", ButtonBar.ButtonData.OK_DONE);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            paidBy.setItems(super.getPaidBy());

            currentCost = fitnessCost;
            amountPaid.setText(String.valueOf(currentCost));

            if (paymentIsGoing) {
                createBtn.setDisable(true);
                tellInfo();
            }
            paymentValidation();

            try {
                initFields();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            service.setOnSucceeded(e -> {
                createBtn.setGraphic(null);
                System.out.println("Done..");


                if (finished) {
                    Alert alert = confirm("Waxaad samaysay payment cusub.", ok);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ok) {
                        System.out.println("Yes");
                    }
                } else {
                    System.out.println("Error occur");
                }
            });


        });


    }

    @FXML
    void createPaymentHandler(ActionEvent event) {

        if (isValid(getMandatoryFields(), null) && validateDiscount() == null) {
            if (start) {
                service.restart();
                createBtn.setGraphic(getLoadingImageView());
                createBtn.setText("Creating");
            } else {
                service.start();
                createBtn.setGraphic(getLoadingImageView());
                createBtn.setText("Creating");
                start = true;
            }

        }
    }

    @Override
    public void setCustomer(Customers customer) {
        super.customer = customer;
        if (customer != null) {
            firstName.setText(customer.getFirstName());
            middleName.setText(customer.getFirstName());
            lastName.setText(customer.getFirstName());
            middleName.setText(customer.getMiddleName());
            lastName.setText(customer.getLastName());
            phone.setText(customer.getPhone());

            if (customer.getGander().equals("Male")) {
                male.setSelected(true);
            } else {
                female.setSelected(true);
            }

            try {
                if (customer.getImage() != null) {
                    imgView.setImage(new Image(new FileInputStream(customer.getImage())));
                }
            } catch (FileNotFoundException e) {
                errorMessage("Khalad ba ka dhacay " + e.getMessage());
            }

            expDate.setValue(LocalDate.now().plusDays(30));


            if (!customer.getPayments().isEmpty()) {
                for (Payments payment : customer.getPayments()) {
                    if (payment.isOnline() && (payment.getExpDate().isAfter(LocalDate.now()))
                            || payment.getExpDate().equals(LocalDate.now())) {
                        System.out.println("Is online " + payment);
                        paymentIsGoing = true;
                    }
                }
            }
        }
    }


    private void paymentValidation() {
        boxChooser.valueProperty().addListener((observable, oldValue, newValue) -> {
            //Stop the user to name a box into remove or something insha Allah
            if ((oldValue == null || oldValue.getBoxName().matches("re.*")) && !newValue.getBoxName().matches("re.*")) {
                currentCost += vipBoxCost;
            } else if (oldValue != null && boxChooser.getValue().getBoxName().matches("re.*")) {
                currentCost -= vipBoxCost;
            }
            amountPaid.setText(String.valueOf(currentCost));
        });

        poxing.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (poxing.isSelected()) {
                currentCost += poxingCost;
            } else {
                currentCost -= poxingCost;
            }
            amountPaid.setText(String.valueOf((currentCost)));

        });


    }

    private String validateDiscount() {

        if ((!discount.getText().isEmpty() || !discount.getText().isBlank())) {
            if (!discount.getText().matches("[0-9]*")) {
                discountValidtaion.setVisible(true);
                discountValidtaion.setText("discount must be digits only ");
                return "error";
            } else {

                double _discount = Double.parseDouble(discount.getText());

                double maxDiscount = 1.0;
                if (_discount > maxDiscount) {
                    discountValidtaion.setVisible(true);
                    discountValidtaion.setText("discount can't greater then max discount of $" + maxDiscount);
                    return "error";
                } else {
                    discountValidtaion.setVisible(false);
                    discountValidtaion.setText(null);
                    return null;
                }
            }
        }

        return null;
    }

    private void initFields() throws SQLException {
        this.fitnessCost = GymService.getGym().getFitnessCost();
        this.poxingCost = GymService.getGym().getPoxingCost();
        this.vipBoxCost = GymService.getGym().getBoxCost();

        paidBy.setItems(super.getPaidBy());
        currentCost = fitnessCost;
        amountPaid.setText(String.valueOf(currentCost));

        getMandatoryFields().addAll(amountPaid, paidBy);

        if (boxChooser.getItems().isEmpty()) {

            for (Box box : GymService.getGym().getVipBoxes()) {
                if (box.isReady()) boxChooser.getItems().add(box);
            }

        }

        boxChooser.getItems().add(new Box(0, "remove box", false));

        expDate.setValue(LocalDate.now().plusDays(30));
    }


    private final Service<Void> service = new Service<>() {
        @Override
        protected Task<Void> createTask() {

            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        double _discount = ((!discount.getText().isEmpty() || !discount.getText().isBlank())) ? Double.parseDouble(discount.getText()) : 0;

                        currentCost -= _discount;
                        Thread.sleep(1000);
                        Payments payment = new PaymentBuilder()
                                .setAmountPaid(currentCost)
                                .setExpDate(expDate.getValue())
                                .setPaidBy(paidBy.getValue())
                                .setPoxing(poxing.isSelected())
                                .setCustomerFK(customer.getPhone())
                                .build();


                        if (boxChooser.getValue() != null && !boxChooser.getValue().getBoxName().matches("remove box")) {
                            payment.setBox(boxChooser.getValue());
                        }
//                        System.out.println(1 / 0);

                        //Add finish buttom calling the reg
                        customer.getPayments().add(payment);

                        PaymentService.insertPayment(customer);

                        finished = true;

                    } catch (Exception e) {
                        e.printStackTrace();
                        Platform.runLater(() -> {
                            errorMessage(e.getMessage());
                            finished = false;
                        });
                    }


                    return null;
                }
            };
        }
    };


    private void tellInfo() {
        paymentInfo.setText("Macmiilkan weli wakhtigu kama dhicin");
        paymentInfo.setStyle("-fx-text-fill: red;");
        FadeIn fadeIn = new FadeIn(paymentInfo);
        fadeIn.setCycleCount(50);
        fadeIn.play();

    }

    private Alert confirm(String message, ButtonType buttonType) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, buttonType);
        return alert;
    }


}
