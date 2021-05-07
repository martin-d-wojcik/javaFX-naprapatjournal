package program;

import helpers.Navigation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import login.UserHolder;
import patients.PatientHolder;
import resources.StylingLayout;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProgramController implements Initializable {
    Navigation navigation = new Navigation();

    // left menu
    @FXML
    private Label lblUserLoggedInHeader;
    @FXML
    private AnchorPane anchorPaneLeftmenu;
    @FXML
    private Button btnPatients;
    @FXML
    private Button btnJournals;
    @FXML
    private Button btnProgram;
    @FXML
    private Button btnStart;
    @FXML
    private Button btnExercises;

    // list of programs
    @FXML
    private AnchorPane anchorPaneListOfPrograms;

    // header
    @FXML
    private Label lblPatientName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StylingLayout.stylingLeftMenu("program", lblUserLoggedInHeader, anchorPaneLeftmenu, btnPatients,
                btnJournals, btnProgram, btnStart, btnExercises);

        // styling list of programs
        anchorPaneListOfPrograms.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND);

        // logged in user
        lblUserLoggedInHeader.setText("Inloggad: " + login.UserHolder.getLoggedInUser());

        lblPatientName.setText(PatientHolder.getFirstName() + " " + PatientHolder.getLastName()
                + ", " + PatientHolder.getPersonNr());
        lblPatientName.setStyle("-fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-font-weight: bold");
    }

    public void GoToPatients(javafx.event.ActionEvent event) {
        this.navigation.navigateToPatients(btnPatients);
    }

    public void GoToJournals(javafx.event.ActionEvent event) {
        this.navigation.navigateToJournals(btnJournals);
    }
}
