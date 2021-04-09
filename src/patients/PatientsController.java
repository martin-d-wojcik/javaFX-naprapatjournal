package patients;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientsController implements Initializable {
    @FXML
    private Label lblUserLoggedInHeader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public PatientsController() {}

    public void setHeader(String userName) {
        lblUserLoggedInHeader.setText(userName);
    }
}
