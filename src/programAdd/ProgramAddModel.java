package programAdd;

import dbUtil.dbConnection;
import patients.PatientHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProgramAddModel {
    Connection connection;

    public ProgramAddModel() {
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

    public int addNewProgramToDb(String listOfExercises, String programName) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs = null;
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd, HH:mm").format(new Date());

        String sqlQuerySaveProgram = "INSERT INTO program (personNr, information, programName, dateOfCreation, active) VALUES " +
                "(?, ?, ?, ?, ?)";

        int rowsInserted = 0;

        try {
            pr = this.connection.prepareStatement(sqlQuerySaveProgram);
            pr.setString(1, PatientHolder.getPersonNr());
            pr.setString(2, listOfExercises);
            pr.setString(3, programName);
            pr.setString(4, timeStamp);
            pr.setBoolean(5, true);

            rowsInserted = pr.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            assert pr != null;
            pr.close();

        }
        return rowsInserted;
    }
}
