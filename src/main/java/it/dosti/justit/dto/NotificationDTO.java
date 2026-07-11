package it.dosti.justit.dto;

import it.dosti.justit.model.notification.NotificationType;

import java.time.LocalDateTime;

public class NotificationDTO {

    private Integer id;
    private String fromUsername;
    private String toUsername;
    private String message;
    private LocalDateTime createdTime;
    private boolean read;



    public NotificationDTO(){
        // It's only needed by Jackson data binding
    }



    public void setId(Integer id) {
        this.id = id;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }
    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
    public void setRead(boolean read) {
        this.read = read;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Integer getId() {
        return id;
    }
    public String getFromUsername() {
        return fromUsername;
    }
    public String getToUsername() {
        return toUsername;
    }
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
    public boolean isRead() {
        return read;
    }
    public String getMessage() {
        return message;
    }
}
