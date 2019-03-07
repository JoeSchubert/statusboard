package statusboard.panels;

import javax.swing.JDialog;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import statusboard.Settings;
import static statusboard.StatusBoardFrame.backgroundColor;
import static statusboard.StatusBoardFrame.foregroundColor;
import statusboard.models.LogsListModel;

/**
 *
 * @author joe
 */
public class LogsList extends javax.swing.JPanel {
        private final JDialog jdg;
        private final LogsListModel logsListModel;
        private TableRowSorter<LogsListModel> sorter;

    /**
     * Creates new form UserList
     * @param jdiag
     */
    public LogsList(JDialog jdiag) {
        jdg = jdiag;
        logsListModel = new LogsListModel();
        initComponents();
        logsListTable.setAutoResizeMode(0);
        logsListTable.setOpaque(false);
        sorter = new TableRowSorter<LogsListModel>(logsListModel);
        logsListTable.setRowSorter(sorter);
        DefaultTableColumnModel colModel = (DefaultTableColumnModel) logsListTable.getColumnModel();

         TableColumn col;
         col = colModel.getColumn(0);
         col.setPreferredWidth(120);
         col.setWidth(120);

         col = colModel.getColumn(1);
         col.setPreferredWidth(100);
         col.setWidth(100);

         col = colModel.getColumn(2);
         col.setPreferredWidth(500);
         col.setWidth(500);

         jdg.pack();
         setupColors();
         jdg.setLocationRelativeTo(null);
         jdg.setVisible(true);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        logsListTable = new javax.swing.JTable();
        filterText = new javax.swing.JTextField();
        filterButton = new javax.swing.JButton();
        clearFilterButton = new javax.swing.JButton();

        logsListTable.setModel(logsListModel);
        logsListTable.setColumnSelectionAllowed(true);
        logsListTable.setOpaque(false);
        logsListTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        logsListTable.setShowVerticalLines(false);
        logsListTable.setUpdateSelectionOnSort(false);
        jScrollPane1.setViewportView(logsListTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        filterText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterTextActionPerformed(evt);
            }
        });

        filterButton.setText("Filter");
        filterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterButtonActionPerformed(evt);
            }
        });

        clearFilterButton.setText("Clear Filter");
        clearFilterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearFilterButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(filterText, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(filterButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(clearFilterButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filterText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filterButton)
                    .addComponent(clearFilterButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void filterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterButtonActionPerformed
        filterLogs();
    }//GEN-LAST:event_filterButtonActionPerformed

    private void clearFilterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearFilterButtonActionPerformed
        clearFilter();
    }//GEN-LAST:event_clearFilterButtonActionPerformed

    private void filterTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterTextActionPerformed
        filterLogs();
    }//GEN-LAST:event_filterTextActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearFilterButton;
    private javax.swing.JButton filterButton;
    private javax.swing.JTextField filterText;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable logsListTable;
    // End of variables declaration//GEN-END:variables

    private void filterLogs() {
        RowFilter<LogsListModel, Object> rf = null;
        try {
            rf = RowFilter.regexFilter("(?i)" + filterText.getText());
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }
    
    private void clearFilter() {
        sorter.setRowFilter(null);
        filterText.setText("");
    }

        private void setupColors() {
        this.setBackground(backgroundColor);
        jdg.setBackground(backgroundColor);
        jdg.setForeground(foregroundColor);
        jPanel1.setBackground(backgroundColor);
        jPanel1.setForeground(foregroundColor);
        clearFilterButton.setBackground(backgroundColor);
        clearFilterButton.setForeground(foregroundColor);
        filterButton.setBackground(backgroundColor);
        filterButton.setForeground(foregroundColor);
        jScrollPane1.setBackground(backgroundColor);
        jScrollPane1.setForeground(foregroundColor);
        jScrollPane1.getViewport().setBackground(backgroundColor);
        logsListTable.setBackground(backgroundColor);
        logsListTable.setForeground(foregroundColor);
        jScrollPane1.setBackground(backgroundColor);
        jScrollPane1.setForeground(foregroundColor);
        filterText.setBackground(backgroundColor);
        filterText.setForeground(foregroundColor);
        logsListTable.setOpaque(false);
        logsListTable.getTableHeader().setBackground(backgroundColor);
         logsListTable.getTableHeader().setForeground(foregroundColor);
    }
        
}
