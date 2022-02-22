package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AlertFormController {
    public Button btnProceed;
    public Button btnCallPolice;
    public Label lblId;
    public Label lblName;
    public Label lblDate;

    public void initialize(){

    }

    public void initData(String studentId, String studentName, LocalDateTime date, boolean in){
        lblId.setText("ID: " + studentId.toUpperCase());
        lblName.setText("NAME: " + studentName.toUpperCase());
        lblDate.setText(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")) + " - " + (in? "IN" : "OUT"));
    }

    public void btnProceed_OnAction(ActionEvent actionEvent) {
    }

    public void btnCallPolice_OnAction(ActionEvent actionEvent) {
    }
}
