package com.Divarproject.GUI;

import com.Divarproject.Ads.Ad;
import com.Divarproject.Register.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class showAdsController {

    @FXML
    private AnchorPane adsContainer;

    private List<Ad> ads;
    private User loggedInUser;

    public void setAds(List<Ad> ads, User loggedInUser) {
        this.ads = ads;
        this.loggedInUser = loggedInUser;

        if (adsContainer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("خطا");
            alert.setContentText("عدم مقداردهی adsContainer");
            alert.showAndWait();
            return;
        }

        adsContainer.getChildren().clear();
        double yOffset = 50;

        for (Ad ad : ads) {
            HBox adBox = new HBox(10);
            adBox.setSpacing(10);
            adBox.getStyleClass().add("ad-box");
            adBox.setOnMouseClicked(e -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("adDetails.fxml"));
                    Parent root = loader.load();

                    adDetailsController adDetailsController = loader.getController();
                    adDetailsController.setAdAndUserData(ad, loggedInUser, ads);

                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    NavigationHelper.navigateToScene(stage, root);
                } catch (IOException ex) {
                    System.err.println("خطا در بارگذاری صفحه جزئیات آگهی: " + ex.getMessage());
                }
            });

            // نمایش تصویر
            ImageView imageView = null;
            try {
                String imagePath = ad.getImagePath();
                Image image;
                if (imagePath != null && !imagePath.isEmpty()) {
                    imagePath = "file:" + imagePath;
                    image = new Image(imagePath);
                } else {
                    image = new Image(getClass().getResourceAsStream("/com/Divarproject/images/default.png"));
                }
                imageView = new ImageView(image);
            } catch (Exception e) {
                System.err.println("خطا در بارگذاری تصویر آگهی: " + e.getMessage());
                try {
                    Image defaultImage = new Image(getClass().getResourceAsStream("/com/Divarproject/images/default.png"));
                    imageView = new ImageView(defaultImage);
                } catch (Exception ex) {
                    System.err.println("خطا در بارگذاری تصویر پیش‌فرض: " + ex.getMessage());
                }
            }

            if (imageView != null) {
                imageView.setFitWidth(100);
                imageView.setFitHeight(75);
                imageView.setPreserveRatio(true);
            }

            // نمایش اطلاعات آگهی
            Label title = new Label(ad.getName());
            Label price = new Label(ad.getPrice() + " تومان");
            adBox.getChildren().addAll(imageView, title, price);

            AnchorPane.setTopAnchor(adBox, yOffset);
            AnchorPane.setLeftAnchor(adBox, 10.0);
            AnchorPane.setRightAnchor(adBox, 10.0);

            adsContainer.getChildren().add(adBox);

            yOffset += 120;
        }
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        NavigationHelper.navigateToDashboard(event, loggedInUser, ads);
    }
}