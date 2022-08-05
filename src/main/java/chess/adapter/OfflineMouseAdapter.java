package chess.adapter;

import chess.Model;
import chess.playermodel.OfflinePlayerModel;

import java.util.List;

public class OfflineMouseAdapter implements Adapter {
    private final Model model;
    private final List<OfflinePlayerModel> players;

    public OfflineMouseAdapter(Model model, List<OfflinePlayerModel> players) {
        this.model = model;
        this.players = players;
    }

    @Override
    public void click(int x, int y) {
        for (OfflinePlayerModel player : players) {
            if (model.isThisPlayerMoveNow(player)) {
                ((OfflinePlayerModel) model.getSelectedPlayer()).click(x, y);
                return;
            }
        }


    }
}
