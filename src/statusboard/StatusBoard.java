package statusboard;

import statusboard.databaseHelpers.RosterDataBaseHelper;
import java.awt.KeyboardFocusManager;

public class StatusBoard {
    public static StatusBoardFrame sbf;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RosterDataBaseHelper db = new RosterDataBaseHelper();
        db.openDatabase();
       if (db.existsTableRoster() == false) {
            db.createRosterTable();
        }
        sbf = new StatusBoardFrame();
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(sbf);

        java.awt.EventQueue.invokeLater(() -> {
            sbf.pack();
            sbf.setLocationRelativeTo(null);
            sbf.setVisible(true);
        });
    }
    
}
