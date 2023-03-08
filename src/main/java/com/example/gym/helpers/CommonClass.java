package com.example.gym.helpers;

import animatefx.animation.FadeIn;
import animatefx.animation.Shake;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;

public abstract class CommonClass {

    public final String URL = "/com/example/gym/views";
    private Shake shake;
    private SlideInRight slideInRight;
    private SlideInLeft slideInLeft;
    private FadeIn fadeIn;

    protected boolean start = false;
    protected boolean error = false;
    protected BorderPane borderPane;

    public final String[] images = {
            "/com/example/gym/style/icons/loading_5.gif",
            "/com/example/gym/style/icons/sx_back.gif"
    };

    protected FXMLLoader openWindow(String url, BorderPane borderPane, VBox sidePane, HBox menu, StackPane notificationsHBox) throws IOException {
//        if (sidePane != null && !sidePane.isVisible()) {
//            sidePane.setVisible(true);
//            slideInLeft.setNode(sidePane);
//            slideInLeft.play();
//            // System.out.println("In Opener The ref is " + DashboardController.borderPane);
//            // DashboardController.borderPane.setLeft(sidePane);
//            borderPane.setLeft(sidePane);
//        }
        //side menu

        if (menu != null) {
            menu.setVisible(true);
            getFadeIn().setNode(menu);
            getSlideInLeft().playOnFinished(fadeIn);
            getFadeIn().play();
        }
        //top profile
        if (notificationsHBox != null) {
            notificationsHBox.setVisible(true);
            getFadeIn().setNode(notificationsHBox);
            getFadeIn().play();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        AnchorPane anchorPane = loader.load();
        getSlideInRight().setNode(anchorPane);
        getSlideInRight().play();
        // DashboardController.borderPane.setCenter(anchorPane);
        //  System.out.println("In Opener The ref is " + DashboardController.borderPane);
        borderPane.setCenter(anchorPane);
        return loader;
    }


    private SlideInLeft getSlideInLeft() {
        if (slideInLeft == null) {
            slideInLeft = new SlideInLeft();
        }
        return slideInLeft;
    }


    private SlideInRight getSlideInRight() {
        if (slideInRight == null) {
            slideInRight = new SlideInRight();
        }
        return slideInRight;
    }

    private FadeIn getFadeIn() {
        if (fadeIn == null) {
            fadeIn = new FadeIn();
        }
        return fadeIn;
    }

    private Shake getShake() {
        if (shake == null) {
            shake = new Shake();
            System.out.println("Shake init");
        }
        return shake;
    }
    private ImageView loadingImageView;
    public ImageView getLoadingImageView() {
        if (loadingImageView == null) {
            java.net.URL url = getClass().getResource(images[0]);
            Image image = new Image(String.valueOf(url));
            loadingImageView = new ImageView();
            loadingImageView.setFitHeight(30);
            loadingImageView.setFitWidth(30);
            loadingImageView.setImage(image);
        }
        return loadingImageView;
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }


    public BorderPane getBorderPane() {
        if (this.borderPane != null) {
            return borderPane;
        }
        return null;
    }
}
