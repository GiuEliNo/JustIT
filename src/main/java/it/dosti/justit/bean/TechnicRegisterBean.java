package it.dosti.justit.bean;

public class TechnicRegisterBean extends RegisterBean {
    private String shopName;

    private Integer shopId;

    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }


    public Integer getShopId() {
        return shopId;
    }
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}
