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


    public ClientUser(Integer id, String name, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;

    }


}
