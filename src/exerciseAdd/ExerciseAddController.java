package exerciseAdd;

import dbUtil.dbConnection;
import helpers.Navigation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import resources.StylingLayout;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ExerciseAddController implements Initializable {
    Navigation navigation = new Navigation();

    @FXML
    private Label lblAddPatientHeader;

    // textfields
    @FXML
    private TextField textfieldExerciseName;
    @FXML
    private TextField textFieldbodyPart;
    @FXML
    private TextField textFieldexerciseType;
    @FXML
    private TextField textFielddescription;

    // warning labels
    @FXML
    private Label lblexerciseNameWarning;
    @FXML
    private Label lblbodyPartWarning;
    @FXML
    private Label lblexerciseTypeWarning;
    @FXML
    private Label lbldescriptionWarning;

    // buttons
    @FXML
    private Button btnAddNewExercise;
    @FXML
    private Button btnCancel;

    // sql queries
    String sqlInsertNewExercise = "INSERT INTO exercise (exerciseName, type, description, bodypart) VALUES (?, ?, ?, ?)";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hideWarningLabels();

        // styling controls
        btnAddNewExercise.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLayout.BACKGROUND_DARK_GREY
                + "; -fx-font-weight: bold");
        btnCancel.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");
        lblAddPatientHeader.setStyle("-fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");
    }

    public void AddExerciseData(javafx.event.ActionEvent event) throws SQLException {
        Connection conn = dbConnection.getConnection();

        String nameOfExercise = textfieldExerciseName.getText();
        String bodyPart = textFieldbodyPart.getText();
        String typeOfExercise = textFieldexerciseType.getText();
        String description = textFielddescription.getText();

        if(nameOfExercise.trim().isEmpty() || bodyPart.trim().isEmpty() || typeOfExercise.trim().isEmpty() ||
                description.trim().isEmpty()) {

            hideWarningLabels();
            // assert that no text field is empty
            if(nameOfExercise.trim().isEmpty()) {
                lblexerciseNameWarning.visibleProperty().setValue(true);
            }
            if(bodyPart.trim().isEmpty()) {
                lblbodyPartWarning.visibleProperty().setValue(true);
            }
            if(typeOfExercise.trim().isEmpty()) {
                lblexerciseTypeWarning.visibleProperty().setValue(true);
            }
            if(description.trim().isEmpty()) {
                lbldescriptionWarning.visibleProperty().setValue(true);
            }
        }
        else {
            // Prepare query
            assert conn != null;
            try {
                PreparedStatement prepStmt = conn.prepareStatement(sqlInsertNewExercise);
                prepStmt.setString(1, nameOfExercise);
                prepStmt.setString(2, typeOfExercise);
                prepStmt.setString(3, description);
                prepStmt.setString(4, bodyPart);

                prepStmt.executeUpdate();
                prepStmt.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ã–vningen sparad.");
                alert.setHeaderText("Det gick ju bra.");
                alert.show();

                // Close exercise Add window
                navigation.navigateToExercises(btnCancel);

            } catch (SQLException e) {
                System.err.println("Error: " + e);
                Alert al;
                if (e.toString().toLowerCase().contains("primarykey") &&
                        e.toString().toLowerCase().contains("unique")){

                    al = new Alert(Alert.AlertType.ERROR, "Övning " + nameOfExercise + " finns redan !"
                            + "\n\nRätta till namnet");
                    al.setHeaderText("Sparades INTE i databasen !");
                    al.show();
                }
                else{
                    al = new Alert(Alert.AlertType.ERROR, e.toString());
                    al.setHeaderText("SQL Exception !");
                    al.show();
                }
            }
        }
    }

    public void CancelAddExercise(javafx.event.ActionEvent event) {
        navigation.navigateToExercises(btnCancel);
    }

    private void hideWarningLabels() {
        lblexerciseNameWarning.setVisible(false);
        lblbodyPartWarning.setVisible(false);
        lblexerciseTypeWarning.setVisible(false);
        lbldescriptionWarning.setVisible(false);
    }
}
