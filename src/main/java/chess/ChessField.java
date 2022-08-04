package chess;

import chess.figure.Figure;
import chess.figure.FigureColor;

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
        if (figures.get(y).get(x) == null) { //assert
            throw new Error();
        }
        return figures.get(y).get(x);
    }

    public boolean containsFigure(int x, int y) {
        return figures.get(y).get(x) != null;
    }

    public Figure getNullable(int x, int y) {
        return figures.get(y).get(x);
    }

    public Figure remove(int x, int y) {
        get(x, y); //assert
        return set(x, y, null);
    }

    public Figure set(int x, int y, Figure figure) {
        Figure old = getNullable(x, y);
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

    public <T extends Figure> List<T> findAll(Class<T> figureClass, FigureColor color) {
        List<T> result = new LinkedList<>();
        for (Figure figure : getFigures(color)) {
            if (figure.getClass().equals(figureClass)) {
                result.add((T) figure);
            }
        }
        return result;
    }

    public Figure findFirst(Class<? extends Figure> figureClass, FigureColor color) {
        for (Figure figure : getFigures(color)) {
            if (figure.getClass().equals(figureClass)) {
                return figure;
            }
        }
        return null;
    }

    public ChessField copy() {
        var field = new ChessField(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.containsFigure(i, j)) {
                    Figure figure = this.get(i, j).copy();
                    field.set(i, j, figure);
                } else {
                    field.set(i, j, null);
                }
            }
        }
        return field;
    }

    public void checkForBugs() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Figure f = this.getNullable(i, j);
                if (f != null && (f.getX() != i || f.getY() != j)) {
                    throw new Error();
                }
            }
        }
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
