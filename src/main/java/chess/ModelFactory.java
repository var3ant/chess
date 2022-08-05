package chess;

import chess.figure.FigureColor;
import chess.playermodel.OfflinePlayerModel;
import chess.playermodel.OnlinePlayerModel;
import chess.playermodel.PlayerModel;
import chess.view.FieldView;

import java.io.IOException;

public class ModelFactory {
    public static PlayerModel[] getOfflinePlayers() {

        return new PlayerModel[]{
                new OfflinePlayerModel(FigureColor.White),
                new OfflinePlayerModel(FigureColor.Black)
        };
    }

    public static PlayerModel[] getOnlinePlayers(int port) throws IOException {

        return new PlayerModel[]{
                new OfflinePlayerModel(FigureColor.White),
                new OnlinePlayerModel(FigureColor.Black, port)
        };
    }

    public static PlayerModel[] getOnlinePlayers(int port, String ip) throws IOException {

        return new PlayerModel[]{
                new OfflinePlayerModel(FigureColor.White),
                new OnlinePlayerModel(FigureColor.Black, ip, port)
        };
    }
}
