package com.Divarproject.GUI;

import com.Divarproject.Ads.Ad;
import com.Divarproject.Ads.AnimalAd;
import com.Divarproject.Ads.CarAd;
import com.Divarproject.Register.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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

    @FXML
    private Label daste;

    @FXML
    private Label Accident;

    @FXML
    private Label milage;

    @FXML
    private Label productionage;

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

        if (selectedAd.getType() == Ad.AdType.CAR) {

            CarAd carad = (CarAd) selectedAd;
            // نمایش اطلاعات آگهی
            name.setText(selectedAd.getName());
            price.setText((selectedAd.getPrice()) + " تومان");
            descriptionLabel.setText(selectedAd.getDescription());
            milage.setText("کارکرد: " + carad.getMileage() + " کیلومتر");
            productionage.setText("سال تولید: " + carad.getProductionage());
            Accident.setText((carad.isHasAccident()) ? "تصادفی است!" : "تصادفی نیست");
            daste.setText("آگهی ها/" + Ad.AdType.CAR.toString());

        } else if (selectedAd.getType() == Ad.AdType.ANIMAL) {

            AnimalAd animalad = (AnimalAd) selectedAd;
            name.setText(animalad.getName());
            price.setText(animalad.getPrice() + " تومان");
            descriptionLabel.setText(animalad.getDescription());
            milage.setText("نوع حیوان: " + animalad.getAnimalType());
            productionage.setText("سن: " + animalad.getAge() + " سال");
            Accident.setText((animalad.getIsVaccinated()) ? "واکسن زده" : "واکسن نزده!");
            daste.setText("آگهی ها/" + Ad.AdType.ANIMAL.toString());
        }
    }

    // دکمه بازگشت
    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("showAds.fxml"));
        Parent root = loader.load();

        showAdsController sa = loader.getController();
        sa.setAds(ads, loggedInUser);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        NavigationHelper.navigateToScene(stage, root);
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

        if(loggedInUser.equals(selectedAd.getOwner())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("خطا!");
            alert.setContentText("نمیشه با خودت چت کنی...!");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Parent root = loader.load();

            DashboardController dc = loader.getController();
            dc.setUser(loggedInUser, ads); // ارسال کاربر فعلی و فروشنده

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            NavigationHelper.navigateToScene(stage, root);
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
        Parent root = loader.load();

        ChatController chatController = loader.getController();
        chatController.setUserData(loggedInUser, selectedAd.getOwner()); // ارسال کاربر فعلی و فروشنده
        chatController.setAds(ads);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        NavigationHelper.navigateToScene(stage, root);
    }
}