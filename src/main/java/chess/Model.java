package chess;

import chess.field.ChessField;
import chess.figure.*;
import chess.move.Move;
import chess.playermodel.PlayerModel;
import chess.view.FieldView;

import java.util.List;

public class Model {
    private final List<? extends PlayerModel> players;
    public ChessField field;
    private final FieldView fieldView;
    private int selectedPlayer = 0;
    private FigureColor whoWon = null;

    public Model(ChessField chessField, FieldView fieldView, List<? extends PlayerModel> players) {
        this.fieldView = fieldView;
        this.players = players;

        this.field = chessField;
        getSelectedPlayer().calcMoves(field);
    }

    public Model(int fieldSize, FieldView fieldView, List<? extends PlayerModel> players) {
        this.fieldView = fieldView;
        this.players = players;

        this.field = new ChessField(fieldSize);
        initDefaultField();
        getSelectedPlayer().calcMoves(field);
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

    public void doMove(PlayerModel playerModel, int figureX, int figureY, Move move) {
        if (!isThisPlayerMoveNow(playerModel)) {
            return;
        }
        if (whoWon != null) {
            return;
        }
        move.move(field.get(figureX, figureY), field);
        selectNextPlayer();
        getSelectedPlayer().notificationMoveWasHappened(figureX, figureY, move);
        var moves = getSelectedPlayer().getMoves();
        if (moves.values().stream().allMatch(List::isEmpty)) {
            whoWon = getSelectedPlayer().myColor.another();
        }
        fieldView.repaint();
    }

    public Figure getSelectedFigure() {
        return players.get(selectedPlayer).getSelectedFigure();
    }

    public PlayerModel getSelectedPlayer() {
        return players.get(selectedPlayer);
    }

    private void selectNextPlayer() {
        selectedPlayer = (selectedPlayer + 1) % 2;
        field.checkForBugs();
        getSelectedPlayer().calcMoves(field);
        System.out.println("now its " + getSelectedPlayer().myColor.name() + " turn");
    }

    public FigureColor whoWon() {
        return whoWon;
    }

    public Figure getFigure(int x, int y) {
        return field.getNullable(x, y);
    }

    public void notificationSelectedFigureChanged(PlayerModel playerModel) {
        fieldView.repaint();
    }

    public boolean isThisPlayerMoveNow(PlayerModel playerModel) {
        return playerModel == getSelectedPlayer();
    }
}
