package it.dosti.justit.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ReviewCreatedDTO {
    private final String username;
    private final Integer shopId;
    private final LocalDateTime occurredAt;

    public ReviewCreatedDTO(String username, Integer shopId) {
        this.username = username;
        this.shopId = shopId;
        this.occurredAt = LocalDateTime.now(ZoneId.systemDefault());
    }

    public String getUsername() {
        return username;
    }

    public Integer getShopId() {
        return shopId;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}
