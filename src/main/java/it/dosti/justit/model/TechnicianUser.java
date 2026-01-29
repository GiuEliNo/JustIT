package it.dosti.justit.model;

public class TechnicianUser extends User{
    private Integer shopId;

    public TechnicianUser(Integer id, String name, String username, String email, Integer shopId) {
        this.setId(id);
        this.setName(name);
        this.setUsername(username);
        this.setEmail(email);
        this.setShopId(shopId);
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}
