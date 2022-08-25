package chess.view.menu.rooms;

import chess.GameInitializer;
import chess.dto.GameInfo;
import chess.view.MainView;
import chess.websocket.api.GameListApi;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class CreateGamePanel extends JPanel {
    public CreateGamePanel() {
        setLayout(new GridBagLayout());


        JTextField gameNameField = new JTextField();
        gameNameField.setColumns(15);
        JLabel labelGame = new JLabel("name");

        JButton createBtn = new JButton("create and connect");
        createBtn.addActionListener((e) -> {
            GameInfo gameInfo = GameListApi.createGame(gameNameField.getText());
            if (gameInfo.id != null) {
                System.out.println("U ARE IN GAME!");
                MainView.open(new WaitForStartPanel(gameInfo.id));
            }
        });

        JButton backBtn = new JButton("back");
        backBtn.addActionListener((e) -> {
            MainView.open(new RoomsPanel());
        });

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;

        add(labelGame, c);


        c.gridx = 1;
        add(gameNameField, c);


        c.gridx = 0;
        c.gridy = 1;
        add(backBtn, c);

        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.EAST;
        add(createBtn, c);
    }
}
