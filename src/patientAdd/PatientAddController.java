package patientAdd;

import dbUtil.dbConnection;
import helpers.Navigation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import resources.StylingLayout;
import resources.StylingMainViewContent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PatientAddController implements Initializable {
    Navigation navigation = new Navigation();
    PatientAddModel patientAddModel = new PatientAddModel();

    @FXML
    private AnchorPane paneAddPatients;
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
    @FXML
    private TextField textFieldRegistrationDate;
    @FXML
    private TextField textFieldOccupation;
    @FXML
    private TextField textFieldIllnessList;
    @FXML
    private TextField textFieldOperationList;
    @FXML
    private TextField textFieldMedicinUsed;
    @FXML
    private TextField textFieldAttention;
    @FXML
    private TextField textFieldDiagnosis;
    @FXML
    private TextField textFieldNextVisit;

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
    @FXML
    private Button btnUpdatePatient;
    @FXML
    private Button btnRefillPatient;
    @FXML
    private Button btnArchive;
    @FXML
    private Button btnCancelUpdate;

    private String xsqlAddPatient = "INSERT INTO customer(personNr, firstName, lastName, streetAdress, "
            + "city, postalCode, email, phoneNumber) "
            + " VALUES (?,?,?,?,?,?,?,?)";

    private String sqlAddPatient = "INSERT INTO customer(personNr, firstName, lastName, streetAdress, "
            + " city, postalCode, email, phoneNumber,"
            + " registrationDate, occupation, illnessList, operationList, "
            + " medicinUsed, attention, diagnosis, nextVisit) "
            + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private String sqlGetThePatient = "SELECT firstName, lastName, streetAdress, "
            + " city, postalCode, email, phoneNumber,"
            + " registrationDate, occupation, illnessList, operationList, "
            + " medicinUsed, attention, diagnosis, nextVisit "
            + " FROM customer "
            + " WHERE personNr = ?";

    private String sqlUpdatePatient = "UPDATE customer "
            + " SET firstName = ?, lastName = ?, streetAdress = ?, "
            + " city = ?, postalCode = ?, email = ?, phoneNumber = ?, "
            + " registrationDate = ?, occupation = ?, illnessList = ?, operationList = ?, "
            + " medicinUsed = ?, attention = ?, diagnosis = ?, nextVisit = ? "
            + " WHERE personNr = ? ";

    private String personNr_savedInDB = "";
    private String firstName_savedInDB = "";
    private String lastName_savedInDB = "";
    private String streetAdress_savedInDB = "";
    private String city_savedInDB = "";
    private String postalCode_savedInDB = "";
    private String email_savedInDB = "";
    private String phoneNumber_savedInDB = "";
    private String registrationDate_savedInDB = "";
    private String occupation_savedInDB = "";
    private String illnessList_savedInDB = "";
    private String operationList_savedInDB = "";
    private String medicinUsed_savedInDB = "";
    private String attention_savedInDB = "";
    private String diagnosis_savedInDB = "";
    private String nextVisit_savedInDB = "";

    private enum DBoperation {ADD, UPDATE, DELETE, ARCHIVE}
    private DBoperation currentDbOperation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        textFieldPersonNr.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(personNr_savedInDB)){
                this.textFieldPersonNr.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.textFieldPersonNr.setStyle("-fx-text-inner-color: #0000FF;");
            }
            // Warning: empty field
            if(newText.trim().isEmpty()) { lblPersonNrWarning.visibleProperty().setValue(true); }
            else { lblPersonNrWarning.visibleProperty().setValue(false); }
        });

        textFieldFirstName.textProperty().addListener((obs, oldText, newText) -> {
///            System.out.println("txtPassword changed from "+oldText+" to "+newText);
            if(newText.equals(firstName_savedInDB)){
                this.textFieldFirstName.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.textFieldFirstName.setStyle("-fx-text-inner-color: #0000FF;");
            }
            // Warning: empty field
            if(newText.trim().isEmpty()) { lblFirstNameWarning.visibleProperty().setValue(true); }
            else { lblFirstNameWarning.visibleProperty().setValue(false); }
        });

        textFieldLastName.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(lastName_savedInDB)){
                this.textFieldLastName.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.textFieldLastName.setStyle("-fx-text-inner-color: #0000FF;");
            }
            // Warning: empty field
            if(newText.trim().isEmpty()) { lblLastNameWarning.visibleProperty().setValue(true); }
            else { lblLastNameWarning.visibleProperty().setValue(false); }
        });

        textFieldStreetAdress.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(streetAdress_savedInDB)){
                this.textFieldStreetAdress.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.textFieldStreetAdress.setStyle("-fx-text-inner-color: #0000FF;");
            }
            // Warning: empty field
            if(newText.trim().isEmpty()) { lblStreetAdressWarning.visibleProperty().setValue(true); }
            else { lblStreetAdressWarning.visibleProperty().setValue(false); }
        });

        textFieldCity.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(city_savedInDB)){
                this.textFieldCity.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.textFieldCity.setStyle("-fx-text-inner-color: #0000FF;");
            }
            // Warning: empty field
            if(newText.trim().isEmpty()) { lblCityWarning.visibleProperty().setValue(true); }
            else { lblCityWarning.visibleProperty().setValue(false); }
        });

        textFieldPostalCode.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(postalCode_savedInDB)){
                this.textFieldPostalCode.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.textFieldPostalCode.setStyle("-fx-text-inner-color: #0000FF;");
            }
            // Warning: empty field
            if(newText.trim().isEmpty()) { lblPostalCodeWarning.visibleProperty().setValue(true); }
            else { lblPostalCodeWarning.visibleProperty().setValue(false); }
        });

        textFieldEmail.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(email_savedInDB)){
                this.textFieldEmail.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.textFieldEmail.setStyle("-fx-text-inner-color: #0000FF;");
            }
            // Warning: empty field
            if(newText.trim().isEmpty()) { lblEmailWarning.visibleProperty().setValue(true); }
            else { lblEmailWarning.visibleProperty().setValue(false); }
        });

        textFieldPhoneNr.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(phoneNumber_savedInDB)){
                this.textFieldPhoneNr.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.textFieldPhoneNr.setStyle("-fx-text-inner-color: #0000FF;");
            }
            // Warning: empty field
            if(newText.trim().isEmpty()) { lblPhoneNrWarning.visibleProperty().setValue(true); }
            else { lblPhoneNrWarning.visibleProperty().setValue(false); }
        });

        textFieldRegistrationDate.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(registrationDate_savedInDB)){
                this.textFieldRegistrationDate.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.textFieldRegistrationDate.setStyle("-fx-text-inner-color: #0000FF;");
            }
        });

        textFieldOccupation.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(occupation_savedInDB)){
                this.textFieldOccupation.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.textFieldOccupation.setStyle("-fx-text-inner-color: #0000FF;");
            }
        });

        textFieldIllnessList.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(illnessList_savedInDB)){
                this.textFieldIllnessList.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.textFieldIllnessList.setStyle("-fx-text-inner-color: #0000FF;");
            }
        });

        textFieldOperationList.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(operationList_savedInDB)){
                this.textFieldOperationList.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.textFieldOperationList.setStyle("-fx-text-inner-color: #0000FF;");
            }
        });

        textFieldMedicinUsed.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(medicinUsed_savedInDB)){
                this.textFieldMedicinUsed.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.textFieldMedicinUsed.setStyle("-fx-text-inner-color: #0000FF;");
            }
        });

        textFieldAttention.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(attention_savedInDB)){
                this.textFieldAttention.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.textFieldAttention.setStyle("-fx-text-inner-color: #0000FF;");
            }
        });

        textFieldDiagnosis.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(diagnosis_savedInDB)){
                this.textFieldDiagnosis.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.textFieldDiagnosis.setStyle("-fx-text-inner-color: #0000FF;");
            }
        });

        textFieldNextVisit.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(nextVisit_savedInDB)){
                this.textFieldNextVisit.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.textFieldNextVisit.setStyle("-fx-text-inner-color: #0000FF;");
            }
        });

        if (patients.PatientHolder.getPersonNr().trim().isEmpty()){
            currentDbOperation = DBoperation.ADD;
            lblAddPatientHeader.setText("Lägg till ny patient. Fyll uppgifter och klicka Lägg till");

            // Enable textFieldPersonNr
            textFieldPersonNr.setEditable(true);

            //Buttons
            btnAddNewPatient.setVisible(true);
            btnCancel.setVisible(true);
            btnUpdatePatient.setVisible(false);
            btnRefillPatient.setVisible(false);
            btnCancelUpdate.setVisible(false);
            btnArchive.setVisible(false);
        }
        else {
            currentDbOperation = DBoperation.UPDATE;
            personNr_savedInDB = patients.PatientHolder.getPersonNr();
            lblAddPatientHeader.setText("Du får ändra uppgifter för patienten "+ personNr_savedInDB + ". Fyll uppgifter och klicka Spara");

            // Disable textFieldPersonNr
            textFieldPersonNr.setEditable(false);

            // Enable Update buttons
            btnAddNewPatient.setVisible(false);
            btnCancel.setVisible(false);
            btnUpdatePatient.setVisible(true);
            btnRefillPatient.setVisible(true);
            btnCancelUpdate.setVisible(true);
            btnArchive.setVisible(true);

            fillPatientTextFields(patients.PatientHolder.getPersonNr());
        }

        // styling header
        lblAddPatientHeader.setStyle("-fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
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

        // styling buttons
        btnAddNewPatient.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLayout.BACKGROUND_DARK_GREY
                + "; -fx-font-weight: bold");
        btnCancel.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");
        btnUpdatePatient.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-text-fill: " + StylingLayout.BACKGROUND_DARK_GREY
                + "; -fx-font-weight: bold");
        btnRefillPatient.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");
        btnArchive.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");
        btnCancelUpdate.setStyle("-fx-background-color: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_BACKGROUND
                + "; -fx-text-fill: " + StylingLayout.ITEM_SELECTED_IN_LEFT_MENU_TEXT_FILL
                + "; -fx-font-weight: bold");

        hideWarningLabels();
    }

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static String getValueOrDefaultString(String value, String defaultValue) {
        return value == null ? defaultValue : value;
    }

    private void fillPatientTextFields(String persNr) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            resultSet = this.patientAddModel.getPatientInfo(persNr);

            if (resultSet.next()) {
                personNr_savedInDB = persNr;
                firstName_savedInDB = getValueOrDefault(resultSet.getString(1), "");
                lastName_savedInDB = getValueOrDefault(resultSet.getString(2), "");
                streetAdress_savedInDB = getValueOrDefault(resultSet.getString(3), "");
                city_savedInDB = getValueOrDefault(resultSet.getString(4), "");
                postalCode_savedInDB = getValueOrDefault(resultSet.getString(5), "");
                email_savedInDB = getValueOrDefault(resultSet.getString(6), "");
                phoneNumber_savedInDB = getValueOrDefault(resultSet.getString(7), "");
                registrationDate_savedInDB = getValueOrDefault(resultSet.getString(8), "");
                occupation_savedInDB = getValueOrDefault(resultSet.getString(9), "");
                illnessList_savedInDB = getValueOrDefault(resultSet.getString(10), "");
                operationList_savedInDB = getValueOrDefault(resultSet.getString(11), "");
                medicinUsed_savedInDB = getValueOrDefault(resultSet.getString(12), "");
                attention_savedInDB = getValueOrDefault(resultSet.getString(13), "");
                diagnosis_savedInDB = getValueOrDefault(resultSet.getString(14), "");
                nextVisit_savedInDB = getValueOrDefault(resultSet.getString(15), "");

                this.textFieldPersonNr.setText(personNr_savedInDB);
                this.textFieldFirstName.setText(firstName_savedInDB);
                this.textFieldLastName.setText(lastName_savedInDB);
                this.textFieldStreetAdress.setText(streetAdress_savedInDB);
                this.textFieldCity.setText(city_savedInDB);
                this.textFieldPostalCode.setText(postalCode_savedInDB);
                this.textFieldEmail.setText(email_savedInDB);
                this.textFieldPhoneNr.setText(phoneNumber_savedInDB);
                this.textFieldRegistrationDate.setText(registrationDate_savedInDB);

                this.textFieldOccupation.setText(occupation_savedInDB);
                this.textFieldIllnessList.setText(illnessList_savedInDB);
                this.textFieldOperationList.setText(operationList_savedInDB);
                this.textFieldMedicinUsed.setText(medicinUsed_savedInDB);
                this.textFieldAttention.setText(attention_savedInDB);
                this.textFieldDiagnosis.setText(diagnosis_savedInDB);
                this.textFieldNextVisit.setText(nextVisit_savedInDB);

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Patienten " + persNr + " hittades inte");
                alert.setHeaderText("Sökning misslyckad !");
                alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void AddPatientData(javafx.event.ActionEvent event) throws SQLException {
        // Connection conn = dbConnection.getConnection();

        String personNr = textFieldPersonNr.getText();
        String firstName = textFieldFirstName.getText();
        String lastName = textFieldLastName.getText();
        String streetAdress = textFieldStreetAdress.getText();
        String city = textFieldCity.getText();
        String postalCode = textFieldPostalCode.getText();
        String email = textFieldEmail.getText();
        String phoneNr = textFieldPhoneNr.getText();
        String regDate = textFieldRegistrationDate.getText();
        String occupation = textFieldOccupation.getText();
        String illnesses = textFieldIllnessList.getText();
        String operations = textFieldOperationList.getText();
        String medicins = textFieldMedicinUsed.getText();
        String attention = textFieldAttention.getText();
        String diagnosis = textFieldDiagnosis.getText();
        String nextVisit = textFieldNextVisit.getText();

        if(personNr.trim().isEmpty() || firstName.trim().isEmpty() || lastName.trim().isEmpty() ||
                streetAdress.trim().isEmpty() || city.trim().isEmpty() || postalCode.trim().isEmpty() ||
                email.trim().isEmpty() || phoneNr.trim().isEmpty()) {

            hideWarningLabels();
            // assert that no text field is empty
            if(personNr.trim().isEmpty()) {
                lblPersonNrWarning.visibleProperty().setValue(true);
            }
            if(firstName.trim().isEmpty()) {
                lblFirstNameWarning.visibleProperty().setValue(true);
            }
            if(lastName.trim().isEmpty()) {
                lblLastNameWarning.visibleProperty().setValue(true);
            }
            if(streetAdress.trim().isEmpty()) {
                lblStreetAdressWarning.visibleProperty().setValue(true);
            }
            if(city.trim().isEmpty()) {
                lblCityWarning.visibleProperty().setValue(true);
            }
            if(postalCode.trim().isEmpty()) {
                lblPostalCodeWarning.visibleProperty().setValue(true);
            }
            if(email.trim().isEmpty()) {
                lblEmailWarning.visibleProperty().setValue(true);
            }
            if(phoneNr.trim().isEmpty()) {
                lblPhoneNrWarning.visibleProperty().setValue(true);
            }
        }
        else {
            if (this.patientAddModel.addNewPatient(personNr, firstName, lastName, streetAdress,city, postalCode,
                    email, phoneNr, regDate, occupation, illnesses, operations, medicins,
                    attention, diagnosis, nextVisit)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Patienten lades till.");
                alert.setHeaderText("Det gick ju bra.");
                alert.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Patienten sparades inte.");
                alert.setHeaderText("Det gick inte så bra.");
                alert.show();
            }

            // Close Patient Add window
            navigation.navigateToPatients(btnCancel);
        }
    }

    public void UpdatePatientData(javafx.event.ActionEvent event) {
        String firstName = textFieldFirstName.getText().trim();
        String lastName = textFieldLastName.getText().trim();
        String streetAdress = textFieldStreetAdress.getText().trim();
        String city = textFieldCity.getText().trim();
        String postalCode = textFieldPostalCode.getText().trim();
        String email = textFieldEmail.getText().trim();
        String phoneNumber = textFieldPhoneNr.getText().trim();
        String registrationDate = textFieldRegistrationDate.getText().trim();
        String occupation = textFieldOccupation.getText().trim();
        String illnessList = textFieldIllnessList.getText().trim();
        String operationList = textFieldOperationList.getText().trim();
        String medicinUsed = textFieldMedicinUsed.getText().trim();
        String attention = textFieldAttention.getText().trim();
        String diagnosis = textFieldDiagnosis.getText().trim();
        String nextVisit = textFieldNextVisit.getText().trim();

        boolean someTextFieldsAreEmpty = firstName.isEmpty() || lastName.isEmpty() ||
                streetAdress.isEmpty() || city.isEmpty() || postalCode.isEmpty() ||
                email.isEmpty() || phoneNumber.isEmpty();

        boolean allTextFieldsNotChanged = firstName.equals(firstName_savedInDB) && lastName.equals(lastName_savedInDB) &&
                streetAdress.equals(streetAdress_savedInDB) && city.equals(city_savedInDB) &&
                postalCode.equals(postalCode_savedInDB) && email.equals(email_savedInDB) &&
                phoneNumber.equals(phoneNumber_savedInDB) &&
                registrationDate.equals(registrationDate_savedInDB) && occupation.equals(occupation_savedInDB) &&
                illnessList.equals(illnessList_savedInDB) && operationList.equals(operationList_savedInDB) &&
                medicinUsed.equals(medicinUsed_savedInDB) && attention.equals(attention_savedInDB) &&
                diagnosis.equals(diagnosis_savedInDB) && nextVisit.equals(nextVisit_savedInDB);

        if (someTextFieldsAreEmpty) {

            if(firstName.trim().isEmpty()) { lblFirstNameWarning.visibleProperty().setValue(true); }
            else { lblFirstNameWarning.visibleProperty().setValue(false); }

            if(lastName.trim().isEmpty()) { lblLastNameWarning.visibleProperty().setValue(true); }
            else { lblLastNameWarning.visibleProperty().setValue(false); }

            if(streetAdress.trim().isEmpty()) { lblStreetAdressWarning.visibleProperty().setValue(true); }
            else { lblStreetAdressWarning.visibleProperty().setValue(false); }

            if(city.trim().isEmpty()) { lblCityWarning.visibleProperty().setValue(true); }
            else { lblCityWarning.visibleProperty().setValue(false); }

            if(postalCode.trim().isEmpty()) { lblPostalCodeWarning.visibleProperty().setValue(true); }
            else { lblPostalCodeWarning.visibleProperty().setValue(false); }

            if(email.trim().isEmpty()) { lblEmailWarning.visibleProperty().setValue(true); }
            else { lblEmailWarning.visibleProperty().setValue(false); }

            if(phoneNumber.trim().isEmpty()) { lblPhoneNrWarning.visibleProperty().setValue(true); }
            else { lblPhoneNrWarning.visibleProperty().setValue(false); }

            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Några obligatoriska uppgifter är inte ifyllda.");
            alert.setHeaderText("Inmatningsfel.");
            alert.show();
        }
        else if (allTextFieldsNotChanged) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Inga uppgifter ändrades så länge.\nInget att spara!");
            alert.setHeaderText("Inmatningsfel.");
            alert.show();

        }
        else {
            if (this.patientAddModel.updatePatient(firstName, lastName, streetAdress,city, postalCode,
                    email, phoneNumber, registrationDate, occupation, illnessList, operationList, medicinUsed,
                    attention, diagnosis, nextVisit, personNr_savedInDB)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Personnummer: " + personNr_savedInDB + "\n" +
                                "Förnamn: \t" + firstName + "\n" +
                                "Efternamn: \t" + lastName + "\n\n" +
                                "Nya uppgifter sparade.");
                alert.setHeaderText("Det gick att uppdatera.");
                alert.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Det gick inte att uppdatera " + firstName + " " + lastName);
                alert.setHeaderText("Det gick snett.");
                alert.show();
            }

            // Close Update window
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/patients/patients.fxml"));
                Stage window = (Stage) btnCancel.getScene().getWindow();
                window.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void RefillPatientData(javafx.event.ActionEvent event) {
        this.textFieldPersonNr.setText(personNr_savedInDB);
        this.textFieldFirstName.setText(firstName_savedInDB);
        this.textFieldLastName.setText(lastName_savedInDB);
        this.textFieldStreetAdress.setText(streetAdress_savedInDB);
        this.textFieldCity.setText(city_savedInDB);
        this.textFieldPostalCode.setText(postalCode_savedInDB);
        this.textFieldEmail.setText(email_savedInDB);
        this.textFieldPhoneNr.setText(phoneNumber_savedInDB);
        this.textFieldRegistrationDate.setText(registrationDate_savedInDB);
        this.textFieldOccupation.setText(occupation_savedInDB);
        this.textFieldIllnessList.setText(illnessList_savedInDB);
        this.textFieldOperationList.setText(operationList_savedInDB);
        this.textFieldMedicinUsed.setText(medicinUsed_savedInDB);
        this.textFieldAttention.setText(attention_savedInDB);
        this.textFieldDiagnosis.setText(diagnosis_savedInDB);
        this.textFieldNextVisit.setText(nextVisit_savedInDB);

    }

    public void ArchivePatient(javafx.event.ActionEvent event) throws SQLException {
        Alert al = new Alert(Alert.AlertType.WARNING, "ArchivePatient \n Not ready yet...");
        al.setHeaderText("NOT READY.");
        al.show();

    }

    public void CancelAddPatient(javafx.event.ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/patients/patients.fxml"));
            Stage window = (Stage) btnCancel.getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
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

    private void clearTextFields() {
        textFieldPersonNr.clear();
        textFieldFirstName.clear();
        textFieldLastName.clear();
        textFieldStreetAdress.clear();
        textFieldCity.clear();
        textFieldPostalCode.clear();
        textFieldEmail.clear();
        textFieldPhoneNr.clear();
        textFieldRegistrationDate.clear();
        textFieldOccupation.clear();
        textFieldIllnessList.clear();
        textFieldOperationList.clear();
        textFieldMedicinUsed.clear();
        textFieldAttention.clear();
        textFieldDiagnosis.clear();
        textFieldNextVisit.clear();
    }
}
