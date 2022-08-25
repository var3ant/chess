package chess.view.menu.rooms;

import chess.GameInitializer;
import chess.websocket.api.GameListApi;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class WaitForGameThread extends Thread {
    private static final String waitingText = "waiting for a start";
    private final String gameId;
    private final JLabel waitingLabel;

    public WaitForGameThread(String gameId, JLabel waitingLabel) {
        this.gameId = gameId;
        this.waitingLabel = waitingLabel;
        waitingLabel.setText(waitingText);
    }

    @Override
    public void run() {
        StringBuilder dots = new StringBuilder("");
        super.run();
        var gameInfo = GameListApi.waitForStart(gameId);
        while (!gameInfo.isDone()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (dots.length() != 3) {
                dots.append(".");
            } else {
                dots.setLength(0);
            }
            waitingLabel.setText(waitingText + dots);
        }
        try {
            GameInitializer.onlineWebsocketGame(gameInfo.get());
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new Error();
        }
    }
}
