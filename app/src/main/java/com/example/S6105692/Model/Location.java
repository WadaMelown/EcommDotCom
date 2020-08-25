package com.example.S6105692.Model;

import java.io.Serializable;

public class Location implements Serializable {

    private String name, description, amount, location, number;

    public Location(String name, String description, String amount, String location, String number) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.location = location;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public String getLocation() {
        return location;
    }

    public String getNumber() {
        return number;
    }
}
