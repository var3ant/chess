package chess.figure;

import java.util.ArrayList;
import java.util.List;

public class FactoryPromotionFigures {
    public static List<Figure> getFigures(FigureColor color) {
        List<Class<? extends Figure>> figureClasses = List.of(Queen.class, Rook.class, Bishop.class, Knight.class);
        List<Figure> figures = new ArrayList<>();
        for (Class<? extends Figure> figureClass : figureClasses) {
            figures.add(getInstance(figureClass, color));
        }
        return figures;
    }

    public static <T extends Figure> T getInstance(Class<T> figureClass, FigureColor color) {
        if (figureClass == Queen.class) {
            return (T) new Queen(color);
        } else if (figureClass == Rook.class) {
            return (T) new Rook(color, false); //TODO: вообще не важно false там или нет, потому что он всё равно на другой стороне поля находится.
                                                        //Поэтому эта фабрика вообще не нужна и можно newInstance() вызывать. Но я решил обобщить, вдруг еще фигуры появятся.
        } else if (figureClass == Knight.class) {
            return (T) new Knight(color);
        } else if (figureClass == Bishop.class) {
            return (T) new Bishop(color);
        } else {
            throw new IllegalArgumentException(figureClass.toString());
        }
    }
}
