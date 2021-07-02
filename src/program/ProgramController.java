package program;

import dbUtil.dbConnection;
import exercises.ExerciseData;
import helpers.Navigation;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import patients.PatientData;
import patients.PatientHolder;
import resources.StylingLayout;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProgramController implements Initializable {
    Navigation navigation = new Navigation();
    ObservableList<ExerciseData> exercisesList = FXCollections.observableArrayList();

    @FXML
    private Label lblTemp;

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

    // program main view
    @FXML
    private Button btnCreateProgram;
    @FXML
    private Label lblProgramHeader;
    @FXML
    private TextField textFieldNameOfProgram;
    @FXML
    private Label lblProgramNameWarning;
    @FXML
    private ComboBox<String> comboBoxExerciseType;
    @FXML
    private ComboBox<String> comboBoxExerciseBodyPart;
    @FXML
    private ComboBox<String> comboBoxNameOfExercise;
    @FXML
    private Button btnSaveProgram;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnRestore;

    @FXML
    private TableView<ExerciseData> tableViewExercises;
    @FXML
    private TableColumn<ExerciseData, String> tableColumnExerciseName;
    @FXML
    private TableColumn<ExerciseData, String> tableColumnBodyPart;
    @FXML
    private TableColumn<ExerciseData, String> tableColumnType;
    @FXML
    private TableColumn<ExerciseData, String> tableColumnDescription;
    @FXML
    private TableColumn<ExerciseData, String> tableColumnEdit;
    @FXML
    private TableColumn<ExerciseData, String> tableColumnDelete;

    // header
    @FXML
    private Label lblPatientName;

    // sql queires
    private String sqlQueryExerciseType = "SELECT DISTINCT type FROM exercise";
    private String sqlQueryExerciseBodyPart = "SELECT DISTINCT bodyPart FROM exercise";
    private String sqlQueryExerciseName = "SELECT exerciseName from exercise";
    private String sqlQueryExerciseByType = "SELECT exerciseName from exercise WHERE type=?";

    // table data
    /* private final int TABLE_EDIT_COLUMN_NR = 4;
    private final String columnEditText = "Redigera";
    private final int TABLE_DELETE_COLUMN_NR = 5;
    private final String columnDeleteText = "Radera"; */

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

        // styling main view
        lblProgramNameWarning.setVisible(false);
        btnCreateProgram.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLayout.BACKGROUND_DARK_GREY
                + "; -fx-font-weight: bold");
        lblProgramHeader.setStyle("-fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");
        lblProgramHeader.setVisible(false);
        comboBoxExerciseBodyPart.setVisible(false);
        comboBoxExerciseType.setVisible(false);
        comboBoxNameOfExercise.setVisible(false);
        btnRestore.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLayout.BACKGROUND_DARK_GREY
                + "; -fx-font-weight: bold");
        btnRestore.setVisible(false);
        tableViewExercises.setVisible(false);
        btnSaveProgram.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLayout.BACKGROUND_DARK_GREY
                + "; -fx-font-weight: bold");
        btnSaveProgram.setVisible(false);
        btnCancel.setStyle("-fx-background-color:  " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");

        // populate combo boxes
        comboBoxExerciseType.setItems(FXCollections.observableArrayList(getExerciseTypeData()));
        comboBoxExerciseBodyPart.setItems(FXCollections.observableArrayList(getExerciseBodyPartData()));
        comboBoxNameOfExercise.setItems(FXCollections.observableArrayList(getExerciseData()));
    }

    public void ExerciseSelected(javafx.event.ActionEvent event) {
        String exerciseNameSelected = comboBoxNameOfExercise.getSelectionModel().getSelectedItem();
        String bodyPartSelected = comboBoxExerciseBodyPart.getSelectionModel().getSelectedItem();
        String typeSelected = comboBoxExerciseType.getSelectionModel().getSelectedItem();
        String description = "dummy description";

        // add selected items to exercise list
        exercisesList.add(new ExerciseData(exerciseNameSelected, typeSelected, bodyPartSelected, description));

        // fill columns in exercises table view
        this.tableColumnExerciseName.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("exerciseName"));
        this.tableColumnBodyPart.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("bodyPart"));
        this.tableColumnType.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("type"));
        this.tableColumnDescription.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("description"));

        this.tableViewExercises.setItems(null);
        this.tableViewExercises.setItems(exercisesList);

        lblTemp.setText(exerciseNameSelected);
    }

    public void TypeOfExerciseSelected(javafx.event.ActionEvent event) {
        String typeSelected = comboBoxExerciseType.getSelectionModel().getSelectedItem();

        comboBoxNameOfExercise.setItems(FXCollections.observableArrayList(getExerciseData(sqlQueryExerciseByType, typeSelected)));
    }

    public List<String> getExerciseData() {
        List<String> options = new ArrayList<>();

        try {
            Connection conn = dbConnection.getConnection();
            assert conn != null;
            ResultSet rs = conn.createStatement().executeQuery(sqlQueryExerciseName);

            while (rs.next()) {
                options.add(rs.getString("exerciseName"));
            }

            rs.close();

            return options;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> getExerciseData(String sqlQuery, String queryParameter) {
        List<String> options = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection conn = null;

        try {
            conn = dbConnection.getConnection();
            assert conn != null;

            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, queryParameter);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                options.add(resultSet.getString("exerciseName"));
            }

            resultSet.close();

            return options;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> getExerciseBodyPartData() {
        List<String> options = new ArrayList<>();

        try {
            Connection conn = dbConnection.getConnection();
            assert conn != null;
            ResultSet rs = conn.createStatement().executeQuery(sqlQueryExerciseBodyPart);

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

    public List<String> getExerciseTypeData() {
        List<String> options = new ArrayList<>();

        try {
            Connection conn = dbConnection.getConnection();
            assert conn != null;
            ResultSet rs = conn.createStatement().executeQuery(sqlQueryExerciseType);

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

    public void RestoreExerciseComboBox(javafx.event.ActionEvent event) {
        comboBoxNameOfExercise.setItems(FXCollections.observableArrayList(getExerciseData()));
    }

    @FXML
    public void SelectExerciseFromTable(MouseEvent e) {
        lblTemp.setText("SelectExerciseFromTable clicked");

        /* try {

            String exeName = tableViewExercises.getSelectionModel().getSelectedItem().getExerciseName();
            String progID = "";
            String exeType = tableViewExercises.getSelectionModel().getSelectedItem().getType();
            String bodyPart = tableViewExercises.getSelectionModel().getSelectedItem().getBodyPart();
            String exeDescript = tableViewExercises.getSelectionModel().getSelectedItem().getDescription();

            ExerciseHolder.setExerciseName(exeName);
            ExerciseHolder.setProgramID(progID);
            ExerciseHolder.setType(exeType);
            ExerciseHolder.setBodyPart(bodyPart);
            ExerciseHolder.setDescription(exeDescript);

            int selectedColumn = tableViewExercises.getSelectionModel().getSelectedCells().get(0).getColumn();

            if (selectedColumn == TABLE_EDIT_COLUMN_NR){
                Alert al = new Alert(Alert.AlertType.INFORMATION, "EDIT PRESSED");
                al.show();

                AnchorPane paneEditExercise = FXMLLoader.load(getClass().getResource("/exerciseEdit/exerciseEdit.fxml"));
                //   rootPane.getChildren().setAll(paneEditExercise);
            }
            if (selectedColumn == TABLE_DELETE_COLUMN_NR){
                Alert al = new Alert(Alert.AlertType.INFORMATION, "DELETE PRESSED");
                al.show();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } */
    }

    public void AddProgramData(javafx.event.ActionEvent event) {
        if (textFieldNameOfProgram.getText().isEmpty()) {
            lblProgramNameWarning.visibleProperty().setValue(true);
        } else {
            btnCreateProgram.setVisible(false);
            lblProgramHeader.setVisible(true);
            comboBoxExerciseBodyPart.setVisible(true);
            comboBoxExerciseType.setVisible(true);
            comboBoxNameOfExercise.setVisible(true);
            btnRestore.setVisible(true);
            tableViewExercises.setVisible(true);
            btnSaveProgram.setVisible(true);
            lblProgramNameWarning.visibleProperty().setValue(false);
        }
    }

    public void SaveProgram(javafx.event.ActionEvent event) {
        this.tableColumnExerciseName.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("exerciseName"));
    }

    public void CancelAddProgram(javafx.event.ActionEvent event) {
        navigation.navigateToPatients(btnCancel);
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
