package statusboard.databaseHelpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
    private static final String USERNAME = "USERNAME";
    private static final String EVENT = "EVENT";
    private static final String SOURCE = "SOURCE";
    private static final String DETAIL = "DETAIL";
    // This is the minimum version that is required for schema
    private final int DB_VERSION = DataBaseHelper.CURRENT_VERSION;

    public LoggingDataBaseHelper() {
        c = dbh.getDatabaseConnection();
        if (existsTableLogs() == false) {
            createLogsTable();
        } else {
            int x = dbh.getDatabaseVersion();
            if (x < DB_VERSION) {
                updateTable(x);
            }
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
                String sql = "CREATE TABLE "
                        + TABLE_NAME + " "
                        + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + DATETIME + " TEXT NOT NULL,"
                        + USERNAME + " TEXT NOT NULL DEFAULT '',"
                        + EVENT + " TEXT NOT NULL,"
                        + SOURCE + " TEXT NOT NULL DEFAULT '',"
                        + DETAIL + " TEXT NOT NULL)";
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        return false;
    }

    private boolean existsTableLogs() {
        if (c != null) {
            ResultSet tables = null;
            try {
                DatabaseMetaData dbm = c.getMetaData();
                tables = dbm.getTables(null, null, TABLE_NAME, null);
                return tables.next();
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            } finally {
                try { 
                    if (tables != null) {
                        tables.close();
                    } 
                } catch (Exception e) {
                }
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
                sql = "INSERT INTO " + TABLE_NAME + "(" + DATETIME + "," + EVENT + ", " + DETAIL + ") "
                        + "VALUES(?,?,?)";

                PreparedStatement pstmt = c.prepareStatement(sql);

                pstmt.setString(1, ts);
                pstmt.setString(2, event);
                pstmt.setString(3, detail);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }

    public ArrayList<LogObject> getAllEntries() {
        if (c == null) {
            openDatabase();
        }
        ArrayList<LogObject> logs = new ArrayList<>();
        ResultSet result = null;
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + DATETIME + " DESC";
            result = stmt.executeQuery(sql);
            while (result.next()) {
                logs.add(populateLogObject(result));
            }
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return log;
    }

    public final void pruneLogs() {
        if (c == null) {
            openDatabase();
        }
        try {
            Statement stmt = c.createStatement();
            stmt.closeOnCompletion();
            stmt.execute("DELETE FROM " + TABLE_NAME + " WHERE " + DATETIME + "< datetime('now', '-60 days');"); 
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
        public void  saveLog(File fileName) {
        if (c == null) {
            openDatabase();
        }
        try {
            StringBuilder rowString = new StringBuilder();
            Statement stmt = c.createStatement();
            ResultSet result;
            String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + DATETIME + " DESC";
            result = stmt.executeQuery(sql);
            ResultSetMetaData metadata = result.getMetaData();
            int columnCount = metadata.getColumnCount();
            rowString.append("THIS FILE IS FOUO\n");
            // Start at column 2. There is no need for the ID column to be exported
            for (int i = 2; i <= columnCount; i++) {
                rowString.append(metadata.getColumnName(i)).append(", ");
            }
            rowString.append("\n");
            while (result.next()) {
                //Start at column 2. There is no need for the IDs to be exported
                for (int i = 2; i <= columnCount; i++) {
                    rowString.append("\"").append(result.getString(i)).append("\"").append(", ");
                }
                rowString.append("\n");
            }
            Files.write(Paths.get(fileName.getCanonicalPath()), rowString.toString().getBytes());
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private void updateTable(int v) {
        if (c == null) {
            openDatabase();
        }
        try {
            // Version 2 - Version 1 only had Roster changes
            if (v < 2) {
                Statement stmt = c.createStatement();
                stmt.closeOnCompletion();
                String sql = "ALTER TABLE " + TABLE_NAME + " RENAME TO OLD_LOGS";
                stmt.execute(sql);
                createLogsTable();
                sql = "INSERT INTO " + TABLE_NAME + "(" + DATETIME + ", " + EVENT + ", " + DETAIL + ") SELECT " + DATETIME + ", " + EVENT + ", " + DETAIL + " FROM OLD_LOGS";
                stmt.execute(sql);
                //Hacky - dropt the OLD_LOGS table during the pruning if it still exists. JDBC kept reporting that the database was locked when trying to do it here.
                stmt.execute("DROP TABLE IF EXISTS OLD_LOGS");
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } 
    }
}
