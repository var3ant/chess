package chess.view;

import chess.Controller;
import chess.Model;
import chess.figure.Figure;
import chess.move.Move;

import java.awt.*;
import java.io.IOException;

public class FieldView extends Component {
    public final int cellSize = 80;
    public final int panelSize = 640;
    //PlayerModel model = new PlayerModel(panelSize / cellSize, this, FigureColor.White);
    Model model = new Model(panelSize / cellSize, this);

    public FieldView() {
        setPreferredSize(new Dimension(panelSize, panelSize));
        setMinimumSize(new Dimension(panelSize, panelSize));
        setMaximumSize(new Dimension(panelSize, panelSize));
        setFocusable(true);
        addMouseListener(new Controller(model, this));
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
            g.setColor(Color.ORANGE);
            g.drawString(model.whoWon().toString() + " win", panelSize / 2, panelSize / 2);
        }
    }


}
