package patientAdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dbUtil.dbConnection;

public class PatientsModel {
    Connection connection;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    private String sqlGetAllCustomersBasic = "SELECT personNr, firstName, lastName, streetAdress, city, postalCode, email, phoneNumber FROM customer";
    private String sqlGetCustomerByName_Like = "SELECT personNr, firstName, lastName, streetAdress, city, postalCode, email, phoneNumber "
            + "FROM customer WHERE firstName LIKE ? AND lastName LIKE ?";
    private String sqlGetCustomerByPersonNr_Like = "SELECT personNr, firstName, lastName, streetAdress, city, postalCode, email, phoneNumber "
            + "FROM customer WHERE personNr LIKE ?";

    public PatientsModel() {
        try {
            this.connection = dbConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // check if db connection is ok
        if(this.connection == null) {
            System.exit(1);
        }
    }

    public ResultSet getAllPatients() {
        try {
            resultSet = this.connection.createStatement().executeQuery(sqlGetAllCustomersBasic);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getCustomerByName(String firstName, String lastName) {
        try {
            preparedStatement = this.connection.prepareStatement(sqlGetCustomerByName_Like);
            preparedStatement.setString(1, firstName + "%");
            preparedStatement.setString(2, lastName + "%");

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getCustomerByPersonNr(String personNr) {
        try {
            preparedStatement = this.connection.prepareStatement(sqlGetCustomerByPersonNr_Like);
            preparedStatement.setString(1, personNr + "%");
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }
}
