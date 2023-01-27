package chess.figure;

import chess.field.ChessField;
import chess.move.*;
import chess.Properties;
import chess.view.FieldView;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Pawn extends Figure {
    private static final String imageName = "pawn.png";
    private boolean isFirstStep = true;

    public Pawn(FigureColor color, int x, int y) {
        super(color, x, y);
    }

    public Pawn(FigureColor color) {
        super(color);
    }

    public Pawn(FigureColor color, boolean isFirstStep) {
        this(color);
        this.isFirstStep = isFirstStep;
    }

    @Override
    public Image getImage() throws IOException {
        BufferedImage im = super.loadImage(getColor().colorPrefix + "_" + imageName);
        return Scalr.resize(im, FieldView.cellSize);
    }


    @Override
    public List<List<Position>> getMovingLines(ChessField field) {

        List<List<Position>> result = new LinkedList<>();
        List<Position> line = new LinkedList<>();
        int newY = getY() + getColor().direction;
        if (newY >= 0 && newY < field.size) {
            line.add(new Position(getX(), newY));
            result.add(line);
            if (isFirstStep) {
                newY = getY() + getColor().direction + getColor().direction;
                if (newY >= 0 && newY < field.size) {
                    line.add(new Position(getX(), newY));
                }
            }
        }

        return result;
    }

    public List<Position> getTookSteps(ChessField field) {
        List<Position> result = new LinkedList<>();
        int newY = getY() + getColor().direction;
        if (newY >= 0 && newY < field.size) {
            Figure figure;
            int newX = getX() - 1;
            if (newX >= 0) {
                figure = field.getNullable(newX, newY);
                if (figure != null && figure.getColor() != getColor()) {
                    result.add(new Position(newX, newY));
                }
            }

            newX = getX() + 1;
            if (newX < field.size) {
                figure = field.getNullable(newX, newY);
                if (figure != null && figure.getColor() != getColor()) {
                    result.add(new Position(newX, newY));
                }
            }
        }

        return result;
    }

    @Override
    public List<Move> getAvailableMoves(ChessField field, boolean withCastling) {
        List<List<Position>> lines = getMovingLines(field);
        List<Move> actualMoves = new LinkedList<>();
        for (List<Position> line : lines) {
            for (Position position : line) {
                if (field.getNullable(position.x, position.y) != null) {
                    break;
                }
                actualMoves.add(new Step(position));
            }
        }
        for (Position tookStep : getTookSteps(field)) {
            actualMoves.add(new Took(tookStep));
        }


        return actualMoves.stream().map(move -> {
            if (move.y == 0 || move.y == field.size - 1) {
                Promotion promotion = new Promotion(move.x, move.y);
                promotion.setPromotionFigure(Queen.class);
                return promotion;
            } else return move;
        }).collect(Collectors.toList());
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
    }


    @Override
    public void doMove() {
        super.doMove();
        isFirstStep = false;
    }

    //FIXME: copy and reInit dependent on each other. Pattern save/load should fix it.
    @Override
    public Figure copy() {
        Pawn copy = new Pawn(getColor(), getX(), getY());
        copy.isFirstStep = isFirstStep;
        return copy;
    }

    @Override
    public void reInit(Figure figureWhoMove) {
        super.reInit(figureWhoMove);
        this.isFirstStep = ((Pawn) figureWhoMove).isFirstStep;
    }
}
