package chess;

import chess.figure.Figure;
import chess.figure.FigureColor;
import chess.figure.Rook;

public class PlayerModel {
    public Figure selectedFigure;
    public final FigureColor myColor;

    public PlayerModel(FigureColor myColor) {
        this.myColor = myColor;
    }

}
