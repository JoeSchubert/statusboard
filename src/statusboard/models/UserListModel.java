package statusboard.models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import statusboard.CrewMemberObject;
import statusboard.databaseHelpers.RosterDataBaseHelper;

public class UserListModel extends AbstractTableModel {
    private final RosterDataBaseHelper db;
    private List<CrewMemberObject> rows = new ArrayList<>();
    
    public UserListModel() {
        db = new RosterDataBaseHelper();
        rows = db.getAllMembers();
    }
    
    public void refreshUserListModel() {
        rows.clear();
        rows = db.getAllMembers();
        fireTableDataChanged();
    }
       
    public CrewMemberObject getMemberAtPos(int pos) {
        return rows.get(pos);
    }
    
    public void add(CrewMemberObject cm) {
        int rowCount = getRowCount();
        this.rows.add(cm);
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
        CrewMemberObject crew = this.rows.get(rowindex);
        switch (columnindex) {
            case 0: return crew.getRank();
            case 1: return crew.getFirstName();
            case 2: return crew.getLastName();
            
        }
        return null;
    }

}