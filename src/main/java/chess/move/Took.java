package chess.move;

import chess.ChessField;
import chess.Coord;
import chess.figure.Figure;

import java.awt.*;

public class Took extends Move {
    private static final Color color = new Color(255, 0, 0, 100);

    @Override
    public Color getColor() {
        return color;
    }

    public Took(int x, int y) {
        super(x, y);
    }

    public Took(Coord coord) {
        super(coord);
    }

    @Override
    public void privateMove(Figure figure, ChessField field) {
        field.remove(figure.getX(), figure.getY());
        if (field.set(x, y, figure) == null) {
            throw new Error("attempt to take piece by coordinate (" + x + ", " + y + ")" + " where there is no piece");//ASSERT
        }
    }


}
