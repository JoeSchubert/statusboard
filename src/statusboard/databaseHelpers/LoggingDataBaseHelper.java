package statusboard.databaseHelpers;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class LoggingDataBaseHelper {
    private static Connection c = null;
    static DataBaseHelper dbh;
    private static final String TABLE_NAME = "LOGS";
    private static final String ID = "ID";
    private static final String DATETIME = "DATETIME";
    private static final String EVENT = "EVENT";
    private static final String DETAIL = "DETAIL";
    
    public LoggingDataBaseHelper() {
        c = dbh.getDatabaseConnection();
        if (existsTableLogs() == false) {
            createLogsTable();
        }
    }
    
    private boolean createLogsTable() {
        if (c != null) {
            Statement stmt;
            try {
                stmt = c.createStatement();
                String sql = "CREATE TABLE " +
                         TABLE_NAME + " " +
                        "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DATETIME + " TEXT NOT NULL," +
                        EVENT + " TEXT NOT NULL," +
                        DETAIL + " TEXT NOT NULL)";
                stmt.executeUpdate(sql);
                stmt.close();
            } catch ( SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return false;
    }
    
    private boolean existsTableLogs() {
        if (c != null) {
            try {
                DatabaseMetaData dbm = c.getMetaData();
                ResultSet tables = dbm.getTables(null, null, TABLE_NAME, null);
                return tables.next();
            } catch ( SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return false;
    }
    
    public void logEvent(String event, String detail) {
        if (c != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String ts = sdf.format(timestamp);
                String sql;
                sql = "INSERT INTO " + TABLE_NAME + "(" + DATETIME + "," + EVENT + ", " + DETAIL +") "
                    + "VALUES(?,?,?)";

                PreparedStatement pstmt = c.prepareStatement(sql);
                
                pstmt.setString(1, ts);
                pstmt.setString(2, event);
                pstmt.setString(3, detail);

                pstmt.executeUpdate();
            } catch ( SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }
    
}
