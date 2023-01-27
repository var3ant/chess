package chess.view.menu.rooms;

import chess.view.MainView;
import chess.websocket.api.GameListApi;

import javax.swing.*;
import java.awt.*;

public class WaitForStartPanel extends JPanel {
    public WaitForStartPanel(String gameId) {
        setLayout(new GridBagLayout());
        JLabel waitingLabel = new JLabel();

        JButton backBtn = new JButton("back to list of games");
        backBtn.addActionListener((e) -> {
            GameListApi.leftTheGame(gameId);//TODO: with response coz game still in game list.
            MainView.open(new RoomsPanel());
        });

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;

        add(backBtn, c);


        c.gridy = 1;
        add(waitingLabel, c);
        new WaitForGameThread(gameId, waitingLabel).start();
    }
}
