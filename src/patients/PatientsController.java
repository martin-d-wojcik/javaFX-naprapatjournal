package patients;

import dbUtil.dbConnection;
import helpers.Navigation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import resources.StylingLayout;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PatientsController implements Initializable {
    Navigation navigation = new Navigation();

    @FXML
    private AnchorPane rootPane;

    // left menu
    @FXML
    private Label lblTemp;
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

    // search section
    @FXML
    private TextField txtFieldPersonNr;
    @FXML
    private TextField txtFieldFirstName;
    @FXML
    private TextField txtFieldLastName;
    @FXML
    private Button btnAllPatients;
    @FXML
    private Button btnSearchPatient;
    @FXML
    private Button btnAddPatient;

    // table
    @FXML
    private TableView<PatientData> tableViewPatients;
    @FXML
    private TableColumn<PatientData, String> tableColumnPersonNr;
    @FXML
    private TableColumn<PatientData, String> tableColumnFirstName;
    @FXML
    private TableColumn<PatientData, String> tableColumnLastName;
    @FXML
    private TableColumn<PatientData, String> tableColumnStreetAdress;
    @FXML
    private TableColumn<PatientData, String> tableColumnCity;
    @FXML
    private TableColumn<PatientData, String> tableColumnPostalCode;
    @FXML
    private TableColumn<PatientData, String> tableColumnEmail;
    @FXML
    private TableColumn<PatientData, String> tableColumnPhoneNr;

    // SQL queries
    private String sqlGetAllCustomersBasic = "SELECT personNr, firstName, lastName, streetAdress, city, postalCode, email, phoneNumber FROM customer";
    private String sqlGetCustomerByPersonNr = "SELECT personNr, firstName, lastName, streetAdress, city, postalCode, email, phoneNumber FROM customer WHERE personNr=?";
    private String sqlGetCustomerByPersonNr_Like = "SELECT personNr, firstName, lastName, streetAdress, city, postalCode, email, phoneNumber "
            + "FROM customer WHERE personNr LIKE ?";
    private String sqlGetCustomerByName_Like = "SELECT personNr, firstName, lastName, streetAdress, city, postalCode, email, phoneNumber "
            + "FROM customer WHERE firstName LIKE ? AND lastName LIKE ?";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StylingLayout.stylingLeftMenu("patients", lblUserLoggedInHeader, anchorPaneLeftmenu, btnPatients,
                btnJournals, btnProgram, btnStart, btnExercises);

        // styling search section
        btnSearchPatient.setStyle("-fx-background-color:  " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");
        btnAllPatients.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");
        btnAddPatient.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLayout.BACKGROUND_DARK_GREY
                + "; -fx-font-weight: bold");
        lblTemp.setStyle("-fx-text-fill: red; -fx-font-weight: bold");

        setHeader(login.UserHolder.getLoggedInUser());
        fillTableView();
    }

    public void setHeader(String userName) {
        lblUserLoggedInHeader.setText("Inloggad: " + userName);
    }

    private void fillTableView() {
        ObservableList<PatientData> data = FXCollections.observableArrayList();

        try {
            Connection conn = dbConnection.getConnection();
            assert conn != null;
            ResultSet rs = conn.createStatement().executeQuery(sqlGetAllCustomersBasic);

            // check if the resultSet, rs has anything in the table
            while (rs.next()) {
                data.add(new PatientData(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8)));
            }
            conn.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }

        fillTableWithPatientData(data);
    }

    private void fillTableWithPatientData(ObservableList<PatientData> data) {
        this.tableColumnPersonNr.setCellValueFactory(new PropertyValueFactory<PatientData, String>("patientPersonNr"));
        this.tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<PatientData, String>("patientFirstName"));
        this.tableColumnLastName.setCellValueFactory(new PropertyValueFactory<PatientData, String>("patientLastName"));
        this.tableColumnStreetAdress.setCellValueFactory(new PropertyValueFactory<PatientData, String>("patientStreetAdress"));
        this.tableColumnCity.setCellValueFactory(new PropertyValueFactory<PatientData, String>("patientCity"));
        this.tableColumnPostalCode.setCellValueFactory(new PropertyValueFactory<PatientData, String>("patientPostalCode"));
        this.tableColumnEmail.setCellValueFactory(new PropertyValueFactory<PatientData, String>("patientEmail"));
        this.tableColumnPhoneNr.setCellValueFactory(new PropertyValueFactory<PatientData, String>("patientPhoneNr"));

        this.tableViewPatients.setItems(null);
        this.tableViewPatients.setItems(data);
    }

    public void ListAllPatients(javafx.event.ActionEvent event) {
        fillTableView();
    }

    public void ShowAddPatient(javafx.event.ActionEvent event) {
        try {
            AnchorPane paneAddPatient = FXMLLoader.load(getClass().getResource("/patientAdd/patientAdd.fxml"));
            rootPane.getChildren().setAll(paneAddPatient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SearchPatient(javafx.event.ActionEvent event) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection conn = null;

        try {
            conn = dbConnection.getConnection();
            assert conn != null;

            // search for personNr OR firstName/lastName
            if (txtFieldPersonNr.getText().trim().isEmpty() && txtFieldFirstName.getText().trim().isEmpty()
                    && txtFieldLastName.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Ange personn nummer, förnamn eller efternamn");
                alert.setHeaderText("Ett fel har inträffat !");
                alert.show();

            } else if (txtFieldPersonNr.getText().trim().isEmpty()) {
                String firstName = txtFieldFirstName.getText().trim();
                String lastName = txtFieldLastName.getText().trim();

                preparedStatement = conn.prepareStatement(sqlGetCustomerByName_Like);
                preparedStatement.setString(1, firstName + "%");
                preparedStatement.setString(2, lastName + "%");

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    displayQueryResultInTable_SomeRows(resultSet);

                    /// ? txtFieldFirstName.clear();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Inga patienter hittades med det namnet");
                    alert.setHeaderText("Ett fel har inträffat !");
                    alert.show();
                }

            } else {
                String personNr = txtFieldPersonNr.getText().trim();
                preparedStatement = conn.prepareStatement(sqlGetCustomerByPersonNr_Like);
                preparedStatement.setString(1, personNr + "%");
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    displayQueryResultInTable_SomeRows(resultSet);

                    txtFieldPersonNr.clear();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Inga patienter hittades med det person numret");
                    alert.setHeaderText("Ett fel har inträffat !");
                    alert.show();
                }

            }
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, throwables.toString());
            alert.setHeaderText("SQLException har inträffat !");
            alert.show();
        }

    }
    @FXML
    public void SelectPatientFromTable(MouseEvent e) {
        // TODO: parse personNr, firstName, lastName to JournalController
        try {
            String patientPersonNr = tableViewPatients.getSelectionModel().getSelectedItem().getPatientPersonNr();
            String patientFirstName = tableViewPatients.getSelectionModel().getSelectedItem().getPatientFirstName();
            String patientLastName = tableViewPatients.getSelectionModel().getSelectedItem().getPatientLastName();

            PatientHolder.setPersonNr(patientPersonNr);
            PatientHolder.setFirstName(patientFirstName);
            PatientHolder.setLastName(patientLastName);

            Parent root = FXMLLoader.load(getClass().getResource("/journal/journal.fxml"));
            Stage window = (Stage) tableViewPatients.getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void displayQueryResultInTable_SomeRows(ResultSet resultSet) throws SQLException {
        ObservableList<PatientData> data = FXCollections.observableArrayList();

        if (resultSet.getRow() == 0) {
            lblTemp.setText("Inga användare hittades");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Inga användare hittades");
            alert.setHeaderText("Ett fel har inträffat");
            alert.show();

        } else {
            try {
                do{
                    data.add(new PatientData(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                            resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
                            resultSet.getString(7), resultSet.getString(8)));
                }
                while (resultSet.next());

            } catch (SQLException e) {
                System.err.println("Error: " + e);
            }

            fillTableWithPatientData(data);
        }
    }

    public void GoToExercises(javafx.event.ActionEvent event) {
        this.navigation.navigateToExercises(btnExercises);
    }
}
