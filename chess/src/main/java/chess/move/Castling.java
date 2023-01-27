package chess.move;

import chess.field.ChessField;
import chess.figure.Figure;
import chess.figure.Rook;

import java.awt.*;

public class Castling extends Move {
    private final int direction;
    private final Position rookPosition;

    private static final Color color = new Color(255, 100, 255, 100);

    @Override
    public Color getColor() {
        return color;
    }

    public Castling(int x, int y, int direction, Position rookPosition) {
        super(x, y);
        this.direction = direction;
        this.rookPosition = rookPosition;
    }

    public Castling(Position position, int direction, Position rookPosition) {
        super(position);
        this.direction = direction;
        this.rookPosition = rookPosition;
    }

    @Override
    public void privateMove(Figure figure, ChessField field) {
        field.remove(figure.getX(), figure.getY());

        if (field.set(x, y, figure) != null) {
            throw new Error("attempt to castle by King to the position where is piece is already located");//ASSERT
        }

        Rook rook = (Rook) field.remove(rookPosition.x, rookPosition.y);


        if (field.set(x + direction, y, rook) != null) {
            throw new Error("attempt to castle by Rook to the position where is piece is already located");//ASSERT
        }
    }

}
