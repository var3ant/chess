package chess.move;

import chess.ChessField;
import chess.Coord;
import chess.figure.Figure;
import chess.figure.Rook;

import java.awt.*;

public class Castling extends Move {
    private final int direction;
    private final Coord rookCoord;

    private static final Color color = new Color(255, 100, 255, 100);

    @Override
    public Color getColor() {
        return color;
    }

    public Castling(int x, int y, int direction, Coord rookCoord) {
        super(x, y);
        this.direction = direction;
        this.rookCoord = rookCoord;
    }

    public Castling(Coord coord, int direction, Coord rookCoord) {
        super(coord);
        this.direction = direction;
        this.rookCoord = rookCoord;
    }

    @Override
    public void privateMove(Figure figure, ChessField field) {
        field.remove(figure.getX(), figure.getY());

        if (field.set(x, y, figure) != null) {
            throw new Error("");//HARDCODE:
        }

        Rook rook = (Rook) field.remove(rookCoord.x, rookCoord.y);


        if (field.set(x + direction, y, rook) != null) {
            throw new Error("");//HARDCODE:
        }
    }

}
