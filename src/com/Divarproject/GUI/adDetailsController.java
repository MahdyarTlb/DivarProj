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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class adDetailsController {

    @FXML
    private ImageView adImageView;

    @FXML
    private Label name;

    @FXML
    private Label price;

    @FXML
    private Label descriptionLabel;

    private Ad selectedAd; // آگهی انتخاب‌شده
    private User loggedInUser; // کاربر لاگین‌شده
    private List<Ad> ads; // لیست آگهی‌ها

    // تنظیم اطلاعات آگهی و کاربر
    public void setAdAndUserData(Ad ad, User user, List<Ad> ads) {
        this.selectedAd = ad;
        this.loggedInUser = user;
        this.ads = ads;

        // نمایش تصویر آگهی
        if (selectedAd.getImagePath() != null && !selectedAd.getImagePath().isEmpty()) {
            String imagePath = "file:" + selectedAd.getImagePath();
            try {
                adImageView.setImage(new Image(imagePath));
            } catch (Exception e) {
                System.err.println("خطا در بارگذاری تصویر آگهی: " + e.getMessage());
                adImageView.setImage(new Image(getClass().getResourceAsStream("/com/Divarproject/images/default.png")));
            }
        } else {
            adImageView.setImage(new Image(getClass().getResourceAsStream("/com/Divarproject/images/default.png")));
        }

        // نمایش اطلاعات آگهی
        name.setText(selectedAd.getName());
        price.setText((selectedAd.getPrice()) + " تومان");
        descriptionLabel.setText(selectedAd.getDescription());
    }

    // دکمه بازگشت
    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("showAds.fxml"));
        Parent root = loader.load();

        showAdsController sa = loader.getController();
        sa.setAds(ads, loggedInUser);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // دکمه تماس با کاربر
    @FXML
    private void handleContactUser(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("تماس با کاربر");
        alert.setHeaderText(null);
        alert.setContentText("تماس با فروشنده: " + selectedAd.getContact());
        alert.showAndWait();
    }

    // دکمه چت با کاربر
    @FXML
    private void handleChatWithUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
        Parent root = loader.load();

        ChatController chatController = loader.getController();
        chatController.setUserData(loggedInUser, selectedAd.getOwner()); // ارسال کاربر فعلی و فروشنده
        chatController.setAds(ads);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }
}