package exercises;

import dbUtil.dbConnection;
import helpers.Navigation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import resources.StylingLayout;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ExercisesController implements Initializable {
    Navigation navigation = new Navigation();

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

    // search section
    @FXML
    private ComboBox<String> comboBoxType;
    @FXML
    private ComboBox<String> comboBoxBodyPart;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnAllExercises;
    @FXML
    private Button btnNewExercise;

    // sql queries
    private String sqlQueryType = "SELECT DISTINCT type FROM exercise";
    private String sqlQueryBodyPart = "SELECT DISTINCT bodyPart FROM exercise";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StylingLayout.stylingLeftMenu("exercises", lblUserLoggedInHeader, anchorPaneLeftmenu, btnPatients,
                btnJournals, btnProgram, btnStart, btnExercises);

        // logged in user
        lblUserLoggedInHeader.setText("Inloggad: " + login.UserHolder.getLoggedInUser());

        // styling search section
        btnSearch.setStyle("-fx-background-color:  " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");
        btnAllExercises.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");
        btnNewExercise.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLayout.BACKGROUND_DARK_GREY
                + "; -fx-font-weight: bold");

        comboBoxType.setItems(FXCollections.observableArrayList(getTypeData()));
        comboBoxBodyPart.setItems(FXCollections.observableArrayList(getBodyPartData()));
    }

    private List<String> getBodyPartData() {
        List<String> options = new ArrayList<>();

        try {
            Connection conn = dbConnection.getConnection();
            assert conn != null;
            ResultSet rs = conn.createStatement().executeQuery(sqlQueryBodyPart);

            while (rs.next()) {
                options.add(rs.getString("bodyPart"));
            }

            rs.close();

            return options;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> getTypeData() {
        List<String> options = new ArrayList<>();

        try {
            Connection conn = dbConnection.getConnection();
            assert conn != null;
            ResultSet rs = conn.createStatement().executeQuery(sqlQueryType);

            while (rs.next()) {
                options.add(rs.getString("type"));
            }

            rs.close();

            return options;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void GoToPatients(javafx.event.ActionEvent event) {
        this.navigation.navigateToPatients(btnPatients);
    }
}
