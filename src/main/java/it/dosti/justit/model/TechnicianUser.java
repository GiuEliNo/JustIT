package it.dosti.justit.model;

public class TechnicianUser implements User{
    private Integer id;
    private String username;
    private String email;
    private String name;
    private Integer shopId;

    public TechnicianUser(Integer id, String name, String username, String email, Integer shopId) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.shopId = shopId;
    }

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


    public Integer getShopId() {
        return shopId;
    }
}
