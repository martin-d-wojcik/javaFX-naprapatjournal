package program;

import dbUtil.dbConnection;
import exercises.ExerciseData;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProgramModel {
    Connection connection;

    // sql queries
    private String sqlQueryEmailProgramToPatient = "SELECT email FROM customer WHERE personNr=?";

    public ProgramModel() {
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

    public Boolean sendEmail(String personNr) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        boolean emailSent = false;

        try {
            conn = dbConnection.getConnection();
            assert conn != null;

                preparedStatement = conn.prepareStatement(sqlQueryEmailProgramToPatient);
                preparedStatement.setString(1, personNr);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    emailSent = true;
                }

                resultSet.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return emailSent;
    }

    public int deleteProgramFromDb(String personNr, String programName) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs = null;

        String sqlQueryDeleteProgram = "DELETE FROM program WHERE personNr=? AND programName=?";

        int rowsInserted = 0;

        try {
            pr = this.connection.prepareStatement(sqlQueryDeleteProgram);
            pr.setString(1, personNr);
            pr.setString(2, programName);

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

    public ArrayList<ProgramData> getPrograms(String personNr) throws SQLException {
        ArrayList<ProgramData> programList = new ArrayList<ProgramData>();
        PreparedStatement pr = null;
        ResultSet rs = null;
        String sqlQueryPreparedStatement = "SELECT programName, information, dateOfCreation " +
                "FROM program " +
                "WHERE personNr=?";

        try {
            pr = this.connection.prepareStatement(sqlQueryPreparedStatement);
            pr.setString(1, personNr);

            // the result of the query is returned in an tabular format
            rs = pr.executeQuery();

            // if (rs.next()) {
            while (rs.next()) {
                ProgramData programData = new ProgramData(rs.getString(1), rs.getString(2), rs.getString(3));
                programList.add(programData);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            assert pr != null;
            pr.close();
            assert rs != null;
            rs.close();
        }
        return programList;
    }

    public int newProgram(String personNr, @NotNull ObservableList<ExerciseData> listOfExercises, String programName) throws SQLException {
    	PreparedStatement pr = null;
        ResultSet rs = null;
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd, HH:mm").format(new Date());
        String exercises = "";
        StringBuilder stringBuilder = new StringBuilder();

        for (ExerciseData exerciseName : listOfExercises) {
            exercises = (stringBuilder).append(exerciseName.getExerciseName()).append("; ").toString();
        }

        String sqlQueryPreparedStatement = "INSERT INTO program (personNr, information, dateOfCreation, active, programName)\n" +
                "VALUES (?, ?, ?, ?, ?);";

        int rowsInserted = 0;

        try {
            pr = this.connection.prepareStatement(sqlQueryPreparedStatement);
            pr.setString(1, personNr);
            pr.setString(2, exercises);
            pr.setString(3, timeStamp);
            pr.setBoolean(4, true);
            pr.setString(5, programName);

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

    public int updateProgram(String personNr, String programName, String exercises) throws SQLException {
    	PreparedStatement pr = null;
        ResultSet rs = null;

        String sqlQueryUpdateProgram = "UPDATE program " +
        							" SET information = ? " +
        							" WHERE personNr = ? AND programName = ? ";

        int rowsInserted = 0;

        try {
            pr = this.connection.prepareStatement(sqlQueryUpdateProgram);
            pr.setString(1, exercises);
            pr.setString(2, personNr);
            pr.setString(3, programName);
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
