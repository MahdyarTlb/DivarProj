package com.Divarproject.GUI;

import com.Divarproject.Ads.Ad;
import com.Divarproject.Data.DataManager;
import com.Divarproject.Rate.Rating;
import com.Divarproject.Rate.RatingManager;
import com.Divarproject.Register.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
    private Label timestampLabel;

    @FXML
    private TextField messageInput;

    @FXML
    private Label usernameLabel;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label Rating;

    private ObservableList<String> messages = FXCollections.observableArrayList(); // لیست پیام‌ها

    // تنظیم اطلاعات کاربران
    public void setUserData(User loggedInUser, User seller) {
        this.loggedInUser = loggedInUser;
        this.seller = seller;

        usernameLabel.setText(seller.getUserName());

        List<Rating> ratings = DataManager.getInstance().getRatings();
        double averageRating = RatingManager.getAverageRating(seller, ratings);
        Rating.setText("امتیاز: " + String.format("%.1f", averageRating));

        // بارگذاری پیام‌های قبلی از فایل
        messages.addAll(ChatStorage.loadMessages(loggedInUser.getUserName(), seller.getUserName()));
        loadMessages();
    }

    // بارگذاری پیام‌ها
    private void loadMessages() {
        messagesContainer.getChildren().clear(); // پاک کردن محتوای قبلی
        double yOffset = 10;
        for (String message : messages) {
            String[] parts = message.split(" ");
            String senderName = parts[0];
            String content = message.substring(senderName.length() + 2); // جدا کردن نام فرستنده از متن پیام

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

            // اضافه کردن زمان پیام
            String timestamp = parts[parts.length - 1]; // فرض بر اینکه زمان پیام آخرین قسمت پیام هست
            timestampLabel.setText(timestamp);
        }
    }

    private ObservableList<String> messages1 = FXCollections.observableArrayList(); // لیست پیام‌ها

    // ارسال پیام
    @FXML
    private void sendMessage() {
        String message = messageInput.getText().trim();
        if (!message.isEmpty()) {

            String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
            String fmessage = loggedInUser.getUserName() + ":  " + message + " (" + timestamp + ")";
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

    @FXML
    private void rateUser() {
        List<Rating> ratings = DataManager.getInstance().getRatings();
        //-----------------------------------------------------------------------------------------------
        if (RatingManager.hasRated(loggedInUser, seller, ratings)) {
            showErrorAlert("خطا", "شما قبلاً به این کاربر امتیاز داده‌اید!");
            return;
        }
        //-----------------------------------------------------------------------------------------------
        ChoiceBox<Integer> ratingChoiceBox = new ChoiceBox<>();
        ratingChoiceBox.getItems().addAll(1, 2, 3, 4, 5);
        ratingChoiceBox.setValue(5); // مقدار پیش‌فرض

        // ایجاد Alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("امتیازدهی به کاربر");
        alert.setHeaderText("لطفاً امتیاز خود را به کاربر " + seller.getUserName() + " وارد کنید:");
        alert.getDialogPane().setContent(ratingChoiceBox);

        // نمایش Alert و دریافت نتیجه
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                int score = ratingChoiceBox.getValue();
                try {
                    // اضافه کردن امتیاز جدید
                    Rating newRating = new Rating(loggedInUser, seller, score);
                    ratings.add(newRating);

                    // ذخیره امتیازات
                    RatingManager.saveRating(ratings);

                    // نمایش پیام موفقیت
                    showInfoAlert("موفقیت", "امتیاز شما با موفقیت ثبت شد.");

                    //update
                    double averageRating = RatingManager.getAverageRating(seller, ratings);
                    Rating.setText("امتیاز: " + String.format("%.1f", averageRating));

                } catch (IllegalArgumentException e) {
                    showErrorAlert("خطا", e.getMessage());
                }
            }
        });
    }

    // نمایش پیام موفقیت
    private void showInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // نمایش پیام خطا
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}