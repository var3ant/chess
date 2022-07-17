package chess.move;

import chess.ChessField;
import chess.figure.Figure;

public class MoveBackup {
    private Figure figureOnObj;
    private Figure figureWhoMove;

    public MoveBackup(Figure figureOnObj, Figure figureWhoMove) {
        this.figureOnObj = figureOnObj;
        this.figureWhoMove = figureWhoMove;
    }

    public void load(Figure figure, ChessField field, Move move) {
        figure.reInit(figureWhoMove);//TODO: NEED TO COPY EVERYTHING NOT ONLY POSITION!!!
        field.set(move.x, move.y, figureOnObj);
        field.set(figureWhoMove.getX(), figureWhoMove.getY(), figureWhoMove);
    }
}
