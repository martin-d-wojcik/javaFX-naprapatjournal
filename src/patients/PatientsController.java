package patients;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import resources.StylingLeftMenu;
import resources.StylingMainViewHeader;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PatientsController implements Initializable {
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
    private Button btnJournaler;
    @FXML
    private Button btnProgram;
    @FXML
    private Button btnStart;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // styling left menu
        anchorPaneLeftmenu.setStyle("-fx-background-color: " + StylingLeftMenu.BACKGROUND_DARK_GREY);
        lblUserLoggedInHeader.setStyle("-fx-text-fill: " + StylingLeftMenu.LOGGED_IN_USER_HEADER_TEXT_FILL);

        // styling left menu - buttons
        btnPatients.setStyle("-fx-background-color: " + StylingLeftMenu.BACKGROUND_DARK_GREY
                + "; -fx-text-fill: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL);
        btnJournaler.setStyle("-fx-background-color: " + StylingLeftMenu.BACKGROUND_DARK_GREY
                + "; -fx-text-fill: " + StylingLeftMenu.ITEMS_IN_LEFT_MENU_TEXT_FILL);
        btnProgram.setStyle("-fx-background-color: " + StylingLeftMenu.BACKGROUND_DARK_GREY
                + "; -fx-text-fill: " + StylingLeftMenu.ITEMS_IN_LEFT_MENU_TEXT_FILL);
        btnStart.setStyle("-fx-background-color: " + StylingLeftMenu.BACKGROUND_DARK_GREY +
                "; -fx-text-fill: " + StylingLeftMenu.ITEMS_IN_LEFT_MENU_TEXT_FILL);
        btnPatients.setAlignment(Pos.BASELINE_LEFT);
        btnProgram.setAlignment(Pos.BASELINE_LEFT);
        btnJournaler.setAlignment(Pos.BASELINE_LEFT);
        btnStart.setAlignment(Pos.BASELINE_LEFT);

        // styling table view
        // tableColumnPersonNr.setStyle("-fx-background-color: #366F2B");

        // styling search section
        btnSearchPatient.setStyle("-fx-background-color:  " + StylingMainViewHeader.BUTTON_SEARCH_BACKGROUND_COLOR
                + "; -fx-text-fill: " + StylingMainViewHeader.BUTTON_SEARCH_TEXT_FILL
                + "; -fx-font-weight: bold");
        btnAllPatients.setStyle("-fx-background-color: " + StylingMainViewHeader.BUTTON_SEARCH_BACKGROUND_COLOR
                + "; -fx-text-fill: " + StylingMainViewHeader.BUTTON_SEARCH_TEXT_FILL
                + "; -fx-font-weight: bold");

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

        // get the StringProperties from the PatientData class
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

    public void SearchPatient(javafx.event.ActionEvent event) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // search for personNr OR firstName/lastName
        if (txtFieldPersonNr.getText().isEmpty()
                && txtFieldFirstName.getText().isEmpty()
                && txtFieldLastName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Ange personn nummer, förnamn eller efternamn");
            alert.setHeaderText("Ett fel har inträffat !");
            alert.show();
        }
        else if (txtFieldPersonNr.getText().isEmpty()) {
            lblTemp.setText("namn");

            // TODO: query the DB with firstName and/or lastName
        }
        else {
            try {
                String personNr = txtFieldPersonNr.getText();

                Connection conn = dbConnection.getConnection();

                assert conn != null;
                preparedStatement = conn.prepareStatement(sqlGetCustomerByPersonNr);
                preparedStatement.setString(1, personNr);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    displayQueryResultInTable(resultSet);

                    txtFieldPersonNr.clear();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Inga patienter hittades med det person numret");
                    alert.setHeaderText("Ett fel har inträffat !");
                    alert.show();
                }

                conn.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private void displayQueryResultInTable(ResultSet resultSet) throws SQLException {
        ObservableList<PatientData> data = FXCollections.observableArrayList();

        if (resultSet.getRow() == 0) {
            lblTemp.setText("Inga användare hittades");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Inga användare hittades");
            alert.setHeaderText("Ett fel har inträffat");
            alert.show();

        } else {
            try {
                data.add(new PatientData(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8)));
            } catch (SQLException e) {
                System.err.println("Error: " + e);
            }

            fillTableWithPatientData(data);
        }
    }
}
