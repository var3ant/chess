package chess.adapter;

import chess.Model;
import chess.playermodel.OfflinePlayerModel;

public class OfflineMouseAdapter implements Adapter {
    private final Model model;

    public OfflineMouseAdapter(Model model) {
        this.model = model;
    }

    @Override
    public void click(int x, int y) {
        ((OfflinePlayerModel) model.getSelectedPlayer()).click(x, y);
    }
}
