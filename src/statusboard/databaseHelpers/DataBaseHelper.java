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
    static int version = -1;

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
        if (version == -1) {
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
                    version = result.getInt(1);
                }
                stmt.close();
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        System.out.println("Returning version: " + version);
        return version;
    }

    public int incrementDatabaseVersion() {
        if (c == null) {
            openDatabase();
        }
        // ensure version has been populated
        if (version == 0) {
            getDatabaseVersion();
        }
        version++;
        System.out.println("new version: " + version);
        try {
            Statement stmt = c.createStatement();
            stmt.closeOnCompletion();
            stmt.execute("PRAGMA user_version = " + version);
            System.out.println("After execute");
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return version;
    }

}
