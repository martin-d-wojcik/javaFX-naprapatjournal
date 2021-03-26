package login;

import admin.AdminController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import users.UsersController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    LoginModel loginModel = new LoginModel();

    @FXML
    private Label dbstatus;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginBtn;

    @FXML
    private Label loginStatus;

    public void initialize(URL url, ResourceBundle rb) {}

    @FXML
    public void logIn(ActionEvent event) {
        try {
            // parse the login credentials from the controls in the fxml
            if(this.loginModel.isLoggedIn(this.username.getText(), this.password.getText())) {
                Stage stage = (Stage)this.loginBtn.getScene().getWindow();
                stage.close();

                if(this.username.getText().equals("Admin")) {
                    adminLogin();
                } else {
                    userLogin();
                }
            }
            else {
                this.loginStatus.setText("Wrong login credentials");
            }
        } catch (Exception localEx) {
            localEx.printStackTrace();
        }
    }

    public void adminLogin() {
        try {
            Stage adminStage = new Stage();
            FXMLLoader adminLoader = new FXMLLoader();
            Pane adminRoot = FXMLLoader.load(getClass().getResource("/admin/admin.fxml"));

            // attach the controller file to the fxml file
            AdminController adminController = (AdminController)adminLoader.getController();

            Scene scene = new Scene(adminRoot);
            adminStage.setScene(scene);
            adminStage.setMaximized(true);
            adminStage.setTitle("Admin Dashboard");
            adminStage.setResizable(false);
            adminStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void userLogin() {
        try {
            Stage userStage = new Stage();
            FXMLLoader userLoader = new FXMLLoader();
            // Pane root = (Pane)userLoader.load(getClass().getResource("/users/user.fxml").openStream());
            Pane userRoot = FXMLLoader.load(getClass().getResource("/users/user.fxml"));

            // attach the controller file to the fxml file
            UsersController usersController = (UsersController)userLoader.getController();

            Scene scene = new Scene(userRoot);
            userStage.setScene(scene);
            userStage.setMaximized(true);
            userStage.setTitle("User Dashboard");
            userStage.setResizable(false);
            userStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
