package com.yearup.dealership;

import com.yearup.dealership.util.ColorCodes;

public class Vehicle {
    private int vin;
    private int year;
    private String make;
    private String model;
    private String vehicleType;
    private String color;
    private int odometer;
    private double price;

    public Vehicle(int vin, int year, String make, String model, String vehicleType, String color, int odometer, double price) {
        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
        this.vehicleType = vehicleType;
        this.color = color;
        this.odometer = odometer;
        this.price = price;
    }

    // Getters for all fields
    public int getVin() {
        return vin;
    }

    public int getYear() {
        return year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getColor() {
        return color;
    }

    public int getOdometer() {
        return odometer;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        String colorString;
        if (color.equalsIgnoreCase("Red")) {
            colorString = ColorCodes.RED + color + ColorCodes.RESET;
        } else if (color.equalsIgnoreCase("White")) {
            colorString = ColorCodes.WHITE + color + ColorCodes.RESET;
        } else if (color.equalsIgnoreCase("Blue")) {
            colorString = ColorCodes.BLUE + color + ColorCodes.RESET;
        } else {
            colorString = color;
        }
        return vin + " | " + year + " | " + make + " | " + model + " | " + vehicleType + " | " + colorString + " | " + odometer + " | $" + price;

    }
}