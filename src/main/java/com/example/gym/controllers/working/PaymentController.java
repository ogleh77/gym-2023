package com.example.gym.controllers.working;

import animatefx.animation.FadeIn;
import com.example.gym.dto.main.PaymentService;
import com.example.gym.dto.services.GymService;
import com.example.gym.entities.main.Customers;
import com.example.gym.entities.main.PaymentBuilder;
import com.example.gym.entities.main.Payments;
import com.example.gym.entities.service.Box;
import com.example.gym.entities.service.Gym;
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
import java.util.ResourceBundle;

public class PaymentController extends CommonClass implements Initializable {
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
    private Label paymentInfo;

    @FXML
    private TextField phone;

    @FXML
    private JFXCheckBox poxing;

    @FXML
    private Label title;
    private double fitnessCost;
    private double poxingCost;
    private double vipBoxCost;
    private double currentCost = 0;
    private boolean paymentIsGoing = false;
    private final Gym currentGym;
    private final ButtonType ok;
    private LocalDate expiringDate;

    public PaymentController() throws SQLException {
        currentGym = GymService.getGym();
        ok = new ButtonType("Finish", ButtonBar.ButtonData.OK_DONE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            try {
                initFields();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            paidBy.setItems(super.getPaidBy());
            currentCost = fitnessCost;
            amountPaid.setText(String.valueOf(currentCost));
            if (paymentIsGoing) {
                //createBtn.setDisable(true);
                tellInfo(expiringDate);
            }
            paymentValidation();
        });
        service.setOnSucceeded(e -> {
            createBtn.setGraphic(null);
            //System.out.println(discount.getText());
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
                        expiringDate = payment.getExpDate();
                        System.out.println("Is online " + payment);
                        paymentIsGoing = true;
                    }
                }
            }
        }

    }


    //-----------------Helpers-------------------
    private final Service<Void> service = new Service<>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(1000);
                    try {
                        double _discount = ((!discount.getText().isEmpty() || !discount.getText().isBlank())) ? Double.parseDouble(discount.getText()) : 0;

                        currentCost -= _discount;

                        Payments payment = new PaymentBuilder()
                                .setAmountPaid(currentCost)
                                .setExpDate(expDate.getValue())
                                .setPaidBy(paidBy.getValue())
                                .setPoxing(poxing.isSelected())
                                .setCustomerFK(customer.getPhone())
                                .setDiscount(_discount)
                                .build();

                        if (boxChooser.getValue() != null && !boxChooser.getValue().getBoxName().matches("remove box")) {
                            payment.setBox(boxChooser.getValue());
                            boxChooser.getItems().remove(boxChooser.getValue());
                        }
                        customer.getPayments().add(payment);
                        PaymentService.insertPayment(customer);
                        Platform.runLater(()->{
                            informationAlert("Waxaad samayasay payment cusub..");
                        });

                    } catch (SQLException e) {
                        Platform.runLater(()->  {
                            errorMessage(e.getMessage());
                        });
                    }
                    return null;
                }
            };
        }
    };


    private String validateDiscount() {

        if ((!discount.getText().isEmpty() || !discount.getText().isBlank())) {
            if (!discount.getText().matches("[0-9]*")) {
                discountValidtaion.setVisible(true);
                discountValidtaion.setText("discount must be digits only ");
                return "error";
            } else {

                double _discount = Double.parseDouble(discount.getText());

                if (_discount > currentGym.getMaxDiscount()) {
                    discountValidtaion.setVisible(true);
                    discountValidtaion.setText("discount can't greater then max discount of $" + currentGym.getMaxDiscount());
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
        expDate.setValue(LocalDate.now().plusDays(30));
        this.fitnessCost = currentGym.getFitnessCost();
        this.poxingCost = currentGym.getPoxingCost();
        this.vipBoxCost = currentGym.getBoxCost();

        paidBy.setItems(super.getPaidBy());
        currentCost = fitnessCost;
        amountPaid.setText(String.valueOf(currentCost));

        getMandatoryFields().addAll(amountPaid, paidBy);

        if (boxChooser.getItems().isEmpty()) {
            for (Box box : currentGym.getVipBoxes()) {
                if (box.isReady()) boxChooser.getItems().add(box);
            }

        }

        boxChooser.getItems().add(new Box(0, "remove box", false));

        expDate.setValue(LocalDate.now().plusDays(30));
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

    private void tellInfo(LocalDate expDate) {
        paymentInfo.setText(customer.getFirstName() + " Wakhtigu kama dhici wuxuse ka dhaacyaa" + expDate.toString() + " Insha Allah");
        paymentInfo.setStyle("-fx-text-fill: red;");
        FadeIn fadeIn = new FadeIn(paymentInfo);
        fadeIn.setCycleCount(50);
        fadeIn.play();

    }

}
