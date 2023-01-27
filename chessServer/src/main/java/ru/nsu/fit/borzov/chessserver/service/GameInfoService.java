package ru.nsu.fit.borzov.chessserver.service;

import lombok.Setter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.nsu.fit.borzov.chessserver.dto.GameInfoDto;
import ru.nsu.fit.borzov.chessserver.dto.GameStatus;
import ru.nsu.fit.borzov.chessserver.userauth.UserAuthService;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class GameInfoService {
    private final UserAuthService userAuthService;
    @Setter
    private SimpMessagingTemplate messagingTemplate;
    private final Map<String, GameInfoDto> games = new HashMap<>();
    private final AtomicLong nextGameId = new AtomicLong(0);

    public GameInfoService(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    public List<GameInfoDto> getAll() {
        return List.copyOf(games.values());
    }

    public List<GameInfoDto> getAvailableGames() {
        return games.values().stream().filter((game) -> game.users.size() < 2).collect(Collectors.toList());
    }

//    public String createGame(String name) {
//        long id = nextGameId.getAndAdd(1);
//        GameInfoDto gameInfoDto = new GameInfoDto(Long.toString(id), name, List.of());
//        games.put(gameInfoDto.id, gameInfoDto);
//        return gameInfoDto.id;
//    }

    public GameInfoDto createGameAndConnect(String userId, String name) {
        long id = nextGameId.getAndAdd(1);
        List<String> users = new ArrayList<>();
        users.add(userId);
        GameInfoDto gameInfoDto = new GameInfoDto(Long.toString(id), name, users);
        games.put(gameInfoDto.id, gameInfoDto);
        return gameInfoDto;
    }

    public boolean addUserToGame(String login, String id) {
        if (!games.containsKey(id)) {
            return false;
        }
        return games.get(id).addUser(login);
    }

    public boolean checkRules(String gameId, String userId, String color, String move) {
        List<String> users = games.get(gameId).users;
        int userNum = -1;
        for (int i = 0; i < users.size(); i++) {
            var user = users.get(i);
            if (user.equals(userId)) {
                userNum = i;
                break;
            }
        }
        if (userNum == -1) {
            return false;
        }
        return (userNum % 2 == 0) == (color.equals("w"));
    }

    public GameInfoDto getGame(String id) {
        return games.get(id);
    }

    public void surrender(String gameId, String login) {
        games.remove(gameId);
        messagingTemplate.convertAndSend("/topic/game/" + gameId + "/meta", GameStatus.SURRENDER);
    }

    public void deleteFromGamesBySessionId(String sessionId) {
        leftFromGames(userAuthService.getLoginBySessionId(sessionId));
    }

    public void leftFromGame(String loginId, String gameId) {
        games.remove(gameId);
        messagingTemplate.convertAndSend("/topic/game/" + gameId + "/meta", GameStatus.LEFT.name());
    }

    public void leftFromGames(String loginId) {
        List<GameInfoDto> gamesWithUser = games.values().stream()
                .filter(gameInfoDto -> gameInfoDto.users.contains(loginId))
                .collect(Collectors.toList());
        for (GameInfoDto gameInfoDto : gamesWithUser) {
            leftFromGame(loginId, gameInfoDto.id);
        }
    }
}
