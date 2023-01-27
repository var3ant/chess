package chess.figure;

import chess.Properties;
import chess.field.ChessField;
import chess.move.Position;
import chess.move.Move;
import chess.move.Promotion;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public abstract class Figure {
    private int x = -1;
    private int y = -1;
    private final FigureColor color;

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

    public abstract List<List<Position>> getMovingLines(ChessField field);


    public abstract List<Move> getAvailableMoves(ChessField field, boolean withCastling);

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
        List<Move> availableMoves = getAvailableMoves(field, true);
        List<Move> toReturn = new LinkedList<>();
        for (Move move : availableMoves) {
            ChessField sandboxField = field.copy();
            Figure myCopy = sandboxField.get(this.getX(), this.getY());
            move.move(myCopy, sandboxField);
            if (!hasThreatOfLoosingKing(sandboxField)) {
                toReturn.add(move);
            }
            field.checkForBugs();
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
            throw new Error("King is dead?!");//ASSERT
        }

        return isCellBeaten(field, king.getX(), king.getY());
    }


    private boolean isCellBeaten(ChessField field, int x, int y) {
        return isCellsBeaten(field, List.of(new Position(x, y)));
    }

    protected boolean isCellsBeaten(ChessField field, List<Position> cells) {
//        System.out.println("isCellsBeaten: " + this + ", cells" + cells);
        for (Figure figure : field.getFigures(this.getColor().another())) {
            for (Move move : figure.getAvailableMoves(field, false)) {//FIXME: figure king
                if (cells.stream().anyMatch((coord) -> coord.x == move.x && coord.y == move.y)) {
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Figure)) {
            return false;
        }
        Figure eqFigure = (Figure) obj;
        return x == eqFigure.x && y == eqFigure.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }


    protected BufferedImage loadImage(String name) throws IOException {
        return ImageIO.read(ClassLoader.getSystemClassLoader().getResource(Properties.PATH_TO_FIGURES + name));
    }
}
