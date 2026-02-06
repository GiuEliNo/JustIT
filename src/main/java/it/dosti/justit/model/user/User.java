package it.dosti.justit.model.user;

public abstract class User {
    private Integer id;
    private String username;
    private String email;
    private String name;

    Integer getId() {
        return id;
    }
    void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    void setName(String name) {
        this.name = name;
    }


}
