package com.Divarproject.GUI;

import com.Divarproject.Ads.Ad;
import com.Divarproject.Ads.AdManager;
import com.Divarproject.Ads.AnimalAd;
import com.Divarproject.Ads.CarAd;
import com.Divarproject.Register.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class editAd_AnimalController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField descriptionArea;

    @FXML
    private TextField imagePathField;

    @FXML
    private TextField animalType;

    @FXML
    private TextField animalAge;

    @FXML
    private CheckBox isVaccinated;

    @FXML
    private ImageView imageView;

    private Ad selectedAd; // آگهی انتخاب‌شده
    private User loggedInUser; // کاربر لاگین‌شده
    private List<Ad> ads; // لیست آگهی‌ها

    // تنظیم اطلاعات آگهی و کاربر
    public void setAdAndUserData(Ad ad, User user, List<Ad> ads) {
        this.selectedAd = ad;
        this.loggedInUser = user;
        this.ads = ads;

        // نمایش اطلاعات فعلی آگهی
        titleField.setText(selectedAd.getName());
        priceField.setText(String.valueOf(selectedAd.getPrice()));
        descriptionArea.setText(selectedAd.getDescription());
        imagePathField.setText(selectedAd.getImagePath());

        // بررسی نوع آگهی
        if (selectedAd instanceof AnimalAd) {
            AnimalAd animalAd = (AnimalAd) selectedAd;
            if(!(animalAd.getAnimalType().isEmpty())){
                animalType.setText(animalAd.getAnimalType());
            }
            if(animalAd.getAge() != -1){
                animalAge.setText(String.valueOf(animalAd.getAge()));
            }
            isVaccinated.setSelected(animalAd.getIsVaccinated());

        }

        // نمایش تصویر (اگر وجود داشته باشه)
        if (selectedAd.getImagePath() != null && !selectedAd.getImagePath().isEmpty()) {
            imageView.setImage(new Image(new File(selectedAd.getImagePath()).toURI().toString()));
        }
    }

    // دکمه ذخیره تغییرات
    @FXML
    private void handleSubmit(ActionEvent event) throws IOException {
        if (validateInputs()) {
            // بررسی نوع آگهی
            if (selectedAd instanceof AnimalAd) {
                AnimalAd animalAd = (AnimalAd) selectedAd;
                // ویرایش فقط فیلد‌هایی که پر شدن
                if (!titleField.getText().isEmpty()) {
                    animalAd.setName(titleField.getText());
                }
                if (!priceField.getText().isEmpty()) {
                    animalAd.setPrice(Integer.parseInt(priceField.getText()));
                }
                if (!descriptionArea.getText().isEmpty()) {
                    animalAd.setDescription(descriptionArea.getText());
                }
                if (!imagePathField.getText().isEmpty()) {
                    animalAd.setImagePath(imagePathField.getText());
                }
                if (!animalType.getText().isEmpty()) {
                    animalAd.setAnimalType(animalType.getText());
                }
                if (!animalAge.getText().isEmpty()) {
                    animalAd.setAge(Integer.parseInt(animalAge.getText()));
                }
                animalAd.setVaccinated(isVaccinated.isSelected());

                ads.remove(selectedAd); // حذف آگهی قدیمی
                ads.add(animalAd); // اضافه کردن آگهی جدید

                // ذخیره تغییرات در فایل
                AdManager.saveAds(ads);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("موفق!");
                alert.setContentText("با موفقیت آگهی شما ویرایش شد.");
                alert.showAndWait();

                // بازگشت به صفحه آگهی‌های من
                FXMLLoader loader = new FXMLLoader(getClass().getResource("myAds.fxml"));
                Parent root = loader.load();

                MyAdsController myAdsController = loader.getController();
                myAdsController.setUserData(loggedInUser, ads);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                NavigationHelper.navigateToScene(stage, root);
            } else {
                showAlert("خطا", "نوع آگهی نامعتبر است.");
            }
        }
    }

    // اعتبارسنجی ورودی‌ها
    private boolean validateInputs() {
        if (!priceField.getText().isEmpty() && !isNumeric(priceField.getText())) {
            showAlert("خطا", "قیمت باید عدد باشد.");
            return false;
        }
        if (!animalAge.getText().isEmpty() && !isNumeric(animalAge.getText())) {
            showAlert("خطا", "سن حیوان باید عدد باشد.");
            return false;
        }
        return true;
    }

    // بررسی عدد بودن رشته
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // نمایش پیام خطا
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

    // دکمه بازگشت
    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("myAds.fxml"));
        Parent root = loader.load();

        MyAdsController myAdsController = loader.getController();
        myAdsController.setUserData(loggedInUser, ads);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        NavigationHelper.navigateToScene(stage, root);
    }

    // دکمه انتخاب تصویر
    @FXML
    private void handleImageSelection(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("انتخاب تصویر");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("تصاویر", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            imagePathField.setText(selectedFile.getAbsolutePath());
            imageView.setImage(new Image(selectedFile.toURI().toString()));
        }
    }
}