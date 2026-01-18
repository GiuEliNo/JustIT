package it.dosti.justit.model;

public class Technician implements User{
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String name;
    private Integer shop;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getShop() {
        return shop;
    }
}
