package com.Divarproject.GUI;

import com.Divarproject.Register.User;
import com.Divarproject.Register.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
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

        for (User user : users) {
            if (user.getUserName().equals(username) && user.getPassword().equals(userpass)) {
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
            dashboardController.setUser(username);

            Stage stage = (Stage) email.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            status.setText("خطا در بارگذاری داشبورد!");
        }
    }
}
