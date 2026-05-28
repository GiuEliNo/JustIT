package it.dosti.justit.bean;

import it.dosti.justit.model.Coordinates;

public class ClientRegisterBean extends RegisterBean {
    private String address;
    private Coordinates coordinates;


    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
