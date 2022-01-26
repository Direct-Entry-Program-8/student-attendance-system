package controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SplashScreenFormController {
    public Label lblStatus;
    private File file;              // Backup file to restore

    public void initialize() {
        establishDBConnection();
    }

    private void establishDBConnection() {
        lblStatus.setText("Establishing DB Connection..");

        new Thread(() -> {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dep8_student_attendance", "root", "mysql");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {

                /* Let's find out whether the DB exists or not */
                if (e.getSQLState().equals("42000")) {
                    Platform.runLater(this::loadImportDBForm);
                }else{
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private void loadImportDBForm() {
        try {
            SimpleObjectProperty<File> fileProperty = new SimpleObjectProperty<>();
            Stage stage = new Stage();
            AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/ImportDBForm.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setTitle("Student Attendance System: First Time Boot");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(lblStatus.getScene().getWindow());
            stage.centerOnScreen();
            stage.showAndWait();
            file = fileProperty.getValue();

            if (file == null){
                lblStatus.setText("Creating a new DB..");
                new Thread(()->{
                    try {
                        sleep(100);
                        Platform.runLater(()->lblStatus.setText("Loading database script.."));

                        InputStream is = this.getClass().getResourceAsStream("/assets/db-script.sql");
                        byte[] buffer = new byte[is.available()];
                        is.read(buffer);
                        String script = new String(buffer);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }else{
                /* Todo: Restore the backup */
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
