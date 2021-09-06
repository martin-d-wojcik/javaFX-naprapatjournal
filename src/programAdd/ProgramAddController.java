package programAdd;

import dbUtil.dbConnection;
import exercises.ExerciseData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jetbrains.annotations.NotNull;
import resources.StylingLayout;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProgramAddController implements Initializable {
    ObservableList<ExerciseData> exercisesList = FXCollections.observableArrayList();
    String typeSelected;
    String bodyPartSelected;

    @FXML
    private TextField textFieldNameOfProgram;
    @FXML
    private Button btnCreateProgram;
    @FXML
    private Label lblProgramNameWarning;
    @FXML
    private Label lblProgramHeader;
    @FXML
    private ComboBox<String> comboBoxExerciseType;
    @FXML
    private ComboBox<String> comboBoxExerciseBodyPart;
    @FXML
    private ComboBox<String> comboBoxNameOfExercise;
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

    // sql queires
    private String sqlQueryExerciseType = "SELECT DISTINCT type FROM exercise";
    private String sqlQueryExerciseBodyPart = "SELECT DISTINCT bodyPart FROM exercise";
    private String sqlQueryExerciseName = "SELECT exerciseName from exercise";
    private String sqlQueryExerciseByType = "SELECT exerciseName from exercise WHERE type=?";
    private String sqlQueryExerciseByBodypart = "SELECT * from exercise WHERE bodyPart=?";
    private String sqlQueryExerciseByTtypeAndBodyPart = "SELECT * from exercise WHERE bodyPart=? AND type=?";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnCreateProgram.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLayout.BACKGROUND_DARK_GREY
                + "; -fx-font-weight: bold");
        lblProgramNameWarning.setVisible(false);
        lblProgramHeader.setStyle("-fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");
        lblProgramHeader.setVisible(false);
        comboBoxExerciseType.setVisible(false);
        comboBoxExerciseBodyPart.setVisible(false);
        comboBoxNameOfExercise.setVisible(false);
        btnRestore.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLayout.BACKGROUND_DARK_GREY
                + "; -fx-font-weight: bold");
        btnRestore.setVisible(false);
        tableViewExercises.setVisible(false);

        // populate combo boxes
        setComboBoxesToDefault();
    }

    public void AddProgramData(javafx.event.ActionEvent event) {
        if (textFieldNameOfProgram.getText().isEmpty()) {
            lblProgramNameWarning.visibleProperty().setValue(true);
        } else {
            lblProgramNameWarning.setVisible(false);
            btnCreateProgram.setVisible(false);
            lblProgramHeader.setVisible(true);
            comboBoxExerciseType.setVisible(true);
            comboBoxExerciseBodyPart.setVisible(true);
            comboBoxNameOfExercise.setVisible(true);
            btnRestore.setVisible(true);
            tableViewExercises.setVisible(true);
            /* btnSaveProgram.setVisible(true);
            lblProgramNameWarning.visibleProperty().setValue(false);*/
        }
    }

    // combo box selections
    public void TypeOfExerciseSelected(javafx.event.ActionEvent event) {
        typeSelected = comboBoxExerciseType.getSelectionModel().getSelectedItem();

        comboBoxNameOfExercise.setItems(FXCollections.observableArrayList(getExerciseData(sqlQueryExerciseByType, typeSelected)));
    }

    public void BodyPartSelected(javafx.event.ActionEvent event) {
        if(typeSelected == null) {
            bodyPartSelected = comboBoxExerciseBodyPart.getSelectionModel().getSelectedItem();

            comboBoxNameOfExercise.setItems(FXCollections.observableArrayList(getExerciseData(sqlQueryExerciseByBodypart, bodyPartSelected)));
        }
        else {
            comboBoxNameOfExercise.setItems(FXCollections.observableArrayList(getExerciseData(sqlQueryExerciseByTtypeAndBodyPart, bodyPartSelected, typeSelected)));
        }
    }

    private void setComboBoxesToDefault() {
        comboBoxExerciseType.setItems(FXCollections.observableArrayList(getExerciseTypeData()));
        comboBoxExerciseType.getSelectionModel().clearSelection();
        comboBoxExerciseBodyPart.setItems(FXCollections.observableArrayList(getExerciseBodyPartData()));
        comboBoxExerciseBodyPart.getSelectionModel().clearSelection();
        comboBoxNameOfExercise.setItems(FXCollections.observableArrayList(getExerciseData()));
        comboBoxNameOfExercise.getSelectionModel().clearSelection();
    }

    public void RestoreExerciseComboBox(javafx.event.ActionEvent event) {
        // comboBoxNameOfExercise.setItems(FXCollections.observableArrayList(getExerciseData()));
        resetSelectionData();
        setComboBoxesToDefault();
        // comboBoxExerciseType.setStyle("fx-background-color: #ff5733");
        // comboBoxExerciseType.getEditor().promptTextProperty().unbind();
        comboBoxExerciseType.getEditor().setPromptText("lalal");
    }

    private void resetSelectionData() {
        typeSelected = null;
        bodyPartSelected = null;
    }

    // TODO: move to ProgramAddModel
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

    public List<String> getExerciseData(String sqlQuery, String queryFirst, String querySecond) {
        List<String> options = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection conn = null;

        try {
            conn = dbConnection.getConnection();
            assert conn != null;

            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, queryFirst);
            preparedStatement.setString(1, querySecond);

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

    public void ExerciseSelected(javafx.event.ActionEvent event) {
        String exerciseNameSelected = comboBoxNameOfExercise.getSelectionModel().getSelectedItem();
        String bodyPartSelected = comboBoxExerciseBodyPart.getSelectionModel().getSelectedItem();
        String typeSelected = comboBoxExerciseType.getSelectionModel().getSelectedItem();
        String description = "dummy description";

        // add selected items to exercise list
        exercisesList.add(new ExerciseData(exerciseNameSelected, typeSelected, bodyPartSelected, description));

        // fill columns in exercises table view
        this.tableColumnExerciseName.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("exerciseName"));

        if (bodyPartSelected != null) {this.tableColumnBodyPart.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("bodyPart"));}

        if (typeSelected != null) {this.tableColumnType.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("type"));}

        // this.tableColumnDescription.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("description"));

        this.tableViewExercises.setItems(null);
        this.tableViewExercises.setItems(exercisesList);
    }
}