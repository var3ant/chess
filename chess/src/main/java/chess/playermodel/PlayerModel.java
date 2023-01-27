package chess.playermodel;

import chess.field.ChessField;
import chess.Model;
import chess.figure.Figure;
import chess.figure.FigureColor;
import chess.move.Move;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PlayerModel {
    private final Map<Figure, List<Move>> moves = new HashMap<>();
    public final FigureColor myColor;
    protected Model model;

    protected PlayerModel(FigureColor myColor) {
        this.myColor = myColor;
    }

    public void calcMoves(ChessField field) {
        moves.clear();
        for (Figure figure : field.getFigures(myColor)) {
            moves.put(figure, figure.getMovesNotInCheck(field));
        }
    }


    public Map<Figure, List<Move>> getMoves() {
        return moves;
    }

    public Figure getSelectedFigure() {
        return null;
    }

    public Move findMoveIfAvailable(Figure selectedFigure, int x, int y) {
        return moves.get(selectedFigure).stream().filter(f -> f.x == x && f.y == y).findFirst().orElse(null);
    }

    public void notificationMoveWasHappened(int figureX, int figureY, Move move) {

    }

    public void setModel(Model model) {
        this.model = model;
    }

}
