package ru.nsu.fit.borzov.chessserver.userauth;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserAuthService {
    private Map<String, String> sessionIdToLogin = new HashMap<>();

    public boolean register(String sessionId, String login) {
        if (sessionIdToLogin.containsKey(sessionId)) {
            return false;
        }
        sessionIdToLogin.put(sessionId, login);
        return true;
    }

    public String getLoginBySessionId(String sessionId) {
        return sessionIdToLogin.get(sessionId);
    }

    public String delete(String sessionId) {
        return sessionIdToLogin.remove(sessionId);
    }
}
