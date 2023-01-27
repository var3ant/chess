package ru.nsu.fit.borzov.chessserver.dto;

import java.util.List;

public class GameInfoDto {
    public String id;
    public String name;
    public List<String> users;

    public GameInfoDto(String id, String name, List<String> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public boolean addUser(String login) {
        if (users.size() < 2) {
            users.add(login);
            return true;
        }
        return false;
    }
}
