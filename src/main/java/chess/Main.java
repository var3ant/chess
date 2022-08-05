package chess;

import chess.adapter.OfflineMouseAdapter;
import chess.field.FieldReader;
import chess.figure.FigureColor;
import chess.playermodel.OfflinePlayerModel;
import chess.playermodel.OnlinePlayerModel;
import chess.playermodel.PlayerModel;
import chess.view.FieldView;
import chess.view.View;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        var fieldView = new FieldView();
        var view = new View(fieldView);

        switch (args.length) {
            case 0 -> offline(fieldView);
            case 1 -> onlineServer(fieldView, Integer.parseInt(args[0]));
            case 2 -> onlineClient(fieldView, args[0], Integer.parseInt(args[1]));
            default -> throw new IllegalArgumentException("invalid args");
        }

        JFrame frame = new JFrame();
        frame.add(view);
        frame.pack();
        frame.setFocusable(false);
        frame.setVisible(true);
    }

    public static Model createModel(FieldView fieldView, List<? extends PlayerModel> players) throws FileNotFoundException {
        return new Model(8, fieldView, players);
//        return new Model(FieldReader.fieldFromFile("default"), fieldView, players);
//        return new Model(FieldReader.fieldFromFile("debug"), fieldView, players);
    }

    public static void onlineServer(FieldView fieldView, int port) throws IOException {

        OfflinePlayerModel offlinePlayerModel = new OfflinePlayerModel(FigureColor.White);

        List<PlayerModel> players = List.of(
                offlinePlayerModel,
                new OnlinePlayerModel(FigureColor.Black, port)
        );

        var model = new Model(8, fieldView, players);

        fieldView.setModel(model);
        for (PlayerModel player : players) {
            player.setModel(model);
        }


        OfflineMouseAdapter adapter = new OfflineMouseAdapter(model, Collections.singletonList(offlinePlayerModel));
        fieldView.addOfflineListener(adapter);
    }

    public static void onlineClient(FieldView fieldView, String ip, int port) throws IOException {

        OfflinePlayerModel offlinePlayerModel = new OfflinePlayerModel(FigureColor.Black);

        List<PlayerModel> players = List.of(
                new OnlinePlayerModel(FigureColor.White, ip, port),
                offlinePlayerModel
        );

        var model = new Model(8, fieldView, players);

        fieldView.setModel(model);
        for (PlayerModel player : players) {
            player.setModel(model);
        }


        OfflineMouseAdapter adapter = new OfflineMouseAdapter(model, Collections.singletonList(offlinePlayerModel));
        fieldView.addOfflineListener(adapter);
    }

    public static void offline(FieldView fieldView) throws IOException {

        List<OfflinePlayerModel> players = List.of(
                new OfflinePlayerModel(FigureColor.White),
                new OfflinePlayerModel(FigureColor.Black)
        );
        var model = createModel(fieldView, players);

        fieldView.setModel(model);
        for (PlayerModel player : players) {
            player.setModel(model);
        }

        OfflineMouseAdapter adapter = new OfflineMouseAdapter(model, players);
        fieldView.addOfflineListener(adapter);
    }
}
