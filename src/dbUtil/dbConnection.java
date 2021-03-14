package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    private static final String DBCONNECTION = "jdbc:sqlite:C://Coding//Java//NaprapatJournal//journal.db";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(DBCONNECTION);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
