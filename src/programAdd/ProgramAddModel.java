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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProgramAddModel {
    Connection connection;
    private String sqlQueryExerciseByBodypart = "SELECT * from exercise WHERE bodyPart=?";
    private String sqlQueryExerciseByType = "SELECT exerciseName from exercise WHERE type=?";
    private String sqlQueryExerciseByTtypeAndBodyPart = "SELECT * from exercise WHERE bodyPart=? AND type=?";
    private String sqlQueryExerciseName = "SELECT exerciseName from exercise";
    private String sqlQueryExerciseType = "SELECT DISTINCT type FROM exercise";
    private String sqlQueryDescription = "SELECT description FROM exercise WHERE exerciseName=? ";
    private String sqlQueryExerciseBodyPart = "SELECT DISTINCT bodyPart FROM exercise";

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

        // TODO: move to global level
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

                // remove space after semicolon
                exerciseInformation = exerciseInformation.replace("; ", ";");
                // save semicolon separated string to arrayList
                exerciseSections = exerciseInformation.split(";");
            }
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

    public List<String> getExerciseBodyPartData() {
        List<String> options = new ArrayList<>();

        try {
            Connection conn = dbConnection.getConnection();
            assert conn != null;
            ResultSet rs = conn.createStatement().executeQuery(sqlQueryExerciseBodyPart);

            while (rs.next()) {
                options.add(rs.getString("bodyPart"));
            }

            rs.close();

            return options;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String getExerciseDescription(String exercName) {
        String description = "";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Connection conn = dbConnection.getConnection();
            assert conn != null;

            preparedStatement = conn.prepareStatement(sqlQueryDescription);
            preparedStatement.setString(1, exercName);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                description = resultSet.getString("description");
            }

            resultSet.close();

            return description;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> getExerciseTypeData() {
        List<String> options = new ArrayList<>();

        try {
            Connection conn = dbConnection.getConnection();
            assert conn != null;
            ResultSet rs = conn.createStatement().executeQuery(sqlQueryExerciseType);

            while (rs.next()) {
                options.add(rs.getString("type"));
            }

            rs.close();

            return options;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> getExerciseData() {
        List<String> options = new ArrayList<>();

        try {
            Connection conn = dbConnection.getConnection();
            assert conn != null;
            ResultSet rs = conn.createStatement().executeQuery(sqlQueryExerciseName);

            while (rs.next()) {
                options.add(rs.getString("exerciseName"));
            }

            rs.close();

            return options;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> getExerciseDataTwoSelections(String queryFirst, String querySecond) {
        List<String> options = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection conn = null;

        try {
            conn = dbConnection.getConnection();
            assert conn != null;

            preparedStatement = conn.prepareStatement(sqlQueryExerciseByTtypeAndBodyPart);
            preparedStatement.setString(1, queryFirst);
            preparedStatement.setString(2, querySecond);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                options.add(resultSet.getString("exerciseName"));
            }

            resultSet.close();

            return options;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> getExerciseDataOneSelection(String selectionMade, String queryParameter) {
        List<String> options = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection conn = null;

        try {
            conn = dbConnection.getConnection();
            assert conn != null;

            if (selectionMade.equals("exerciseType")) {
                preparedStatement = conn.prepareStatement(sqlQueryExerciseByType);
                preparedStatement.setString(1, queryParameter);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    options.add(resultSet.getString("exerciseName"));
                }

                resultSet.close();
            }
            else if (selectionMade.equals("exerciseBodyPart")) {
                preparedStatement = conn.prepareStatement(sqlQueryExerciseByBodypart);
                preparedStatement.setString(1, queryParameter);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    options.add(resultSet.getString("exerciseName"));
                }

                resultSet.close();
            }

            return options;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
