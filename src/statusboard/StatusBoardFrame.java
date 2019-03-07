package statusboard;

import java.awt.Color;
import java.awt.Component;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.plaf.UIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import statusboard.databaseHelpers.RosterDataBaseHelper;
import statusboard.models.*;
import statusboard.panels.AddEditMember;
import statusboard.panels.UserList;
import statusboard.panels.LogsList;

public final class StatusBoardFrame extends javax.swing.JFrame implements KeyEventDispatcher {

    private static RosterDataBaseHelper DB;
    private final static Constants CON = new Constants();
    private static StringBuilder keyInput = new StringBuilder();
    private AddEditMember aem;
    private UserList userList;
    private LogsList logsList;
    private final JDialog jdg = new JDialog();
    public static Color backgroundColor = UIManager.getColor("Panel.background");
    public static Color foregroundColor = UIManager.getColor(Color.BLACK);
    private static Settings settings;

    private final CrewListModel coModel, xoModel, officerModel, chiefModel, engineeringModel, operationsModel, deckModel, supportModel;

    /**
     * Creates new form StatusBoardFrame
     */
    public StatusBoardFrame() {
        DB = RosterDataBaseHelper.getInstance();
        settings = Settings.getInstance();
        coModel = new CrewListModel(CON.COMMANDING_OFFICER);
        xoModel = new CrewListModel(CON.EXECUTIVE_OFFICER);
        officerModel = new CrewListModel(CON.OFFICERS);
        chiefModel = new CrewListModel(CON.CHIEFS);
        engineeringModel = new CrewListModel(CON.ENGINEERING);
        operationsModel = new CrewListModel(CON.OPERATIONS);
        deckModel = new CrewListModel(CON.DECK);
        supportModel = new CrewListModel(CON.SUPPORT);

        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        setupTablesLAF();
        setupColors();
        String cutterName = getCutterName();
        if (cutterName != null) {
            cutterLabel.setText(cutterName);
            this.setTitle(cutterName);
        }
        setNumberAfloatLabel(DB.getNumberAfloat());
        nightModeToggle.setSelected(settings.getNightMode());

        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            timeLabel.setText(sdf.format(date));
        }, 0, 1, TimeUnit.SECONDS);
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
        commandingOfficerLabel = new javax.swing.JLabel();
        commandingOfficerJScrollPane = new javax.swing.JScrollPane();
        coTable = new javax.swing.JTable();
        executiveOfficerLabel = new javax.swing.JLabel();
        executiveOfficerJScrollPane = new javax.swing.JScrollPane();
        xoTable = new javax.swing.JTable();
        officersLabel = new javax.swing.JLabel();
        officersJScrollPane = new javax.swing.JScrollPane();
        officersTable = new javax.swing.JTable();
        chiefsLabel = new javax.swing.JLabel();
        engineeringJScrollPane = new javax.swing.JScrollPane();
        engineeringTable = new javax.swing.JTable();
        lastScanLabel = new javax.swing.JLabel();
        lastScanNameLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        chiefsJScrollPane = new javax.swing.JScrollPane();
        chiefsTable = new javax.swing.JTable();
        operationsJScrollPane = new javax.swing.JScrollPane();
        operationsTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        deckJScrollPane = new javax.swing.JScrollPane();
        deckTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        supportJScrollPane = new javax.swing.JScrollPane();
        supportTable = new javax.swing.JTable();
        cutterLabel = new javax.swing.JLabel();
        managerUsersButton = new javax.swing.JButton();
        logsButton = new javax.swing.JButton();
        lastScanColorBox = new javax.swing.JLabel();
        lastScanTimeLabel = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        nightModeToggle = new javax.swing.JToggleButton();
        numberAfloat = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        commandingOfficerLabel.setText("Commanding Officer");

        coTable.setAutoCreateRowSorter(true);
        coTable.setModel(coModel);
        coTable.setAutoscrolls(false);
        coTable.setName("coTable"); // NOI18N
        coTable.setOpaque(false);
        coTable.setRowSelectionAllowed(false);
        coTable.setShowHorizontalLines(false);
        coTable.setShowVerticalLines(false);
        coTable.setTableHeader(null);
        coTable.setUpdateSelectionOnSort(false);
        commandingOfficerJScrollPane.setViewportView(coTable);

        executiveOfficerLabel.setText("Executive Officer");

        xoTable.setAutoCreateRowSorter(true);
        xoTable.setModel(xoModel);
        xoTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        xoTable.setAutoscrolls(false);
        xoTable.setName("xoTable"); // NOI18N
        xoTable.setOpaque(false);
        xoTable.setRowSelectionAllowed(false);
        xoTable.setShowHorizontalLines(false);
        xoTable.setShowVerticalLines(false);
        xoTable.setTableHeader(null);
        xoTable.setUpdateSelectionOnSort(false);
        executiveOfficerJScrollPane.setViewportView(xoTable);

        officersLabel.setText("Officers");
        officersLabel.setName("officersLabel"); // NOI18N

        officersTable.setAutoCreateRowSorter(true);
        officersTable.setModel(officerModel);
        officersTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        officersTable.setAutoscrolls(false);
        officersTable.setEditingColumn(0);
        officersTable.setName("officersTable"); // NOI18N
        officersTable.setOpaque(false);
        officersTable.setRowSelectionAllowed(false);
        officersTable.setShowHorizontalLines(false);
        officersTable.setShowVerticalLines(false);
        officersTable.setTableHeader(null);
        officersTable.setUpdateSelectionOnSort(false);
        officersJScrollPane.setViewportView(officersTable);

        chiefsLabel.setText("Chiefs");
        chiefsLabel.setName("chiefsLabel"); // NOI18N

        engineeringTable.setAutoCreateRowSorter(true);
        engineeringTable.setModel(engineeringModel);
        engineeringTable.setAutoscrolls(false);
        engineeringTable.setName("engineeringTable"); // NOI18N
        engineeringTable.setOpaque(false);
        engineeringTable.setRowSelectionAllowed(false);
        engineeringTable.setShowHorizontalLines(false);
        engineeringTable.setShowVerticalLines(false);
        engineeringTable.setTableHeader(null);
        engineeringTable.setUpdateSelectionOnSort(false);
        engineeringJScrollPane.setViewportView(engineeringTable);

        lastScanLabel.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lastScanLabel.setText("Last Scan");
        lastScanLabel.setFocusable(false);
        lastScanLabel.setName("lastScanLabel"); // NOI18N

        lastScanNameLabel.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        lastScanNameLabel.setName("lastScanName"); // NOI18N

        jLabel1.setText("Engineering");

        chiefsTable.setAutoCreateRowSorter(true);
        chiefsTable.setModel(chiefModel);
        chiefsTable.setAutoscrolls(false);
        chiefsTable.setName("chiefsTable"); // NOI18N
        chiefsTable.setOpaque(false);
        chiefsTable.setRowSelectionAllowed(false);
        chiefsTable.setShowHorizontalLines(false);
        chiefsTable.setShowVerticalLines(false);
        chiefsTable.setTableHeader(null);
        chiefsTable.setUpdateSelectionOnSort(false);
        chiefsJScrollPane.setViewportView(chiefsTable);

        operationsTable.setAutoCreateRowSorter(true);
        operationsTable.setModel(operationsModel);
        operationsTable.setAutoscrolls(false);
        operationsTable.setName("chiefsTable"); // NOI18N
        operationsTable.setOpaque(false);
        operationsTable.setRowSelectionAllowed(false);
        operationsTable.setShowHorizontalLines(false);
        operationsTable.setShowVerticalLines(false);
        operationsTable.setTableHeader(null);
        operationsTable.setUpdateSelectionOnSort(false);
        operationsJScrollPane.setViewportView(operationsTable);

        jLabel2.setText("Operations");

        deckTable.setAutoCreateRowSorter(true);
        deckTable.setModel(deckModel);
        deckTable.setAutoscrolls(false);
        deckTable.setName("chiefsTable"); // NOI18N
        deckTable.setOpaque(false);
        deckTable.setRowSelectionAllowed(false);
        deckTable.setShowHorizontalLines(false);
        deckTable.setShowVerticalLines(false);
        deckTable.setTableHeader(null);
        deckTable.setUpdateSelectionOnSort(false);
        deckJScrollPane.setViewportView(deckTable);

        jLabel3.setText("Deck");

        jLabel4.setText("Support");

        supportTable.setAutoCreateRowSorter(true);
        supportTable.setModel(supportModel);
        supportTable.setAutoscrolls(false);
        supportTable.setName("chiefsTable"); // NOI18N
        supportTable.setOpaque(false);
        supportTable.setRowSelectionAllowed(false);
        supportTable.setShowHorizontalLines(false);
        supportTable.setShowVerticalLines(false);
        supportTable.setTableHeader(null);
        supportTable.setUpdateSelectionOnSort(false);
        supportJScrollPane.setViewportView(supportTable);

        cutterLabel.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        cutterLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cutterLabel.setName("cutterLabel"); // NOI18N

        managerUsersButton.setText("Manage Users");
        managerUsersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                managerUsersButtonActionPerformed(evt);
            }
        });

        logsButton.setText("Logs");
        logsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logsButtonActionPerformed(evt);
            }
        });

        lastScanColorBox.setToolTipText("");

        lastScanTimeLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lastScanTimeLabel.setToolTipText("");

        timeLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLabel.setText("Current Time");

        nightModeToggle.setText("Night Mode");
        nightModeToggle.setAlignmentY(0.0F);
        nightModeToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nightModeToggleActionPerformed(evt);
            }
        });

        numberAfloat.setText("On-board: XX");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(officersLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(officersJScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(chiefsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(chiefsJScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lastScanLabel)
                        .addGap(18, 18, 18)
                        .addComponent(lastScanTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lastScanColorBox, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lastScanNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(commandingOfficerJScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(commandingOfficerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(executiveOfficerJScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(executiveOfficerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(engineeringJScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(operationsJScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(deckJScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(supportJScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(managerUsersButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(logsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cutterLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 812, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nightModeToggle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(numberAfloat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(commandingOfficerLabel)
                            .addComponent(executiveOfficerLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(commandingOfficerJScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(executiveOfficerJScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addComponent(cutterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(nightModeToggle)
                        .addGap(18, 18, 18)
                        .addComponent(numberAfloat)))
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(officersLabel)
                            .addComponent(chiefsLabel)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(deckJScrollPane)
                            .addComponent(operationsJScrollPane)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(chiefsJScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                                    .addComponent(officersJScrollPane))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lastScanLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lastScanTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lastScanNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(lastScanColorBox, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15))))
                            .addComponent(supportJScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(engineeringJScrollPane)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(logsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(managerUsersButton)
                        .addGap(18, 18, 18)
                        .addComponent(timeLabel)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void managerUsersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_managerUsersButtonActionPerformed
        displayUserList();
    }//GEN-LAST:event_managerUsersButtonActionPerformed

    private void nightModeToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nightModeToggleActionPerformed
        settings.setNightMode(nightModeToggle.isSelected());
        setupColors();
    }//GEN-LAST:event_nightModeToggleActionPerformed

    private void logsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logsButtonActionPerformed
        displayLogList();
    }//GEN-LAST:event_logsButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane chiefsJScrollPane;
    private javax.swing.JLabel chiefsLabel;
    private javax.swing.JTable chiefsTable;
    private javax.swing.JTable coTable;
    private javax.swing.JScrollPane commandingOfficerJScrollPane;
    private javax.swing.JLabel commandingOfficerLabel;
    private javax.swing.JLabel cutterLabel;
    private javax.swing.JScrollPane deckJScrollPane;
    private javax.swing.JTable deckTable;
    private javax.swing.JScrollPane engineeringJScrollPane;
    private javax.swing.JTable engineeringTable;
    private javax.swing.JScrollPane executiveOfficerJScrollPane;
    private javax.swing.JLabel executiveOfficerLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lastScanColorBox;
    private javax.swing.JLabel lastScanLabel;
    private javax.swing.JLabel lastScanNameLabel;
    private javax.swing.JLabel lastScanTimeLabel;
    private javax.swing.JButton logsButton;
    private javax.swing.JButton managerUsersButton;
    private javax.swing.JToggleButton nightModeToggle;
    private javax.swing.JLabel numberAfloat;
    private javax.swing.JScrollPane officersJScrollPane;
    private javax.swing.JLabel officersLabel;
    private javax.swing.JTable officersTable;
    private javax.swing.JScrollPane operationsJScrollPane;
    private javax.swing.JTable operationsTable;
    private javax.swing.JScrollPane supportJScrollPane;
    private javax.swing.JTable supportTable;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JTable xoTable;
    // End of variables declaration//GEN-END:variables

    private void setupTablesLAF() {
        for (int i = 0; i < 8; i++) {
            JTable temp = getTable(i);
            temp.setAutoResizeMode(0);
            temp.setOpaque(false);
            temp.setShowGrid(false);
            temp.setDefaultRenderer(Boolean.class, new BooleanRenderer());
            temp.setDefaultRenderer(String.class, new StringRenderer());
            ((DefaultTableCellRenderer) temp.getDefaultRenderer(Object.class)).setOpaque(false);

            DefaultTableColumnModel colModel = (DefaultTableColumnModel) temp.getColumnModel();
            TableColumn col;
            col = colModel.getColumn(0);
            col.setPreferredWidth(0);
            col.setWidth(0);

            col = colModel.getColumn(1);
            col.setPreferredWidth(40);
            col.setWidth(40);

            col = colModel.getColumn(2);
            col.setPreferredWidth(120);
            col.setWidth(120);
            col.sizeWidthToFit();
        }
    }

    private void setupColors() {
        if (settings.getNightMode()) {
            backgroundColor = Color.BLACK;
            foregroundColor = Color.WHITE;
        } else {
            backgroundColor = UIManager.getColor("Panel.background");
            foregroundColor = Color.BLACK;
        }

        this.getContentPane().setBackground(backgroundColor);
        jPanel1.setBackground(backgroundColor);

        for (Component comp : jPanel1.getComponents()) {
            if (comp instanceof JScrollPane) {
                ((JScrollPane) comp).getViewport().setBackground(backgroundColor);
            } else if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(foregroundColor);
            } else if (comp instanceof JButton) {
                ((JButton) comp).setBackground(backgroundColor);
                ((JButton) comp).setForeground(foregroundColor);
            } else if (comp instanceof JToggleButton) {
                ((JToggleButton) comp).setBackground(backgroundColor);
                ((JToggleButton) comp).setForeground(foregroundColor);
            }
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        // Don't capture teh keys if the add/edit user dialog is displayed
        if (jdg.isVisible()) {
            return false;
        }
        switch (e.getID()) {
            case KeyEvent.KEY_PRESSED:
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (keyInput.toString().matches("[A-Za-z0-9]+") && keyInput.length() == 18) {
                        // Alphanumeric string that is 18 characters long, assume it's a barcode and process it
                        CrewMemberObject cm = DB.getMemberByBarcode(keyInput.toString());
                        if (cm.getId() != 0) {
                            // a non-zero value indicates that the CrewMemberObject had the id and status filled by the db call.
                            DB.toggleCrewMemberStatusByScanner(cm, cm.isStatus());
                            refreshDeptTable(cm.getDepartment());
                            lastScanNameLabel.setText(cm.getRank() + " " + cm.getLastName());
                            Date date = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                            lastScanTimeLabel.setText(" @ " + sdf.format(date));
                            // Reverse logic on the cm.isStatus() as it is being toggled above
                            if (cm.isStatus()) {
                                lastScanColorBox.setBackground(Color.RED);
                                lastScanColorBox.setOpaque(true);
                            } else {
                                lastScanColorBox.setBackground(Color.GREEN);
                                lastScanColorBox.setOpaque(true);
                            }
                        } else {
                            displayAddEditUser();
                            aem.setBarcodeText(keyInput.toString());
                        }
                    }
                    keyInput = new StringBuilder();
                } else if ((e.getKeyCode() >= 48 && e.getKeyCode() <= 57) || (e.getKeyCode() >= 65 && e.getKeyCode() <= 90) || (e.getKeyCode() >= 97 && e.getKeyCode() <= 122)) {
                    //only store alphanumeric values, anything else is jibberish
                    keyInput.append(e.getKeyChar());
                }
                e.consume();
                return true;
            default:
                return false;
        }
    }

    private void displayAddEditUser() {
        aem = new AddEditMember(jdg);
        jdg.setAutoRequestFocus(true);
        jdg.setSize(300, 400);
        jdg.setResizable(false);
        jdg.add(aem);
        jdg.pack();
        jdg.setLocationRelativeTo(null);
        jdg.setVisible(true);
        jdg.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                jdg.dispose();
            }
        });
    }

    private void displayUserList() {
        if (jdg.isVisible()) {
            jdg.getContentPane().removeAll();
        }
        userList = new UserList(jdg);
        jdg.setAutoRequestFocus(true);
        jdg.setSize(280, 500);
        jdg.setResizable(false);
        jdg.add(userList);
        jdg.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                jdg.getContentPane().removeAll();
                jdg.dispose();
            }
        });
        jdg.setLocationRelativeTo(null);
        jdg.setVisible(true);
    }

    private void displayLogList() {
        if (jdg.isVisible()) {
            jdg.getContentPane().removeAll();
        }
        logsList = new LogsList(jdg);
        jdg.setAutoRequestFocus(true);
        jdg.setResizable(true);
        jdg.add(logsList);
        jdg.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                jdg.getContentPane().removeAll();
                jdg.dispose();
            }
        });
        jdg.pack();
        jdg.setLocationRelativeTo(null);
        jdg.setVisible(true);
    }

    public void refreshDeptTable(String dept) {
        getModel(dept).refreshRows();
        getModel(dept).fireTableDataChanged();
    }

    private String getCutterName() {
        String cutterName = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("cutter name.txt"));
            cutterName = br.readLine();
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return cutterName;
    }

    private CrewListModel getModel(String dept) {
        if (dept.equals(CON.getDepartmentByPos(0))) {
            return coModel;
        } else if (dept.equals(CON.getDepartmentByPos(1))) {
            return xoModel;
        } else if (dept.equals(CON.getDepartmentByPos(2))) {
            return officerModel;
        } else if (dept.equals(CON.getDepartmentByPos(3))) {
            return chiefModel;
        } else if (dept.equals(CON.getDepartmentByPos(4))) {
            return engineeringModel;
        } else if (dept.equals(CON.getDepartmentByPos(5))) {
            return operationsModel;
        } else if (dept.equals(CON.getDepartmentByPos(6))) {
            return deckModel;
        } else if (dept.equals(CON.getDepartmentByPos(7))) {
            return supportModel;
        }
        return null;
    }

    private JTable getTable(int pos) {
        switch (pos) {
            case 0:
                return coTable;
            case 1:
                return xoTable;
            case 2:
                return officersTable;
            case 3:
                return chiefsTable;
            case 4:
                return engineeringTable;
            case 5:
                return operationsTable;
            case 6:
                return deckTable;
            case 7:
                return supportTable;
            default:
                return null;
        }
    }

    public void setNumberAfloatLabel(String text) {
        numberAfloat.setText(text);
    }

    public static class BooleanRenderer extends JLabel implements TableCellRenderer, UIResource {

        public BooleanRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Boolean boolValue = (Boolean) value;
            if (boolValue == null) {
                setBackground(null);
            } else {
                if (!boolValue) {
                    this.setBackground(Color.RED);
                } else {
                    this.setBackground(Color.GREEN);
                }
            }
            CrewListModel c = (CrewListModel) table.getModel();
            this.setToolTipText(c.getLastScan(row));
            return this;
        }
    }

    public static class StringRenderer extends DefaultTableCellRenderer {

        public StringRenderer() {
            setOpaque(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            c.setForeground(foregroundColor);
            return c;
        }
    }

}
