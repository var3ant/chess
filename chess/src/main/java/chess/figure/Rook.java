package chess.figure;

import chess.field.ChessField;
import chess.move.Position;
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

public class Rook extends FigureWithSameLinesTookAndStep {
    private static final String imageName = "rook.png";
    private boolean isFirstStep = true;

    public Rook(FigureColor color, int x, int y) {
        super(color, x, y);
    }

    public Rook(FigureColor color) {
        super(color);
    }

    public Rook(FigureColor color, boolean isFirstStep) {
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
        List<Position> horizontalRight = new LinkedList<>();
        int newX = this.getX();
        int newY = this.getY();

        newX++;
        while (newX < field.size) {
            horizontalRight.add(new Position(newX, newY));
            newX++;
        }


        List<Position> horizontalLeft = new LinkedList<>();
        newX = this.getX();
        newX--;
        while (newX >= 0) {
            horizontalLeft.add(new Position(newX, newY));
            newX--;
        }


        List<Position> verticalUp = new LinkedList<>();
        newX = this.getX();
        newY = this.getY();
        newY--;
        while (newY >= 0) {
            verticalUp.add(new Position(newX, newY));
            newY--;
        }


        List<Position> verticalDown = new LinkedList<>();
        newY = this.getY();
        newY++;
        while (newY < field.size) {
            verticalDown.add(new Position(newX, newY));
            newY++;
        }


        List<List<Position>> result = new LinkedList<>();
        result.add(horizontalRight);
        result.add(horizontalLeft);
        result.add(verticalUp);
        result.add(verticalDown);
        return result;
    }

    @Override
    public void doMove() {
        super.doMove();
        isFirstStep = false;
    }


    @Override
    public Figure copy() {
        Rook copy = new Rook(getColor(), getX(), getY());
        copy.isFirstStep = isFirstStep;
        return copy;
    }

    @Override
    public void reInit(Figure figureWhoMove) {
        super.reInit(figureWhoMove);
        this.isFirstStep = ((Rook) figureWhoMove).isFirstStep;
    }

    public boolean isFirstStep() {
        return isFirstStep;
    }
}
