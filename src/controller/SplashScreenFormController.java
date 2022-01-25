package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SplashScreenFormController {
    public Label lblStatus;

    public void initialize(){
        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(()->{
                try {
                    Stage stage = new Stage();
                    AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/AdminHomeForm.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.sizeToScene();
                    stage.setTitle("Student Attendance: Sign In");
                    stage.setResizable(false);
                    stage.show();
                    stage.centerOnScreen();
                    ((Stage)lblStatus.getScene().getWindow()).close();
                } catch ( IOException e) {
                    e.printStackTrace();
                }
            });
        }).start();

    }
}
