package it.dosti.justit.db.query;

public class RegisterQuery {
    private RegisterQuery() {}

    public static final String REGISTER_SHOP = "INSERT INTO SHOP (name,address,phone,email,description,image,openingHours,homeAssistance,latitude,longitude) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String REGISTER_USER = "INSERT INTO User(name, username, email, password, address, latitude, longitude) VALUES (?, ?, ?, ?, ?, ? ,?)";

    public static final String REGISTER_TECHNICIAN = "INSERT INTO TECHNICIAN (username, password, email, name, shop) " +
            "VALUES (?, ?, ?, ?, ?)";

}
