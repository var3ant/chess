package chess.dto;

import chess.move.Move;

public class MoveDto {
    public final int figureX;
    public final int figureY;
    public final Move move;

    public MoveDto(int figureX, int figureY, Move move) {
        this.figureX = figureX;
        this.figureY = figureY;
        this.move = move;
    }
}
