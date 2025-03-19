package com.Divarproject.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationHelper {

    // متد عمومی برای بازگشت به داشبورد
    public static void navigateToDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(NavigationHelper.class.getResource("dashboard.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
