package statusboard.databaseHelpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import statusboard.Constants;

public class DataBaseHelper {

    private static Connection c = null;
    static Constants con = new Constants();
    static int existingVersion = -1;
    private static final int CURRENT_VERSION = 1;

    public DataBaseHelper() {
        if (c == null) {
            c = openDatabase();
        }
    }
    
    public Connection getDatabaseConnection() {
        return c;
    }

    private static Connection openDatabase() {
        Connection t = null;
        try {
            Class.forName("org.sqlite.JDBC");
            t = DriverManager.getConnection(con.getDatabaseName());
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return t;
    }

    public int getDatabaseVersion() {
        // only call the SQL query if we have not already populated the version.
        if (existingVersion == -1) {
            if (c == null) {
                openDatabase();
            }
            try {
                Statement stmt = c.createStatement();
                stmt.closeOnCompletion();
                ResultSet result;
                String sql = "PRAGMA user_version;";
                result = stmt.executeQuery(sql);
                while (result.next()) {
                    existingVersion = result.getInt(1);
                }
                stmt.close();
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        setDatabaseVersion();
        return existingVersion;
    }

    public void setDatabaseVersion() {
        if (c == null) {
            openDatabase();
        }
        try {
            Statement stmt = c.createStatement();
            stmt.closeOnCompletion();
            stmt.execute("PRAGMA user_version = " + CURRENT_VERSION);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

}
