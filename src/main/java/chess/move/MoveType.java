package chess.move;

import chess.ChessField;
import chess.figure.Figure;

import java.awt.*;

public enum MoveType {//TODO: 100 opacity
    Step(new Color(255, 255, 255, 100)),
    Took(new Color(255, 0, 0, 100)),
    Castling(new Color(255, 100, 255, 100)),
    Promotion(new Color(0, 255, 0, 100));

    public final Color color;

    MoveType(Color color) {
        this.color = color;
    }

    public MoveBackup doBackup(Figure figure, ChessField field, Move move) {
        Figure figureOnObj = null;
        Figure figureWhoMove = null;
        if (field.get(move.x, move.y) != null) {
            figureOnObj = field.get(move.x, move.y).copy();
        }
        if (figure != null) {
            figureWhoMove = figure.copy();
        }
        return new MoveBackup(figureOnObj, figureWhoMove);
    }

    private void step(Figure figure, ChessField field, Move move) {
        if (field.remove(figure.getX(), figure.getY()) == null) {
            throw new Error("");//TODO:
        }

        if (field.set(move.x, move.y, figure) != null) {
            throw new Error("");//TODO:
        }
        figure.setPosition(move.x, move.y);
    }

    private void took(Figure figure, ChessField field, Move move) {
        if (field.remove(figure.getX(), figure.getY()) == null) {
            throw new Error("");//TODO:
        }
        if (field.set(move.x, move.y, figure) == null) {
            throw new Error("");//TODO:
        }
        figure.setPosition(move.x, move.y);
    }

    public void doWork(Figure figure, ChessField field, Move move) {
        switch (move.type) {
            case Step -> step(figure, field, move);
            case Took -> took(figure, field, move);
        }
        figure.doMove();
    }
}
