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

    public boolean isUserLoggedIn(String user, String password) throws Exception {
        PreparedStatement pr = null;
        ResultSet rs = null;
        String sqlQueryPreparedStatement = "SELECT * FROM login where loginName=? and password=?";

        try {
            pr = this.connection.prepareStatement(sqlQueryPreparedStatement);
            pr.setString(1, user);
            pr.setString(2, password);

            // the result of the query is returned in an tabular format
            rs = pr.executeQuery();

            // next() returns true if the resultSet contains more rows
            return rs.next();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        finally {
            assert pr != null;
            pr.close();
            assert rs != null;
            rs.close();
        }
    }

    public boolean isAdmin(String user) throws Exception {
        PreparedStatement pr = null;
        ResultSet rs = null;
        String sqlQueryPreparedStatement = "SELECT * FROM login where loginName=? and role='Admin'";

        try {
            pr = this.connection.prepareStatement(sqlQueryPreparedStatement);
            pr.setString(1, user);

            rs = pr.executeQuery();
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
        }
    }
}
