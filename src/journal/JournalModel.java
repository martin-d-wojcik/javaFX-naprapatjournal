package journal;

import dbUtil.dbConnection;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JournalModel {
    Connection connection;

    public JournalModel() {
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

    public ArrayList<JournalData> getJournals(String personNr) throws SQLException {
        ArrayList<JournalData> journalList = new ArrayList<JournalData>();
        PreparedStatement pr = null;
        ResultSet rs = null;
        String sqlQueryPreparedStatement = "SELECT information, dateOfCreation " +
                "FROM notes " +
                "WHERE personNr=?";

        try {
            pr = this.connection.prepareStatement(sqlQueryPreparedStatement);
            pr.setString(1, personNr);

            // the result of the query is returned in an tabular format
            rs = pr.executeQuery();

            // if (rs.next()) {
            while (rs.next()) {
                JournalData journalData = new JournalData(rs.getString(2), rs.getString(1));
                journalList.add(journalData);
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
        return journalList;
    }

    public JournalData updateJournalNotes(String information, int notesId) throws SQLException {
        JournalData journalData = new JournalData();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlPreparedStatement = "UPDATE notes\n" +
                "SET information=?\n" +
                "WHERE notesId=?;";

        try {
            preparedStatement = this.connection.prepareStatement(sqlPreparedStatement);
            preparedStatement.setString(1, information);
            preparedStatement.setString(2, String.valueOf(notesId));

            // the result of the query is returned in an tabular format
            resultSet = preparedStatement.executeQuery();

            // if (rs.next()) {
            while (resultSet.next()) {
                journalData.setInformation(information);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            assert preparedStatement != null;
            preparedStatement.close();
            assert resultSet != null;
            resultSet.close();
        }
        return journalData;
    }

    public int newJournalNotes(String personNr, String information) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs = null;
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd, HH:mm").format(new Date());

        String sqlQueryPreparedStatement = "INSERT INTO notes (personNr, information, dateOfCreation)\n" +
                "VALUES (?, ?, ?);";

        int rowsInserted = 0;

        try {
            pr = this.connection.prepareStatement(sqlQueryPreparedStatement);
            pr.setString(1, personNr);
            pr.setString(2, information);
            pr.setString(3, timeStamp);

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

    public int updateJournalNotes(String personNr, String information, String date) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs = null;
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd, HH:mm").format(new Date());

        String sqlQueryPreparedStatement = "UPDATE notes SET information = ? " +
                " WHERE personNr = ? AND dateOfCreation =  ?";

        int rowsUpdated = 0;

        try {
            pr = this.connection.prepareStatement(sqlQueryPreparedStatement);
            pr.setString(1, "*** Text ändrad " + timeStamp + " ***\n" + information);
            pr.setString(2, personNr);
            pr.setString(3, date);

            rowsUpdated = pr.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            assert pr != null;
            pr.close();

        }
        return rowsUpdated;
    }
}
