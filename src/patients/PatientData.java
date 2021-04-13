package patients;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PatientData {
    private final StringProperty patientPersonNr;
    private final StringProperty patientFirstName;
    private final StringProperty patientLastName;
    private final StringProperty patientStreetAdress;
    private final StringProperty patientCity;
    private final StringProperty patientPostalCode;
    private final StringProperty patientEmail;
    private final StringProperty patientPhoneNr;

    public PatientData(String patientPersonNr, String patientFirstName, String patientLastName,
                       String patientStreetAdress, String patientCity, String patientPostalCode,
                       String patientEmail, String patientPhoneNr) {
        this.patientPersonNr = new SimpleStringProperty(patientPersonNr);
        this.patientFirstName = new SimpleStringProperty(patientFirstName);
        this.patientLastName = new SimpleStringProperty(patientLastName);
        this.patientStreetAdress = new SimpleStringProperty(patientStreetAdress);
        this.patientCity = new SimpleStringProperty(patientCity);
        this.patientPostalCode = new SimpleStringProperty(patientPostalCode);
        this.patientEmail = new SimpleStringProperty(patientEmail);
        this.patientPhoneNr = new SimpleStringProperty(patientPhoneNr);
    }

    public String getPatientPersonNr() {
        return patientPersonNr.get();
    }

    public StringProperty patientPersonNrProperty() {
        return patientPersonNr;
    }

    public void setPatientPersonNr(String patientPersonNr) {
        this.patientPersonNr.set(patientPersonNr);
    }

    public String getPatientFirstName() {
        return patientFirstName.get();
    }

    public StringProperty patientFirstNameProperty() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName.set(patientFirstName);
    }

    public String getPatientLastName() {
        return patientLastName.get();
    }

    public StringProperty patientLastNameProperty() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName.set(patientLastName);
    }

    public String getPatientStreetAdress() {
        return patientStreetAdress.get();
    }

    public StringProperty patientStreetAdressProperty() {
        return patientStreetAdress;
    }

    public void setPatientStreetAdress(String patientStreetAdress) {
        this.patientStreetAdress.set(patientStreetAdress);
    }

    public String getPatientCity() {
        return patientCity.get();
    }

    public StringProperty patientCityProperty() {
        return patientCity;
    }

    public void setPatientCity(String patientCity) {
        this.patientCity.set(patientCity);
    }

    public String getPatientPostalCode() {
        return patientPostalCode.get();
    }

    public StringProperty patientPostalCodeProperty() {
        return patientPostalCode;
    }

    public void setPatientPostalCode(String patientPostalCode) {
        this.patientPostalCode.set(patientPostalCode);
    }

    public String getPatientEmail() {
        return patientEmail.get();
    }

    public StringProperty patientEmailProperty() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail.set(patientEmail);
    }

    public String getPatientPhoneNr() {
        return patientPhoneNr.get();
    }

    public StringProperty patientPhoneNrProperty() {
        return patientPhoneNr;
    }

    public void setPatientPhoneNr(String patientPhoneNr) {
        this.patientPhoneNr.set(patientPhoneNr);
    }
}
