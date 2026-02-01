package it.dosti.justit.DB.query;

public class RegisterQuery {
    private RegisterQuery() {}

    public static final String REGISTER_SHOP = "INSERT INTO SHOP (name,address,phone,email,description,image,openingHours,homeAssistance,latitude,longitude) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String REGISTER_USER = "INSERT INTO User(name, username, email, password) VALUES (?, ?, ?, ?)";

    public static final String REGISTER_TECHNICIAN = "INSERT INTO TECHNICIAN (username, password, email, name, shop) " +
            "VALUES (?, ?, ?, ?, (SELECT id FROM Shop WHERE name = ?))";

}
