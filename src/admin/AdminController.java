package admin;
import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private TextField txtFirstAndLastName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtRole;

    @FXML
    private TableView<UserData> userTable;

    @FXML
    private TableColumn<UserData, String> columnName;

    @FXML
    private TableColumn<UserData, String> columnPassword;

    @FXML
    private TableColumn<UserData, String> columnRole;

    @FXML
    private Label lblAddUser;

    @FXML
    private TextField txtUserNameSearch;

    @FXML
    private Label lblSearchUser;

    private dbConnection dbConn;
    // private ObservableList<UserData> data;

    private String sqlGetAllUsers = "SELECT * FROM login";
    private String sqlAddUser = "INSERT INTO login(user, password, role) VALUES (?,?,?)";
    private String sqlSearchUser = "SELECT * FROM login WHERE user=?";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillTableView();
    }

    @FXML
    private void loadUserData(ActionEvent event) {
        fillTableView();
    }

    private void fillTableView() {
        ObservableList<UserData> data = FXCollections.observableArrayList();

        try {
            Connection conn = dbConnection.getConnection();
            assert conn != null;
            ResultSet rs = conn.createStatement().executeQuery(sqlGetAllUsers);

            // check if the resultSet, rs has anything in the table
            while (rs.next()) {
                data.add(new UserData(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }

        // get the StringProperties from the UserData class
        this.columnName.setCellValueFactory(new PropertyValueFactory<UserData, String>("userName"));
        this.columnPassword.setCellValueFactory(new PropertyValueFactory<UserData, String>("userPassword"));
        this.columnRole.setCellValueFactory(new PropertyValueFactory<UserData, String>("userRole"));

        this.userTable.setItems(null);
        this.userTable.setItems(data);
    }

    private void displayQueryResultInTable(ResultSet resultSet) throws SQLException {
        ObservableList<UserData> data = FXCollections.observableArrayList();

        if (resultSet.getRow() == 0) {
            lblSearchUser.setText("Inga användare hittades");
        } else {
            try {
                data.add(new UserData(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)));
            } catch (SQLException e) {
                System.err.println("Error: " + e);
            }
        }

        // get the StringProperties from the UserData class
        this.columnName.setCellValueFactory(new PropertyValueFactory<UserData, String>("userName"));
        this.columnPassword.setCellValueFactory(new PropertyValueFactory<UserData, String>("userPassword"));
        this.columnRole.setCellValueFactory(new PropertyValueFactory<UserData, String>("userRole"));

        this.userTable.setItems(null);
        this.userTable.setItems(data);
    }


    @FXML
    private void addUser(ActionEvent event) throws SQLException {
        Connection conn = dbConnection.getConnection();

        String firstAndLast = txtFirstAndLastName.getText();
        String passw = txtPassword.getText();

        if (firstAndLast.trim().isEmpty() || passw.trim().isEmpty()) {
            this.lblAddUser.setText("Användare och/eller lösenord får inte vara tomt.");
        }
        else{
            // Prepare query
            assert conn != null;
            PreparedStatement prepStmt = conn.prepareStatement(sqlAddUser);
            prepStmt.setString(1, firstAndLast);
            prepStmt.setString(2, passw);
            prepStmt.setString(3, "User");
            prepStmt.executeUpdate();
            prepStmt.close();

            this.lblAddUser.setText("Ny användare tillagd.");

            // Update TableView
            fillTableView();

            // Clean input fields
            txtFirstAndLastName.setText("");
            txtPassword.setText("");
        }
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

            // while (resultSet.next()) {}
            while (resultSet.next()) {
                displayQueryResultInTable(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        /* } finally {
            try{
                if(resultSet != null) resultSet.close();
                if(preparedStatement != null) preparedStatement.close();
        } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }*/
        }
    }
}
