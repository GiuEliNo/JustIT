package it.dosti.justit.bean;

public class UserBean {
    private String username;
    private String email;
    private String name;
    private String password;

    public UserBean(String name, String email, String username) {
        this.setEmail(email);
        this.setName(name);
        this.setUsername(username);
    }

    public UserBean() {
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
