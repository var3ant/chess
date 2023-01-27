package chess.websocket.api;

import chess.dto.GameInfo;
import chess.websocket.WebsocketChannel;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static chess.websocket.WebsocketChannel.PREFIX;

public class GameListApi {
    public static GameInfo connectToGame(String gameId) {
        String path = PREFIX + "game/connect";
        try {
            return WebsocketChannel.sendAndGet(new TypeReference<>() {
            }, gameId, path);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public static List<GameInfo> getListOfGames() {
        String path = PREFIX + "game/list/available";
        try {
            return WebsocketChannel.sendAndGet(new TypeReference<>() {
            }, "empty", path);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public static GameInfo createGame(String name) {
        String path = PREFIX + "game/add";
        try {
            return WebsocketChannel.sendAndGet(new TypeReference<GameInfo>() {
            }, name, path);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public static CompletableFuture<GameInfo> waitForStart(String gameId) {
        CompletableFuture<GameInfo> completableFuture = new CompletableFuture<>();
        WebsocketChannel.subscribe(GameInfo.class, completableFuture::complete, "/topic/game/" + gameId + "/meta");
        return completableFuture;
    }

    public static void leftTheGame(String gameId) {
        WebsocketChannel.send(PREFIX + "game/" + gameId + "/meta/left", "empty");
    }
}
