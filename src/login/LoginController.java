package login;

import javafx.scene.layout.AnchorPane;
import patients.PatientsController;
import admin.AdminController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
            if(this.loginModel.isUserLoggedIn(this.username.getText(), this.password.getText())) {
                Stage stage = (Stage)this.loginBtn.getScene().getWindow();
                stage.close();

                String currentUser = this.username.getText();
                UserHolder.setLoggedInUser(currentUser);
                // works too: UserHolder.loggedInUser = currentUser;

                if(this.loginModel.isAdmin(currentUser)) {
                    adminLogin();
                } else {
                    userLogin();
                }
            }
            else {
                this.loginStatus.setText("Fel användarnamn eller lösenord");
                Alert alert = new Alert(Alert.AlertType.ERROR, "Fel användarnamn eller lösenord");
                alert.setHeaderText("Ett fel har inträffat !");
                alert.show();
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
            // Pane userRoot = FXMLLoader.load(getClass().getResource("/patients/patients.fxml"));
            AnchorPane userRoot = FXMLLoader.load(getClass().getResource("/patients/patients.fxml"));

            // attach the controller file to the fxml file
            PatientsController patientsController = (PatientsController)userLoader.getController();

            Scene scene = new Scene(userRoot);
            userStage.setScene(scene);
            userStage.setTitle("User Dashboard");
            userStage.setResizable(false);
            userStage.show();

            // PatientsController p = new PatientsController();
            // p.setHeader(this.username.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
