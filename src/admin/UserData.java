package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserData {
    private final StringProperty userLoginName;
    private final StringProperty userPassword;
    private final StringProperty userRole;
    private final StringProperty userFirstName;
    private final StringProperty userLastName;
    private final StringProperty userPhoneNr;
    private final StringProperty userEmail;

    public UserData(String loginName, String password, String role, String firstname, String lastname, String telnr, String email) {
        this.userLoginName = new SimpleStringProperty(loginName);
        this.userPassword = new SimpleStringProperty(password);
        this.userRole = new SimpleStringProperty(role);
        this.userFirstName = new SimpleStringProperty(firstname);
        this.userLastName = new SimpleStringProperty(lastname);
        this.userPhoneNr = new SimpleStringProperty(telnr);
        this.userEmail = new SimpleStringProperty(email);
    }

    public String getUserRole() {
        return userRole.get();
    }

    public StringProperty userRoleProperty() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole.set(userRole);
    }

    public String getUserLoginName() {
        return userLoginName.get();
    }

    public StringProperty userLoginNameProperty() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName.set(userLoginName);
    }

    public String getUserPassword() {
        return userPassword.get();
    }

    public StringProperty userPasswordProperty() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword.set(userPassword);
    }

    public String getUserFirstName() {
        return userFirstName.get();
    }

    public StringProperty userFirstNameProperty() {
        return userFirstName;
    }

    public void setFirstUserName(String userFirstName) {
        this.userFirstName.set(userFirstName);
    }

    public String getUserLastName() {
        return userLastName.get();
    }

    public StringProperty userLastNameProperty() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName.set(userLastName);
    }

    public String getUserPhoneNr() {
        return userPhoneNr.get();
    }

    public StringProperty userPhoneNrProperty() {
        return userPhoneNr;
    }

    public void setUserPhoneNr(String userPhoneNr) {
        this.userPhoneNr.set(userPhoneNr);
    }

    public String getUserEmail() {
        return userEmail.get();
    }

    public StringProperty userEmailProperty() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail.set(userEmail);
    }

}
