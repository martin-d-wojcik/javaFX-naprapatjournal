package programAdd;

import dbUtil.dbConnection;
import exercises.ExerciseData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    public int addNewProgramToDb(String personNr, String listOfExercises, String programName) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs = null;
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd, HH:mm").format(new Date());

        String sqlQuerySaveProgram = "INSERT INTO program (personNr, information, programName, dateOfCreation, active) VALUES " +
                "(?, ?, ?, ?, ?)";

        int rowsInserted = 0;

        try {
            pr = this.connection.prepareStatement(sqlQuerySaveProgram);
            pr.setString(1, personNr);
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


    public ObservableList<ExerciseData> getExercisesAlreadySaved(String personNr, String programName) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs = null;
        String sqlQueryGetExercisesForPatient = "SELECT information FROM program WHERE personNr=? AND programName=?";
        String[] exerciseSections = null;
        ObservableList<ExerciseData> data = FXCollections.observableArrayList();

        try {
            pr = this.connection.prepareStatement(sqlQueryGetExercisesForPatient);
            pr.setString(1, personNr);
            pr.setString(2, programName);

            rs = pr.executeQuery();

            while (rs.next()) {
                String exerciseInformation = rs.getString(1);

                /*
                 * 2021-10-08
                 * remove space after semicolon and save in arrayList
                 * 1. in one raw:
                 * exerciseSections = exerciseInformation.replace("; ", ";").split(";");
                 *
                 * 2. or two rows:
                 */
                // remove space after semicolon
                exerciseInformation = exerciseInformation.replace("; ", ";");
                // save semicolon separated string to arrayList
                exerciseSections = exerciseInformation.split(";");
            }

            // TODO: delete last empty entry in exerciseSections
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            assert pr != null;
            pr.close();
        }

        // insert strings into a ObservableList with ProgramData objects
        assert exerciseSections != null;

        for (String exercise : exerciseSections) {
            data.add(new ExerciseData(exercise, "", "", ""));
        }

        return data;
    }
}
