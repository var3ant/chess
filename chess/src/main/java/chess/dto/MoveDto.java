package chess.dto;

import chess.move.Move;

import java.io.Serializable;

public class MoveDto implements Serializable {
    public final int figureX;
    public final int figureY;
    public final Move move;

    public MoveDto(int figureX, int figureY, Move move) {
        this.figureX = figureX;
        this.figureY = figureY;
        this.move = move;
    }

    @Override
    public String toString() {
        return "(" + figureX + ", " + figureY + "): " + move.toString();
    }
}
