package journal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import resources.StylingLeftMenu;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class JournalController implements Initializable {
    JournalModel journalModel = new JournalModel();

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

    // list of journals
    @FXML
    public AnchorPane anchorPaneListOfJournals;
    @FXML
    private AnchorPane anchorPaneListOfJournalTopPadding;
    @FXML
    private Label lblDateInJournalList;

    // header
    @FXML
    public Label lblPatientName;
    @FXML
    public Button btnNewJournal;
    @FXML
    public Button btnNewProgram;

    // journal notes
    @FXML
    public AnchorPane anchorPaneJournalNotes;
    @FXML
    public Label lblJournalNotesHeader;
    @FXML
    public TextArea textAreaJournalNotes;
    @FXML
    public Button btnSaveJournalNotes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // styling left menu
        anchorPaneLeftmenu.setStyle("-fx-background-color: " + StylingLeftMenu.BACKGROUND_DARK_GREY);
        lblUserLoggedInHeader.setStyle("-fx-text-fill: " + StylingLeftMenu.LOGGED_IN_USER_HEADER_TEXT_FILL);

        // styling left menu - buttons
        btnPatients.setStyle("-fx-background-color: " + StylingLeftMenu.BACKGROUND_DARK_GREY
                + "; -fx-text-fill: " + StylingLeftMenu.ITEMS_IN_LEFT_MENU_TEXT_FILL);
        btnJournals.setStyle("-fx-background-color: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL);
        btnProgram.setStyle("-fx-background-color: " + StylingLeftMenu.BACKGROUND_DARK_GREY
                + "; -fx-text-fill: " + StylingLeftMenu.ITEMS_IN_LEFT_MENU_TEXT_FILL);
        btnStart.setStyle("-fx-background-color: " + StylingLeftMenu.BACKGROUND_DARK_GREY +
                "; -fx-text-fill: " + StylingLeftMenu.ITEMS_IN_LEFT_MENU_TEXT_FILL);
        btnPatients.setAlignment(Pos.BASELINE_LEFT);
        btnProgram.setAlignment(Pos.BASELINE_LEFT);
        btnJournals.setAlignment(Pos.BASELINE_LEFT);
        btnStart.setAlignment(Pos.BASELINE_LEFT);
        btnPatients.setPadding(new Insets(0, 0, 0, 20));
        btnProgram.setPadding(new Insets(0, 0, 0, 20));
        btnJournals.setPadding(new Insets(0, 0, 0, 20));
        btnStart.setPadding(new Insets(0, 0, 0, 20));

        // styling list of journals
        anchorPaneListOfJournals.setStyle("-fx-background-color: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND);
        anchorPaneListOfJournalTopPadding.setStyle("-fx-background-color: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND);

        // logged in user
        lblUserLoggedInHeader.setText("Inloggad: " + login.UserHolder.getLoggedInUser());

        // patient header
        lblPatientName.setText(PatientHolder.getFirstName() + " " + PatientHolder.getLastName()
                + ", " + PatientHolder.getPersonNr());
        lblPatientName.setStyle("-fx-text-fill: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-font-weight: bold");
        btnNewJournal.setStyle("-fx-background-color: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLeftMenu.BACKGROUND_DARK_GREY
                + "; -fx-font-weight: bold");
        btnNewProgram.setStyle("-fx-background-color:  " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");

        // Notes
        btnSaveJournalNotes.setVisible(false);

        fillJournalsList();
    }

    private void fillJournalsList() {
        try {
            ArrayList<JournalData> journalList = this.journalModel.getJournals(PatientHolder.getPersonNr());

            if (!journalList.isEmpty()) {
                GridPane gridPane = new GridPane();
                
                for (JournalData jd : journalList) {

                    Label label = new Label();
                    label.setText(jd.getDateOfCreation());
                    label.setStyle("-fx-text-fill: " + StylingLeftMenu.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                    label.setPadding(new Insets(10, 40, 0, 40));
                    gridPane.add(label, 1, journalList.indexOf(jd) + 1);

                    // show the latest journal in textArea and date in the header
                    if (journalList.indexOf(jd) == 0) {
                        textAreaJournalNotes.setText(jd.getInformation());
                        lblJournalNotesHeader.setText(jd.getDateOfCreation());
                    }
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
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/patients/patients.fxml"));
            Stage window = (Stage) btnPatients.getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AddNewJournal(javafx.event.ActionEvent event) {
        textAreaJournalNotes.visibleProperty().setValue(true);
        textAreaJournalNotes.clear();
        btnSaveJournalNotes.setVisible(true);
    }

    public void SaveNewJournal(javafx.event.ActionEvent event) throws SQLException {
        this.journalModel.newJournalNotes(PatientHolder.getPersonNr(), textAreaJournalNotes.getText());

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ny journal sparad");
        alert.setHeaderText("Det gick ju bra!");
        alert.show();
    }
}
