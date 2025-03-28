package com.Divarproject.GUI;

import com.Divarproject.Ads.Ad;
import com.Divarproject.Ads.AdManager;
import com.Divarproject.Register.User;
import com.Divarproject.Register.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class LoginController {
    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private Button register;

    @FXML
    private Label status;

    private List<User> users;

    public void initialize() {
        users = UserManager.LoadUsers();
    }

    @FXML
    private void handleLogin() {
        String username = email.getText();
        String userpass = password.getText();

        if (username.isEmpty() || userpass.isEmpty()) {
            status.setText("لطفا نام کاربری و رمز عبور را وارد کنید!");
            return;
        }

        for (User user : users) {
            if (user.getUserName().equalsIgnoreCase(username) && user.getPassword().equals(userpass)) {
                status.setText("ورود موفقیت‌آمیز!");
                loadDashboard(username);  // ارسال نام کاربر به داشبورد
                return;
            }
        }
        status.setText("نام کاربری یا رمز عبور اشتباه است!");
    }

    @FXML
    private void handleRegister() {
        String username1 = email.getText();
        String userpass1 = password.getText();

        if (username1.isEmpty() || userpass1.isEmpty()) {
            status.setText("لطفا نام کاربری و رمز عبور را وارد کنید!");
            return;
        }

        for (User user : users) {
            if (user.getUserName().equalsIgnoreCase(username1)) {
                status.setText("این نام کاربری قبلاً گرفته شده است!");
                return;
            }
        }

        User newUser = new User(username1, userpass1);
        users.add(newUser);
        UserManager.saveUsers(users);

        status.setText("ثبت‌نام موفقیت‌آمیز! حالا وارد شوید.");
    }

    private void loadDashboard(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Parent root = loader.load();

            // گرفتن کنترلر داشبورد و ارسال نام کاربر
            DashboardController dashboardController = loader.getController();
            User loggedInUser = users.stream()
                    .filter(user -> user.getUserName().equalsIgnoreCase(username))
                    .findFirst()
                    .orElse(null);
            if (loggedInUser == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("خطای ورود");
                alert.setContentText("کاربر با مشخصات مذکور یافت نشد!\n لطفا ابتدا ثبت نام کنید.");
                alert.showAndWait();
                System.err.println("خطا: کاربر پیدا نشد!");
                return;
            }

            // بارگذاری لیست آگهی‌ها
            List<Ad> ads = AdManager.loadAds(users); // اطمینان از بارگذاری آگهی‌ها
            if (ads == null) {
                System.err.println("خطا: لیست آگهی‌ها خالی است!");
                return;
            }


            dashboardController.setUser(loggedInUser, ads);

            Stage stage = (Stage) email.getScene().getWindow();
            NavigationHelper.navigateToScene(stage, root);
        } catch (Exception e) {
            status.setText("خطا در بارگذاری داشبورد!");
        }
    }
}