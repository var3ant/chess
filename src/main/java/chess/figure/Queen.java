package chess.figure;

import chess.ChessField;
import chess.Coord;
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

public class Queen extends FigureWithSameLinesTookAndStep {
    private static final String imageName = "queen.png";

    public Queen(FigureColor color, int x, int y) {
        super(color, x, y);
    }
    public Queen(FigureColor color) {
        super(color);
    }
    @Override
    public Image getImage() throws IOException {
        String path = Properties.PATH_TO_FIGURES;
        BufferedImage im = ImageIO.read(new File(path + getColor().colorPrefix + "_" + imageName));
        return Scalr.resize(im, FieldView.cellSize);
    }


    @Override
    public List<List<Coord>> getMovingLines(ChessField field) {

        //BISHOP

        List<Coord> downRight = new LinkedList<>();
        int newX = this.getX();
        int newY = this.getY();

        newX++;
        newY++;
        while (newX < field.size && newY < field.size) {
            downRight.add(new Coord(newX, newY));
            newX++;
            newY++;
        }


        List<Coord> downLeft = new LinkedList<>();
        newX = this.getX();
        newY = this.getY();
        newX--;
        newY++;

        while (newX >= 0  && newY < field.size) {
            downLeft.add(new Coord(newX, newY));
            newX--;
            newY++;
        }


        List<Coord> upLeft = new LinkedList<>();
        newX = this.getX();
        newY = this.getY();
        newX--;
        newY--;
        while (newX >= 0 && newY >= 0) {
            upLeft.add(new Coord(newX, newY));
            newX--;
            newY--;
        }


        List<Coord> upRight = new LinkedList<>();
        newX = this.getX();
        newY = this.getY();
        newX++;
        newY--;

        while (newX < field.size && newY >= 0) {
            upRight.add(new Coord(newX, newY));
            newX++;
            newY--;
        }


        //ROOK

        List<Coord> horizontalRight = new LinkedList<>();

        newX = this.getX();
        newY = this.getY();

        newX++;
        while (newX < field.size) {
            horizontalRight.add(new Coord(newX, newY));
            newX++;
        }


        List<Coord> horizontalLeft = new LinkedList<>();
        newX = this.getX();
        newX--;
        while (newX >= 0) {
            horizontalLeft.add(new Coord(newX, newY));
            newX--;
        }


        List<Coord> verticalUp = new LinkedList<>();
        newX = this.getX();
        newY = this.getY();
        newY--;
        while (newY >= 0) {
            verticalUp.add(new Coord(newX, newY));
            newY--;
        }


        List<Coord> verticalDown = new LinkedList<>();
        newY = this.getY();
        newY++;
        while (newY < field.size) {
            verticalDown.add(new Coord(newX, newY));
            newY++;
        }





        List<List<Coord>> result = new LinkedList<>();


        result.add(horizontalRight);
        result.add(horizontalLeft);
        result.add(verticalUp);
        result.add(verticalDown);

        result.add(upRight);
        result.add(upLeft);
        result.add(downRight);
        result.add(downLeft);
        return result;
    }

    @Override
    public Figure copy() {
        Queen copy = new Queen(getColor(),getX(),getY());
        return copy;
    }

}
