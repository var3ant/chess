package chess.websocket.dto;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.concurrent.CompletableFuture;

public class Pair {
    public CompletableFuture<Object> data;
    public TypeReference<?> clazz;

    public Pair(CompletableFuture<Object> data, TypeReference<?> clazz) {
        this.data = data;
        this.clazz = clazz;
    }
}
