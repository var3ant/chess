package chess;

import chess.adapter.Adapter;
import chess.view.FieldView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller implements MouseListener {
    private Adapter adapter;
    private FieldView fieldView;

    public Controller(Adapter adapter, FieldView fieldView) {
        this.adapter = adapter;
        this.fieldView = fieldView;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        adapter.click(x / fieldView.cellSize,y / fieldView.cellSize);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
