package exercises;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import resources.StylingLayout;

import java.net.URL;
import java.util.ResourceBundle;

public class ExercisesController implements Initializable {
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
    public AnchorPane anchorPaneListOfPrograms;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // styling left menu
        anchorPaneLeftmenu.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY);
        lblUserLoggedInHeader.setStyle("-fx-text-fill: " + StylingLayout.LOGGED_IN_USER_HEADER_TEXT_FILL);

        // styling left menu - buttons
        btnPatients.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY
                + "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
        btnJournals.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
        btnProgram.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY
                + "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
        btnStart.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY +
                "; -fx-text-fill: " + StylingLayout.ITEMS_IN_LEFT_MENU_TEXT_FILL);
        btnExercises.setStyle("-fx-background-color: " + StylingLayout.BACKGROUND_DARK_GREY +
                "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL);
        btnPatients.setAlignment(Pos.BASELINE_LEFT);
        btnProgram.setAlignment(Pos.BASELINE_LEFT);
        btnJournals.setAlignment(Pos.BASELINE_LEFT);
        btnStart.setAlignment(Pos.BASELINE_LEFT);
        btnExercises.setAlignment(Pos.BASELINE_LEFT);
        btnPatients.setPadding(new Insets(0, 0, 0, 20));
        btnProgram.setPadding(new Insets(0, 0, 0, 20));
        btnJournals.setPadding(new Insets(0, 0, 0, 20));
        btnStart.setPadding(new Insets(0, 0, 0, 20));
        btnExercises.setPadding(new Insets(0, 0, 0, 20));

        // styling list of journals
        anchorPaneListOfPrograms.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND);
    }
}
