package it.dosti.justit.model;

public class ClientUser implements User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String name;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public Integer getId() {
        return id;
    }

    public ClientUser(String username, String password, String email, String name) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
    }

    public ClientUser(Integer id, String username, String password, String email, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
    }


}
