package programAdd;

import dbUtil.dbConnection;
import exercises.ExerciseData;
import helpers.Navigation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import patients.PatientHolder;
import program.ProgramHolder;
import resources.StylingLayout;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProgramAddController implements Initializable {
    ObservableList<ExerciseData> exercisesList = FXCollections.observableArrayList();
    Navigation navigation = new Navigation();
    ProgramAddModel programAddModel = new ProgramAddModel();
    String typeSelected;
    String bodyPartSelected;
    String exerciseNameSelected;
    String description;
    int currentListIndex = 0;
    
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
    private TableColumn<?, ?> tableColumnDelete;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

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
        btnSave.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLayout.BACKGROUND_DARK_GREY
                + "; -fx-font-weight: bold");
        btnSave.setVisible(false);
        btnCancel.setStyle("-fx-background-color:  " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");
        btnCancel.setVisible(false);

        // populate combo boxes
        setComboBoxesToDefault();

        if (ProgramHolder.getAddExercises()) {
            textFieldNameOfProgram.setText(ProgramHolder.getProgramName());
            lblProgramNameWarning.setVisible(false);
            btnCreateProgram.setVisible(false);
            lblProgramHeader.setVisible(false);
            comboBoxExerciseType.setVisible(true);
            comboBoxExerciseBodyPart.setVisible(true);
            comboBoxNameOfExercise.setVisible(true);
            btnRestore.setVisible(true);
            tableViewExercises.setVisible(true);
            btnSave.setVisible(true);
            btnCancel.setVisible(true);

            ObservableList<ExerciseData> exercisesAlreadyAdded = FXCollections.observableArrayList();

            try {
                exercisesAlreadyAdded = this.programAddModel.getExercisesAlreadySaved(PatientHolder.getPersonNr(), ProgramHolder.getProgramName());

                // fill table view with exercises previously selected and saved
                this.tableColumnExerciseName.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("exerciseName"));
                this.tableViewExercises.setItems(null);
                this.tableViewExercises.setItems(exercisesAlreadyAdded);

                addDeleteButtonsToTable();

                exercisesList = exercisesAlreadyAdded;

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
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
            btnSave.setVisible(true);
            btnCancel.setVisible(true);
            // lblProgramNameWarning.visibleProperty().setValue(false);
        }
    }

    public void SaveProgram(javafx.event.ActionEvent event) throws SQLException {
        // get all selected exercises from the tableView
        TableColumn<ExerciseData, String> column = tableColumnExerciseName;

        StringBuilder listOfExercises = new StringBuilder();
        for (ExerciseData item : tableViewExercises.getItems()) {
            listOfExercises.append(tableColumnExerciseName.getCellObservableValue(item).getValue());
            listOfExercises.append("; ");
        }

        if (ProgramHolder.getAddExercises()) {
            // remove old program before saving changes
        	this.programAddModel.deleteProgramFromDb(PatientHolder.getPersonNr(), textFieldNameOfProgram.getText());
        }

        int rowsInserted = this.programAddModel.addNewProgramToDb(PatientHolder.getPersonNr(),
                listOfExercises.toString(), textFieldNameOfProgram.getText());

        if (rowsInserted==1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Programmet sparat.");
            alert.setHeaderText("Det gick ju bra.");
            alert.show();

            navigation.navigateToPrograms(btnSave);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Programmet skapades inte");
            alert.setHeaderText("Något gick snett!");
            alert.show();
        }
    }

    public void CancelAddProgram(javafx.event.ActionEvent event) {
        navigation.navigateToJournals(btnCancel);
    }

	// combo box selections
	public void TypeOfExerciseSelected(javafx.event.ActionEvent event) {
		typeSelected = comboBoxExerciseType.getSelectionModel().getSelectedItem();
		if (bodyPartSelected == null) {
			comboBoxNameOfExercise.setItems(FXCollections.observableArrayList(
			        this.programAddModel.getExerciseDataOneSelection("exerciseType", typeSelected)));
		} else {
            bodyPartSelected = comboBoxExerciseBodyPart.getSelectionModel().getSelectedItem();
			comboBoxNameOfExercise.setItems(FXCollections.observableArrayList(
			        this.programAddModel.getExerciseDataTwoSelections(bodyPartSelected, typeSelected)));
		}
	}

	public void BodyPartSelected(javafx.event.ActionEvent event) {
		bodyPartSelected = comboBoxExerciseBodyPart.getSelectionModel().getSelectedItem();
		if (typeSelected == null) {
            comboBoxNameOfExercise.setItems(FXCollections.observableArrayList(
                    this.programAddModel.getExerciseDataOneSelection("exerciseBodyPart", bodyPartSelected)));
		} else {
            typeSelected = comboBoxExerciseType.getSelectionModel().getSelectedItem();
			comboBoxNameOfExercise.setItems(FXCollections.observableArrayList(
                    this.programAddModel.getExerciseDataTwoSelections(bodyPartSelected, typeSelected)));
		}
	}

	public void ExerciseSelected(javafx.event.ActionEvent event) {
		exerciseNameSelected = comboBoxNameOfExercise.getSelectionModel().getSelectedItem();

		if (exerciseNameSelected != null) {

			description = this.programAddModel.getExerciseDescription(exerciseNameSelected);

			// add selected items to exercise list
			exercisesList.add(new ExerciseData(exerciseNameSelected, typeSelected, bodyPartSelected, description)); //, exerciseDelete));

			// fill columns names in exercises table view
			this.tableColumnExerciseName.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("exerciseName"));
			this.tableColumnBodyPart.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("bodyPart"));
			this.tableColumnType.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("type"));
			this.tableColumnDescription.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("description"));

			addDeleteButtonsToTable();
			
			this.tableViewExercises.setItems(null);
			this.tableViewExercises.setItems(exercisesList);
		}
	}

	private void setComboBoxesToDefault() {
		comboBoxExerciseType.setItems(FXCollections.observableArrayList(this.programAddModel.getExerciseTypeData()));
		comboBoxExerciseType.getSelectionModel().clearSelection();
        comboBoxExerciseBodyPart.setItems(FXCollections.observableArrayList(this.programAddModel.getExerciseBodyPartData()));
		// comboBoxExerciseBodyPart.setItems(FXCollections.observableArrayList(getExerciseBodyPartData()));
		comboBoxExerciseBodyPart.getSelectionModel().clearSelection();

		// Prevent to fire onAction event when changing comboBoxNameOfExercise items
		EventHandler<ActionEvent> handler = comboBoxNameOfExercise.getOnAction(); // save onAction handler
		comboBoxNameOfExercise.setOnAction(null); // disable onAction
        comboBoxNameOfExercise.setItems(FXCollections.observableArrayList(this.programAddModel.getExerciseData()));
		comboBoxNameOfExercise.getSelectionModel().clearSelection();
		comboBoxNameOfExercise.setOnAction(handler); // enable onAction again
	}

    public void RestoreExerciseComboBox(javafx.event.ActionEvent event) {
        resetSelectionData();
        setComboBoxesToDefault();
        comboBoxExerciseType.getEditor().setPromptText("Typ av övning");
    }

    private void resetSelectionData() {
        typeSelected = null;
        bodyPartSelected = null;
        exerciseNameSelected = null;
        description = null;
    }

	private void addDeleteButtonsToTable() {
		if(tableViewExercises.getColumns().size() == 5){
			// remove last column, than restore it adding new row
			tableViewExercises.getColumns().remove(4);
		}
		
        TableColumn<ExerciseData, Void> colBtn = new TableColumn("Ta bort");

        Callback<TableColumn<ExerciseData, Void>, TableCell<ExerciseData, Void>> cellFactory = new Callback<TableColumn<ExerciseData, Void>, TableCell<ExerciseData, Void>>() {
            @Override
            public TableCell<ExerciseData, Void> call(final TableColumn<ExerciseData, Void> param) {
                final TableCell<ExerciseData, Void> cell = new TableCell<ExerciseData, Void>() {

                    private final Button btn = new Button("Radera");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	ExerciseData data = getTableView().getItems().get(getIndex());
                            tableViewExercises.getItems().remove(getIndex());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tableViewExercises.getColumns().add(colBtn);
    }
}