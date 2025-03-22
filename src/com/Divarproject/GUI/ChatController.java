package com.Divarproject.GUI;

import com.Divarproject.Ads.Ad;
import com.Divarproject.Register.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ChatController {

    private List<Ad> ads;
    private User loggedInUser;
    private User seller; // فروشنده (طرف مقابل)

    public void setAds(List<Ad> ads) {
        if (ads == null) {
            this.ads = Collections.emptyList(); // جلوگیری از Null Pointer Exception
        } else {
            this.ads = ads;
        }
    }
    @FXML
    private AnchorPane messagesContainer;

    @FXML
    private TextField messageInput;

    @FXML
    private Label usernameLabel;

    @FXML
    private ScrollPane scrollPane;

    private ObservableList<String> messages = FXCollections.observableArrayList(); // لیست پیام‌ها

    // تنظیم اطلاعات کاربران
    public void setUserData(User loggedInUser, User seller) {
        this.loggedInUser = loggedInUser;
        this.seller = seller;

        usernameLabel.setText(seller.getUserName());

        // بارگذاری پیام‌های قبلی از فایل
        messages.addAll(ChatStorage.loadMessages(loggedInUser.getUserName(), seller.getUserName()));
        loadMessages();
    }

    // بارگذاری پیام‌ها
    private void loadMessages() {
        messagesContainer.getChildren().clear(); // پاک کردن محتوای قبلی
        double yOffset = 10;
        for (String message : messages) {
            HBox messageBox = new HBox();
            Label messageLabel = new Label(message);
            // تشخیص اینکه پیام از طرف کاربر لاگین‌شده هست یا طرف مقابل
            if (message.startsWith(loggedInUser.getUserName())) {
                // پیام کاربر لاگین‌شده
                messageLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Kalameh'; -fx-font-size: 14px;" +
                        "-fx-padding: 10; -fx-background-color: #024037; -fx-border-radius: 10; -fx-background-radius: 10;");
                messageBox.setAlignment(Pos.CENTER_RIGHT);
                AnchorPane.setRightAnchor(messageBox, 10.0);
            } else {
                // پیام طرف مقابل
                messageLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Kalameh'; -fx-font-size: 14px;" +
                        "-fx-padding: 10; -fx-background-color: #a51542; -fx-border-radius: 10; -fx-background-radius: 10;" +
                        "-fx-alignment: center-left;");
                messageBox.setAlignment(Pos.CENTER_LEFT);
                AnchorPane.setLeftAnchor(messageBox, 10.0);
            }
            AnchorPane.setTopAnchor(messageBox, yOffset);
            messageBox.getChildren().add(messageLabel);
            messageBox.getStyleClass().add("ad-box2");
            messagesContainer.getChildren().add(messageBox);

            yOffset += 55;

            scrollPane.setVvalue(1.0);
        }


    }

    private ObservableList<String> messages1 = FXCollections.observableArrayList(); // لیست پیام‌ها

    // ارسال پیام
    @FXML
    private void sendMessage() {
        String message = messageInput.getText().trim();
        if (!message.isEmpty()) {
            String fmessage = loggedInUser.getUserName() + ":  " + message;
            messages.add(fmessage); // اضافه کردن پیام به لیست
            messageInput.clear(); // پاک کردن فیلد ورودی
            loadMessages(); // بروزرسانی صفحه

            // ذخیره پیام‌ها در فایل مخصوص جفت کاربر
            ChatStorage.saveMessages(loggedInUser.getUserName(), seller.getUserName(), messages);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "لطفاً پیام خود را وارد کنید!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    // دکمه بازگشت
    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        NavigationHelper.navigateToDashboard(event, loggedInUser, ads);
    }

    private boolean flag = false;

    // دکمه امتیاز دادن
    @FXML
    private void rateUser() {
        if (!flag) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("امتیاز دادن");
            alert.setHeaderText(null);
            alert.setContentText("آیا می‌خواهید به این کاربر امتیاز دهید؟");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    flag = true;
                    // منطق امتیاز دادن
                    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert1.setTitle(null);
                    alert1.setContentText("کاربر " + seller.getUserName() + " امتیاز دریافت کرد.");
                    alert1.showAndWait();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("زرنگی!؟");
            alert.setContentText("نمیتونی به یه کاربر دو  بار امتیاز بدی!\n یدونه بستشه:)");
            alert.showAndWait();
        }
    }
}