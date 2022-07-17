package chess.figure;

public enum FigureColor {
    Black("b", +1),
    White("w", -1);

    public final String colorPrefix;
    public final int direction;

    FigureColor(String prefix, int direction) {
        colorPrefix = prefix;
        this.direction = direction;
    }

    public static FigureColor another(FigureColor color) {
        if (color == Black) {
            return White;
        }
        if (color == White) {
            return Black;
        }
        throw new Error("T_T");//TODO:
    }
}
