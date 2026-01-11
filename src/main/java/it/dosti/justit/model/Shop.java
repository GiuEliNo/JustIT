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

    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return name;
    }
}
