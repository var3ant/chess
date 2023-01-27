package chess.view;

import javax.swing.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class MainView {
    public static JFrame frame = null;

    public static void initFrame() {
        if (frame == null) {
            frame = new JFrame();
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }


    public static void open(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.pack();
        frame.setFocusable(false);
        frame.setVisible(true);
    }
}
