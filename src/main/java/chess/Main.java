package chess;

import chess.adapter.OfflineMouseAdapter;
import chess.exception.InvalidArgumentsException;
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
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        var fieldView = new FieldView();
        var view = new View(fieldView);
        try {
            switch (args.length) {
                case 0 -> offline(fieldView);
                case 1 -> {
                    try {
                        int port = Integer.parseInt(args[0]);
                        onlineServer(fieldView, port);
                    } catch (NumberFormatException e) {
                        throw new InvalidArgumentsException();
                    }
                }
                case 2 -> {
                    try {
                        int port = Integer.parseInt(args[1]);
                        onlineClient(fieldView, args[0], port);
                    } catch (NumberFormatException e) {
                        throw new InvalidArgumentsException();
                    }
                }
                default -> throw new InvalidArgumentsException();
            }
        } catch (InvalidArgumentsException e) {
            System.out.println("Invalid arguments:");
            System.out.println("there are 3 options for arguments:");
            System.out.println("1) Offline game: no arguments");
            System.out.println("1) Online game host: port");
            System.out.println("1) Online game client: ip port");
            return;
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
