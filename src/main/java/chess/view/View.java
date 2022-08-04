package chess.view;

import javax.swing.*;
import java.awt.*;

public class View extends JPanel {
    public View() {
        setLayout(new GridBagLayout());
        var c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        add(new FieldView(), c);
        add(new HudView(), c);
    }
}
