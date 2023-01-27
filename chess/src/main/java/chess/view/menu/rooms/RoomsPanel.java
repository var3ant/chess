package chess.view.menu.rooms;

import chess.GameInitializer;
import chess.dto.GameInfo;
import chess.view.MainView;
import chess.websocket.api.GameListApi;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class RoomsPanel extends JPanel {
    private List<GameInfo> gameInfoList;
    private JButton connectBtn;
    public RoomsPanel() {
        setLayout(new GridBagLayout());
        JList list = new JList();
        gameInfoList = GameListApi.getListOfGames();
        list.setListData(gameInfoList.stream().map((g) -> g.name).toArray());

        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(140, 250));
        GridBagConstraints c = new GridBagConstraints();

        JButton updateBtn = new JButton("update");
        updateBtn.addActionListener((e) -> {
            connectBtn.setEnabled(false);
            gameInfoList = GameListApi.getListOfGames();
            list.setListData(gameInfoList.stream().map((g) -> g.name).toArray());
        });

        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        add(updateBtn, c);


        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 2;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        add(listScroller, c);


        connectBtn = new JButton("connect");
        connectBtn.setEnabled(false);
        connectBtn.addActionListener((e) -> {
            GameInfo gameInfo = gameInfoList.get(list.getSelectedIndex());
            System.out.println("element: " + gameInfo);
            gameInfo = GameListApi.connectToGame(gameInfo.id);
            if (gameInfo != null && !Objects.equals(gameInfo.name, "")) {
                System.out.println("GAME OPENED");
                try {
                    GameInitializer.onlineWebsocketGame(gameInfo);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    throw new Error();
                }
            } else {
                System.out.println("NOT AVAILABLE");
                connectBtn.setEnabled(false);
                gameInfoList = GameListApi.getListOfGames();
                list.setListData(gameInfoList.stream().map((g) -> g.name).toArray());
            }
        });

        list.addListSelectionListener(ev -> {
            if (!ev.getValueIsAdjusting()) {
                connectBtn.setEnabled(true);
            }
        });

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(connectBtn, c);


        JButton createBtn = new JButton("create new game");
        createBtn.addActionListener((e) -> {
            MainView.open(new CreateGamePanel());
        });

        c.gridx = 1;
        c.gridy = 3;
        add(createBtn, c);
    }
}
