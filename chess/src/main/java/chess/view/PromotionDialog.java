package chess.view;

import chess.figure.Figure;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class PromotionDialog extends JDialog {
    private Figure selectedFigure = null;

    public PromotionDialog(JFrame frame, List<Figure> figures) throws IOException {
        super(frame, "Choose piece", true);
        JPanel panel = new JPanel();
        for (var figure : figures) {
            JButton button = new JButton();
            button.setIcon(new ImageIcon(figure.getImage()));
            panel.add(button);
            button.addActionListener(e -> {
                        selectedFigure = figure;
                        this.dispose();
                    }
            );
        }
        this.add(panel);
        this.pack();
    }

    public Figure getSelectedFigure() {
        return selectedFigure;
    }
}
