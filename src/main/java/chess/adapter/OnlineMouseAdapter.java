package chess.adapter;

import chess.Model;
import chess.playermodel.OfflinePlayerModel;

public class OnlineMouseAdapter implements Adapter {
    private final Model model;
    private final OfflinePlayerModel playerModel;

    public OnlineMouseAdapter(Model model, OfflinePlayerModel playerModel) {
        this.model = model;
        this.playerModel = playerModel;
    }

    @Override
    public void click(int x, int y) {
        if (model.isThisPlayerMoveNow(playerModel)) {
            playerModel.click(x, y);
        }
    }
}
