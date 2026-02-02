package it.dosti.justit.db.query;


public class ShopQuery {
    private ShopQuery() {}

    public static final String SELECT_ALL_SHOPS = "SELECT * FROM Shop";
    public static final String SELECT_SHOP_BY_NAME = "SELECT * FROM Shop WHERE name = ?";
    public static final String SELECT_SHOP_BY_ID = "SELECT * FROM Shop WHERE id = ?";
    public static final String SELECT_ID_SHOP = "SELECT ID FROM Shop WHERE name = ?";

}
