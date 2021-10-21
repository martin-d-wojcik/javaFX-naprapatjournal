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
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private String sqlQueryEmailProgramToPatient = "SELECT email FROM customer WHERE personNr=?";
    private String sqlQueryDeleteProgram = "DELETE FROM program WHERE personNr=? AND programName=?";
    private String sqlQueryGetProgramInfoByPersonNr = "SELECT programName, information, dateOfCreation " +
            "FROM program " +
            "WHERE personNr=?";
    private String sqlQueryUpdateProgram = "UPDATE program " +
            " SET information = ? " +
            " WHERE personNr = ? AND programName = ? ";
    private String sqlQueryNewProgram   = "INSERT INTO program (personNr, information, dateOfCreation, active, programName)\n" +
            "VALUES (?, ?, ?, ?, ?);";

    public ProgramModel() {
        try {
            this.connection = dbConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if(this.connection == null) {
            System.exit(1);
        }
    }

    private void cleanupAfterSqlCall() throws SQLException {
        assert preparedStatement != null;
        preparedStatement.close();
        assert resultSet != null;
        resultSet.close();
    }

    public Boolean sendEmail(String personNr) throws SQLException {
        boolean emailSent = false;

        try {
                preparedStatement = this.connection.prepareStatement(sqlQueryEmailProgramToPatient);
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
        finally {
            cleanupAfterSqlCall();
        }

        return emailSent;
    }

    public int deleteProgramFromDb(String personNr, String programName) throws SQLException {
        int rowsInserted = 0;

        try {
            preparedStatement = this.connection.prepareStatement(sqlQueryDeleteProgram);
            preparedStatement.setString(1, personNr);
            preparedStatement.setString(2, programName);

            rowsInserted = preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            cleanupAfterSqlCall();
        }

        return rowsInserted;
    }

    public ArrayList<ProgramData> getPrograms(String personNr) throws SQLException {
        ArrayList<ProgramData> programList = new ArrayList<ProgramData>();

        try {
            preparedStatement = this.connection.prepareStatement(sqlQueryGetProgramInfoByPersonNr);
            preparedStatement.setString(1, personNr);

            // the result of the query is returned in an tabular format
            resultSet = preparedStatement.executeQuery();

            // if (rs.next()) {
            while (resultSet.next()) {
                ProgramData programData = new ProgramData(resultSet.getString(1),
                        resultSet.getString(2), resultSet.getString(3));
                programList.add(programData);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            cleanupAfterSqlCall();
        }
        return programList;
    }

    public int newProgram(String personNr, @NotNull ObservableList<ExerciseData> listOfExercises, String programName) throws SQLException {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd, HH:mm").format(new Date());
        String exercises = "";
        StringBuilder stringBuilder = new StringBuilder();

        for (ExerciseData exerciseName : listOfExercises) {
            exercises = (stringBuilder).append(exerciseName.getExerciseName()).append("; ").toString();
        }

        int rowsInserted = 0;

        try {
            preparedStatement = this.connection.prepareStatement(sqlQueryNewProgram);
            preparedStatement.setString(1, personNr);
            preparedStatement.setString(2, exercises);
            preparedStatement.setString(3, timeStamp);
            preparedStatement.setBoolean(4, true);
            preparedStatement.setString(5, programName);

            rowsInserted = preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            cleanupAfterSqlCall();
        }

        return rowsInserted;
    }

    public int updateProgram(String personNr, String programName, String exercises) throws SQLException {
        int rowsInserted = 0;

        try {
            preparedStatement = this.connection.prepareStatement(sqlQueryUpdateProgram);
            preparedStatement.setString(1, exercises);
            preparedStatement.setString(2, personNr);
            preparedStatement.setString(3, programName);
            rowsInserted = preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            cleanupAfterSqlCall();
        }

        return rowsInserted;
    }

}
