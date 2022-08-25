package chess.playermodel;

import chess.dto.GameInfo;
import chess.dto.MoveDto;
import chess.figure.FigureColor;
import chess.move.Move;
import chess.websocket.WebsocketChannel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class OnlineWebSocketPlayerModel extends PlayerModel {
    private final GameInfo gameInfo;

    public OnlineWebSocketPlayerModel(GameInfo gameInfo, FigureColor myColor) {
        super(myColor);
        this.gameInfo = gameInfo;
        start();
    }

    private void start() {
        WebsocketChannel.subscribe(gameInfo, (moveDto) -> {
            model.doMove(this, moveDto.figureX, moveDto.figureY, moveDto.move);
        }, myColor);
    }

    @Override
    public void notificationMoveWasHappened(int figureX, int figureY, Move move) {
        super.notificationMoveWasHappened(figureX, figureY, move);
        WebsocketChannel.doMove(gameInfo, myColor.another(), new MoveDto(figureX, figureY, move));
    }
}
