package chess.move;

import chess.field.ChessField;
import chess.figure.FactoryPromotionFigures;
import chess.figure.Figure;

import java.awt.*;

public class Promotion extends Move {
    private static final Color color = new Color(0, 255, 0, 100);
    private Class<? extends Figure> figureClass;

    @Override
    public Color getColor() {
        return color;
    }

    public Promotion(int x, int y) {
        super(x, y);
    }

    public Promotion(Position position) {
        super(position);
    }

    public void setPromotionFigure(Class<? extends Figure> figureClass) {
        this.figureClass = figureClass;
    }

    @Override
    public void privateMove(Figure figure, ChessField field) {
        field.remove(figure.getX(), figure.getY());
        field.set(x, y, FactoryPromotionFigures.getInstance(figureClass, figure.getColor()));
    }

}
