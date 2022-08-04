package chess;

import chess.view.FieldView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller implements MouseListener {
    private Model model;
    private FieldView fieldView;

    public Controller(Model model, FieldView fieldView) {
        this.model = model;
        this.fieldView = fieldView;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        model.click(x / fieldView.cellSize,y / fieldView.cellSize);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
