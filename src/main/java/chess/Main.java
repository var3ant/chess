package chess;

import chess.adapter.Adapter;
import chess.adapter.OfflineMouseAdapter;
import chess.figure.FigureColor;
import chess.playermodel.OfflinePlayerModel;
import chess.playermodel.PlayerModel;
import chess.view.FieldView;
import chess.view.View;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        var fieldView = new FieldView();
        var view = new View(fieldView);

        PlayerModel[] players;

        if (args.length == 0) {
            players = ModelFactory.getOfflinePlayers();
        } else if (args.length == 1) {
            players = ModelFactory.getOnlinePlayers(Integer.parseInt(args[0]));
        } else if (args.length == 2) {
            players = ModelFactory.getOnlinePlayers(Integer.parseInt(args[0]), args[1]);
        } else {
            throw new Error("invalid count of args");
        }

        var model = new Model(8, fieldView, players);

        fieldView.setModel(model);
        for (PlayerModel player : players) {
            player.setModel(model);
        }


        Adapter adapter = new OfflineMouseAdapter(model);


        JFrame frame = new JFrame();
        frame.add(view);
        frame.pack();
        frame.setFocusable(false);
        frame.setVisible(true);
    }
}
