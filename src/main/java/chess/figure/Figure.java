package chess.figure;

import chess.ChessField;
import chess.Coord;
import chess.move.Move;
import chess.move.MoveBackup;
import chess.move.MoveType;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public abstract class Figure {
    private int x = -1;
    private int y = -1;
    private FigureColor color;

    public Figure(FigureColor color) {
        this.color = color;
    }

    public Figure(FigureColor color, int x, int y) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public abstract Image getImage() throws IOException;

    public FigureColor getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract List<List<Coord>> getMovingLines(ChessField field);


    public abstract List<Move> getAvailableMoves(ChessField field);

    public Move findMoveIfAvailable(ChessField field, int x, int y) {
        for (Move move : getMovesNotInCheck(field)) {
            if (move.x == x && move.y == y) {
                return move;
            }
        }
        return null;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public void doMove() {

    }

    public List<Move> getMovesNotInCheck(ChessField field) {
        List<Move> availableMoves = getAvailableMoves(field);
        List<Move> toReturn = new LinkedList<>();
        for (Move move : availableMoves) {
            //TODO: SAVE BACKUP FOR EVERY TYPE OF MOVE NOT ONLY STEP AND TOOK!!!
            MoveBackup backup = move.type.doBackup(this, field, move);
            move.type.doWork(this, field, move);
            if (!hasThreatOfLoosingKing(field)) {
                toReturn.add(move);
            }
            backup.load(this, field, move);
        }
        return toReturn;
    }

    private boolean hasThreatOfLoosingKing(ChessField field) {
        King king = null;
        for (Figure figure : field.getFigures(this.getColor())) {
            if (figure instanceof King) {
                king = (King) figure;
                break;
            }
        }

        if (king == null) {
            throw new Error();//TODO:
        }

        return isCellBeaten(field, king.getX(), king.getY());
    }


    private boolean isCellBeaten(ChessField field, int x, int y) {
        for (Figure figure : field.getFigures(FigureColor.another(this.getColor()))) {
            for (Move move : figure.getAvailableMoves(field)) {
                if (x == move.x && y == move.y) {
                    return true;
                }
            }
        }
        return false;
    }

    public abstract Figure copy();

    public void reInit(Figure figureWhoMove) {
        this.x = figureWhoMove.x;
        this.y = figureWhoMove.y;
    }
}
