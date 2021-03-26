package login;

import java.sql.*;
import dbUtil.dbConnection;

public class LoginModel {
    Connection connection;

    public LoginModel() {
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

    public boolean isDatabaseConnected() {
        return this.connection != null;
    }

    public boolean isLoggedIn(String user, String password) throws Exception {
        String sqlQuery = "SELECT * FROM login WHERE user='" + user + "' and password='" + password + "'";
        ResultSet rs = null;

        try {
            Connection conn = this.connection;
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlQuery);

            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        finally {
            assert rs != null;
            rs.close();
        }

        // execute the query using PreparedStatement
        /* PreparedStatement pr = null;
        ResultSet rs = null;
        String sqlQueryPreparedStatement = "SELECT * FROM login where user=? and password=? and role=?";

        try {
            pr = this.connection.prepareStatement(sqlQueryPreparedStatement);
            pr.setString(1, user);
            pr.setString(2, password);
            pr.setString(2, option);

            // the result of the query is returned in an tabular format
            rs = pr.executeQuery();

            // next() returns true if the resultSet contains more rows
            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        finally {
            assert pr != null;
            pr.close();
            assert rs != null;
            rs.close();
        } */
    }
}
