package com.Divarproject.GUI;

import javafx.event.ActionEvent;
import com.Divarproject.Ads.Ad;
import com.Divarproject.Ads.AdManager;
import com.Divarproject.Ads.CarAd;
import com.Divarproject.Register.User;
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


public class addAdController {
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
    private TextField productionYearField;
    @FXML
    private TextField mileageField;
    @FXML
    private CheckBox hasAccidentCheckBox;
    @FXML
    private Button submitButton;
    @FXML
    private ImageView imageView;

    private User loggedInUser; // کاربر لاگین شده
    private List<Ad> ads; // لیست آگهی‌ها

    // تنظیم اطلاعات کاربر و آگهی‌ها
    public void setUserData(User user, List<Ad> ads) {
        this.loggedInUser = user;
        this.ads = ads;
    }

    // متد ثبت آگهی
    @FXML
    private void handleSubmit(ActionEvent event) {
        try {
            // گرفتن اطلاعات از فیلدها
            String title = titleField.getText();
            String description = descriptionArea.getText();
            int price = Integer.parseInt(priceField.getText());
            String contact = contactField.getText();
            String imagePath = imagePathField.getText();
            int productionYear = Integer.parseInt(productionYearField.getText());
            int mileage = Integer.parseInt(mileageField.getText());
            boolean hasAccident = hasAccidentCheckBox.isSelected();

            // ساخت آگهی جدید
            CarAd newAd = new CarAd(
                    title, description, price, contact, loggedInUser, imagePath,
                    productionYear, mileage, hasAccident
            );
            if(ads == null){
                System.err.println("عدم مقداردهی صحیح ads!");
                return;
            }

            ads.add(newAd);
            AdManager.saveAds(ads); // ذخیره آگهی در فایل

            Alert alert =  new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ایجاد آگهی");
            alert.setHeaderText(null);
            alert.setContentText("آگهی شما با موفقیت ثبت و تایید شد!");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Parent root = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.setUser(loggedInUser, ads);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene); // فقط صحنه (Scene) عوض می‌شود
            stage.show();

        } catch (NumberFormatException | IOException e) {
            // مدیریت خطاها
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("لطفاً اطلاعات عددی را صحیح وارد کنید!");
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
