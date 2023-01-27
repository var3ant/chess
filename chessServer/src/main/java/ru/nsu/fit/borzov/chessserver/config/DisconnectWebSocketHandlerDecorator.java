package ru.nsu.fit.borzov.chessserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import ru.nsu.fit.borzov.chessserver.service.GameInfoService;

public class DisconnectWebSocketHandlerDecorator extends WebSocketHandlerDecorator {

    private static final Logger logger = LoggerFactory.getLogger(DisconnectWebSocketHandlerDecorator.class);
    private final GameInfoService gameInfoService;

    public DisconnectWebSocketHandlerDecorator(WebSocketHandler delegate, GameInfoService gameInfoService) {
        super(delegate);
        this.gameInfoService = gameInfoService;
    }

    @Override
    public void handleMessage(final WebSocketSession session, final WebSocketMessage<?> message)
            throws Exception {
        if (message instanceof TextMessage) {
            TextMessage msg = (TextMessage) message;
            String payload = msg.getPayload();

            if (payload.contains("DISCONNECT")) {
                logger.info("ignore disconnect frame to avoid double disconnect: {}", message);
                return;
            }
        }

        super.handleMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        super.afterConnectionClosed(session, closeStatus);
        gameInfoService.deleteFromGamesBySessionId(session.getId());
        logger.info("connection closed");

    }
}