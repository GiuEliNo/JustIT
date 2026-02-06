package it.dosti.justit.model.user;

import it.dosti.justit.model.Coordinates;

public class ClientUser extends User {

    private String address;
    private Coordinates coordinates;

    public ClientUser(Integer id, String name, String username, String email, String address, Coordinates coordinates) {
        this.setId(id);
        this.setName(name);
        this.setUsername(username);
        this.setEmail(email);
        this.setAddress(address);
        this.coordinates = coordinates;
    }

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
