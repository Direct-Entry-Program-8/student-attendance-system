package controller;

import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    private PreparedStatement stmSearchStudent;

    public void initialize() {
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
            new DepAlert(Alert.AlertType.WARNING, "Failed to connect with DB","Connection Error", "Error").show();
            e.printStackTrace();
            Platform.runLater(()->{
                ((Stage)(btnIn.getScene().getWindow())).close();
            });
        }
    }

    public void btnIn_OnAction(ActionEvent event) {

    }


    public void btnOut_OnAction(ActionEvent event) {

    }


    public void txtStudentID_OnAction(ActionEvent event) {
        lblStudentName.setText("Please enter/scan the student ID to proceed");
        imgProfile.setImage(new Image("/view/assets/qr-code.png"));

        if (txtStudentID.getText().trim().isEmpty()){
           return;
        }

        try {
            stmSearchStudent.setString(1, txtStudentID.getText().trim());
            ResultSet rst = stmSearchStudent.executeQuery();

            if (rst.next()){
                lblStudentName.setText(rst.getString("name").toUpperCase());
                InputStream is = rst.getBlob("picture").getBinaryStream();
                imgProfile.setImage(new Image(is));
            }else{
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
