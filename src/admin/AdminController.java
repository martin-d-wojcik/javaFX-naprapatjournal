package admin;
import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private TextField role;

    @FXML
    private TableView<UserData> userTable;

    @FXML
    private TableColumn<UserData, String> columnName;

    @FXML
    private TableColumn<UserData, String> columnPassword;

    @FXML
    private TableColumn<UserData, String> columnRole;

    private dbConnection dbConn;
    // private ObservableList<UserData> data;

    private String sqlGetAllUsers = "SELECT * FROM login";
    private String sqlAdduser = "INSERT INTO login(user, password, role) VALUES (?,?,?)";
    // TODO: use PreparedStatement

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dbConn = new dbConnection();
    }

    /* public void initialize(URL url, ResourceBundle rb) {
        this.dbConn = new dbConnection();
    } >*/

    @FXML
    private void loadUserData(ActionEvent event) throws SQLException {
        ObservableList<UserData> data = FXCollections.observableArrayList();

        try {
            Connection conn = dbConnection.getConnection();
            // ObservableList<UserData> data = FXCollections.observableArrayList();
            assert conn != null;
            ResultSet rs = conn.createStatement().executeQuery(sqlGetAllUsers);

            // check if the resultSet, rs has anything in the table
            while (rs.next()) {
                data.add(new UserData(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }

        // get the StringProperties from the UserData class
        this.columnName.setCellValueFactory(new PropertyValueFactory<UserData, String>("userName"));
        this.columnName.setCellValueFactory(new PropertyValueFactory<UserData, String>("userPassword"));

        this.userTable.setItems(null);
        this.userTable.setItems(data);
    }

    @FXML
    private void addUser(ActionEvent event) throws SQLException {
        Connection conn = dbConnection.getConnection();

    }
}
