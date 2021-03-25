package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserData {
    private final StringProperty userName;
    private final StringProperty userPassword;
    private final StringProperty userRole;

    public UserData(String name, String password, String role) {
        this.userName = new SimpleStringProperty(name);
        this.userPassword = new SimpleStringProperty(password);
        this.userRole = new SimpleStringProperty(role);
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

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
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
}
