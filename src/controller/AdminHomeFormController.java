package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import security.SecurityContextHolder;

public class AdminHomeFormController {

    public Button btnRecordAttendance;
    public Button btnViewReports;
    public Button btnUserProfile;
    public Button btnManageUsers;
    public Button btnBackupRestore;
    public Button btnSignOut;
    public Label lblHover;
    public Label lblGreeting;

    public void initialize(){
        final String initialText = lblHover.getText();
        btnRecordAttendance.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));
        btnViewReports.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));
        btnUserProfile.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));
        btnManageUsers.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));
        btnBackupRestore.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));
        btnSignOut.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));

        btnRecordAttendance.setOnMouseExited(event -> lblHover.setText(initialText));
        btnViewReports.setOnMouseExited(event -> lblHover.setText(initialText));
        btnUserProfile.setOnMouseExited(event -> lblHover.setText(initialText));
        btnManageUsers.setOnMouseExited(event -> lblHover.setText(initialText));
        btnBackupRestore.setOnMouseExited(event -> lblHover.setText(initialText));
        btnSignOut.setOnMouseExited(event -> lblHover.setText(initialText));

        lblGreeting.setText("Welcome " + SecurityContextHolder.getPrincipal().getName() + "!");
    }

    private void displayHoveringText(Button button){
        lblHover.setText(button.getAccessibleText());
    }

    public void btnBackupRestore_OnAction(ActionEvent event) {

    }

    
    public void btnManageUsers_OnAction(ActionEvent event) {

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
