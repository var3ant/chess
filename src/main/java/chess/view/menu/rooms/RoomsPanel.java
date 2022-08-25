package chess.view.menu.rooms;

import chess.GameInitializer;
import chess.dto.GameInfo;
import chess.view.MainView;
import chess.websocket.api.GameListApi;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class RoomsPanel extends JPanel {
    public List<GameInfo> gameInfoList;

    public RoomsPanel() {
        setLayout(new GridBagLayout());
        JList list = new JList();
        gameInfoList = GameListApi.getListOfGames();
        list.setListData(gameInfoList.stream().map((g) -> g.name).toArray());

        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(80, 250));
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 2;
        add(list, c);

        JButton connectBtn = new JButton("connect");
        connectBtn.addActionListener((e) -> {
            GameInfo gameInfo = gameInfoList.get(list.getSelectedIndex());
            System.out.println("element: " + gameInfo);
            gameInfo = GameListApi.connectToGame(gameInfo.id);
            if (gameInfo != null) {
                System.out.println("GAME OPENED");
                try {
                    GameInitializer.onlineWebsocketGame(gameInfo);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    throw new Error();
                }
            } else {
                System.out.println("NOT AVAILABLE");
                List<GameInfo> gameList = GameListApi.getListOfGames();
                list.setListData(gameList.toArray());
            }
        });

        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = 1;
        add(connectBtn, c);


        JButton createBtn = new JButton("create new game");
        createBtn.addActionListener((e) -> {
            MainView.open(new CreateGamePanel());
        });

        c.gridx = 2;
        c.gridy = 2;
        add(createBtn, c);
    }
}
