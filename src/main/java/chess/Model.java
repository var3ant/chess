package chess;

import chess.figure.*;
import chess.move.Move;
import chess.view.FieldView;

import java.util.List;

public class Model {
    private final PlayerModel[] players;
    public ChessField field;
    private final FieldView fieldView;
    private int selectedPlayer = 0;
    private FigureColor whoWon = null;

    public Model(int fieldSize, FieldView fieldView) {
        this.fieldView = fieldView;
        this.field = new ChessField(fieldSize);
        initDefaultField();
//        initDebugField();
        players = new PlayerModel[]{
                new PlayerModel(FigureColor.White),
                new PlayerModel(FigureColor.Black)
        };
        getSelectedPlayer().calcMoves(field);
    }

    private void initDebugField() {
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

    //smells: method smells because there is no selectedplayer.using() in java
    public void click(int x, int y) {
        if (whoWon != null) {
            return;
        }
        PlayerModel selectedPlayer = getSelectedPlayer();
        System.out.println(x + " " + y);
        Figure figure = field.getNullable(x, y);

        if (figure != null && figure.getColor() == selectedPlayer.myColor) {
            selectedPlayer.selectedFigure = figure;
            fieldView.repaint();
            return;
        }

        if (selectedPlayer.selectedFigure == null) {
            return;
        }

        Move move = selectedPlayer.findMoveIfAvailable(selectedPlayer.selectedFigure, x, y);// selectedPlayer.selectedFigure.findMoveIfAvailable(field, x, y);
        if (move != null) {
            move.move(selectedPlayer.selectedFigure, field);
            selectedPlayer.selectedFigure = null;
            selectNextPlayer();
            var moves = getSelectedPlayer().getMoves();
            if (moves.values().stream().allMatch(List::isEmpty)) {
                whoWon = getSelectedPlayer().myColor.another();
            }
            fieldView.repaint();
        }

        if (figure == null) {
            selectedPlayer.selectedFigure = null;
            fieldView.repaint();
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
        field.checkForBugs();
        getSelectedPlayer().calcMoves(field);
    }

    public FigureColor whoWon() {
        return whoWon;
    }
}
