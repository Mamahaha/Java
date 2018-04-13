package org.led.study.javaxwebsocket.component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    private static Map<String, String> sessionUserMap = new ConcurrentHashMap<String, String>();
    private static Map<String, String> userPasswordMap = new ConcurrentHashMap<String, String>();

    public static void add(String session, String user, String password) {
        if (session == null || user == null || password == null) {
            return;
        }

        sessionUserMap.put(session, user);
        userPasswordMap.put(user, password);
    }

    public static void remove(String session) {
        if (session == null) {
            return;
        }

        String user = sessionUserMap.get(session);
        if (user != null) {
            sessionUserMap.remove(session);
            userPasswordMap.remove(user);
        }
    }

    public static boolean isSessionValid(String sessionId, String user) {
        return (sessionId != null && user != null && sessionUserMap.containsKey(sessionId) && user.equals(sessionUserMap.get(sessionId)));
    }

    public static boolean isUserValid(String user, String password) {
        return (user != null && password != null && password.equals(userPasswordMap.get(user)));
    }
}
