package programAdd;

import dbUtil.dbConnection;
import exercises.ExerciseData;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
//import org.jetbrains.annotations.NotNull;
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
//    private TableColumn<ExerciseData, String> tableColumnDelete;
    private TableColumn<?, ?> tableColumnDelete;
    
//    private final int TABLE_DELETE_COLUMN_NR = 5;
  //  private final String exerciseDelete = "[ Radera ]";

    
    // sql queiries
    private String sqlQueryExerciseType = "SELECT DISTINCT type FROM exercise";
    private String sqlQueryExerciseBodyPart = "SELECT DISTINCT bodyPart FROM exercise";
    private String sqlQueryExerciseName = "SELECT exerciseName from exercise";
    private String sqlQueryExerciseByType = "SELECT exerciseName from exercise WHERE type=?";
    private String sqlQueryExerciseByBodypart = "SELECT * from exercise WHERE bodyPart=?";
    private String sqlQueryExerciseByTtypeAndBodyPart = "SELECT * from exercise WHERE bodyPart=? AND type=?";
    private String sqlQueryDescription = "SELECT description FROM exercise WHERE exerciseName=? ";


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
		// 2021-09-11
		typeSelected = comboBoxExerciseType.getSelectionModel().getSelectedItem();
		if (bodyPartSelected == null) {
			comboBoxNameOfExercise
					.setItems(FXCollections.observableArrayList(getExerciseData(sqlQueryExerciseByType, typeSelected)));
		} else {
			comboBoxNameOfExercise.setItems(FXCollections.observableArrayList(
					getExerciseData(sqlQueryExerciseByTtypeAndBodyPart, bodyPartSelected, typeSelected)));
		}
	}

	public void BodyPartSelected(javafx.event.ActionEvent event) {
		// 2021-09-11
		bodyPartSelected = comboBoxExerciseBodyPart.getSelectionModel().getSelectedItem();
		if (typeSelected == null) {
			comboBoxNameOfExercise.setItems(
					FXCollections.observableArrayList(getExerciseData(sqlQueryExerciseByBodypart, bodyPartSelected)));
		} else {
			comboBoxNameOfExercise.setItems(FXCollections.observableArrayList(
					getExerciseData(sqlQueryExerciseByTtypeAndBodyPart, bodyPartSelected, typeSelected)));
		}
	}

	public void ExerciseSelected(javafx.event.ActionEvent event) {
		exerciseNameSelected = comboBoxNameOfExercise.getSelectionModel().getSelectedItem();

		if (exerciseNameSelected != null) {

			description = getExerciseDescription(exerciseNameSelected);

			// add selected items to exercise list
			exercisesList.add(new ExerciseData(exerciseNameSelected, typeSelected, bodyPartSelected, description)); //, exerciseDelete));

			// fill columns names in exercises table view
			this.tableColumnExerciseName.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("exerciseName"));
			this.tableColumnBodyPart.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("bodyPart"));
			this.tableColumnType.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("type"));
			this.tableColumnDescription.setCellValueFactory(new PropertyValueFactory<ExerciseData, String>("description"));

// 2021-09-17
			addDeleteButtonsToTable();
			
			this.tableViewExercises.setItems(null);
			this.tableViewExercises.setItems(exercisesList);
		}
	}

	private void setComboBoxesToDefault() {

		comboBoxExerciseType.setItems(FXCollections.observableArrayList(getExerciseTypeData()));
		comboBoxExerciseType.getSelectionModel().clearSelection();
		comboBoxExerciseBodyPart.setItems(FXCollections.observableArrayList(getExerciseBodyPartData()));
		comboBoxExerciseBodyPart.getSelectionModel().clearSelection();
		/*
		 * Prevent to fire onAction event when changing comboBoxNameOfExercise
		 * items
		 */
		EventHandler<ActionEvent> handler = comboBoxNameOfExercise.getOnAction(); // save onAction handler
		comboBoxNameOfExercise.setOnAction(null); // disable onAction
		comboBoxNameOfExercise.setItems(FXCollections.observableArrayList(getExerciseData()));
		comboBoxNameOfExercise.getSelectionModel().clearSelection();
		comboBoxNameOfExercise.setOnAction(handler); // enable onAction again
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
        exerciseNameSelected = null;
        description = null;
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
			preparedStatement.setString(2, querySecond);

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

	public String getExerciseDescription(String exercName) {
		String descr = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			Connection conn = dbConnection.getConnection();
			assert conn != null;

			preparedStatement = conn.prepareStatement(sqlQueryDescription);
			preparedStatement.setString(1, exercName);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				descr = resultSet.getString("description");
			}

			resultSet.close();

			return descr;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/*
	 *  2021-09-17
	 */
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