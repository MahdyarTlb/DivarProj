package com.Divarproject.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardController {

    @FXML
    private Label User;

    public void setUser(String username){
        User.setText("خوش آمدی " + username + "!");
    }


    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void handleAddAd(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("addAd.fxml")); // باز کردن صفحه ثبت آگهی
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleShowAds(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("showAds.fxml")); // باز کردن صفحه نمایش آگهی‌ها
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0); // بستن برنامه
    }
}
