package chess.playermodel;

import chess.figure.Figure;
import chess.figure.FigureColor;
import chess.move.Move;
import chess.move.Promotion;


public class OfflinePlayerModel extends PlayerModel {
    public Figure selectedFigure;

    public OfflinePlayerModel(FigureColor myColor) {
        super(myColor);
    }

    public void click(int x, int y) {
        System.out.println(x + " " + y);
        Figure figure = model.getFigure(x, y);

        if (figure != null && figure.getColor() == myColor) {
            setSelectedFigure(figure);
            return;
        }

        if (selectedFigure == null) {
            return;
        }

        Move move = findMoveIfAvailable(selectedFigure, x, y);
        if (move != null) {
            if (move instanceof Promotion) {
                Figure newFigureClass = model.selectFigureForPromotion(myColor);
                if(newFigureClass != null) {
                    ((Promotion) move).setPromotionFigure(newFigureClass.getClass());
                } else {
                    return;
                }
            }
            model.doMove(this, selectedFigure.getX(), selectedFigure.getY(), move);
            setSelectedFigure(null);
            return;
        }

        if (figure == null) {
            setSelectedFigure(null);
            return;
        }
    }

    private void setSelectedFigure(Figure figure) {
        selectedFigure = figure;
        model.notificationSelectedFigureChanged(this);
    }

    @Override
    public Figure getSelectedFigure() {
        return selectedFigure;
    }
}
