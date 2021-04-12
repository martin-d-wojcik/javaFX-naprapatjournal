package patients;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import resources.Styling;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class PatientsController implements Initializable {
    @FXML
    private Label lblUserLoggedInHeader;

    @FXML
    private AnchorPane anchorPaneLeftmenu;

    @FXML
    private Label lblPatienter;

    @FXML
    private Label lblJournaler;

    @FXML
    private Label lblProgram;

    @FXML
    private Label lblHem;

    @FXML
    private Line lineLeftMenuTop;

    @FXML
    private Label lblTemp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // styling
        lblUserLoggedInHeader.setStyle("-fx-text-fill: " + Styling.loggedInUserHeaderTextFill);
        anchorPaneLeftmenu.setStyle("-fx-background-color: " + Styling.backgroundDarkGrey);
        lblHem.setStyle("-fx-text-fill: " + Styling.itemSelectedInLeftMenuTextFill);
        lblPatienter.setStyle("-fx-text-fill: " + Styling.loggedInUserHeaderTextFill);
        lblJournaler.setStyle("-fx-text-fill: " + Styling.loggedInUserHeaderTextFill);
        lblProgram.setStyle("-fx-text-fill: " + Styling.loggedInUserHeaderTextFill);
        // lineLeftMenuTop.setStyle("-fx-text-fill: " + Styling.loggedInUserHeaderLine);

        setHeader(login.UserHolder.getLoggedInUser());
    }

    public void setHeader(String userName) {
        lblUserLoggedInHeader.setText("Inloggad: " + userName);
    }

    public void goToPatients(ActionEvent event) {
        lblTemp.setText("Boom");
    }
}
