package it.dosti.justit.utils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class SessionManager {

    private static SessionManager instance;
    private Map<String, Session > activeSessions;
    private PersistencyType persistencyType;

    private SessionManager() {
        this.activeSessions = new ConcurrentHashMap<>();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public Session getActiveSession(String sessionId) {
        return activeSessions.get(sessionId);
    }

    public String createSession() {
        String sessionId = UUID.randomUUID().toString();
        activeSessions.put(sessionId, new Session(sessionId));
        return sessionId;
    }


    public void setPersistencyType(PersistencyType persistencyType) {
        this.persistencyType = persistencyType;
    }

    public PersistencyType getPersistencyType() {
        return persistencyType;
    }

    public void logout(String sessionId) {
        activeSessions.remove(sessionId);
    }

}
