package it.dosti.justit.model;

public class Shop {
    private String name;
    private String address;
    private String phone;
    private String email;
    private String description;
    private String image; // per ora lo tratto come se fosse un path, non se poi un blob nel db da vedere
    private String openingHours;  // forse in futuro un tipo date
    private boolean homeAssistance;

    public Shop(String name) {
        this.name = name;
    }

    public Shop(String name, String address, String phone, String email, String description, String image, String openingHours, boolean homeAssistance) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.description = description;
        this.image = image;
        this.openingHours = openingHours;
        this.homeAssistance = homeAssistance;
    }

    public String getName() {
        return name;
    }

    // javaFX chiama toString per mostrare l'oggetto in una lista
    @Override
    public String toString() {
        return name;
    }
}
