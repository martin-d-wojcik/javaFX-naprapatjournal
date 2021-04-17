package patientAdd;

import dbUtil.dbConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import resources.StylingLeftMenu;
import resources.StylingMainViewContent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PatientAddController implements Initializable {
    @FXML
    private Pane paneMainContent;
    @FXML
    private Label lblAddPatientHeader;

    // textFields
    @FXML
    private TextField textFieldPersonNr;
    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldStreetAdress;
    @FXML
    private TextField textFieldCity;
    @FXML
    private TextField textFieldPostalCode;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldPhoneNr;

    // warning labels
    @FXML
    private Label lblPersonNrWarning;
    @FXML
    private Label lblFirstNameWarning;
    @FXML
    private Label lblLastNameWarning;
    @FXML
    private Label lblStreetAdressWarning;
    @FXML
    private Label lblCityWarning;
    @FXML
    private Label lblPostalCodeWarning;
    @FXML
    private Label lblEmailWarning;
    @FXML
    private Label lblPhoneNrWarning;

    // buttons
    @FXML
    private Button btnAddNewPatient;
    @FXML
    private Button btnCancel;

    // SQL queries
    private String sqlAddPatient = "INSERT INTO customer(personNr, firstName, lastName, streetAdress, city, postalCode, email, phoneNumber) VALUES (?,?,?,?,?,?,?,?)";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // styling header
       lblAddPatientHeader.setStyle("-fx-text-fill: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");

        // styling textFields
        textFieldPersonNr.setStyle("-fx-border-color: " + StylingMainViewContent.TEXTFIELD_BORDER_COLOR);
        textFieldFirstName.setStyle("-fx-border-color: " + StylingMainViewContent.TEXTFIELD_BORDER_COLOR);
        textFieldLastName.setStyle("-fx-border-color: " + StylingMainViewContent.TEXTFIELD_BORDER_COLOR);
        textFieldStreetAdress.setStyle("-fx-border-color: " + StylingMainViewContent.TEXTFIELD_BORDER_COLOR);
        textFieldCity.setStyle("-fx-border-color: " + StylingMainViewContent.TEXTFIELD_BORDER_COLOR);
        textFieldPostalCode.setStyle("-fx-border-color: " + StylingMainViewContent.TEXTFIELD_BORDER_COLOR);
        textFieldEmail.setStyle("-fx-border-color: " + StylingMainViewContent.TEXTFIELD_BORDER_COLOR);
        textFieldPhoneNr.setStyle("-fx-border-color: " + StylingMainViewContent.TEXTFIELD_BORDER_COLOR);

        // styling warning labels
        // TODO

        // styling buttons
        btnAddNewPatient.setStyle("-fx-background-color: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLeftMenu.BACKGROUND_DARK_GREY
                + "; -fx-font-weight: bold");
        btnCancel.setStyle("-fx-background-color: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLeftMenu.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");

        hideWarningLabels();
    }

    public void AddPatientData(javafx.event.ActionEvent event) throws SQLException {
        Connection conn = dbConnection.getConnection();

        String personNr = textFieldPersonNr.getText();
        String firstName = textFieldFirstName.getText();
        String lastName = textFieldLastName.getText();
        String streetAdress = textFieldStreetAdress.getText();
        String city = textFieldCity.getText();
        String postalCode = textFieldPostalCode.getText();
        String email = textFieldEmail.getText();
        String phoneNr = textFieldPhoneNr.getText();

        if(personNr.trim().isEmpty() || firstName.trim().isEmpty() || lastName.trim().isEmpty() ||
                streetAdress.trim().isEmpty() || city.trim().isEmpty() || postalCode.trim().isEmpty() ||
                email.trim().isEmpty() || phoneNr.trim().isEmpty()) {
            // assert that no textfield is empty
            if(personNr.trim().isEmpty()) {
                hideWarningLabels();
                lblPersonNrWarning.visibleProperty().setValue(true);
            }
            if(firstName.trim().isEmpty()) {
                hideWarningLabels();
                lblFirstNameWarning.visibleProperty().setValue(true);
            }
            if(lastName.trim().isEmpty()) {
                hideWarningLabels();
                lblLastNameWarning.visibleProperty().setValue(true);
            }
            if(streetAdress.trim().isEmpty()) {
                hideWarningLabels();
                lblStreetAdressWarning.visibleProperty().setValue(true);
            }
            if(city.trim().isEmpty()) {
                hideWarningLabels();
                lblCityWarning.visibleProperty().setValue(true);
            }
            if(postalCode.trim().isEmpty()) {
                hideWarningLabels();
                lblPostalCodeWarning.visibleProperty().setValue(true);
            }
            if(email.trim().isEmpty()) {
                hideWarningLabels();
                lblEmailWarning.visibleProperty().setValue(true);
            }
            if(phoneNr.trim().isEmpty()) {
                hideWarningLabels();
                lblPhoneNrWarning.visibleProperty().setValue(true);
            }
        }
        else {
            // Prepare query
            assert conn != null;
            PreparedStatement prepStmt = conn.prepareStatement(sqlAddPatient);
            prepStmt.setString(1, personNr);
            prepStmt.setString(2, firstName);
            prepStmt.setString(3, lastName);
            prepStmt.setString(4, streetAdress);
            prepStmt.setString(5, city);
            prepStmt.setString(6, postalCode);
            prepStmt.setString(7, email);
            prepStmt.setString(8, phoneNr);

            prepStmt.executeUpdate();//
            prepStmt.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ny patient tillagd.");
            alert.setHeaderText("Insättning lyckad");
            alert.show();
        }
    }

    private void hideWarningLabels() {
        lblPersonNrWarning.visibleProperty().setValue(false);
        lblFirstNameWarning.visibleProperty().setValue(false);
        lblLastNameWarning.visibleProperty().setValue(false);
        lblStreetAdressWarning.visibleProperty().setValue(false);
        lblCityWarning.visibleProperty().setValue(false);
        lblPostalCodeWarning.visibleProperty().setValue(false);
        lblEmailWarning.visibleProperty().setValue(false);
        lblPhoneNrWarning.visibleProperty().setValue(false);
    }
}
