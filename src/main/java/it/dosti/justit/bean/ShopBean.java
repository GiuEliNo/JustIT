package it.dosti.justit.bean;

import it.dosti.justit.model.Coordinates;

public class ShopBean {
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String description;
    private byte[] image;
    private String openingHours;
    private boolean homeAssistance;
    private String homeAssistanceMessage;
    private Coordinates coordinates;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public boolean isHomeAssistance() {
        return homeAssistance;
    }

    public void setHomeAssistance(boolean homeAssistance) {
        this.homeAssistance = homeAssistance;
    }

    public String getHomeAssistanceMessage() {
        return homeAssistanceMessage;
    }

    public void setHomeAssistanceMessage(String homeAssistanceMessage) {
        this.homeAssistanceMessage = homeAssistanceMessage;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
