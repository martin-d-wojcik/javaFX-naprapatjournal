package journal;

import dbUtil.dbConnection;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        // String resultStringToReturn = "";
        String sqlQueryPreparedStatement = "SELECT information, dateOfCreation " +
                "FROM notes " +
                "WHERE personNr=?";

        try {
            pr = this.connection.prepareStatement(sqlQueryPreparedStatement);
            pr.setString(1, personNr);

            // the result of the query is returned in an tabular format
            rs = pr.executeQuery();

            if (rs.next()) {
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
}
