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

public class Bishop extends FigureWithSameLinesTookAndStep {
    private static final String imageName = "bishop.png";

    public Bishop(FigureColor color, int x, int y) {
        super(color, x, y);
    }
    public Bishop(FigureColor color) {
        super(color);
    }
    @Override
    public Image getImage() throws IOException {
        String path = Properties.PATH_TO_FIGURES;
        BufferedImage im = ImageIO.read(new File(path + getColor().colorPrefix + "_" + imageName));
        return Scalr.resize(im, FieldView.cellSize);
    }


    @Override
    public List<List<Position>> getMovingLines(ChessField field) {
        List<Position> downRight = new LinkedList<>();
        int newX = this.getX();
        int newY = this.getY();

        newX++;
        newY++;
        while (newX < field.size && newY < field.size) {
            downRight.add(new Position(newX, newY));
            newX++;
            newY++;
        }


        List<Position> downLeft = new LinkedList<>();
        newX = this.getX();
        newY = this.getY();
        newX--;
        newY++;

        while (newX >= 0  && newY < field.size) {
            downLeft.add(new Position(newX, newY));
            newX--;
            newY++;
        }


        List<Position> upLeft = new LinkedList<>();
        newX = this.getX();
        newY = this.getY();
        newX--;
        newY--;
        while (newX >= 0 && newY >= 0) {
            upLeft.add(new Position(newX, newY));
            newX--;
            newY--;
        }


        List<Position> upRight = new LinkedList<>();
        newX = this.getX();
        newY = this.getY();
        newX++;
        newY--;

        while (newX < field.size && newY >= 0) {
            upRight.add(new Position(newX, newY));
            newX++;
            newY--;
        }



        List<List<Position>> result = new LinkedList<>();
        result.add(upRight);
        result.add(upLeft);
        result.add(downRight);
        result.add(downLeft);
        return result;
    }

    @Override
    public Figure copy() {
        Bishop copy = new Bishop(getColor(),getX(),getY());
        return copy;
    }

}
