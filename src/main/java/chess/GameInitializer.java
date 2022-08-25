package chess;

import chess.adapter.OfflineMouseAdapter;
import chess.dto.GameInfo;
import chess.figure.FigureColor;
import chess.playermodel.OfflinePlayerModel;
import chess.playermodel.OnlinePlayerModel;
import chess.playermodel.OnlineWebSocketPlayerModel;
import chess.playermodel.PlayerModel;
import chess.view.FieldView;
import chess.view.MainView;
import chess.view.View;
import chess.websocket.WebsocketChannel;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class GameInitializer {

    public void onlineServer(int port) throws IOException {
        MainView.initFrame();

        FieldView fieldView = new FieldView();
        View view = new View(fieldView, MainView.frame);

        OfflinePlayerModel offlinePlayerModel = new OfflinePlayerModel(FigureColor.White);

        java.util.List<PlayerModel> players = java.util.List.of(
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

        MainView.open(view);
    }

    public void onlineClient(String ip, int port) throws IOException {
        MainView.initFrame();

        FieldView fieldView = new FieldView();
        View view = new View(fieldView, MainView.frame);

        OfflinePlayerModel offlinePlayerModel = new OfflinePlayerModel(FigureColor.Black);

        java.util.List<PlayerModel> players = java.util.List.of(
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

        MainView.open(view);
    }

    public static void onlineWebsocketGame(GameInfo gameInfo) throws IOException {
        java.util.List<PlayerModel> players;
        OfflinePlayerModel offlinePlayerModel;
        if(gameInfo.users.get(0).equals(WebsocketChannel.loginId.toString())) {
            offlinePlayerModel = new OfflinePlayerModel(FigureColor.White);
            players = java.util.List.of(
                    offlinePlayerModel,
                    new OnlineWebSocketPlayerModel(gameInfo, FigureColor.Black)
            );
        } else {
            offlinePlayerModel = new OfflinePlayerModel(FigureColor.Black);

            players = java.util.List.of(
                    new OnlineWebSocketPlayerModel(gameInfo, FigureColor.White),
                    offlinePlayerModel
            );
        }

        FieldView fieldView = new FieldView();
        View view = new View(fieldView, MainView.frame);

        var model = new Model(8, fieldView, players);

        fieldView.setModel(model);
        for (PlayerModel player : players) {
            player.setModel(model);
        }


        OfflineMouseAdapter adapter = new OfflineMouseAdapter(model, Collections.singletonList(offlinePlayerModel));
        fieldView.addOfflineListener(adapter);

        MainView.open(view);
    }

    public static void onlineWebsocket(java.util.List<PlayerModel> players, OfflinePlayerModel offlinePlayerModel) throws IOException, ExecutionException, InterruptedException {
        MainView.initFrame();

        FieldView fieldView = new FieldView();
        View view = new View(fieldView, MainView.frame);

        var model = new Model(8, fieldView, players);

        fieldView.setModel(model);
        for (PlayerModel player : players) {
            player.setModel(model);
        }


        OfflineMouseAdapter adapter = new OfflineMouseAdapter(model, Collections.singletonList(offlinePlayerModel));
        fieldView.addOfflineListener(adapter);

        MainView.open(view);
    }

    public static void offline() throws IOException {
        MainView.initFrame();

        FieldView fieldView = new FieldView();
        View view = new View(fieldView, MainView.frame);

        java.util.List<OfflinePlayerModel> players = List.of(
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

        MainView.open(view);
    }


    public static Model createModel(FieldView fieldView, java.util.List<? extends PlayerModel> players) throws FileNotFoundException {
        return new Model(8, fieldView, players);
//        return new Model(FieldReader.fieldFromFile("default"), fieldView, players);
//        return new Model(FieldReader.fieldFromFile("debug"), fieldView, players);
    }
}
