package chess.view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class View extends JPanel {
    public static JFrame frame;
    public View(FieldView fieldView, JFrame frame) {
        View.frame = frame;
        setLayout(new GridBagLayout());
        setFocusable(false);
        var c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        add(fieldView, c);
//        add(new HudView(), c);
    }

    public static JFrame getRef() {
        return frame;
    }
}
