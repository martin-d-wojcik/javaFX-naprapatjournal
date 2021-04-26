package journal;

import dbUtil.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public ResultSet getJournals(String personNr) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs = null;
        String sqlQueryPreparedStatement = "SELECT notes.information, notes.dateOfCreation\n" +
                "FROM notes\n" +
                "INNER JOIN customer ON notes.personNr=customer.personNr\n" +
                "WHERE customer.personNr=?";

        try {
            pr = this.connection.prepareStatement(sqlQueryPreparedStatement);
            pr.setString(1, personNr);

            // the result of the query is returned in an tabular format
            rs = pr.executeQuery();

            // move the cursor to the last row
            // rs.last();
            // get the index of the last row
            // numberOdRows = rs.getRow();
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
        return rs;
    }
}
