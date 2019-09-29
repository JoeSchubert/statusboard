package statusboard;

import java.awt.KeyboardFocusManager;

public class StatusBoard {

    public static StatusBoardFrame sbf;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        sbf = new StatusBoardFrame();
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(sbf);

        java.awt.EventQueue.invokeLater(() -> {
            sbf.setSize(1024,768);
            sbf.pack();
            sbf.setLocationRelativeTo(null);
            sbf.setVisible(true);
        });

    }

}
