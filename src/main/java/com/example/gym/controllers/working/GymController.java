package com.example.gym.controllers.working;

import com.example.gym.dto.services.BoxService;
import com.example.gym.dto.services.GymService;
import com.example.gym.entities.service.Box;
import com.example.gym.entities.service.Gym;
import com.example.gym.helpers.CommonClass;
import com.example.gym.helpers.CustomException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GymController extends CommonClass implements Initializable {
    @FXML
    private TextField addBox;

    @FXML
    private TextField boxCost;

    @FXML
    private ComboBox<Box> boxView;

    @FXML
    private TextField fitnessCost;

    @FXML
    private TextField gymName;

    @FXML
    private AnchorPane gymPane;

    @FXML
    private TextField maxDiscount;

    @FXML
    private TextField pendDate;

    @FXML
    private TextField poxingCost;
    private Gym currentGym;

    @FXML
    private TextField zaad;
    @FXML
    private TextField eDahab;

    public GymController() throws SQLException {
        currentGym = GymService.getGym();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(this::initData);
        fitnessValidation();
        poxingValidation();
        discountValidation();
        boxValidation();
        pendValidation();
    }

    @FXML
    void cancelHandler(MouseEvent event) {

    }

    @FXML
    void createBoxHandler(ActionEvent event) {
        Box box = new Box(addBox.getText());
        try {
            BoxService.insertBox(box);
            boxView.getItems().add(box);
            informationAlert("Waxaad diwaan gelisay khanad cusub fadlan ka check garee box viewga");
        } catch (CustomException e) {
            errorMessage(e.getMessage());
        }

    }

    @FXML
    void updateHandler() {
        double fitness_Cost = Double.parseDouble(fitnessCost.getText());
        double pox_cost = Double.parseDouble(poxingCost.getText());
        double max_discount = Double.parseDouble(maxDiscount.getText());
        int zaad_number = Integer.parseInt(zaad.getText());
        int eDahab_number = Integer.parseInt(eDahab.getText());
        int pend_date = Integer.parseInt(pendDate.getText());
        double box_cost = Double.parseDouble(boxCost.getText());

        currentGym = new Gym(1, gymName.getText(), fitness_Cost, pox_cost, box_cost,
                max_discount, pend_date, zaad_number, eDahab_number);

        try {
            System.out.println("Gym service then " + GymService.getGym());
            GymService.updateGym(currentGym);
            System.out.println("Gym service now");
            System.out.println(GymService.getGym());
        } catch (SQLException e) {
            errorMessage("" + e.getMessage());
        }
        System.out.println(currentGym);

    }


    private void fitnessValidation() {
        System.out.println("Validating fitness");

        fitnessCost.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                fitnessCost.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void poxingValidation() {
        System.out.println("Validating poxing");
        poxingCost.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                poxingCost.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void discountValidation() {
        System.out.println("Validating max");

        maxDiscount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                maxDiscount.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void boxValidation() {
        System.out.println("Validating box");
        boxCost.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                boxCost.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void pendValidation() {
        System.out.println("Validating pend");
        pendDate.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                pendDate.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void validate() {
        if (fitnessCost.getText().isBlank() && fitnessCost.getText().isEmpty()) {
            fitnessValidation();
        } else if (poxingCost.getText().isBlank() && poxingCost.getText().isEmpty()) {
            poxingValidation();
        } else if (maxDiscount.getText().isBlank() && maxDiscount.getText().isEmpty()) {
            discountValidation();
        } else if (boxCost.getText().isBlank() && boxCost.getText().isEmpty()) {
            boxValidation();
        } else if (pendDate.getText().isBlank() && pendDate.getText().isEmpty()) {
            pendValidation();
        }
    }


    private void initData() {
        gymName.setText(currentGym.getGymName());
        fitnessCost.setText(String.valueOf(currentGym.getFitnessCost()));
        boxCost.setText(String.valueOf(currentGym.getBoxCost()));
        poxingCost.setText(String.valueOf(currentGym.getPoxingCost()));
        maxDiscount.setText(String.valueOf(currentGym.getMaxDiscount()));
        eDahab.setText((String.valueOf(currentGym.geteDahab())));
        zaad.setText((String.valueOf(currentGym.getZaad())));
        boxView.setItems(currentGym.getVipBoxes());
        pendDate.setText(String.valueOf(currentGym.getPendingDate()));
    }
}
