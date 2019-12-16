package statusboard.panels;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import statusboard.CrewMemberObject;
import statusboard.StatusBoard;
import static statusboard.StatusBoardFrame.backgroundColor;
import static statusboard.StatusBoardFrame.foregroundColor;
import statusboard.TableColumnAdjuster;
import statusboard.databaseHelpers.RosterDataBaseHelper;
import statusboard.models.UserListModel;

public class UserList extends javax.swing.JPanel {

    private final JDialog jdg;
    private final UserListModel userListModel;
    private final JDialog aemDialog = new JDialog();
    private final RosterDataBaseHelper db = RosterDataBaseHelper.getInstance();
    String barcode;

    public UserList(JDialog jdiag, String updateBarcode) {
        jdg = jdiag;
        barcode = updateBarcode;
        userListModel = new statusboard.models.UserListModel();
        initComponents();
        userListTable.setAutoResizeMode(0);
        userListTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(userListTable, false);
        tca.adjustColumns();
        jdg.pack();
        if (barcode.equals("0")) {
            editButton.setVisible(true);
            deleteButton.setVisible(true);
            selectButton.setVisible(false);
            jdg.setTitle("");
        } else {
            editButton.setVisible(false);
            deleteButton.setVisible(false);
            selectButton.setVisible(true);
            jdg.setTitle("Select User to Update");
        }
        jdg.setLocationRelativeTo(null);
        setupColors();
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
        userListTable = new javax.swing.JTable();
        deleteButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        selectButton = new javax.swing.JButton();

        userListTable.setModel(userListModel);
        userListTable.setOpaque(false);
        userListTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        userListTable.setShowVerticalLines(false);
        userListTable.setTableHeader(null);
        userListTable.setUpdateSelectionOnSort(false);
        jScrollPane1.setViewportView(userListTable);

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        editButton.setText("Edit");
        editButton.setToolTipText("");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        selectButton.setText("Select");
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(editButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteButton)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editButton)
                    .addComponent(deleteButton)
                    .addComponent(selectButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        if (userListTable.getSelectedRow() != -1) {
            AddEditMember aem = new AddEditMember(aemDialog, userListModel.getMemberAtPos(userListTable.getSelectedRow()));
            aemDialog.setAutoRequestFocus(true);
            aemDialog.setSize(200, 350);
            aemDialog.setResizable(false);
            aemDialog.add(aem);
            aemDialog.pack();
            aemDialog.setLocationRelativeTo(null);
            aemDialog.setVisible(true);
            aemDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    userListModel.refreshUserListModel();
                    aemDialog.getContentPane().removeAll();
                }
            });
        }
    }//GEN-LAST:event_editButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        db.deleteRow(userListModel.getMemberAtPos(userListTable.getSelectedRow()));
        StatusBoard.sbf.refreshDeptTable(userListModel.getMemberAtPos(userListTable.getSelectedRow()).getDepartment());
        userListModel.refreshUserListModel();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectButtonActionPerformed
        if (userListTable.getSelectedRow() != -1) {
            CrewMemberObject cmo = userListModel.getMemberAtPos(userListTable.getSelectedRow());
            db.insertRow(cmo.getId(), cmo.getPayGrade(), cmo.getRank(), cmo.getFirstName(), cmo.getLastName(), cmo.getDepartment(), barcode, true);
            String[] options = {"OK"};
            int n = JOptionPane.showOptionDialog(this,
                   "User has been updated. ","Update Complete",
                   JOptionPane.PLAIN_MESSAGE,
                   JOptionPane.QUESTION_MESSAGE,
                   null,
                   options,
                   options[0]);
            
            if(n == 0) {
                // Trigger the window closing event so that the listener in StatusBoardFrame proper resets the scannerDisabled variable.
                jdg.dispatchEvent(new WindowEvent((Window)jdg, WindowEvent.WINDOW_CLOSING));
            }

        }
    }//GEN-LAST:event_selectButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton selectButton;
    private javax.swing.JTable userListTable;
    // End of variables declaration//GEN-END:variables

    private void setupColors() {
        this.setBackground(backgroundColor);
        jdg.setBackground(backgroundColor);
        jdg.setForeground(foregroundColor);
        aemDialog.setBackground(backgroundColor);
        jPanel1.setBackground(backgroundColor);
        jPanel1.setForeground(foregroundColor);
        deleteButton.setBackground(backgroundColor);
        deleteButton.setForeground(foregroundColor);
        editButton.setBackground(backgroundColor);
        editButton.setForeground(foregroundColor);
        selectButton.setBackground(backgroundColor);
        selectButton.setForeground(foregroundColor);
        jScrollPane1.setBackground(backgroundColor);
        jScrollPane1.setForeground(foregroundColor);
        jScrollPane1.getViewport().setBackground(backgroundColor);
        userListTable.setBackground(backgroundColor);
        userListTable.setForeground(foregroundColor);
        userListTable.setOpaque(false);
    }
}
