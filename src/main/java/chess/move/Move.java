package chess.move;

import chess.ChessField;
import chess.Coord;
import chess.figure.Figure;

public class Move {
    public final int x;
    public final int y;
    public final MoveType type;

    public Move(int x, int y, MoveType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public Move(Coord coord, MoveType type) {
        this(coord.x,coord.y, type);
    }
}
