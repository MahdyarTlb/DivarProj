package com.Divarproject.GUI;

import com.Divarproject.Ads.Ad;
import com.Divarproject.Register.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class DashboardController {

    private User loggedInUser;
    private List<Ad> ads;


    @FXML
    private Label User;

    public void setUser(User user, List<Ad> ads) {
        this.loggedInUser = user;
        this.ads = ads;

        User.setText("خوش آمدی " + loggedInUser.getUserName() + "!");
    }


    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void handleAddAd(ActionEvent event) throws IOException {
        // بارگذاری فایل FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Category_selection.fxml"));
        Parent root = loader.load();

        // گرفتن کنترلر فرم انتخاب دسته‌بندی
        CategorySselectionController categorySelectionController = loader.getController();
        categorySelectionController.setUserData(loggedInUser, ads); // ارسال کاربر و لیست آگهی‌ها

        // تنظیمات Stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleShowAds(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("showAds.fxml")); // باز کردن صفحه نمایش آگهی‌ها
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0); // بستن برنامه
    }
}
