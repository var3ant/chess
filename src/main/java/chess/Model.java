package chess;

import chess.figure.*;
import chess.move.Move;

public class Model {
    private final PlayerModel[] players;
    public ChessField field;
    private final View view;
    private int selectedPlayer = 0;

    public Model(int fieldSize, View view) {
        this.view = view;
        this.field = new ChessField(fieldSize);
        initDefaultField();

        players = new PlayerModel[]{
                new PlayerModel(FigureColor.White),
                new PlayerModel(FigureColor.Black)
        };
    }

    private void initDefaultField() {
        field.set(0, 7, new Rook(FigureColor.White));
        field.set(1, 7, new Knight(FigureColor.White));
        field.set(2, 7, new Bishop(FigureColor.White));
        field.set(3, 7, new Queen(FigureColor.White));
        field.set(4, 7, new King(FigureColor.White));
        field.set(5, 7, new Bishop(FigureColor.White));
        field.set(6, 7, new Knight(FigureColor.White));
        field.set(7, 7, new Rook(FigureColor.White));

        field.set(0, 6, new Pawn(FigureColor.White));
        field.set(1, 6, new Pawn(FigureColor.White));
        field.set(2, 6, new Pawn(FigureColor.White));
        field.set(3, 6, new Pawn(FigureColor.White));
        field.set(4, 6, new Pawn(FigureColor.White));
        field.set(5, 6, new Pawn(FigureColor.White));
        field.set(6, 6, new Pawn(FigureColor.White));
        field.set(7, 6, new Pawn(FigureColor.White));


        field.set(0, 0, new Rook(FigureColor.Black));
        field.set(1, 0, new Knight(FigureColor.Black));
        field.set(2, 0, new Bishop(FigureColor.Black));
        field.set(3, 0, new Queen(FigureColor.Black));
        field.set(4, 0, new King(FigureColor.Black));
        field.set(5, 0, new Bishop(FigureColor.Black));
        field.set(6, 0, new Knight(FigureColor.Black));
        field.set(7, 0, new Rook(FigureColor.Black));

        field.set(0, 1, new Pawn(FigureColor.Black));
        field.set(1, 1, new Pawn(FigureColor.Black));
        field.set(2, 1, new Pawn(FigureColor.Black));
        field.set(3, 1, new Pawn(FigureColor.Black));
        field.set(4, 1, new Pawn(FigureColor.Black));
        field.set(5, 1, new Pawn(FigureColor.Black));
        field.set(6, 1, new Pawn(FigureColor.Black));
        field.set(7, 1, new Pawn(FigureColor.Black));
    }

    //FIXME: method smells because there is no selectedplayer.using() in java
    public void click(int x, int y) {
        PlayerModel selectedPlayer = getSelectedPlayer();
        System.out.println(x + " " + y);
        Figure figure = field.get(x, y);

        if (figure != null && figure.getColor() == selectedPlayer.myColor) {
            selectedPlayer.selectedFigure = figure;
            view.repaint();
            return;
        }

        if (selectedPlayer.selectedFigure == null) {
            return;
        }

        Move move = selectedPlayer.selectedFigure.findMoveIfAvailable(field, x, y);
        if (move != null) {
            move.type.doWork(selectedPlayer.selectedFigure, field, move);
            selectedPlayer.selectedFigure = null;
            selectNextPlayer();
            view.repaint();
        }

        if (figure == null) {
            selectedPlayer.selectedFigure = null;
            view.repaint();
            return;
        }
    }

    public Figure getSelectedFigure() {
        return players[selectedPlayer].selectedFigure;
    }

    public PlayerModel getSelectedPlayer() {
        return players[selectedPlayer];
    }

    private void selectNextPlayer() {
        selectedPlayer = (selectedPlayer + 1) % 2;
    }
}
