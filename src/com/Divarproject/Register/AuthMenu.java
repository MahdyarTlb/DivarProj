package com.Divarproject.Register;

import com.Divarproject.Ads.Ad;
import com.Divarproject.Ads.AdManager;
import com.Divarproject.Ads.CarAd;
import com.Divarproject.Data.DataManager;
import com.Divarproject.Rate.Rating;
import com.Divarproject.Rate.RatingManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

public class AuthMenu extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/login.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Divar");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        List<User> users = UserManager.LoadUsers();
        List<Ad> ads = AdManager.loadAds(users);
        List<Rating> ratings = RatingManager.loadRatings(users);

        DataManager.getInstance().setUsers(users);
        DataManager.getInstance().setAds(ads);
        DataManager.getInstance().setRatings(ratings);

        launch(args);
    }
}
