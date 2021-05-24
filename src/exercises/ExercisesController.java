package exercises;

import dbUtil.dbConnection;
import helpers.Navigation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import resources.StylingLayout;

import java.io.IOException;
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

    @FXML
    private AnchorPane rootPane;

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

    // table
    @FXML
    private TableView<ExerciseData> tableViewExercises;
    @FXML
    private TableColumn<ExerciseData, String> tableColumnName;
    @FXML
    private TableColumn<ExerciseData, String> tableColumnBodyPart;
    @FXML
    private TableColumn<ExerciseData, String> tableColumntype;
    @FXML
    private TableColumn<ExerciseData, String> tableColumnDescription;

    // sql queries
    private String sqlQueryType = "SELECT DISTINCT type FROM exercise";
    private String sqlQueryBodyPart = "SELECT DISTINCT bodyPart FROM exercise";
    private String sqlQueryAllExercises = "SELECT exerciseName, bodyPart, type, description FROM exercise";
    private String sqlQueryExercisesByType = "SELECT exerciseName, bodyPart, type, description FROM exercise WHERE type=?";
    private String sqlQueryExercisesByBodyPart = "SELECT exerciseName, bodyPart, type, description FROM exercise WHERE bodyPart=?";
    private String sqlQueryExercisesByTypeAndBodyPart = "SELECT exerciseName, bodyPart, type, description FROM exercise WHERE bodyPart=? AND type=?";

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

        fillTableView();
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

    private void fillTableView() {
        ObservableList<ExerciseData> data = FXCollections.observableArrayList();

        try {
            Connection conn = dbConnection.getConnection();
            assert conn != null;
            ResultSet rs = conn.createStatement().executeQuery(sqlQueryAllExercises);

            // check if the resultSet, rs has anything in the table
            while (rs.next()) {
                data.add(new ExerciseData(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4)));
            }
            conn.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }

        fillTableWithExerciseData(data);
    }

    private void fillTableViewWithDropdownValue(String sqlQuery, String sqlParameterFirst) {
        ObservableList<ExerciseData> data = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Connection conn = dbConnection.getConnection();
            assert conn != null;
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, sqlParameterFirst);

            resultSet = preparedStatement.executeQuery();

            // check if the resultSet, rs has anything in the table
            while (resultSet.next()) {
                data.add(new ExerciseData(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4)));
            }
            conn.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }

        fillTableWithExerciseData(data);
    }

    private void fillTableViewTwoDropdowns(String sqlQuery, String sqlParameterFirst, String sqlParameterSecond) {
        ObservableList<ExerciseData> data = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Connection conn = dbConnection.getConnection();
            assert conn != null;
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, sqlParameterFirst);
            preparedStatement.setString(2, sqlParameterSecond);

            resultSet = preparedStatement.executeQuery();

            // check if the resultSet, rs has anything in the table
            while (resultSet.next()) {
                data.add(new ExerciseData(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4)));
            }
            conn.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }

        fillTableWithExerciseData(data);
    }

    private void fillTableWithExerciseData(ObservableList<ExerciseData> data) {
        this.tableColumnName.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("exerciseName"));
        this.tableColumnBodyPart.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("type"));
        this.tableColumntype.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("bodyPart"));
        this.tableColumnDescription.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("description"));

        this.tableViewExercises.setItems(null);
        this.tableViewExercises.setItems(data);
    }

    public void FilterExercises(javafx.event.ActionEvent event) {
        if (!comboBoxType.getSelectionModel().isEmpty() && !comboBoxBodyPart.getSelectionModel().isEmpty()) {
            String type = comboBoxType.getSelectionModel().getSelectedItem();
            String bodyPart = comboBoxBodyPart.getSelectionModel().getSelectedItem();
            fillTableViewTwoDropdowns(sqlQueryExercisesByTypeAndBodyPart, bodyPart, type);
        }
        else {
            if (!comboBoxType.getSelectionModel().isEmpty()) {
                String type = comboBoxType.getSelectionModel().getSelectedItem();
                fillTableViewWithDropdownValue(sqlQueryExercisesByType, type);
            }
            else if (!comboBoxBodyPart.getSelectionModel().isEmpty()) {
                String bodyPart = comboBoxBodyPart.getSelectionModel().getSelectedItem();
                fillTableViewWithDropdownValue(sqlQueryExercisesByBodyPart, bodyPart);
            }
        }
    }

    public void ShowAddExercise(javafx.event.ActionEvent event) {
        try {
            AnchorPane paneAddExercise = FXMLLoader.load(getClass().getResource("/exerciseAdd/exerciseAdd.fxml"));
            rootPane.getChildren().setAll(paneAddExercise);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ShowAllExercises(javafx.event.ActionEvent event) {
        fillTableView();

        // TODO: reset prompt text in comboboxes
        comboBoxType.getSelectionModel().clearSelection();
        comboBoxType.setPromptText("Typ av lalala");
        // comboBoxBodyPart.getSelectionModel().clearSelection();
        comboBoxBodyPart.setPromptText("Kroppsdel lalala");
    }

    public void GoToPatients(javafx.event.ActionEvent event) {
        this.navigation.navigateToPatients(btnPatients);
    }
}
