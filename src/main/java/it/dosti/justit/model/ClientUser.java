package it.dosti.justit.model;

public class ClientUser extends User {

    public ClientUser(Integer id, String name, String username, String email) {
        this.setId(id);
        this.setName(name);
        this.setUsername(username);
        this.setEmail(email);
    }
}
