package com.Divarproject.GUI;


import com.Divarproject.Ads.Ad;
import com.Divarproject.Register.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChatListController implements Initializable {

    @FXML
    private VBox chatsContainer;

    private User loggedInUser;
    private List<Ad> ads;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }

    // تنظیم کاربر لاگین‌شده
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
        loadChats(); // بارگذاری لیست چت‌ها
    }

    // بارگذاری چت‌ها
    private void loadChats() {
        List<String> chatUsers = com.Divarproject.GUI.ChatStorage.loadChats(loggedInUser.getUserName());
        chatsContainer.getChildren().clear();

        for (String user : chatUsers) {
            Label chatLabel = new Label("چت با " + user);
            chatLabel.getStyleClass().add("ad-box");
            chatLabel.prefWidthProperty().bind(chatsContainer.widthProperty());
            chatLabel.setAlignment(Pos.CENTER);
            chatLabel.setOnMouseClicked(e -> {
                try {
                    openChat(user);
                } catch (IOException ex) {
                    System.err.println("خطا در بازکردن چت: " + ex.getMessage());
                }
            });
            chatsContainer.getChildren().add(chatLabel);
        }
    }

    // بازکردن صفحه چت با کاربر مورد نظر
    private void openChat(String receiver) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
        Parent root = loader.load();

        ChatController chatController = loader.getController();
        chatController.setUserData(loggedInUser, new User(receiver, "1")); // ساخت یک کاربر موقت برای نمایش چت
        chatController.setAds(ads);

        Stage stage = (Stage) chatsContainer.getScene().getWindow();
        NavigationHelper.navigateToScene(stage, root);
    }

    // دکمه بازگشت
    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Parent root = loader.load();

        DashboardController dashboardController = loader.getController();
        dashboardController.setUser(loggedInUser, ads);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        NavigationHelper.navigateToScene(stage, root);
    }
}