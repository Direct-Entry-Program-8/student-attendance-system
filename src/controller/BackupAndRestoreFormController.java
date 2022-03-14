package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import util.DepAlert;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class BackupAndRestoreFormController {
    public Button btnBackup;

    public void btnBackup_OnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose backup location");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Backup files (*.dep8bak)", "*.dep8bak"));
        File file = fileChooser.showSaveDialog(btnBackup.getScene().getWindow());

        if (file != null){
            ProcessBuilder mysqlDumpProcessBuilder = new ProcessBuilder("mysqldump",
                    "-h", "localhost",
                    "--port", "3306",
                    "-u", "root",
                    "-pmysql",
                    "-d",
                    "--databases", "dep8_student_attendance");
            mysqlDumpProcessBuilder.redirectOutput(file);
            try {
                Process mysqlDump = mysqlDumpProcessBuilder.start();
                int exitCode = mysqlDump.waitFor();

                if (exitCode == 0){
                    new DepAlert(Alert.AlertType.INFORMATION, "").show();
                }else{
                    new DepAlert(Alert.AlertType.ERROR, "Backup process failed, try again!",
                            "Backup failed", "Error", ButtonType.OK).show();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
