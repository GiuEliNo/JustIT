package it.dosti.justit.model.user;

public abstract class User {
    private String username;
    private String email;
    private String name;

    public String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    protected User() {
    }


}
