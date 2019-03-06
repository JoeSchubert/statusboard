package statusboard.models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import statusboard.LogObject;
import statusboard.databaseHelpers.LoggingDataBaseHelper;

public class LogsListModel extends AbstractTableModel {
    private final LoggingDataBaseHelper db;
    private List<LogObject> rows = new ArrayList<>();
    private String[] headers;
    
    public LogsListModel() {
        db  = LoggingDataBaseHelper.getInstance();
        rows =db.getAllEntries();
        headers = new String[]{ "Time", "Event", "Detail"};
    }

    public void add(LogObject log) {
        int rowCount = getRowCount();
        this.rows.add(log);
        fireTableRowsInserted(rowCount, rowCount);                
    }
    
    @Override
    public int getRowCount() {
        return this.rows.size();
    }
    
    @Override
    public int getColumnCount() {
        return 3;
    }
    
    @Override
    public Class<?> getColumnClass(int columnindex) {
        switch (columnindex) {
            case 0: return String.class;
            case 1: return String.class;  
            case 2: return String.class;
        }
        return Object.class;
    }
    
    @Override
    public Object getValueAt(int rowindex, int columnindex) {
        LogObject log = this.rows.get(rowindex);
        switch (columnindex) {
            case 0: return log.getDatetime();
            case 1: return log.getEvent();
            case 2: return log.getDetail();
        }
        return null;
    }
    
    @Override
    public String getColumnName(int col) {
        return headers[col];
    }

}