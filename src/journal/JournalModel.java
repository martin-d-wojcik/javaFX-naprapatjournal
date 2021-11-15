package journal;

import dbUtil.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JournalModel {
    Connection connection;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String sqlInfoDateFromNotes = "SELECT information, dateOfCreation " +
            "FROM notes " +
            "WHERE personNr=?";
    String sqlUpdateNotesByNotesId = "UPDATE notes\n" +
            "SET information=?\n" +
            "WHERE notesId=?;";
    String sqlQueryInsertNotes = "INSERT INTO notes (personNr, information, dateOfCreation)\n" +
            "VALUES (?, ?, ?);";
    String sqlQueryUpdateNotesByPersonNr = "UPDATE notes SET information = ? " +
            " WHERE personNr = ? AND dateOfCreation =  ?";

    public JournalModel() {
        try {
            this.connection = dbConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if(this.connection == null) {
            System.exit(1);
        }
    }

    public ArrayList<JournalData> getJournals(String personNr) throws SQLException {
        ArrayList<JournalData> journalList = new ArrayList<JournalData>();

        try {
            preparedStatement = this.connection.prepareStatement(sqlInfoDateFromNotes);
            preparedStatement.setString(1, personNr);

            // return the result of the query in an tabular format
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                JournalData journalData = new JournalData(resultSet.getString(2), resultSet.getString(1));
                journalList.add(journalData);
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
        return journalList;
    }

    public JournalData updateJournalNotes(String information, int notesId) throws SQLException {
        JournalData journalData = new JournalData();

        try {
            preparedStatement = this.connection.prepareStatement(sqlUpdateNotesByNotesId);
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
            assert resultSet != null;
            resultSet.close();
        }
        return journalData;
    }

    public int newJournalNotes(String personNr, String information) throws SQLException {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd, HH:mm").format(new Date());

        int rowsInserted = 0;

        try {
            preparedStatement = this.connection.prepareStatement(sqlQueryInsertNotes);
            preparedStatement.setString(1, personNr);
            preparedStatement.setString(2, information);
            preparedStatement.setString(3, timeStamp);

            rowsInserted = preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            assert resultSet != null;
            resultSet.close();
        }

        return rowsInserted;
    }

    public int updateJournalNotes(String personNr, String information, String date) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs = null;
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd, HH:mm").format(new Date());
        int rowsUpdated = 0;

        try {
            pr = this.connection.prepareStatement(sqlQueryUpdateNotesByPersonNr);
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
