package chess.move;

import chess.field.ChessField;
import chess.figure.Figure;

import java.awt.*;
import java.io.Serializable;

public abstract class Move implements Serializable {
    public final int x;
    public final int y;

    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Move(Position position) {
        this(position.x, position.y);
    }


    abstract public void privateMove(Figure figure, ChessField field);

    public void move(Figure figure, ChessField field) {
        privateMove(figure, field);
        figure.doMove();
    }

    public abstract Color getColor();

}
