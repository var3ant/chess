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

    public FigureColor another() {
        if (this == Black) {
            return White;
        }
        if (this == White) {
            return Black;
        }
        throw new IllegalArgumentException("Unexpected Color");
    }
}
