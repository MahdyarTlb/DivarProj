package com.Divarproject.GUI;

import com.Divarproject.Ads.AdManager;
import com.Divarproject.Ads.AnimalAd;
import com.Divarproject.Register.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AddAnimalAdController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionArea;
    @FXML
    private TextField priceField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField imagePathField;
    @FXML
    private TextField animalTypeField;
    @FXML
    private TextField ageField;
    @FXML
    private CheckBox isVaccinatedCheckBox;
    @FXML
    private ImageView imageView;

    private User loggedInUser;
    private List<com.Divarproject.Ads.Ad> ads;

    public void setUserData(User user, List<com.Divarproject.Ads.Ad> ads) {
        this.loggedInUser = user;
        this.ads = ads;
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        try {
            // گرفتن اطلاعات از فیلدها
            String title = titleField.getText();
            String description = descriptionArea.getText();
            int price = Integer.parseInt(priceField.getText());
            String contact = contactField.getText();
            String imagePath = imagePathField.getText();
            String animalType = animalTypeField.getText();
            int age = Integer.parseInt(ageField.getText());
            boolean isVaccinated = isVaccinatedCheckBox.isSelected();

            // ساخت آگهی جدید
            AnimalAd newAd = new AnimalAd(
                    title, description, price, contact, loggedInUser, imagePath,
                    animalType, age, isVaccinated
            );
            if(ads == null){
                System.err.println("عدم مقداردهی صحیح ads!");
                return;
            }

            ads.add(newAd);
            AdManager.saveAds(ads); // ذخیره آگهی در فایل

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ایجاد آگهی");
            alert.setHeaderText(null);
            alert.setContentText("آگهی شما با موفقیت ثبت و تایید شد!");
            alert.showAndWait();

            NavigationHelper.navigateToDashboard(event, loggedInUser, ads);

        } catch (NumberFormatException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("لطفاً اطلاعات را صحیح وارد کنید!");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        NavigationHelper.navigateToDashboard(event, loggedInUser, ads);
    }

    @FXML
    private void handleImageSelection(ActionEvent event) throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("انتخاب تصویر");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("تصاویر", "*jpg",  "*png" , "*jpeg"));

        Stage stage = (Stage) imagePathField.getScene().getWindow();
        File selectedfile = fc.showOpenDialog(stage);

        if (selectedfile != null) {
            imagePathField.setText(selectedfile.getAbsolutePath());

            Image image = new Image(selectedfile.toURI().toString());
            imageView.setImage(image);
        }
    }
}