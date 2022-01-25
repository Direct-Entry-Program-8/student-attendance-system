package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserHomeFormController {

    public Button btnRecordAttendance;
    public Button btnViewReports;
    public Button btnUserProfile;
    public Button btnSignOut;
    public Label lblHover;

    public void initialize(){
        final String initialText = lblHover.getText();
        btnRecordAttendance.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));
        btnViewReports.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));
        btnUserProfile.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));
        btnSignOut.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));

        btnRecordAttendance.setOnMouseExited(event -> lblHover.setText(initialText));
        btnViewReports.setOnMouseExited(event -> lblHover.setText(initialText));
        btnUserProfile.setOnMouseExited(event -> lblHover.setText(initialText));
        btnSignOut.setOnMouseExited(event -> lblHover.setText(initialText));
    }

    private void displayHoveringText(Button button){
        lblHover.setText(button.getAccessibleText());
    }

    public void btnRecordAttendance_OnAction(ActionEvent event) {

    }


    public void btnSignOut_OnAction(ActionEvent event) {

    }


    public void btnUserProfile_OnAction(ActionEvent event) {

    }


    public void btnViewReports_OnAction(ActionEvent event) {

    }

}
