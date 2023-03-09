package com.example.gym.controllers;

import com.example.gym.helpers.CommonClass;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;

import java.net.URL;
import java.util.ResourceBundle;

public class OutDatedController extends CommonClass implements Initializable {
    @FXML
    private DatePicker fromDate;

    @FXML
    private ChoiceBox<String> gander;

    @FXML
    private Label headerInfo;

    @FXML
    private Pagination pagination;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private ChoiceBox<String> shift;

    @FXML
    private DatePicker toDate;
    private int column = 0;
    private int row = 1;
    ///private ObservableList<Student> students = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            //   pagination.setPageCount(students.size() == 6 ? 0 : 2);
          //  pagination.setPageFactory(this::createPage);
        });

        service.setOnSucceeded(e -> {
            searchBtn.setText("Search");
            searchBtn.setGraphic(null);


//            Collections.sort(students);
//            pagination.setPageCount(students.size());
            //pagination.setPageFactory(this::createPage);
            //  System.out.println(students);
        });
    }

    @FXML
    void searchHandler(ActionEvent event) {
        if (start) {
            service.restart();
            searchBtn.setText(null);
            searchBtn.setGraphic(getLoadingImageView());
            //   insertData();

        } else {
            service.start();
            searchBtn.setText(null);
            searchBtn.setGraphic(getLoadingImageView());
            start = true;
            //  insertData();
        }
    }

//    private GridPane createPage(int index) {
//        int perPage = 6;
//        int fromPage = perPage * index;
//      //  int toIndex = Math.min(fromPage + perPage, students.size());
//
//        GridPane gridView = null;
//        try {
//            gridView = new GridPane();
//            FXMLLoader loader;
//            AnchorPane anchorPane;
//            CustomerCardController controller;
////            for (int i = fromPage; i < toIndex; i++) {
////
////                if (column == 2) {
////                    column = 0;
////                    row++;
////                }
////                loader = new FXMLLoader();
////                loader.setLocation(getClass().getResource("/com/example/gym/views/info/customer-card.fxml"));
////                anchorPane = loader.load();
////                controller = loader.getController();
////                System.out.println("Passed to " + students.get(i));
////                controller.setStudent(students.get(i));
////                gridView.add(anchorPane, column++, row);
////                GridPane.setMargin(anchorPane, new Insets(5, 10, 5, 5));
////                gridView.setPadding(new Insets(0, 20, 0, 0));
////            }
//
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//        return gridView;
//    }

//    private void insertData() {
//        for (int i = 0; i < 10; i++) {
//            Student student = new Student("Student " + i, i * (int) (Math.random() * 100));
//
//            students.add(student);
//        }
//        System.out.println(students);
//    }

    private final Service<Void> service = new Service<>() {
        @Override
        protected Task<Void> createTask() {

            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(1000);
                    return null;
                }
            };
        }
    };
}
