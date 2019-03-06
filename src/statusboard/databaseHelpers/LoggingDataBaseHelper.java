package statusboard.databaseHelpers;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import statusboard.LogObject;

public class LoggingDataBaseHelper {
    private static LoggingDataBaseHelper ldbh = null;
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
        pruneLogs();
    }
    
    public static LoggingDataBaseHelper getInstance() {
         if (ldbh == null) {
             dbh = new DataBaseHelper();
             ldbh = new LoggingDataBaseHelper();
         } 
         return ldbh;
     }
    
    public void openDatabase() {
        dbh = new DataBaseHelper();
        c = dbh.getDatabaseConnection();
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
    public ArrayList<LogObject> getAllEntries() {
        if (c== null) {
            openDatabase();
        }
        ArrayList<LogObject> logs =  new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet result;
            String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + DATETIME + " DESC";
            result = stmt.executeQuery(sql);
            while (result.next()) {
               logs.add(populateLogObject(result));
            }
        } catch ( SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return logs;
    }
    
    private LogObject populateLogObject(ResultSet result) {
        LogObject log = new LogObject();
        try {
                // Read the values from the query into a LogObject
                log.setDatetime(result.getString(DATETIME))
                .setEvent(result.getString(EVENT))
                .setDetail(result.getString(DETAIL));
            } catch ( SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return log;
    }
    
    public void pruneLogs() {
        if (c == null) {
            openDatabase();
        }
        try {
            Statement stmt = c.createStatement();
            stmt.closeOnCompletion();
            stmt.execute("DELETE FROM " + TABLE_NAME + " WHERE " + DATETIME + "< datetime('now', '-30 days');");
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
