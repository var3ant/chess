package chess.move;

import chess.field.ChessField;
import chess.figure.Figure;

import java.awt.*;

public class Step extends Move {
    private static final Color color = new Color(255, 255, 255, 100);

    @Override
    public Color getColor() {
        return color;
    }

    public Step(int x, int y) {
        super(x, y);
    }

    public Step(Position position) {
        super(position);
    }

    @Override
    public void privateMove(Figure figure, ChessField field) {
        field.remove(figure.getX(), figure.getY());

        if (field.set(x, y, figure) != null) {
            throw new Error("attempt to step to the position where is piece is already located");//ASSERT
        }
    }

}
