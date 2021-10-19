package patientAdd;

import dbUtil.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientAddModel {
    Connection connection;
    private String sqlGetPatientByPersonNr = "SELECT firstName, lastName, streetAdress, "
            + " city, postalCode, email, phoneNumber,"
            + " registrationDate, occupation, illnessList, operationList, "
            + " medicinUsed, attention, diagnosis, nextVisit "
            + " FROM customer "
            + " WHERE personNr = ?";
    private String sqlAddPatient = "INSERT INTO customer(personNr, firstName, lastName, streetAdress, "
            + " city, postalCode, email, phoneNumber,"
            + " registrationDate, occupation, illnessList, operationList, "
            + " medicinUsed, attention, diagnosis, nextVisit) "
            + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private String sqlUpdatePatient = "UPDATE customer "
            + " SET firstName = ?, lastName = ?, streetAdress = ?, "
            + " city = ?, postalCode = ?, email = ?, phoneNumber = ?, "
            + " registrationDate = ?, occupation = ?, illnessList = ?, operationList = ?, "
            + " medicinUsed = ?, attention = ?, diagnosis = ?, nextVisit = ? "
            + " WHERE personNr = ? ";

    public PatientAddModel() {
        try {
            this.connection = dbConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // check if db connection is ok
        if(this.connection == null) {
            System.exit(1);
        }
    }

    public Boolean updatePatient(String firstName, String lastName,
                                 String streetAdress, String city, String  postalCode,
                                 String email, String phoneNr, String regDate, String occupation,
                                 String illnesses, String operations, String medicins,
                                 String attention, String diagnosis, String nextVisit, String personNr) {
        boolean returnValue = false;

        try {
            PreparedStatement prepStmt = this.connection.prepareStatement(sqlUpdatePatient);
            // SET
            prepStmt.setString(1, firstName);
            prepStmt.setString(2, lastName);
            prepStmt.setString(3, streetAdress);
            prepStmt.setString(4, city);
            prepStmt.setString(5, postalCode);
            prepStmt.setString(6, email);
            prepStmt.setString(7, phoneNr);
            prepStmt.setString(8, regDate);
            prepStmt.setString(9, occupation);
            prepStmt.setString(10, illnesses);
            prepStmt.setString(11, operations);
            prepStmt.setString(12, medicins);
            prepStmt.setString(13, attention);
            prepStmt.setString(14, diagnosis);
            prepStmt.setString(15, nextVisit);
            // WHERE
            prepStmt.setString(16, personNr);

            prepStmt.executeUpdate();
            prepStmt.close();

            returnValue = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public Boolean addNewPatient(String personNr, String firstName, String lastName,
                                 String streetAdress, String city, String  postalCode,
                                 String email, String phoneNr, String regDate, String occupation,
                                 String illnesses, String operations, String medicins,
                                 String attention, String diagnosis, String nextVisit) {
        boolean returnValue = false;

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sqlAddPatient);
            preparedStatement.setString(1, personNr);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, streetAdress);
            preparedStatement.setString(5, city);
            preparedStatement.setString(6, postalCode);
            preparedStatement.setString(7, email);
            preparedStatement.setString(8, phoneNr);
            preparedStatement.setString(9, regDate);
            preparedStatement.setString(10, occupation);
            preparedStatement.setString(11, illnesses);
            preparedStatement.setString(12, operations);
            preparedStatement.setString(13, medicins);
            preparedStatement.setString(14, attention);
            preparedStatement.setString(15, diagnosis);
            preparedStatement.setString(16, nextVisit);

            preparedStatement.executeUpdate();
            preparedStatement.close();

            returnValue = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public ResultSet getPatientInfo(String personNr) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = this.connection.prepareStatement(sqlGetPatientByPersonNr);
            preparedStatement.setString(1, personNr);

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }
}
