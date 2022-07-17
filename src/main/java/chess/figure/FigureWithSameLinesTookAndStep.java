package chess.figure;

import chess.ChessField;
import chess.Coord;
import chess.move.Move;
import chess.move.MoveType;

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
                if (field.get(coord.x, coord.y) != null) {
                    if (field.get(coord.x, coord.y).getColor() != this.getColor()) {
                        actualMoves.add(new Move(coord.x, coord.y, MoveType.Took));
                    }
                    break;
                }
                actualMoves.add(new Move(coord.x, coord.y, MoveType.Step));
            }
        }
        return actualMoves;
    }
}
