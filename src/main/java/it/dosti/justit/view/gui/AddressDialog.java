package it.dosti.justit.view.gui;

public class AddressDialog {
    String street;
    String city;
    String country;

    public AddressDialog(String street, String city, String country) {
        this.street = street;
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return street + ", " + city + ", " + country;
    }
}
