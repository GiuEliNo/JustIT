package it.dosti.justit.model;


public class Shop {
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String description;
    private byte[] image;
    private String openingHours;
    private boolean homeAssistance;
    private Coordinates coordinates;

    public Shop(String name) {
        this.name = name;
    }



    private Shop(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.address = builder.address;
        this.phone = builder.phone;
        this.email = builder.email;
        this.description = builder.description;
        this.image = builder.image;
        this.openingHours = builder.openingHours;
        this.homeAssistance = builder.homeAssistance;
        this.coordinates = builder.coordinates;

    }

    public static class Builder {


        private Integer id;
        private String name;
        private String address;
        private String phone;
        private String email;
        private String description;
        private byte[] image;
        private String openingHours;
        private boolean homeAssistance;
        private Coordinates coordinates;


        public Builder(String name) {
            this.name = name;
        }


        public Builder id(Integer id) {
            this.id = id;
            return this;
        }


        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;

        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder image(byte[] image) {
            this.image = image;
            return this;
        }
        public Builder openingHours(String openingHours) {
            this.openingHours = openingHours;
            return this;
        }
        public Builder homeAssistance(boolean homeAssistance) {
            this.homeAssistance = homeAssistance;
            return this;
        }

        public Builder coordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        public Shop build() {
            return new Shop(this);
        }


    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getImage() {
        return image;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public boolean isHomeAssistance() {
        return homeAssistance;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    // javaFX chiama toString per mostrare l'oggetto in una lista
    @Override
    public String toString() {
       return name;
    }
}
