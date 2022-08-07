package chess.view;

import chess.Controller;
import chess.Model;
import chess.Properties;
import chess.adapter.OfflineMouseAdapter;
import chess.figure.Figure;
import chess.move.Move;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class FieldView extends Component {
    public static final int cellSize = Properties.CELL_SIZE;
    public final int panelSize = Properties.PANEL_SIZE;
    private Model model;

    public FieldView() throws IOException {
        setPreferredSize(new Dimension(panelSize, panelSize));
        setMinimumSize(new Dimension(panelSize, panelSize));
        setMaximumSize(new Dimension(panelSize, panelSize));
        setFocusable(true);
    }


    public void addOfflineListener(OfflineMouseAdapter offlineMouseAdapter) {
        addMouseListener(new Controller(offlineMouseAdapter, this));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        requestFocus();

        int fieldSize = panelSize / cellSize;
        Color light = new Color(124, 76, 62);
        Color dark = new Color(81, 42, 42);

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if ((i % 2 + j % 2) % 2 == 0) {
                    g.setColor(light);
                    g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
                } else {
                    g.setColor(dark);
                    g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
                }
            }
        }

        if (model.getSelectedFigure() != null) {
            for (Move move : model.getSelectedFigure().getMovesNotInCheck(model.field)) {
                g.setColor(move.getColor());
                g.fillRect(move.x * cellSize, move.y * cellSize, cellSize, cellSize);
            }
        }

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                Figure figure = model.field.getNullable(i, j);
                if (figure != null) {
                    try {
                        g.drawImage(figure.getImage(), i * cellSize, j * cellSize, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (model.whoWon() != null) {
            g.setColor(Color.WHITE);
            g.fillRect(panelSize / 2, panelSize / 2 - 10, 52, 10);
            g.setColor(Color.BLUE);
            g.drawString(model.whoWon().toString() + " win", panelSize / 2, panelSize / 2);
        }
    }


    public void setModel(Model model) {
        this.model = model;

    }

    public Figure selectFigure(List<Figure> figures) {
        try {
            PromotionDialog dialog = new PromotionDialog(View.getRef(), figures);//FIXME:getRef
            dialog.setVisible(true);
            return dialog.getSelectedFigure();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;//FIXME:!!!
    }
}
