package it.dosti.justit.model.notification;



import it.dosti.justit.model.user.User;

import java.time.LocalDateTime;
import java.time.ZoneId;

public abstract class Notification {
    private  Integer id;
    private  User recipient;
    private  LocalDateTime createdAt;
    private  String message;
    private boolean read;


    protected Notification(User recipient, String message) {
        this.recipient = recipient;
        this.message = message;
        this.createdAt = LocalDateTime.now(ZoneId.systemDefault());
        this.read = false;
    }

    public void setRead(boolean b) {
        this.read = b;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isRead() {
        return read;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getRecipient() {
        return recipient;
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
