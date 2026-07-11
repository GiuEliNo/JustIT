package it.dosti.justit.model.notification;

import it.dosti.justit.model.Shop;
import it.dosti.justit.model.user.User;

public class ReviewNotification extends Notification {
    private Shop shop;

    public ReviewNotification(User recipient, String message, Shop shop) {
        super(recipient, message);
        this.shop = shop;
    }

    public Shop getShop() {
        return shop;
    }
}
