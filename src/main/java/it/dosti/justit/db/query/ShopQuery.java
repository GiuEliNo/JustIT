package it.dosti.justit.db.query;


public class ShopQuery {
    private ShopQuery() {}

    public static final String SELECT_ALL_SHOPS = "SELECT id, name, address, phone, email, description, openingHours, homeAssistance, latitude, longitude FROM Shop";
    public static final String SELECT_SHOP_BY_ID = "SELECT * FROM Shop WHERE id = ?";
    public static final String SELECT_ID_SHOP = "SELECT ID FROM Shop WHERE name = ?";
    public static final String SELECT_SHOP_IMAGE_BY_ID = "SELECT image FROM Shop WHERE id = ?";
    public static final String UPDATE_SHOP_NAME = "UPDATE Shop SET name = ? WHERE id = ?";
    public static final String UPDATE_ADDRESS_COORDINATES_SHOP = "UPDATE Shop SET address = ?, latitude = ?, longitude = ? WHERE id = ?";
    public static final String UPDATE_DESCRIPTION_SHOP = "UPDATE Shop SET description = ? WHERE id = ?";
    public static final String UPDATE_IMAGE_SHOP = "UPDATE Shop SET image = ? WHERE id = ?";
    public static final String UPDATE_EMAIL_SHOP = "UPDATE Shop SET email = ? WHERE id = ?";
    public static final String UPDATE_PHONE_SHOP = "UPDATE Shop SET phone = ? WHERE id = ?";
    public static final String UPDATE_OPENING_HOURS_SHOP = "UPDATE Shop SET openingHours = ? WHERE id = ?";
    public static final String UPDATE_HOME_ASSISTANCE_SHOP = "UPDATE Shop SET homeAssistance = ? WHERE id = ?";

}
