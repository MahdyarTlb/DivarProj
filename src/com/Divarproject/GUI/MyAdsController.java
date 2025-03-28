package com.Divarproject.GUI;

import com.Divarproject.Ads.Ad;
import com.Divarproject.Ads.AdManager;
import com.Divarproject.Register.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MyAdsController {

    @FXML
    private VBox adsContainer;

    private User loggedInUser;
    private ObservableList<Ad> ads = FXCollections.observableArrayList();
    private List<Ad> allAds;

    // تنظیم اطلاعات کاربر لاگین‌شده
    public void setUserData(User user, List<Ad> ads) {
        this.loggedInUser = user;
        this.allAds = ads;
        this.ads.addAll(allAds.stream()
                .filter(ad -> (ad.getOwner().getUserName()).equalsIgnoreCase(loggedInUser.getUserName()))
                .toList()
        );
        loadAds();
    }

    // بارگذاری آگهی‌ها
    private void loadAds() {
        adsContainer.getChildren().clear();
        for (Ad ad : ads) {
            HBox adBox = new HBox();
            adBox.setSpacing(15);

            // تصویر تامبنیل
            ImageView imageView = new ImageView();
            if (ad.getImagePath() != null && !ad.getImagePath().isEmpty()) {
                Image image = new Image(new File(ad.getImagePath()).toURI().toString());
                imageView.setImage(image);
                imageView.setFitHeight(95);
                imageView.setFitWidth(95);
                imageView.setPreserveRatio(true); // حفظ نسبت تصویر
                imageView.getStyleClass().add("adBox2");
            }

            Label adLabel = new Label(ad.getName());
            adLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Kalameh'; -fx-font-size: 14px;");

            // Hover Effect
            adBox.setOnMouseEntered(event -> adBox.setStyle("-fx-background-color: #3c3c3c;"));
            adBox.setOnMouseExited(event -> adBox.setStyle("-fx-background-color: transparent;"));

            // دکمه‌ها
            Button editButton = new Button("ویرایش");
            editButton.setOnAction(e -> handleEditAd(ad));

            Button deleteButton = new Button("حذف");
            deleteButton.setOnAction(e -> handleDeleteAd(ad));

            Button viewButton = new Button("مشاهده آگهی");
            viewButton.setOnAction(e -> handleShowAd(ad));
            viewButton.getStyleClass().add("buttong");

            // تنظیم موقعیت المان‌ها
            HBox leftBox = new HBox(imageView, viewButton);
            leftBox.setAlignment(Pos.CENTER_LEFT);
            leftBox.setSpacing(10);

            HBox rightBox = new HBox(editButton, deleteButton, adLabel);
            rightBox.setAlignment(Pos.CENTER_RIGHT);
            rightBox.setSpacing(10);

            adBox.getChildren().addAll(leftBox, rightBox);
            adBox.setPrefHeight(110);
            adBox.getStyleClass().add("ad-box");

            adsContainer.getChildren().add(adBox);
        }
    }

    // دکمه افزودن آگهی جدید
    @FXML
    private void handleAddAd(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addAd.fxml"));
        Parent root = loader.load();

        addAdController ac = loader.getController();
        ac.setUserData(loggedInUser, allAds);

        Stage current = (Stage) ((Node) event.getSource()).getScene().getWindow();
        NavigationHelper.navigateToScene(current, root);
    }

    // دکمه ویرایش آگهی
    private void handleEditAd(Ad ad) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editAd.fxml"));
            Parent root = loader.load();

            EditAdController ac = loader.getController();
            ac.setAdAndUserData(ad, loggedInUser, allAds);

            Stage current = (Stage) adsContainer.getScene().getWindow();
            NavigationHelper.navigateToScene(current, root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("خطای بازکردن پنجره ویرایش آگهی" + e.toString());
        }
    }

    // دکمه حذف آگهی
    private void handleDeleteAd(Ad ad) {
        loadAds();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("حذف آگهی");
        alert.setContentText("آگهی " + ad.getName() + " حذف خواهد شد!");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            allAds.remove(ad);
            ads.remove(ad);
            AdManager.saveAds(allAds);
            loadAds(); //refresh
            Alert al = new Alert(Alert.AlertType.CONFIRMATION);
            al.setTitle("حذف شد");
            al.setContentText("آگهی شما حذف شد برای همیشه: " + ad.getName());
            al.showAndWait();
        } else {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("اطلاعیه");
            alert2.setContentText("آگهی شما حذف نشد: " + ad.getName());
            alert2.showAndWait();
        }
    }

    // دکمه بازگشت
    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        NavigationHelper.navigateToDashboard(event, loggedInUser, allAds);
    }

    @FXML
    private void handleShowAd(Ad ad) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adDetails.fxml"));
            Parent root = loader.load();

            adDetailsController ac = loader.getController();
            ac.setAdAndUserData(ad, loggedInUser, allAds);

            Stage current = (Stage) adsContainer.getScene().getWindow();
            NavigationHelper.navigateToScene(current, root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("خطا در بازیابی داده های آگهی" + e.toString());
            alert.showAndWait();
        }
    }
}