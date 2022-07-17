package chess;

import chess.figure.Figure;
import chess.figure.FigureColor;
import chess.figure.Knight;
import chess.figure.Rook;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ChessField {
    private final ArrayList<ArrayList<Figure>> figures;
    private Map<FigureColor, Set<Figure>> figuresMap = new HashMap<>();
    public final int size;

    public ChessField(int size) {
        figuresMap.put(FigureColor.White, new HashSet<>());
        figuresMap.put(FigureColor.Black, new HashSet<>());
        this.size = size;
        figures = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ArrayList<Figure> horizontalList = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                horizontalList.add(null);
            }
            figures.add(horizontalList);
        }
    }

    public Figure get(int x, int y) {
        return figures.get(y).get(x);
    }

    public Figure remove(int x, int y) {
        return set(x, y, null);
    }

    public Figure set(int x, int y, Figure figure) {
        Figure old = get(x, y);
        if (old != null) {
            figuresMap.get(old.getColor()).remove(old);
            //old.setPosition(-1, -1);
        }
        if (figure != null) {
            getFigures(figure.getColor()).add(figure);
            figure.setPosition(x, y);
        }

        return figures.get(y).set(x, figure);
    }

    public Set<Figure> getFigures(FigureColor color) {
        return figuresMap.get(color);
    }

//    public void create(int x, int y, FigureColor color, Class<? extends Figure> figureClass) {
//        try {
//            set(x, y, (Figure) figureClass.getDeclaredConstructors()[0].newInstance(color, x, y));
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
}
