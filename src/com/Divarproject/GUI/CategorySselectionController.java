package com.Divarproject.GUI;

import java.util.List;

import com.Divarproject.Ads.Ad;
import com.Divarproject.Register.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CategorySselectionController {

    private User loggedInUser; // کاربر لاگین شده
    private List<Ad> ads; // لیست آگهی‌ها

    private Stage stage;
    private Scene scene;

    @FXML
    private Button car;

    @FXML
    private Button electro;

    @FXML
    private Button house;

    @FXML
    private Button animal;

    @FXML
    private Button back;

    @FXML
    private void handleCarAd(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addAd.fxml"));
        Parent root = loader.load();

        // گرفتن کنترلر فرم ایجاد آگهی
        addAdController addadController = loader.getController();
        addadController.setUserData(loggedInUser, ads); // ارسال کاربر و لیست آگهی‌ها

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        NavigationHelper.navigateToDashboard(event, loggedInUser, ads);
    }

    // تنظیم اطلاعات کاربر و آگهی‌ها
    public void setUserData(User user, List<Ad> ads) {
        this.loggedInUser = user;
        this.ads = ads;
    }

}
