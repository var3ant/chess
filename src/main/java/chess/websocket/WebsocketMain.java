package chess.websocket;

import chess.view.menu.rooms.RoomsPanel;
import chess.view.MainView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class WebsocketMain {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        WebsocketChannel.connect();

        MainView.initFrame();
        MainView.open(new RoomsPanel());
    }

//        java.util.List<PlayerModel> players;
//        OfflinePlayerModel offlinePlayerModel;
//        if (Objects.equals(args[0], "s")) {
//            offlinePlayerModel = new OfflinePlayerModel(FigureColor.White);
//
//            players = java.util.List.of(
//                    offlinePlayerModel,
//                    new OnlineWebSocketPlayerModel(FigureColor.Black)
//            );
//        } else {
//            offlinePlayerModel = new OfflinePlayerModel(FigureColor.Black);
//
//            players = java.util.List.of(
//                    new OnlineWebSocketPlayerModel(FigureColor.White),
//                    offlinePlayerModel
//            );
//        }
//        GameInitializer.onlineWebsocket(players, offlinePlayerModel);
//    }
}
