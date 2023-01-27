package ru.nsu.fit.borzov.chessserver.controller;

import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import ru.nsu.fit.borzov.chessserver.dto.GameInfoDto;
import ru.nsu.fit.borzov.chessserver.dto.Response;
import ru.nsu.fit.borzov.chessserver.service.GameInfoService;
import ru.nsu.fit.borzov.chessserver.userauth.UserAuthService;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class GameController {
    private final SimpMessagingTemplate messagingTemplate;
    private final GameInfoService gameInfoService;
    private final UserAuthService userAuthService;
    private final static String PATH_RESPONSE = "/queue/response";

    public GameController(SimpMessagingTemplate messagingTemplate, GameInfoService gameInfoService, UserAuthService userAuthService) {
        this.messagingTemplate = messagingTemplate;
        this.gameInfoService = gameInfoService;
        this.userAuthService = userAuthService;
        gameInfoService.setMessagingTemplate(messagingTemplate);
    }

    @MessageMapping("/register")
    @SendToUser("/queue/response")
    public Response register(@Payload Response login, StompHeaderAccessor headerAccessor) {
        Logger.getLogger("game controller").info("register session " + headerAccessor.getSessionId() + " and login " + login.data);
        return new Response(login.id, Boolean.toString(userAuthService.register(headerAccessor.getSessionId(), (String) login.data)));
    }

    @MessageMapping("/game/{id}/move/{color}")
    @SendTo("/topic/game/{id}/move/{color}")
    public String move(@DestinationVariable("id") String id, @DestinationVariable("color") String color,
                       @Payload String message, StompHeaderAccessor headerAccessor) {
        Logger.getLogger("game controller").info(headerAccessor.getSessionId());
        if (!gameInfoService.checkRules(id, headerAccessor.getLogin(), color, message)) {
            throw new Error("failed checkRules");//TODO:
        }
        return message;
    }


    @MessageMapping("/game/connect")
    @SendToUser("/queue/response")
    public Response connect(@Payload Response request, @Header("login") String login) {
        String id = (String) request.data;
        var isSuccess = gameInfoService.addUserToGame(login, id);
        if(!isSuccess) {
            messagingTemplate.convertAndSend("/topic/game/" + id + "/meta", new GameInfoDto("","", List.of()));//TODO: переделать в эксепшн, когда реализую на фронте обработку ошибок.
            Logger.getLogger("log").info("game with id:" + id + " doesn't exist");

        } else {
            messagingTemplate.convertAndSend("/topic/game/" + id + "/meta", gameInfoService.getGame(id));
            Logger.getLogger("log").info("game id:" + gameInfoService.getGame(id).id);
        }
        return new Response(request.id, gameInfoService.getGame(id));
    }

//    @MessageMapping("/game/list")
//    @SendTo("/topic/game/list")
//    public List<GameInfoDto> list() {
//        return gameInfoService.getAll();
//    }

    @MessageMapping("/game/list/available")
//    @SendTo("/topic/game/list/available")
    @SendToUser("/queue/response")
    public Response listAvailable(Response request, @Header("login") String login) {
        var result = gameInfoService.getAvailableGames();
        return new Response(request.id, result);
    }

    @MessageMapping("/game/{id}/meta/surrender")
    public void surrender(Response name, @Header("login") String login, @DestinationVariable("id") String gameId) {
        Logger.getLogger("log").info(login);
        gameInfoService.surrender(gameId, login);
    }

    @MessageMapping("/game/{id}/meta/left")
    public void surrender(@Header("login") String login, @DestinationVariable("id") String gameId, @Payload String empty) {
        gameInfoService.leftFromGame(login, gameId);
    }


    @MessageMapping("/game/add")
    @SendToUser("/queue/response")
    public Response createAndJoin(Response name, @Header("login") String login) {
        Logger.getLogger("log").info(login);
        GameInfoDto gameInfoDto = gameInfoService.createGameAndConnect(login, (String) name.data);
        return new Response(name.id, gameInfoDto);
    }
}
