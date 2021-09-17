package program;

import dbUtil.dbConnection;
import exercises.ExerciseData;
import helpers.JavaFxHelper;
import helpers.Navigation;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import journal.JournalData;
import patients.PatientData;
import patients.PatientHolder;
import resources.StylingLayout;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class ProgramController implements Initializable {
    ProgramModel programModel = new ProgramModel();
    Navigation navigation = new Navigation();
    JavaFxHelper javaFxHelper = new JavaFxHelper();

    // temp
    @FXML
    private Label lblTemp;
    @FXML
    private AnchorPane rootPane;

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

    // program details view
    @FXML
    private Label lblProgramName;
    @FXML
    private TextArea textAreaJournalNotes;
    @FXML
    private Button btnNewProgram;
    @FXML
    private Button btnSaveProgram;
    @FXML
    private Button btnDeleteProgram;

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

        // styling header
        lblPatientName.setText(PatientHolder.getFirstName() + " " + PatientHolder.getLastName()
                + ", " + PatientHolder.getPersonNr());
        lblPatientName.setStyle("-fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-font-weight: bold");

        // styling program details
        btnNewProgram.setStyle("-fx-background-color:  " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");
        btnSaveProgram.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLayout.BACKGROUND_DARK_GREY
                + "; -fx-font-weight: bold");
        btnSaveProgram.setVisible(false);
        btnDeleteProgram.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLayout.BACKGROUND_DARK_GREY
                + "; -fx-font-weight: bold");

        // populate list of programs
        fillProgramsList();
    }

    public void fillProgramsList() {
        try {
            ArrayList<ProgramData> programList = this.programModel.getPrograms(PatientHolder.getPersonNr());

            if (programList.isEmpty()) {
                lblProgramName.setText("Det finns inga program");
            }
            else {
                // set the newest program name as header and program info
                lblProgramName.setText(programList.get(programList.size() - 1).getProgramName());
///                textAreaJournalNotes.setText(programList.get(programList.size() - 1).getInformation());
                // 2021-09-17
                textAreaJournalNotes.setText(programList.get(programList.size() - 1).getInformation().replace("; ", "\n").replace(";", "\n"));

                Collections.reverse(programList);

                this.anchorPaneListOfPrograms.getChildren().clear();

                GridPane gridPane = new GridPane();

                for (ProgramData pd : programList) {
                    Label label = new Label();
                    label.setText(pd.getProgramName() + ", " + pd.getDateOfCreation());

                    // set bigger top padding to the first row only
                    if (programList.indexOf(pd) == 0) {
                        label.setPadding(new Insets(50, 40, 0, 40));
                        label.setStyle("-fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL);
                    }
                    else {
                        label.setPadding(new Insets(10, 40, 0, 40));
                        label.setStyle("-fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                    }

                    label.setOnMouseClicked((mouseEvent) -> {
                        // Set All Labels text to default color
                        javaFxHelper.resetGridPaneLabelsTextFill(gridPane);

                        lblProgramName.setText(pd.getProgramName());
                        ///textAreaJournalNotes.setText(pd.getInformation());
                        //2021-09-17
                        textAreaJournalNotes.setText(pd.getInformation().replace("; ", "\n").replace(";", "\n"));

                        label.setStyle("-fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL);
                    });

                    gridPane.add(label, 1, programList.indexOf(pd) + 1);
                }
                anchorPaneListOfPrograms.getChildren().add(0, gridPane);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void NewProgram(javafx.event.ActionEvent event) {
        lblTemp.setText("Nytt program clicked");

        AnchorPane paneNewProgram = null;
        try {
            paneNewProgram = FXMLLoader.load(getClass().getResource("/programAdd/programAdd.fxml"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        rootPane.getChildren().setAll(paneNewProgram);
    }

    public void GoToPatients(javafx.event.ActionEvent event) {
        this.navigation.navigateToPatients(btnPatients);
    }

    public void GoToJournals(javafx.event.ActionEvent event) {
        this.navigation.navigateToJournals(btnJournals);
    }

    public void GoToExercises(javafx.event.ActionEvent event) {
        this.navigation.navigateToExercises(btnExercises);
    }
}
