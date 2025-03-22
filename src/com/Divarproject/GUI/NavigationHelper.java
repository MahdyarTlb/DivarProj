package com.Divarproject.GUI;

import com.Divarproject.Ads.Ad;
import com.Divarproject.Register.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class NavigationHelper {

    // متد عمومی برای بازگشت به داشبورد
    public static void navigateToDashboard(ActionEvent event, User loggedInUser, List<Ad> ads) throws IOException {
        // بارگذاری صفحه داشبورد
        FXMLLoader loader = new FXMLLoader(NavigationHelper.class.getResource("dashboard.fxml"));
        Parent root = loader.load();

        // گرفتن کنترلر داشبورد و ارسال اطلاعات کاربر و آگهی‌ها
        DashboardController dashboardController = loader.getController();
        if (loggedInUser != null) {
            dashboardController.setUser(loggedInUser, ads);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ارور مقداردهی");
            alert.setContentText("مشکل در شناسایی کاربر");
        }

        // تنظیمات Stage و Scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
