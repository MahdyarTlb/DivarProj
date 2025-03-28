package com.Divarproject.GUI;

import com.Divarproject.Ads.Ad;
import com.Divarproject.Data.DataManager;
import com.Divarproject.Rate.Rating;
import com.Divarproject.Rate.RatingManager;
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

    @FXML
    private Label User1;

    public void setUser(User user, List<Ad> ads) {
        this.loggedInUser = user;
        this.ads = ads;
        User.setText("خوش آمدی " + loggedInUser.getUserName() + "!");

        loadDashboard();
    }

    private void loadDashboard() {
        List<Rating> ratings = DataManager.getInstance().getRatings();

        double averageRating = RatingManager.getAverageRating(loggedInUser, ratings);
        User1.setText("امتیاز شما: " + String.format("%.1f", averageRating));
    }

    @FXML
    private void handleAddAd(ActionEvent event) throws IOException {
        // بارگذاری فایل FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Category_selection.fxml"));
        Parent root = loader.load();

        // گرفتن کنترلر فرم انتخاب دسته‌بندی
        CategorySselectionController categorySelectionController = loader.getController();
        categorySelectionController.setUserData(loggedInUser); // ارسال کاربر و لیست آگهی‌ها

        // تنظیمات Stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        NavigationHelper.navigateToScene(stage, root);
    }

    @FXML
    private void handleShowAds(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("showAds.fxml"));
        Parent root = loader.load();

        // گرفتن کنترلر و ارسال لیست آگهی‌ها
        showAdsController sa = loader.getController();
        sa.setAds(ads, loggedInUser);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        NavigationHelper.navigateToScene(stage, root);
    }

    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0); // بستن برنامه
    }

    @FXML
    private void handleChatList(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chatList.fxml"));
        Parent root = loader.load();

        ChatListController chatListController = loader.getController();
        chatListController.setLoggedInUser(loggedInUser);
        chatListController.setAds(ads);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        NavigationHelper.navigateToScene(stage, root);
    }

    @FXML
    private void handlemyAds(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("myAds.fxml"));
        Parent root = loader.load();

        MyAdsController myAdsController = loader.getController();
        myAdsController.setUserData(loggedInUser, ads);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        NavigationHelper.navigateToScene(stage, root);
    }
}
