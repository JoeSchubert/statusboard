package statusboard.databaseHelpers;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import statusboard.Constants;
import statusboard.CrewMemberObject;
import statusboard.StatusBoard;

public class RosterDataBaseHelper {

    private static RosterDataBaseHelper rdbh = null;
    static Connection c = null;
    static DataBaseHelper dbh;
    private static LoggingDataBaseHelper logs;
    static Constants con = new Constants();
    // roster schema names. Do not change once database is initialized
    private final String TABLE_NAME = "ROSTER";
    private final String ID = "ID";
    private final String PAYGRADE = "PAYGRADE";
    private final String RANK = "RANK";
    private final String FNAME = "FIRST_NAME";
    private final String LNAME = "LAST_NAME";
    private final String DEPT = "DEPARTMENT";
    private final String BARCODE = "BARCODE";
    private final String STATUS = "STATUS";
    private final String LAST_SCAN = "LAST_SCAN";
    // This is the minimum version that is required for schema
    private final int DB_VERSION = 1;

    public RosterDataBaseHelper() {
        if (existsTableRoster() == false) {
            createRosterTable();
        } else {
            int x = dbh.getDatabaseVersion();
            if (x < DB_VERSION) {
                updateTable(x);
            }
        }
    }

    public static RosterDataBaseHelper getInstance() {
        if (rdbh == null) {
            dbh = new DataBaseHelper();
            rdbh = new RosterDataBaseHelper();
        }
        return rdbh;
    }

    public void openDatabase() {
        dbh = new DataBaseHelper();
        c = dbh.getDatabaseConnection();
        logs = LoggingDataBaseHelper.getInstance();
    }

    private boolean createRosterTable() {
        if (c == null) {
            openDatabase();
        }
        Statement stmt;
        try {
            stmt = c.createStatement();
            String sql = "CREATE TABLE "
                    + TABLE_NAME + " "
                    + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + PAYGRADE + " TEXT NOT NULL,"
                    + RANK + " TEXT NOT NULL, "
                    + FNAME + " TEXT NOT NULL, "
                    + LNAME + " TEXT NOT NULL, "
                    + DEPT + " TEXT NOT NULL, "
                    + BARCODE + " TEXT NOT NULL, "
                    + STATUS + " BOOLEAN NOT NULL,"
                    + LAST_SCAN + " TEXT);";
            stmt.executeUpdate(sql);
            stmt.close();
            populateTestData();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        return true;
    }

    private boolean existsTableRoster() {
        if (c == null) {
            openDatabase();
        }
        try {
            DatabaseMetaData dbm = c.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME, null);
            return tables.next();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        // return false in case the try block fails.
        return false;
    }

    // Inserts new row if one doesn't exist with ID of int passed.
    // If row exists, update with values
    // if id = 0 a new row will be created
    public boolean insertRow(int id, String paygrade, String rank, String fName, String lName, String department, String barcode, Boolean status) {
        if (c == null) {
            openDatabase();
        }
        try {
            Statement stmt = c.createStatement();
            ResultSet result;
            String sql;
            result = stmt.executeQuery("SELECT * FROM roster WHERE " + ID + "=" + id + ";");
            if (result.next()) {
                // update existing row
                logs.logEvent("Edit Member", "Existing information: " + result.getString(PAYGRADE) + ",  " + result.getString(RANK) + ", " + result.getString(FNAME) + ", " + result.getString(LNAME) + ", " + result.getString(DEPT) + ", " + result.getString(BARCODE)
                        + " - New Information: " + paygrade + ", " + rank + ", " + fName + ", " + lName + ", " + department + ", " + barcode);

                sql = "UPDATE " + TABLE_NAME + " SET " + PAYGRADE + " = ? , " + RANK + " = ?, " + FNAME + " = ? , " + LNAME + " = ? ," + DEPT + " = ? , " + BARCODE + " = ? , " + STATUS + " = ?  "
                        + "WHERE ID = " + id;
            } else {
                // insert new row
                logs.logEvent("Add Member", "Information: " + paygrade + ", " + rank + ", " + fName + ", " + lName + ", " + department + ", " + barcode);
                sql = "INSERT INTO " + TABLE_NAME + "(" + PAYGRADE + ", " + RANK + ", " + FNAME + ", " + LNAME + ", " + DEPT + " ," + BARCODE + ", " + STATUS + ") "
                        + "VALUES(?,?,?,?,?,?,?)";
            }
            PreparedStatement pstmt = c.prepareStatement(sql);

            pstmt.setString(1, paygrade);
            pstmt.setString(2, rank);
            pstmt.setString(3, fName);
            pstmt.setString(4, lName);
            pstmt.setString(5, department);
            pstmt.setString(6, barcode);
            pstmt.setBoolean(7, status);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }

    // Deletes a row with specified ID. If it exists and is deleted, returns true. Otherwise, returns false.
    public boolean deleteRow(CrewMemberObject cmo) {
        if (c == null) {
            openDatabase();
        }
        try {
            Statement stmt = c.createStatement();
            ResultSet result;
            String sql;
            result = stmt.executeQuery("SELECT * FROM roster WHERE " + ID + "=" + cmo.getId() + ";");
            if (result.next()) {
                logs.logEvent("Delete Member", "Delete: " + cmo.getRank() + " " + cmo.getFirstName() + " " + cmo.getLastName());
                sql = "DELETE from roster WHERE " + ID + "= " + cmo.getId();
                PreparedStatement pstmt = c.prepareStatement(sql);
                pstmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }

    public boolean toggleCrewMemberStatusByClick(CrewMemberObject cmo, boolean status) {
        String newStatus;
        if (status == true) {
            newStatus = "Ashore";
        } else {
            newStatus = "Afloat";
        }
        logs.logEvent("Status Toggle", "Source: Mouse Click - " + newStatus + " - " + cmo.getRank() + " " + cmo.getFirstName() + " " + cmo.getLastName());
        return toggleCrewMemberStatus(cmo.getId(), status);
    }

    public boolean toggleCrewMemberStatusByScanner(CrewMemberObject cmo, boolean status) {
        String newStatus;
        if (status == true) {
            newStatus = "Ashore";
        } else {
            newStatus = "Afloat";
        }
        logs.logEvent("Status Toggle", "Source: Barcode Scanner - " + newStatus + " - " + cmo.getRank() + " " + cmo.getFirstName() + " " + cmo.getLastName());
        return toggleCrewMemberStatus(cmo.getId(), status);
    }

    public boolean toggleCrewMemberStatus(int id, boolean status) {
        if (c == null) {
            openDatabase();
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String ts = sdf.format(timestamp);
            boolean newStatus = status != true;
            String sql = "UPDATE roster SET " + STATUS + "= ? , " + LAST_SCAN + "=? WHERE " + ID + "= " + id;
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setBoolean(1, newStatus);
            pstmt.setString(2, ts);
            pstmt.executeUpdate();
            StatusBoard.sbf.setNumberAfloatLabel(getNumberAfloat());
            return true;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }

    public ArrayList<CrewMemberObject> getMembersByDept(String department) {
        ArrayList crewMembers = new ArrayList<CrewMemberObject>();
        if (c == null) {
            openDatabase();
        }
        try {
            Statement stmt = c.createStatement();
            ResultSet result;
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + DEPT + " = '" + department + "' ORDER BY " + PAYGRADE + " DESC, " + RANK + ", " + LNAME + " COLLATE NOCASE";
            result = stmt.executeQuery(sql);
            while (result.next()) {
                crewMembers.add(populateCrewMember(result));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return crewMembers;
    }

    public CrewMemberObject getMemberByBarcode(String barcode) {
        if (c == null) {
            openDatabase();
        }
        CrewMemberObject cm = new CrewMemberObject();
        try {
            Statement stmt = c.createStatement();
            ResultSet result;
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + BARCODE + " =  '" + barcode + "'";
            result = stmt.executeQuery(sql);
            while (result.next()) {
                cm = populateCrewMember(result);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return cm;
    }

    public ArrayList<CrewMemberObject> getAllMembers() {
        if (c == null) {
            openDatabase();
        }
        ArrayList<CrewMemberObject> crew = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet result;
            String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + LNAME + " COLLATE NOCASE";
            result = stmt.executeQuery(sql);
            while (result.next()) {
                crew.add(populateCrewMember(result));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return crew;
    }

    private CrewMemberObject populateCrewMember(ResultSet result) {
        CrewMemberObject cm = new CrewMemberObject();
        try {
            // Read the values from the query in a CrewMemberObject
            cm.setId(result.getInt(ID))
                    .setRank(result.getString(RANK))
                    .setPayGrade(result.getString(PAYGRADE))
                    .setFirstName(result.getString(FNAME))
                    .setLastName(result.getString(LNAME))
                    .setDepartment(result.getString(DEPT))
                    .setBarcode(result.getString(BARCODE))
                    .setStatus(result.getBoolean(STATUS))
                    .setLastScan(result.getString(LAST_SCAN));
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return cm;
    }

    public String getNumberAfloat() {
        if (c == null) {
            openDatabase();
        }
        String num = "On-board: 0";
        try {
            Statement stmt = c.createStatement();
            ResultSet result;
            String sql = "SELECT COUNT(*) as count_name from " + TABLE_NAME + " where " + STATUS + " = true;";
            result = stmt.executeQuery(sql);
            while (result.next()) {
                num = "On-board: " + String.format("%02d", result.getInt("count_name"));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return num;
    }

    private void populateTestData() {

        insertRow(0, "05", "CDR", "CO", "Captain", "CommandingOfficer", "abc123def456", Boolean.TRUE);
        insertRow(0, "04", "LCDR", "XO", "XO", "ExecutiveOfficer", "abc123def456", Boolean.TRUE);

        for (int i = 0; i <= 5; i++) {
            insertRow(0, "01", "ENS", "Junior", "Officer" + i, "Officers", "abc123def456", Boolean.TRUE);
        }
        for (int i = 0; i <= 5; i++) {
            insertRow(0, "01", "ETC", "Electronics", "Chief" + i, "Chiefs", "abc123def456", Boolean.TRUE);
        }
        for (int i = 0; i <= 5; i++) {
            insertRow(0, "E6", "MK1", "Test", "Engineer" + i, "Engineering", "abc123def456", Boolean.TRUE);
        }
        for (int i = 0; i <= 5; i++) {
            insertRow(0, "E6", "ET1", "Test", "Operations" + i, "Operations", "abc123def456", Boolean.TRUE);
        }
        for (int i = 0; i <= 5; i++) {
            insertRow(0, "E6", "BM1", "Test", "Deck" + i, "Deck", "abc123def456", Boolean.TRUE);
        }
        for (int i = 0; i <= 5; i++) {
            insertRow(0, "E6", "SK1", "Test", "Support" + i, "Support", "abc123def456", Boolean.TRUE);
        }
    }

    private void updateTable(int v) {
        if (c == null) {
            openDatabase();
        }
        try {
            if (v < 1) {
                Statement stmt = c.createStatement();
                stmt.closeOnCompletion();
                String sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + LAST_SCAN + " TEXT";
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

}
