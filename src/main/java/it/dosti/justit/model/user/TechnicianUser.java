package it.dosti.justit.model.user;

public class TechnicianUser extends User {
    private Integer shopId;

    public TechnicianUser(Integer id, String name, String username, String email, Integer shopId) {
        this.setId(id);
        this.setName(name);
        this.setUsername(username);
        this.setEmail(email);
        this.setShopId(shopId);
    }

    public TechnicianUser(String name, String username, String email, Integer shopId) {
        this.setName(name);
        this.setUsername(username);
        this.setEmail(email);
        this.setShopId(shopId);
    }

    public TechnicianUser(String username) {
        this.setUsername(username);
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}
