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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Knight extends FigureWithSameLinesTookAndStep {
    private static final String imageName = "knight.png";

    public Knight(FigureColor color, int x, int y) {
        super(color, x, y);
    }
    public Knight(FigureColor color) {
        super(color);
    }
    @Override
    public Image getImage() throws IOException {
        BufferedImage im = super.loadImage(getColor().colorPrefix + "_" + imageName);
        return Scalr.resize(im, FieldView.cellSize);
    }


    @Override
    public List<List<Position>> getMovingLines(ChessField field) {
        return Stream.of(
                new Position(getX() + 2, getY() - 1),
                new Position(getX() + 1, getY() - 2),
                new Position(getX() - 2, getY() - 1),
                new Position(getX() - 1, getY() - 2),
                new Position(getX() + 2, getY() + 1),
                new Position(getX() + 1, getY() + 2),
                new Position(getX() - 2, getY() + 1),
                new Position(getX() - 1, getY() + 2)
        ).filter((coord) ->
                coord.x >= 0
                        && coord.x < field.size
                        && coord.y >= 0
                        && coord.y < field.size
        ).map(coord ->
                List.of(coord)
        ).collect(Collectors.toList());
    }


    @Override
    public Figure copy() {
        Knight copy = new Knight(getColor(),getX(),getY());
        return copy;
    }

}
