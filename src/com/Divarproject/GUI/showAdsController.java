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
        adsContainer.getChildren().clear();

        if(adsContainer == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("خطا");
            alert.setContentText("عدم مقداردهی adscontainer");
            alert.showAndWait();
            return;
        }


        double yOffset = 50;
        for (Ad ad : ads) {
            HBox adBox = new HBox(10);
            adBox.setSpacing(10);
            adBox.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-border-width: 1px;");

            //Image
            ImageView imageView = null;
            try {
                String imagePath = ad.getImagePath();
                Image image;
                if (imagePath != null && !imagePath.isEmpty()) {
                    imagePath = "file:" + imagePath; // اضافه کردن پیشوند file:
                    image = new Image(imagePath);
                } else {
                    // استفاده از تصویر پیش‌فرض
                    image = new Image(getClass().getResourceAsStream("/com/Divarproject/images/default.png"));
                }
                imageView = new ImageView(image);
            } catch (Exception e) {
                System.err.println("خطا در بارگذاری تصویر آگهی " + ad.getName() + ": " + e.getMessage());
                // استفاده از تصویر پیش‌فرض در صورت خطای بارگذاری
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

            //info
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
        // بارگذاری صفحه داشبورد
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Parent root = loader.load();

        // گرفتن کنترلر داشبورد و ارسال اطلاعات کاربر و آگهی‌ها
        DashboardController dashboardController = loader.getController();
        dashboardController.setUser(loggedInUser, ads);

        // تنظیمات Stage و Scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene); // فقط صحنه (Scene) عوض می‌شود
        stage.show();
    }
}
