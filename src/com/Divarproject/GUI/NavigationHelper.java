package com.Divarproject.GUI;

import com.Divarproject.Ads.Ad;
import com.Divarproject.Register.User;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

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
        navigateToScene(stage, root);
    }

    public static void navigateToScene(Stage stage, Parent newSceneRoot) {

        if (stage.getScene() == null) {
            stage.setScene(new Scene(newSceneRoot)); // اگر Scene وجود نداره، یکی اضافه کن
        }

        // Blur Effect برای صفحه فعلی
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(0); // شروع بدون Blur
        stage.getScene().getRoot().setEffect(blur);

        // انیمیشن Blur برای صفحه فعلی
        Timeline blurTimelineOut = new Timeline(
                new KeyFrame(Duration.seconds(0.4), new KeyValue(blur.radiusProperty(), 10))
        );

        // وقتی انیمیشن خروج تموم شد، صفحه جدید رو لود کن
        blurTimelineOut.setOnFinished(event -> {
            Scene newScene = new Scene(newSceneRoot);
            stage.setScene(newScene);

            // Blur Effect برای صفحه جدید
            GaussianBlur newBlur = new GaussianBlur();
            newBlur.setRadius(10); // شروع با Blur
            newScene.getRoot().setEffect(newBlur);

            // انیمیشن Blur برای صفحه جدید
            Timeline blurTimelineIn = new Timeline(
                    new KeyFrame(Duration.seconds(0.4), new KeyValue(newBlur.radiusProperty(), 0))
            );

            blurTimelineIn.play(); // شروع انیمیشن ورود
        });

        blurTimelineOut.play(); // شروع انیمیشن خروج
    }
}
