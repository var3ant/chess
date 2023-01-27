package chess.figure;

import chess.field.ChessField;
import chess.move.Position;
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
    public List<Move> getAvailableMoves(ChessField field, boolean withCastling) {
        List<List<Position>> lines = getMovingLines(field);
        List<Move> actualMoves = new LinkedList<>();
        for (List<Position> line : lines) {
            for (Position position : line) {
                if (field.containsFigure(position.x, position.y)) {
                    if (field.get(position.x, position.y).getColor() != this.getColor()) {
                        actualMoves.add(new Took(position.x, position.y));
                    }
                    break;
                }
                actualMoves.add(new Step(position.x, position.y));
            }
        }
        return actualMoves;
    }
}
