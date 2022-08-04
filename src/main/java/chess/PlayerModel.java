package chess;

import chess.figure.Figure;
import chess.figure.FigureColor;
import chess.move.Move;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerModel {
    public Figure selectedFigure;
    public final FigureColor myColor;
    private final Map<Figure, List<Move>> moves = new HashMap<>();

    public PlayerModel(FigureColor myColor) {
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

    public Move findMoveIfAvailable(Figure selectedFigure, int x, int y) {
        return moves.get(selectedFigure).stream().filter(f -> f.x == x && f.y == y).findFirst().orElse(null);
    }
}
