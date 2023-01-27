package chess.view;

import chess.Model;
import chess.adapter.OfflineMouseAdapter;
import chess.figure.FigureColor;
import chess.playermodel.OfflinePlayerModel;
import chess.playermodel.OnlinePlayerModel;
import chess.playermodel.PlayerModel;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

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
