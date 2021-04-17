package admin;
import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private TextField txtLoginName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtRole;

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
    private Label lblSearchUser;

    @FXML
    private RadioButton rbUser;

    @FXML
    private RadioButton rbAdmin;

    private ToggleGroup rbGroup; // grouping rbUser+rbAdmin

    //TODO    private dbConnection dbConn;
    // private ObservableList<UserData> data;
    private String sqlGetAllUsers = "SELECT * FROM login";
    private String sqlAddUser = "INSERT INTO login(loginName, password, role, firstName, lastName, phoneNr, email) VALUES (?,?,?,?,?,?,?)";
    private String sqlSearchUser = "SELECT * FROM login WHERE loginName=?";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //a group for radio buttons
        ToggleGroup rbGroup = new ToggleGroup();
        //to group radio buttons
        rbAdmin.setToggleGroup(rbGroup);
        rbUser.setToggleGroup(rbGroup);
        // define rbUser as selected
        rbUser.setSelected(true);

        // Fill TableView with data from login Table
        fillTableView();
    }

    @FXML
    private void loadUserData(ActionEvent event) {
        fillTableView();
        lblSearchUser.setText("");
    }

    private void fillTableView() {
        ObservableList<UserData> data = FXCollections.observableArrayList();

        try {
            Connection conn = dbConnection.getConnection();
            assert conn != null;
            ResultSet rs = conn.createStatement().executeQuery(sqlGetAllUsers);

            // check if the resultSet, rs has anything in the table
            while (rs.next()) {
///                data.add(new UserData(rs.getString(1), rs.getString(2), rs.getString(3)));
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
            lblSearchUser.setText("Inga användare hittades");
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
            lblSearchUser.setText("Inga användare hittades");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Inga användare hittades");
            alert.setHeaderText("Ett fel har inträffat !");
            alert.show();
        } else {
            try {
                this.txtLoginName.setText(resultSet.getString(1));
                this.txtPassword.setText(resultSet.getString(2));

                if(resultSet.getString(3).trim().equals("Admin"))
                    this.rbAdmin.setSelected(true);
                else
                    this.rbUser.setSelected(true);

                this.txtFirstName.setText(resultSet.getString(4));
                this.txtLastName.setText(resultSet.getString(5));
                this.txtPhoneNr.setText(resultSet.getString(6));
                this.txtEmail.setText(resultSet.getString(7));
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

    @FXML
    private void addUser(ActionEvent event) throws SQLException {
        Connection conn = dbConnection.getConnection();

        String id = txtLoginName.getText();
        String passw = txtPassword.getText();
        String firstNm = txtFirstName.getText();
        String lastNm = txtLastName.getText();
        String phone = txtPhoneNr.getText();
        String email = txtEmail.getText();

        if (id.trim().isEmpty() || passw.trim().isEmpty()) {
            this.lblAddUser.setText("Användare och/eller lösenord får inte vara tomt.");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Användare och/eller lösenord får inte vara tomt.");
            alert.setHeaderText("Ett fel har inträffat !");
            alert.show();
        }
        else {
            /*
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

                prepStmt.executeUpdate();// SQLException can occure here
                prepStmt.close();

                this.lblAddUser.setText("Ny användare tillagd.");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ny användare tillagd.");
                alert.setHeaderText("Insättning lyckad");
                alert.show();

                // Update TableView
                fillTableView();
                clearInputPane();
            } catch (SQLException e) {
                System.err.println("Error: " + e);
                Alert alert = new Alert(Alert.AlertType.ERROR, e.toString());
                alert.setHeaderText("SQLException har inträffat !");
                alert.show();

            }
        }
        assert conn != null;
        conn.close();

    }


    @FXML
    private void searchUser(ActionEvent event) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String userName = txtUserNameSearch.getText();

            Connection conn = dbConnection.getConnection();

            assert conn != null;
            preparedStatement = conn.prepareStatement(sqlSearchUser);
            preparedStatement.setString(1, userName);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                displayQueryResultInTable(resultSet);
                displayQueryResultInLeftPane(resultSet);

                lblSearchUser.setText("");
                txtUserNameSearch.clear();
            } else {
                clearTableView();
                clearInputPane();

                lblSearchUser.setText("Användare " + userName + " hittades inte");
                Alert alert = new Alert(Alert.AlertType.WARNING, "Användare " + userName + " hittades inte");
                alert.setHeaderText("Sökning misslyckad !");
                alert.show();
            }

            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
