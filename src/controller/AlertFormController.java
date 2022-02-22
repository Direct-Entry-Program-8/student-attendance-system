package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AlertFormController {
    public Button btnProceed;
    public Button btnCallPolice;
    public Label lblId;
    public Label lblName;
    public Label lblDate;

    public void initialize() throws URISyntaxException {
        playSiren();
    }

    private void playSiren() throws URISyntaxException {
        Media media = new Media(this.getClass().getResource("/assets/siren.mp3").toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setCycleCount(2);
        player.play();
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
