package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    private static final String DBSQLITECONN = "jdbc:sqlite:C://Coding//Java//NaprapatJournal//journalDb.db";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(DBSQLITECONN);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
