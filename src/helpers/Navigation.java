package helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import patients.PatientHolder;

import java.io.IOException;

public class Navigation {
    public void navigateToPatients(Button button) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/patients/patients.fxml"));
            Stage window = (Stage) button.getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToJournals(Button button) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/journal/journal.fxml"));
            Stage window = (Stage) button.getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToExercises(Button button) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/exercises/exercises.fxml"));
            Stage window = (Stage) button.getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToPrograms(Button button) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/program/program.fxml"));
            Stage window = (Stage) button.getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateToProgramDetails(Button button) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/programDetails/programDetails.fxml"));
            Stage window = (Stage) button.getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
