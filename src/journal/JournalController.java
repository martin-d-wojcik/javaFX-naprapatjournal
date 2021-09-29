package journal;

import helpers.JavaFxHelper;
import helpers.Navigation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import patients.PatientHolder;
import resources.StylingLayout;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class JournalController implements Initializable {
    JournalModel journalModel = new JournalModel();
    Navigation navigation = new Navigation();
    JavaFxHelper javaFxHelper = new JavaFxHelper();

    // left menu
    @FXML
    private Label lblUserLoggedInHeader;
    @FXML
    public AnchorPane anchorPaneLeftmenu;
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

    // list of journals
    @FXML
    public AnchorPane anchorPaneListOfJournals;

    // header
    @FXML
    public Label lblPatientName;
    @FXML
    public Button btnNewJournal;
    @FXML
    public Button btnNewProgram;
    @FXML
    public Button btnShowPrograms;

    // journal notes
    @FXML
    public AnchorPane anchorPaneJournalNotes;
    @FXML
    public Label lblJournalNotesHeader;
    @FXML
    public TextArea textAreaJournalNotes;
    @FXML
    public Button btnSaveJournalNotes;
    @FXML
    public Button btnSaveChanges;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StylingLayout.stylingLeftMenu("journal", lblUserLoggedInHeader, anchorPaneLeftmenu, btnPatients,
                btnJournals, btnProgram, btnStart, btnExercises);

        // styling list of journals
        anchorPaneListOfJournals.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND);

        // logged in user
        lblUserLoggedInHeader.setText("Inloggad: " + login.UserHolder.getLoggedInUser());

        // patient header
        lblPatientName.setText(PatientHolder.getFirstName() + " " + PatientHolder.getLastName()
                + ", " + PatientHolder.getPersonNr());
        lblPatientName.setStyle("-fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-font-weight: bold");
        btnNewJournal.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLayout.BACKGROUND_DARK_GREY
                + "; -fx-font-weight: bold");
        btnNewProgram.setStyle("-fx-background-color:  " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");

        // Notes
        btnSaveJournalNotes.setStyle("-fx-background-color:  " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");
        btnSaveJournalNotes.setVisible(false);
        btnSaveChanges.setStyle("-fx-background-color:  " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-font-weight: bold");
        btnSaveChanges.setVisible(false);

        fillJournalsList();
    }

    private void fillJournalsList() {
        try {
            ArrayList<JournalData> journalList = this.journalModel.getJournals(PatientHolder.getPersonNr());

            Collections.reverse(journalList);

            if (!journalList.isEmpty()) {
                this.anchorPaneListOfJournals.getChildren().clear();

                GridPane gridPane = new GridPane();

                for (JournalData jd : journalList) {
                    Label label = new Label();
                    label.setText(jd.getDateOfCreation());

                    // set bigger top padding to the first row only
                    if (journalList.indexOf(jd) == 0) {
                        label.setPadding(new Insets(50, 40, 0, 40));
                        label.setStyle("-fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL);
                    }
                    else {
                        label.setPadding(new Insets(10, 40, 0, 40));
                        label.setStyle("-fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                    }

                    label.setOnMouseClicked((mouseEvent) -> {
                        lblJournalNotesHeader.setText(jd.getDateOfCreation());
                        textAreaJournalNotes.setText(jd.getInformation());

                        // Set All Labels text to default color
                        javaFxHelper.resetGridPaneLabelsTextFill(gridPane);

                        label.setStyle("-fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL);
                    });

                    gridPane.add(label, 1, journalList.indexOf(jd) + 1);

                    // show the latest journal in textArea and date in the header
                    textAreaJournalNotes.setText(journalList.get(0).getInformation());
                    lblJournalNotesHeader.setText(journalList.get(0).getDateOfCreation());
               }
                anchorPaneListOfJournals.getChildren().add(0, gridPane);
            }
            else {
                lblJournalNotesHeader.setText("Det finns inga journaler för denna patient. Klicka Ny journal för " +
                        "att lägga till en journal");
                textAreaJournalNotes.visibleProperty().setValue(false);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void GoToPatients(javafx.event.ActionEvent event) {
        navigation.navigateToPatients(btnPatients);
    }

    public void AddNewJournal(javafx.event.ActionEvent event) {
        textAreaJournalNotes.visibleProperty().setValue(true);
        textAreaJournalNotes.clear();
        btnSaveJournalNotes.setVisible(true);
    }

    public void SaveNewJournal(javafx.event.ActionEvent event) throws SQLException {
        int rowsInserted = this.journalModel.newJournalNotes(PatientHolder.getPersonNr(), textAreaJournalNotes.getText());

        if (rowsInserted==1){
            btnSaveJournalNotes.setVisible(false);

            // update list of journals listArray
            fillJournalsList();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ny journal sparad");
            alert.setHeaderText("Det gick ju bra!");
            alert.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Ny journal skapades INTE");
            alert.setHeaderText("Något gick snett!");
            alert.show();
        }
    }

    public void SaveChangedJournal(javafx.event.ActionEvent event) throws SQLException {

        String date = lblJournalNotesHeader.getText().trim();
        int rowsUpdated = this.journalModel.updateJournalNotes(PatientHolder.getPersonNr(),
                textAreaJournalNotes.getText(), date);

        if (rowsUpdated==1){
            btnSaveJournalNotes.setVisible(false);

            // update list of journals listArray
            fillJournalsList();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ändringarna i journalen sparades");
            alert.setHeaderText("Det gick ju bra!");
            alert.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Ändringarna i journalen sparades INTE");
            alert.setHeaderText("Uppdateringen misllyckades!");
            alert.show();
        }
    }

    public void ShowNewProgram(javafx.event.ActionEvent event) {
        navigation.navigateToPrograms(btnNewProgram);
    }

    public void ShowPrograms(javafx.event.ActionEvent event) {
        navigation.navigateToProgramDetails(btnShowPrograms);
    }

    public void GoToExercises(javafx.event.ActionEvent event) {
        this.navigation.navigateToExercises(btnExercises);
    }
}
