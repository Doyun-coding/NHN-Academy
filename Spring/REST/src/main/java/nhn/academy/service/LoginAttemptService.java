package nhn.academy.service;

import java.util.HashMap;
import java.util.Map;

public class LoginAttemptService {

    private final int MAX_ATTEMPTS = 3;
    private Map<String, Integer> attemptsCache = new HashMap<>();

    public void fail(String username) {
        int attempts = attemptsCache.getOrDefault(username, 0);
        attempts++;
        attemptsCache.put(username, attempts);

        if (attempts >= MAX_ATTEMPTS) {
            System.out.println(username + " has failed to login 3 times.");
        }
    }

    public void success(String username) {
        attemptsCache.remove(username);
    }
}

