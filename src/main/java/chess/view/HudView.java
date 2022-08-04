package chess.view;

import javax.swing.*;

public class HudView extends JPanel {
    public final JTextArea stepsHistory;

    public HudView() {
        stepsHistory = new JTextArea();
        //TODO: slider
        stepsHistory.setRows(39);
        stepsHistory.setColumns(20);
        stepsHistory.setEditable(false);
        add(stepsHistory);
    }
}
