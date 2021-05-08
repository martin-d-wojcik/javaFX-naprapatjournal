package admin;
import dbUtil.dbConnection;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;

import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AdminController implements Initializable {
    @FXML
    private TextField txtLoginName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField xtxtRole;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtPhoneNr;

    @FXML
    private TextField txtEmail;

    @FXML
    private TableView<UserData> userTable;

    @FXML
    private TableColumn<UserData, String> loginNameColumn;

    @FXML
    private TableColumn<UserData, String> passwordColumn;

    @FXML
    private TableColumn<UserData, String> roleColumn;

    @FXML
    private TableColumn<UserData, String> firstNameColumn;

    @FXML
    private TableColumn<UserData, String> lastNameColumn;

    @FXML
    private TableColumn<UserData, String> phoneNrColumn;

    @FXML
    private TableColumn<UserData, String> emailColumn;

    @FXML
    private Label lblAddUser;

    @FXML
    private TextField txtUserNameSearch;

    @FXML
    private Label lblNotification;

    @FXML
    private RadioButton rbUser;

    @FXML
    private RadioButton rbAdmin;

    private ToggleGroup rbGroup; // grouping rbUser+rbAdmin

    // private dbConnection dbConn;
    /*
     * SQL queries
     */
    private String sqlGetAllUsers = "SELECT * FROM login";
    private String sqlAddUser = "INSERT INTO login(loginName, password, role, firstName, lastName, phoneNr, email) " +
            " VALUES (?,?,?,?,?,?,?)";
    private String sqlUpdateUser = "UPDATE login SET password = ?, role = ?, firstName = ?, lastName = ?, phoneNr = ?, email = ? " +
            " WHERE loginName = ?";
    private String sqlSearchUser = "SELECT * FROM login WHERE loginName=?";
    private String sqlDeleteUser = "DELETE FROM login WHERE loginName=?";

    /*
     * Save loaded data for detecting if TextFields changes
     */
    private String loginName_savedInDB = "";
    private String password_savedInDB = "";
    private String role_savedInDB = "";
    private String firstName_savedInDB = "";
    private String lastName_savedInDB = "";
    private String phoneNr_savedInDB = "";
    private String email_savedInDB = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*****************************************
         * Add Change_Text_Listeners to TextFields
         *****************************************/
        txtLoginName.textProperty().addListener((obs, oldText, newText) -> {
///            System.out.println("txtPassword changed from "+oldText+" to "+newText);
            if(newText.equals(loginName_savedInDB)){
                this.txtLoginName.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.txtLoginName.setStyle("-fx-text-inner-color: #FF0000;");
            }
        });

        txtPassword.textProperty().addListener((obs, oldText, newText) -> {
///            System.out.println("txtPassword changed from "+oldText+" to "+newText);
            if(newText.equals(password_savedInDB)){
                this.txtPassword.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.txtPassword.setStyle("-fx-text-inner-color: #FF0000;");
            }
        });

        txtFirstName.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(firstName_savedInDB)){
                this.txtFirstName.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.txtFirstName.setStyle("-fx-text-inner-color: #FF0000;");
            }
        });

        txtLastName.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(lastName_savedInDB)){
                this.txtLastName.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.txtLastName.setStyle("-fx-text-inner-color: #FF0000;");
            }
        });

        txtPhoneNr.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(phoneNr_savedInDB)){
                this.txtPhoneNr.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.txtPhoneNr.setStyle("-fx-text-inner-color: #FF0000;");
            }
        });

        txtEmail.textProperty().addListener((obs, oldText, newText) -> {
            if(newText.equals(email_savedInDB)){
                this.txtEmail.setStyle("-fx-text-inner-color: #000000;");
            }
            else{
                this.txtEmail.setStyle("-fx-text-inner-color: #FF0000;");
            }
        });


        /*
         * a group for radio buttons
         */
        ToggleGroup rbGroup = new ToggleGroup();
        //to group radio buttons
        rbAdmin.setToggleGroup(rbGroup);
        rbUser.setToggleGroup(rbGroup);
        // define rbUser as selected
        rbUser.setSelected(true);

        // Fill TableView with data from login Table
        fillTableView();
        /*
         * Warning label colors
         */
        lblAddUser.setTextFill(Color.color(1, 0, 0));
        lblNotification.setTextFill(Color.color(1, 0, 0));
    }

    @FXML
    private void loadUserData(ActionEvent event) {
        this.lblAddUser.setText("");
        this.lblNotification.setText("");
        fillTableView();
        lblNotification.setText("");
    }

    private void fillTableView() {
        ObservableList<UserData> data = FXCollections.observableArrayList();

        try {
            Connection conn = dbConnection.getConnection();
            assert conn != null;
            ResultSet rs = conn.createStatement().executeQuery(sqlGetAllUsers);

            // check if the resultSet, rs has anything in the table
            while (rs.next()) {
                data.add(new UserData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
            conn.close();

        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }

        // get the StringProperties from the UserData class
        this.loginNameColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("userLoginName"));
        this.passwordColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("userPassword"));
        this.roleColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("userRole"));
        this.firstNameColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("userFirstName"));
        this.lastNameColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("userLastName"));
        this.phoneNrColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("userPhoneNr"));
        this.emailColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("userEmail"));

        this.userTable.setItems(null);
        this.userTable.setItems(data);
    }

    private void displayQueryResultInTable(ResultSet resultSet) throws SQLException {
        ObservableList<UserData> data = FXCollections.observableArrayList();

        if (resultSet.getRow() == 0) {
            lblNotification.setText("Inga användare hittades");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Inga användare hittades");
            alert.setHeaderText("Ett fel har inträffat");
            alert.show();

        } else {
            try {
                data.add(new UserData(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)));
            } catch (SQLException e) {
                System.err.println("Error: " + e);
            }
        }

        // get the StringProperties from the UserData class
        this.loginNameColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("userLoginName"));
        this.passwordColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("userPassword"));
        this.roleColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("userRole"));
        this.firstNameColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("userFirstName"));
        this.lastNameColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("userLastName"));
        this.phoneNrColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("userPhoneNr"));
        this.emailColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("userEmail"));

        this.userTable.setItems(null);
        this.userTable.setItems(data);
    }

    private void displayQueryResultInLeftPane(ResultSet resultSet) throws SQLException {
        ObservableList<UserData> data = FXCollections.observableArrayList();

        if (resultSet.getRow() == 0) {
            lblNotification.setText("Inga användare hittades");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Inga användare hittades");
            alert.setHeaderText("Ett fel har inträffat !");
            alert.show();
        } else {
            try {
                /*
                 * Save origin text from database
                 * for detecting changes later
                 */
                loginName_savedInDB = resultSet.getString(1).trim();
                password_savedInDB = resultSet.getString(2).trim();
                role_savedInDB = resultSet.getString(3).trim();
                firstName_savedInDB = resultSet.getString(4).trim();
                lastName_savedInDB = resultSet.getString(5).trim();
                phoneNr_savedInDB = resultSet.getString(6).trim();
                email_savedInDB = resultSet.getString(7).trim();

                this.txtLoginName.setText(loginName_savedInDB);
                this.txtPassword.setText(password_savedInDB);
                if(role_savedInDB.equals("Admin"))
                    this.rbAdmin.setSelected(true);
                else
                    this.rbUser.setSelected(true);
                this.txtFirstName.setText(firstName_savedInDB);
                this.txtLastName.setText(lastName_savedInDB);
                this.txtPhoneNr.setText(phoneNr_savedInDB);
                this.txtEmail.setText(email_savedInDB);

            } catch (SQLException e) {
                System.err.println("Error: " + e);
            }
        }

    }

    @FXML
    private void clearTableView() {
        userTable.getItems().clear();
        userTable.refresh();

    }

    @FXML
    private void clearInputPane(){
        // Clean input fields
        txtLoginName.clear();
        txtPassword.clear();;
        rbUser.setSelected(true);
        txtFirstName.clear();
        txtLastName.clear();
        txtPhoneNr.clear();
        txtEmail.clear();

    }

    /*************************************
     *
     * 		SQL methods
     *
     *************************************/
    @FXML
    private void addUser(ActionEvent event) throws SQLException {
        Connection conn = dbConnection.getConnection();

        String id = txtLoginName.getText().trim();
        String passw = txtPassword.getText().trim();
        String firstNm = txtFirstName.getText().trim();
        String lastNm = txtLastName.getText().trim();
        String phone = txtPhoneNr.getText().trim();
        String email = txtEmail.getText().trim();

        this.lblAddUser.setText("");
        this.lblNotification.setText("");

        /*
         * Break if any empty field
         */
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Fel inmatning !");

        if(id.isEmpty()) {
            this.lblAddUser.setText("Inlogningsnamn 岠tomt.");
            alert.contentTextProperty().set("Inlogningsnamn 岠tomt.");
            alert.show();
            return;
        }
        else if(passw.isEmpty()) {
            this.lblAddUser.setText("L��ord 岠tomt.");
            alert.contentTextProperty().set("L��ord 岠tomt.");
            alert.show();
            return;
        }

        else {
            /* TODO ??
             * Check if user exists and return before update
             */
            // Prepare query
            assert conn != null;
            PreparedStatement prepStmt = conn.prepareStatement(sqlAddUser);
            prepStmt.setString(1, id);
            prepStmt.setString(2, passw);

            if (rbAdmin.isSelected()){
                prepStmt.setString(3, "Admin");
            }
            else{
                prepStmt.setString(3, "User");
            }
            try {
                prepStmt.setString(4, firstNm);
                prepStmt.setString(5, lastNm);
                prepStmt.setString(6, phone);
                prepStmt.setString(7, email);

                int rowInserted = prepStmt.executeUpdate();// SQLException can occure here
                prepStmt.close();

                if (rowInserted ==1){
                    this.lblAddUser.setText("Ny användare tillagd.");
                    Alert al = new Alert(Alert.AlertType.INFORMATION, "Ny användare tillagd.");
                    al.setHeaderText("Insättning lyckad");
                    al.show();

                    // Update TableView
                    fillTableView();
                    clearInputPane();
                }
                else{
                    this.lblAddUser.setText("Ingen ny användare tillagd.");
                    Alert al = new Alert(Alert.AlertType.WARNING, "Ingen ny användare tillagd.");
                    al.setHeaderText("Insättning misslyckad");
                    al.show();
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e);
                Alert al = new Alert(Alert.AlertType.ERROR, e.toString());
                al.setHeaderText("SQLException har inträffat !");
                al.show();

            }
        }
        assert conn != null;
        conn.close();

    }

    @FXML
    private void updateUser(ActionEvent event) throws SQLException {
        Connection conn = dbConnection.getConnection();

        String id = txtLoginName.getText().trim();
        String passw = txtPassword.getText().trim();
        String firstNm = txtFirstName.getText().trim();
        String lastNm = txtLastName.getText().trim();
        String phone = txtPhoneNr.getText().trim();
        String email = txtEmail.getText().trim();
        String role = "";
        if (rbAdmin.isSelected()){
            role = "Admin";
        }
        else{
            role = "User";
        }

        boolean inputsAreSame = id.equals(loginName_savedInDB) &&
                passw.equals(password_savedInDB) &&
                firstNm.equals(firstName_savedInDB) &&
                lastNm.equals(lastName_savedInDB) &&
                phone.equals(phoneNr_savedInDB) &&
                email.equals(email_savedInDB) &&
                role.equals(role_savedInDB) ;

        this.lblAddUser.setText("");
        this.lblNotification.setText("");

        /*
         * Break if no text field was edited
         */
        if(inputsAreSame){
            this.lblAddUser.setText("Ingen textbox blev 寤rad 宮");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Ingen textbox blev 寤rad 宮");
            alert.setHeaderText("Fel inmatning !");
            alert.show();
            return;
        }
        /*
         * Break if any empty field
         */
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Fel inmatning !");

        if(id.isEmpty()) {
            this.lblAddUser.setText("Inlogningsnamn 岠tomt.");
            alert.contentTextProperty().set("Inlogningsnamn 岠tomt.");
            alert.show();
            return;
        }
        else if(passw.isEmpty()) {
            this.lblAddUser.setText("L��ord 岠tomt.");
            alert.contentTextProperty().set("L��ord 岠tomt.");
            alert.show();
            return;
        }
        else if(firstNm.isEmpty()) {
            this.lblAddUser.setText("F��mn 岠tomt.");
            alert.contentTextProperty().set("F��mn 岠tomt.");
            alert.show();
            return;
        }
        else if(lastNm.isEmpty()) {
            this.lblAddUser.setText("Efternamn 岠tomt.");
            alert.contentTextProperty().set("Efternamn 岠tomt.");
            alert.show();
            return;
        }
        else if(phone.isEmpty()) {
            this.lblAddUser.setText("Telefonnummer 岠tomt.");
            alert.contentTextProperty().set("Telefonnummer 岠tomt.");
            alert.show();
            return;
        }
        else if(email.isEmpty()) {
            this.lblAddUser.setText("E-postadress 岠tomt.");
            alert.contentTextProperty().set("E-postadress 岠tomt.");
            alert.show();
            return;
        }
        /*
         * No input errors so far.
         * Run query.
         */
        else {
            // Prepare query
            assert conn != null;
            PreparedStatement prepStmt = conn.prepareStatement(sqlUpdateUser);

            try {
                prepStmt.setString(1, passw);
                prepStmt.setString(2, role);
                prepStmt.setString(3, firstNm);
                prepStmt.setString(4, lastNm);
                prepStmt.setString(5, phone);
                prepStmt.setString(6, email);
                prepStmt.setString(7, id);

                int rowUpdated = prepStmt.executeUpdate();// SQLException can occure here
                prepStmt.close();

                if (rowUpdated ==1){
                    this.lblAddUser.setText("Anv寤aren " + id + " uppdaterad.");
                    Alert al = new Alert(Alert.AlertType.INFORMATION, "Anv寤aren " + id + " uppdaterad.");
                    al.setHeaderText("Uppdatering lyckad");
                    al.show();

                    // Update TableView
                    fillTableView();
                    clearInputPane();
                }
                else{
                    this.lblAddUser.setText("Uppgifter f��nv寤aren " +id + " 寤rades INTE.");
                    Alert al = new Alert(Alert.AlertType.WARNING, "Uppgifter f��nv寤aren " +id + " 寤rades INTE.");
                    al.setHeaderText("Uppdatering misslyckad");
                    al.show();

                }
            } catch (SQLException e) {
                System.err.println("Error: " + e);
                Alert al = new Alert(Alert.AlertType.ERROR, e.toString());
                al.setHeaderText("SQLException har inträffat !");
                al.show();

            }
        }
        assert conn != null;
        conn.close();

    }


    @FXML
    private void searchUser(ActionEvent event) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        this.lblAddUser.setText("");
        this.lblNotification.setText("");

        try {
            String userName = txtUserNameSearch.getText().trim();
            /*
             * Break if empty field
             */
            if(userName.isEmpty()) {
                this.lblNotification.setText("Ange inlogningsnamn !");
                Alert al = new Alert(Alert.AlertType.WARNING, "Ange inlogningsnamn !");
                al.setHeaderText("Fel inmatning f����ng!");
                al.show();
                return;
            }

            Connection conn = dbConnection.getConnection();

            assert conn != null;
            preparedStatement = conn.prepareStatement(sqlSearchUser);
            preparedStatement.setString(1, userName);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                displayQueryResultInTable(resultSet);
                displayQueryResultInLeftPane(resultSet);

                lblNotification.setText("");
                txtUserNameSearch.clear();
            } else {
                clearTableView();
                clearInputPane();

                lblNotification.setText("Användare " + userName + " hittades inte");
                Alert alert = new Alert(Alert.AlertType.WARNING, "Användare " + userName + " hittades inte");
                alert.setHeaderText("Sökning misslyckad !");
                alert.show();
            }

            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void deleteUser(ActionEvent event) {
        PreparedStatement preparedStatement = null;

        this.lblAddUser.setText("");

        try {
            String userName = txtUserNameSearch.getText();
            /*
             * Break if empty field
             */
            if (userName.isEmpty()) {
                this.lblNotification.setText("Ange inlogningsnamn !");
                Alert al = new Alert(Alert.AlertType.WARNING, "Ange inlogningsnamn !");
                al.setHeaderText("Fel inmatning f��adering!");
                al.show();
                return;
            }

            if (modalPopupDelete(userName)) {

                Connection conn = dbConnection.getConnection();

                assert conn != null;
                preparedStatement = conn.prepareStatement(sqlDeleteUser);
                preparedStatement.setString(1, userName);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 1) {
                    lblNotification.setText("Användare " + userName + " raderad");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Användare " + userName + " raderad");
                    alert.setHeaderText("Radering lyckades !");
                    alert.show();

                    txtUserNameSearch.clear();
                    clearInputPane();
                    fillTableView();
                } else {
                    lblNotification.setText("Användare " + userName + " raderades INTE");
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Användare " + userName + " raderades INTE");
                    alert.setHeaderText("Radering misslyckad !");
                    alert.show();

                    txtUserNameSearch.clear();
                }

                conn.close();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * displays a modal popup and waits for answer
     *
     * @param userName
     * @return true if answer is yes, false if answer of the popup is false
     */
    public boolean modalPopupDelete(String userName) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Radera anv寤are");
        alert.setHeaderText("En anv寤are kommer att raderas !");
        alert.setContentText("Ska an寤are " + userName + " verkligen raderas ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            return true;
        else
            return false;
    }
}
