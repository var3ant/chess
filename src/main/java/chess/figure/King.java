package chess.figure;

import chess.ChessField;
import chess.Coord;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class King extends FigureWithSameLinesTookAndStep {
    private static final String imageName = "king.png";
    private boolean isFirstStep = true;

    public King(FigureColor color, int x, int y) {
        super(color, x, y);
    }
    public King(FigureColor color) {
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
        return Stream.of(
                new Coord(getX(), getY() - 1),
                new Coord(getX() + 1, getY() - 1),
                new Coord(getX() + 1, getY()),
                new Coord(getX() + 1, getY() + 1),
                new Coord(getX(), getY() + 1),
                new Coord(getX() - 1, getY() + 1),
                new Coord(getX() - 1, getY()),
                new Coord(getX() - 1, getY() - 1)
        ).filter((coord) ->
                coord.x >= 0
                        && coord.x < field.size
                        && coord.y >= 0
                        && coord.y < field.size
        ).map(List::of).toList();
    }

    @Override
    public void doMove() {
        super.doMove();
        isFirstStep = false;
    }

    @Override
    public Figure copy() {
        King copy = new King(getColor(), getX(), getY());
        copy.isFirstStep = isFirstStep;
        return copy;
    }

    @Override
    public void reInit(Figure figureWhoMove) {
        super.reInit(figureWhoMove);
        this.isFirstStep = ((King) figureWhoMove).isFirstStep;
    }

}
