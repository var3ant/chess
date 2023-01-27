package chess.websocket;

//import org.eclipse.jetty.websocket.api.Session;
//import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
//import org.eclipse.jetty.websocket.client.WebSocketClient;

import chess.dto.GameInfo;
import chess.dto.MoveDto;
import chess.figure.FigureColor;
import chess.websocket.dto.Pair;
import chess.websocket.dto.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.util.SerializationUtils;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class WebsocketChannel {
    public static final String PREFIX = "/app/";
    private static StompSession stomp = null;
    private static Map<Long, Pair> responseMap = new HashMap<>();
    private static AtomicLong responseId = new AtomicLong(0);
    public static final UUID loginId = UUID.randomUUID();

    public static void connect() throws ExecutionException, InterruptedException {
        if (stomp == null) {
            WebSocketClient client = new StandardWebSocketClient();

            WebSocketStompClient stompClient = new WebSocketStompClient(client);
            stompClient.setMessageConverter(new MappingJackson2MessageConverter());

            CompletableFuture<Boolean> isSuccess = new CompletableFuture<>();
            stomp = stompClient.connect("ws://localhost:8080/ws", new StompSessionHandler() {
                @Override
                public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                    isSuccess.complete(true);
                }

                @Override
                public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
                    isSuccess.complete(false);
                }

                @Override
                public void handleTransportError(StompSession stompSession, Throwable throwable) {

                }

                @Override
                public Type getPayloadType(StompHeaders stompHeaders) {
                    return null;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                }
            }).get();
            if (!isSuccess.get()) {
                throw new Error();
            }
//            headers.set("my-login-user",);
            System.out.println("login: " + loginId);
            stomp.subscribe(getHeader("/user/queue/response"), new StompSessionHandler() {
                @Override
                public void afterConnected(StompSession session, StompHeaders connectedHeaders) {

                }

                @Override
                public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
                }

                @Override
                public void handleTransportError(StompSession stompSession, Throwable throwable) {

                }

                @Override
                public Type getPayloadType(StompHeaders stompHeaders) {
                    return Response.class;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                    Response response = (Response) payload;
                    System.out.println("response: " + ((Response<?>) payload).data);
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        byte[] json = mapper.writeValueAsBytes(response.data);
                        Pair pair = responseMap.get(response.id);
                        Object pojo = mapper.readValue(json, pair.clazz);
                        pair.data.complete(pojo);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new Error();
                    }

                }
            });

            String result = WebsocketChannel.sendAndGet(new TypeReference<>() {
            }, loginId, PREFIX + "register");
            System.out.println(result);
        }
    }

    private static StompHeaders getHeader(String path) {
        StompHeaders headers = new StompHeaders();
        headers.setDestination(path);
        headers.setLogin(loginId.toString());
        return headers;
    }

    public static void doMove(GameInfo game, FigureColor color, MoveDto moveDto) {
        String path = PREFIX + "game/" + game.id + "/move/" + color.colorPrefix;
        byte[] serialized = SerializationUtils.serialize(moveDto);
        send(path, serialized);
    }


    public static <T, R> R sendAndGet(TypeReference<R> response, T toSend, String path) throws ExecutionException, InterruptedException {
        Object result = sendWithPromise(response, toSend, path).get();
        return (R) result;
    }

    public static <T> void send(String path, T object) {
        stomp.send(getHeader(path), object);
    }

    public static <R, T> CompletableFuture<Object> sendWithPromise(TypeReference<R> response, T object, String path) {
        long id = responseId.getAndAdd(1);
        var promise = new CompletableFuture<>();
        responseMap.put(id, new Pair(promise, response));
        send(path, new Response(id, object));
        return promise;
    }

    public static <T> StompSession.Subscription subscribe(Class<T> clazz, Consumer<T> action, String path) {
        return stomp.subscribe(path, new StompSessionHandler() {
            @Override
            public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
                System.out.println("subscribed");
            }

            @Override
            public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
                System.out.println("not subscribed");
            }

            @Override
            public void handleTransportError(StompSession stompSession, Throwable throwable) {
                System.out.println("not subscribed");
            }

            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return clazz;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                action.accept((T) o);
            }
        });
    }

    public static void subscribe(GameInfo game, Consumer<MoveDto> action, FigureColor color) {
        String path = "/topic/game/" + game.id + "/move/" + color.colorPrefix;
        System.out.println("path to sub: " + path);
        CompletableFuture<Boolean> isSuccess = new CompletableFuture<>();
        var r = stomp.subscribe(path, new StompSessionHandler() {
            @Override
            public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
                System.out.println("subscribed");
                isSuccess.complete(true);
            }

            @Override
            public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
                System.out.println("not subscribed");
                isSuccess.complete(false);
            }

            @Override
            public void handleTransportError(StompSession stompSession, Throwable throwable) {
                System.out.println("not subscribed");
                isSuccess.complete(false);
            }

            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                byte[] encoded = ((String) o).getBytes();
                byte[] objBytes = Base64.getDecoder().decode(encoded);
                MoveDto dto = (MoveDto) SerializationUtils.deserialize((objBytes));
                action.accept(dto);
            }
        });
//        try {
//            if (!isSuccess.get()) {
//                throw new Error(path + ": connect error");
//            }
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//            throw new Error(path + ": connect error");
//        }
    }

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

    public static void disconnect() {
        stomp.disconnect();
    }
}
