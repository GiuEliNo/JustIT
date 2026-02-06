package it.dosti.justit.model;

import it.dosti.justit.model.user.User;

public class Credentials {
    private User user;
    private String password;

    public Credentials(User user, String password) {
        this.user = user;
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
