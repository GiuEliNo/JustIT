package it.dosti.justit.model.notification;



import it.dosti.justit.model.user.User;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Notification {
    private  Integer id;
    private  User from;
    private  User to;
    private  LocalDateTime createdAt;
    private  String message;
    private boolean read;


    public Notification(User from, User to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.createdAt = LocalDateTime.now(ZoneId.systemDefault());
        this.read = false;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isRead() {
        return read;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }


    public void markRead() {
        this.read = true;
    }

    public String getMessage() {
        return message;
    }
}
