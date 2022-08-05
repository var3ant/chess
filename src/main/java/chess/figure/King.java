package chess.figure;

import chess.field.ChessField;
import chess.move.Position;
import chess.Properties;
import chess.move.Castling;
import chess.move.Move;
import chess.view.FieldView;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    public King(FigureColor color, boolean isFirstStep) {
        this(color);
        this.isFirstStep = isFirstStep;
    }

    @Override
    public List<Move> getAvailableMoves(ChessField field, boolean withCastling) {
        var result = super.getAvailableMoves(field, withCastling);
        if(withCastling) {
            if (isFirstStep) {
                var rooks = field.findAll(Rook.class, getColor());
                for (Rook rook : rooks) {
                    if (rook.isFirstStep()) {
                        List<Position> positions = new ArrayList<>();
                        positions.add(new Position(getX(), getY()));
                        positions.add(new Position(rook.getX(), rook.getY()));
                        if (rook.getY() == getX()) {
                            continue;
                        }
                        int direction = (getX() - rook.getX()) / Math.abs(getX() - rook.getX());
                        boolean isSellsFree = true;
                        for (int x = getX() - direction; x != rook.getX(); x -= direction) {
                            if (x < 0 || x >= field.size) {
                                break;
                            }
                            if (field.containsFigure(x, getY())) {
                                isSellsFree = false;
                                break;
                            }
                            positions.add(new Position(x, getY()));
                        }
                        if (!isSellsFree) {
                            continue;
                        }
                        if (isCellsBeaten(field, positions)) {
                            continue;
                        }

                        result.add(new Castling(new Position(getX() - direction * 2, getY()), direction, new Position(rook.getX(), rook.getY())));
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Image getImage() throws IOException {
        String path = Properties.PATH_TO_FIGURES;
        BufferedImage im = ImageIO.read(new File(path + getColor().colorPrefix + "_" + imageName));
        return Scalr.resize(im, FieldView.cellSize);
    }


    @Override
    public List<List<Position>> getMovingLines(ChessField field) {
        return Stream.of(
                new Position(getX(), getY() - 1),
                new Position(getX() + 1, getY() - 1),
                new Position(getX() + 1, getY()),
                new Position(getX() + 1, getY() + 1),
                new Position(getX(), getY() + 1),
                new Position(getX() - 1, getY() + 1),
                new Position(getX() - 1, getY()),
                new Position(getX() - 1, getY() - 1)
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

    @Override
    public String toString() {
        return super.toString() + " " + getColor().name() + " king";
    }
}
