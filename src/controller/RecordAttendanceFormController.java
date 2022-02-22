package controller;

import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import security.SecurityContextHolder;
import util.DepAlert;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class RecordAttendanceFormController {

    public TextField txtStudentID;
    public ImageView imgProfile;
    public Button btnIn;
    public Button btnOut;
    public Label lblDate;
    public Label lblID;
    public Label lblName;
    public Label lblStatus;
    public Label lblStudentName;
    public AnchorPane root;
    private PreparedStatement stmSearchStudent;
    private String studentId;

    public void initialize() {
        btnIn.setDisable(true);
        btnOut.setDisable(true);
        lblDate.setText(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %1$Tp", new Date()));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), (event -> {
            lblDate.setText(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %1$Tp", new Date()));
        })));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            stmSearchStudent = connection.prepareStatement("SELECT * FROM student WHERE id=?");
        } catch (Exception e) {
            new DepAlert(Alert.AlertType.WARNING, "Failed to connect with DB", "Connection Error", "Error").show();
            e.printStackTrace();
            Platform.runLater(() -> {
                ((Stage) (btnIn.getScene().getWindow())).close();
            });
        }

        root.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case F10:
                    btnIn.fire();
                    break;
                case ESCAPE:
                    btnOut.fire();
                    break;
            }
        });
    }

    public void btnIn_OnAction(ActionEvent event) {
        recordAttendance(true);
    }


    public void btnOut_OnAction(ActionEvent event) {
        recordAttendance(false);
    }

    private void recordAttendance(boolean in) {
        Connection connection = DBConnection.getInstance().getConnection();

        /* Check last record status */
        try {
            String lastStatus = null;
            PreparedStatement stm = connection.
                    prepareStatement("SELECT status, date FROM attendance WHERE student_id=? ORDER BY date DESC LIMIT 1");
            stm.setString(1, studentId);
            ResultSet rst = stm.executeQuery();
            if (rst.next()) {
                lastStatus = rst.getString("status");
            }

            if ((lastStatus != null && lastStatus.equals("IN") && in)||
                    (lastStatus != null && lastStatus.equals("OUT") && !in)){
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/AlertForm.fxml"));
                AnchorPane root = fxmlLoader.load();
                AlertFormController controller = fxmlLoader.getController();
                controller.initData(studentId,lblStudentName.getText(),
                        rst.getTimestamp("date").toLocalDateTime(), in);
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("Alert! Horek");
                stage.sizeToScene();
                stage.centerOnScreen();
                stage.showAndWait();
            } else {
                PreparedStatement stm2 = connection.
                        prepareStatement("INSERT INTO attendance (date, status, student_id, username) VALUES (NOW(),?,?,?)");
                stm2.setString(1, in ? "IN" : "OUT");
                stm2.setString(2, studentId);
                stm2.setString(3, SecurityContextHolder.getPrincipal().getUsername());
                if (stm2.executeUpdate() != 1) {
                    throw new RuntimeException("Failed to add the attendance");
                }
                txtStudentID.clear();
                txtStudentID_OnAction(null);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            new DepAlert(Alert.AlertType.ERROR, "Failed to save the attendance, try again",
                    "Failure", "Error", ButtonType.OK).show();
        }
    }

    public void txtStudentID_OnAction(ActionEvent event) {
        btnIn.setDisable(true);
        btnOut.setDisable(true);
        lblStudentName.setText("Please enter/scan the student ID to proceed");
        imgProfile.setImage(new Image("/view/assets/qr-code.png"));

        if (txtStudentID.getText().trim().isEmpty()) {
            return;
        }

        try {
            stmSearchStudent.setString(1, txtStudentID.getText().trim());
            ResultSet rst = stmSearchStudent.executeQuery();

            if (rst.next()) {
                lblStudentName.setText(rst.getString("name").toUpperCase());
                InputStream is = rst.getBlob("picture").getBinaryStream();
                imgProfile.setImage(new Image(is));
                btnIn.setDisable(false);
                btnOut.setDisable(false);
                studentId = txtStudentID.getText();
                txtStudentID.selectAll();
            } else {
                new DepAlert(Alert.AlertType.ERROR, "Invalid Student ID, Try again!", "Oops!", "Error").show();
                txtStudentID.selectAll();
                txtStudentID.requestFocus();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new DepAlert(Alert.AlertType.WARNING, "Something went wrong. Please try again!", "Connection Failure", "Error").show();
            txtStudentID.selectAll();
            txtStudentID.requestFocus();
        }
    }

}
