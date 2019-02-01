package com.example.ricknmorty;

public class Character {
    // Member variables representing the attributs of a character.
    private int id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private String imageUrl;
    private String origin;
    private String location;


    public Character(int id, String name, String status, String species, String type, String gender, String imageUrl, String origin, String location) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.origin = origin;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public String getGender() {
        return gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public String getOrigin() {
        return origin;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }
}
