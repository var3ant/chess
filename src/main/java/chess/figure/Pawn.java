package chess.figure;

import chess.ChessField;
import chess.Coord;
import chess.move.Move;
import chess.move.MoveBackup;
import chess.move.MoveType;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Pawn extends Figure {
    private static final String imageName = "pawn.png";
    private boolean isFirstStep = true;

    public Pawn(FigureColor color, int x, int y) {
        super(color, x, y);
    }
    public Pawn(FigureColor color) {
        super(color);
    }
    @Override
    public Image getImage() throws IOException {
        String path = "E:\\projects\\hobby\\java\\chess\\src\\main\\resources\\figures\\";
        BufferedImage im = ImageIO.read(new File(path + getColor().colorPrefix + "_" + imageName));
        return Scalr.resize(im, 80);//TODO:cell size
    }


    @Override
    public List<List<Coord>> getMovingLines(ChessField field) {

        List<List<Coord>> result = new LinkedList<>();
        int newY = getY() + getColor().direction;
        if (newY >= 0 && newY < field.size) {
            result.add(List.of(new Coord(getX(), newY)));
        }
        //TODO: long step for a pawn
        if (isFirstStep) {
            newY = getY() + getColor().direction + getColor().direction;
            if (newY >= 0 && newY < field.size) {
                result.add(List.of(new Coord(getX(), newY)));
            }
        }
        return result;
    }

    public List<Coord> getTookSteps(ChessField field) {
        List<Coord> result = new LinkedList<>();
        int newY = getY() + getColor().direction;
        if (newY >= 0 && newY < field.size) {
            Figure figure;
            int newX = getX() - 1;
            if (newX >= 0) {
                figure = field.get(newX, newY);
                if (figure != null && figure.getColor() != getColor()) {
                    result.add(new Coord(newX, newY));
                }
            }

            newX = getX() + 1;
            if (newX < field.size) {
                figure = field.get(newX, newY);
                if (figure != null && figure.getColor() != getColor()) {
                    result.add(new Coord(newX, newY));
                }
            }
        }

        return result;
    }

    @Override
    public List<Move> getAvailableMoves(ChessField field) {
        List<List<Coord>> lines = getMovingLines(field);
        List<Move> actualMoves = new LinkedList<>();
        for (List<Coord> line : lines) {
            for (Coord coord : line) {
                if (field.get(coord.x, coord.y) != null) {
                    break;
                }
                actualMoves.add(new Move(coord, MoveType.Step));
            }
        }
        for (Coord tookStep : getTookSteps(field)) {
            actualMoves.add(new Move(tookStep, MoveType.Took));
        }
        return actualMoves;
    }


    @Override
    public void doMove() {
        super.doMove();
        isFirstStep = false;
    }

    //TODO: copy and reInit dependent on each other. Pattern save/load should fix it.
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
