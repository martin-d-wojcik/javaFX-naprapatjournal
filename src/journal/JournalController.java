package journal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import resources.StylingLeftMenu;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

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
    // @FXML
    // private AnchorPane anchorPaneListOfJournalTopPadding;
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
    @FXML
    public Button btnSaveChanges;

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
        // anchorPaneListOfJournalTopPadding.setStyle("-fx-background-color: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND);

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
        btnSaveJournalNotes.setStyle("-fx-background-color:  " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");
        btnSaveJournalNotes.setVisible(false);
        btnSaveChanges.setStyle("-fx-background-color:  " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-font-weight: bold");

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
                        label.setStyle("-fx-text-fill: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL);
                    }
                    else {
                        label.setPadding(new Insets(10, 40, 0, 40));
                        label.setStyle("-fx-text-fill: " + StylingLeftMenu.ITEMS_IN_LEFT_MENU_TEXT_FILL);
                    }

                    label.setOnMouseClicked((mouseEvent) -> {
                        lblJournalNotesHeader.setText(jd.getDateOfCreation());
                        textAreaJournalNotes.setText(jd.getInformation());

                        // Set All Labels text to default color
                        resetGridPaneLabelsTextFill(gridPane);

                        label.setStyle("-fx-text-fill: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL);
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

    private void resetGridPaneLabelsTextFill(GridPane gridPane){
        int gridPaneHeight = gridPane.getChildren().size();

        for (int row = 0; row < gridPaneHeight; row++){
            // Get Node from gridPane
            Node n = gridPane.getChildren().get(row);
            // Cast to Label
            Label l = (Label) n;
            // Set default style
            l.setStyle("-fx-text-fill: " + StylingLeftMenu.ITEMS_IN_LEFT_MENU_TEXT_FILL);
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
            // TODO
            // Select updated row
            //selectRowInJournalsList(date);

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
}
