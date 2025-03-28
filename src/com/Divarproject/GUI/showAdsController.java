package com.Divarproject.GUI;

import com.Divarproject.Ads.Ad;
import com.Divarproject.Ads.Ad.AdType;
import com.Divarproject.Register.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class showAdsController {

    @FXML
    private AnchorPane adsContainer;

    @FXML
    private ChoiceBox<AdType> typeFilter;

    @FXML
    private TextField minPriceFilter;

    @FXML
    private TextField maxPriceFilter;

    private List<Ad> allAds; // لیست تمام آگهی‌ها
    private User loggedInUser;

    public void setAds(List<Ad> ads, User loggedInUser) {
        this.allAds = ads;
        this.loggedInUser = loggedInUser;

        if (adsContainer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("خطا");
            alert.setContentText("عدم مقداردهی adsContainer");
            alert.showAndWait();
            return;
        }

        // تنظیم انواع آگهی در ChoiceBox
        typeFilter.getItems().addAll(AdType.values());
        typeFilter.getItems().add(0, null); // افزودن گزینه خالی به اول لیست

        // بارگذاری اولیه آگهی‌ها
        loadAds(allAds);
    }

    // بارگذاری آگهی‌ها
    private void loadAds(List<Ad> ads) {
        adsContainer.getChildren().clear();
        double yOffset = 50;

        for (Ad ad : ads) {
            HBox adBox = new HBox(10);
            adBox.setSpacing(10);
            adBox.getStyleClass().add("ad-box");
            adBox.setAlignment(javafx.geometry.Pos.CENTER_RIGHT); // راست‌چین کردن المان‌ها
            adBox.setOnMouseClicked(e -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("adDetails.fxml"));
                    Parent root = loader.load();

                    adDetailsController adDetailsController = loader.getController();
                    adDetailsController.setAdAndUserData(ad, loggedInUser, allAds);

                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    NavigationHelper.navigateToScene(stage, root);
                } catch (IOException ex) {
                    System.err.println("خطا در بارگذاری صفحه جزئیات آگهی: " + ex.getMessage());
                }
            });

            // نمایش تصویر
            ImageView imageView = null;
            try {
                String imagePath = ad.getImagePath();
                Image image;
                if (imagePath != null && !imagePath.isEmpty()) {
                    imagePath = "file:" + imagePath;
                    image = new Image(imagePath);
                } else {
                    image = new Image(getClass().getResourceAsStream("/com/Divarproject/images/default.png"));
                }
                imageView = new ImageView(image);
            } catch (Exception e) {
                System.err.println("خطا در بارگذاری تصویر آگهی: " + e.getMessage());
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

            // نمایش اطلاعات آگهی
            Label title = new Label(ad.getName());
            title.setStyle("-fx-text-fill: white; -fx-font-family: 'Kalameh'; -fx-font-size: 14px;");
            Label price = new Label(ad.getPrice() + " تومان");
            price.setStyle("-fx-text-fill: white; -fx-font-family: 'Kalameh'; -fx-font-size: 14px;");

            adBox.getChildren().addAll(price, title, imageView); // راست‌چین کردن المان‌ها

            AnchorPane.setTopAnchor(adBox, yOffset);
            AnchorPane.setLeftAnchor(adBox, 10.0);
            AnchorPane.setRightAnchor(adBox, 10.0);

            adsContainer.getChildren().add(adBox);

            yOffset += 120;
        }
    }

    // اعمال فیلترها
    @FXML
    private void applyFilters() {
        AdType selectedType = typeFilter.getValue();
        double minPrice = minPriceFilter.getText().isEmpty() ? 0 : Double.parseDouble(minPriceFilter.getText());
        double maxPrice = maxPriceFilter.getText().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxPriceFilter.getText());

        // فیلتر کردن آگهی‌ها
        List<Ad> filteredAds = allAds.stream()
                .filter(ad -> (selectedType == null || ad.getType() == selectedType))
                .filter(ad -> ad.getPrice() >= minPrice && ad.getPrice() <= maxPrice)
                .collect(Collectors.toList());

        // بروزرسانی لیست آگهی‌ها
        loadAds(filteredAds);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        NavigationHelper.navigateToDashboard(event, loggedInUser, allAds);
    }
}