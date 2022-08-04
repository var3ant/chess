package chess.move;

import chess.ChessField;
import chess.Coord;
import chess.figure.Figure;

import java.awt.*;

public abstract class Move {
    public final int x;
    public final int y;

    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Move(Coord coord) {
        this(coord.x, coord.y);
    }


    abstract public void privateMove(Figure figure, ChessField field);

    public void move(Figure figure, ChessField field) {
        privateMove(figure, field);
        figure.doMove();
    }

    public abstract Color getColor();

}
