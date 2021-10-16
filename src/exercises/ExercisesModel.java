package exercises;

import dbUtil.dbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExercisesModel {
    Connection connection;
    private String sqlQueryBodyPart = "SELECT DISTINCT bodyPart FROM exercise";
    private String sqlQueryType = "SELECT DISTINCT type FROM exercise";
    private String sqlQueryAllExercises = "SELECT exerciseName, bodyPart, type, description FROM exercise";

    public ExercisesModel() {
        try {
            this.connection = dbConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if(this.connection == null) {
            System.exit(1);
        }
    }

    public ResultSet getAllExercises() throws SQLException {
        return connection.createStatement().executeQuery(sqlQueryAllExercises);
    }

    public List<String> getBodyPartData() {
        List<String> options = new ArrayList<>();

        try {
            ResultSet rs = connection.createStatement().executeQuery(sqlQueryBodyPart);

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

    public List<String> getTypeData() {
        List<String> options = new ArrayList<>();

        try {
            ResultSet rs = connection.createStatement().executeQuery(sqlQueryType);

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
}
