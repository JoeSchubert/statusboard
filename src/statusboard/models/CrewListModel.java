package statusboard.models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import statusboard.Constants;
import statusboard.CrewMemberObject;
import statusboard.databaseHelpers.RosterDataBaseHelper;

public class CrewListModel extends AbstractTableModel {
    private final RosterDataBaseHelper db;
    private final Constants c = new Constants();
    private List<CrewMemberObject> rows = new ArrayList<>();
    private final String dept;
    
    public CrewListModel(String department) {
        db = RosterDataBaseHelper.getInstance();
        dept = department;
        populateRowsData(department);
    }

    public final void populateRowsData(String department) {
        if (c.isValidDept(department)) {
            this.rows = db.getMembersByDept(department);
        }
    }
    
    public void refreshRows() {
        populateRowsData(this.dept);
        this.fireTableDataChanged();
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
            case 0: return Boolean.class;
            case 1: return String.class;  
            case 2: return String.class;
        }
        return Object.class;
    }
    
    @Override
    public Object getValueAt(int rowindex, int columnindex) {
        CrewMemberObject crew = this.rows.get(rowindex);
        switch (columnindex) {
            case 0: return crew.isStatus();
            case 1: return crew.getRank();
            case 2: return crew.getLastName();
            
        }
        return null;
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
           CrewMemberObject cmo = this.rows.get(rowIndex);
           db.toggleCrewMemberStatusByClick(cmo, cmo.isStatus());
           populateRowsData(cmo.getDepartment());
           fireTableDataChanged();
        }
        return false;
    } 
    
}
