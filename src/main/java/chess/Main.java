package chess;

import chess.view.FieldView;
import chess.view.View;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new View());
        frame.pack();
        frame.setFocusable(false);
        frame.setVisible(true);
    }
}
