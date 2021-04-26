package journal;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import patients.PatientHolder;
import resources.StylingLeftMenu;

import java.net.URL;
import java.util.ResourceBundle;

public class JournalController implements Initializable {
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

    // header
    @FXML
    public Label lblPatientName;
    @FXML
    public Button btnNewJournal;
    @FXML
    public Button btnNewProgram;

    // list of journals

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

        // logged in user
        lblUserLoggedInHeader.setText("Inloggad: " + login.UserHolder.getLoggedInUser());

        // patient header
        lblPatientName.setText("Journaler för: " + PatientHolder.getFirstName() + " " + PatientHolder.getLastName());
        lblPatientName.setStyle("-fx-text-fill: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-font-weight: bold");
        btnNewJournal.setStyle("-fx-background-color: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLeftMenu.BACKGROUND_DARK_GREY
                + "; -fx-font-weight: bold");
        btnNewProgram.setStyle("-fx-background-color:  " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");
    }
}
