package com.Divarproject.GUI;

import com.Divarproject.Ads.Ad;
import com.Divarproject.Register.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MyAdsController {

    @FXML
    private VBox adsContainer;

    private User loggedInUser;
    private ObservableList<Ad> ads = FXCollections.observableArrayList();

    // تنظیم اطلاعات کاربر لاگین‌شده
    public void setUserData(User user, List<Ad> ads) {
        this.loggedInUser = user;
        this.ads.addAll(ads.stream()
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
            adBox.setSpacing(10);

            Label adLabel = new Label(ad.getName());
            adLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Kalameh Light'; -fx-font-size: 14px;");

            adBox.setOnMouseEntered(event -> adBox.setStyle("-fx-background-color: #3c3c3c;"));
            adBox.setOnMouseExited(event -> adBox.setStyle("-fx-background-color: transparent;"));

            Button editButton = new Button("ویرایش");
            editButton.setOnAction(e -> handleEditAd(ad));

            Button deleteButton = new Button("حذف");
            deleteButton.setOnAction(e -> handleDeleteAd(ad));

            adBox.setAlignment(Pos.CENTER_RIGHT);

            adBox.getStyleClass().add("ad-box");


            adBox.getChildren().addAll(adLabel, editButton, deleteButton);
            adsContainer.getChildren().add(adBox);
        }
    }

    // دکمه افزودن آگهی جدید
    @FXML
    private void handleAddAd(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addAd.fxml"));
        Parent root = loader.load();

        addAdController ac = loader.getController();
        ac.setUserData(loggedInUser, ads);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // دکمه ویرایش آگهی
    private void handleEditAd(Ad ad) {
        System.out.println("ویرایش آگهی: " + ad.getName());
        // TODO: باز کردن صفحه ویرایش آگهی
    }

    // دکمه حذف آگهی
    private void handleDeleteAd(Ad ad) {
        loadAds();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("حذف آگهی");
        alert.setContentText("آگهی " + ad.getName() + " حذف خواهد شد!");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            ads.remove(ad);
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
        NavigationHelper.navigateToDashboard(event, loggedInUser, ads);
    }
}