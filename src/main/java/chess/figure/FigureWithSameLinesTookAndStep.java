package chess.figure;

import chess.ChessField;
import chess.Coord;
import chess.move.Move;
import chess.move.Step;
import chess.move.Took;

import java.util.LinkedList;
import java.util.List;

public abstract class FigureWithSameLinesTookAndStep extends Figure {
    public FigureWithSameLinesTookAndStep(FigureColor color, int x, int y) {
        super(color, x, y);
    }

    public FigureWithSameLinesTookAndStep(FigureColor color) {
        super(color);
    }

    @Override
    public List<Move> getAvailableMoves(ChessField field) {
        List<List<Coord>> lines = getMovingLines(field);
        List<Move> actualMoves = new LinkedList<>();
        for (List<Coord> line : lines) {
            for (Coord coord : line) {
                if (field.containsFigure(coord.x, coord.y)) {
                    if (field.get(coord.x, coord.y).getColor() != this.getColor()) {
                        actualMoves.add(new Took(coord.x, coord.y));
                    }
                    break;
                }
                actualMoves.add(new Step(coord.x, coord.y));
            }
        }
        return actualMoves;
    }
}
