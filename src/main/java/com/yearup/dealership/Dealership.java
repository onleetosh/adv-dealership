package com.yearup.dealership;

import com.yearup.dealership.util.Console;

import java.util.ArrayList;

public class Dealership {

    private String name;
    private String address;
    private String phone;

    private ArrayList<Vehicle> inventory;

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<Vehicle>();
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

    public ArrayList<Vehicle> getInventory() {
        return inventory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Vehicle> getAllVehicles() {
        return this.inventory;
    }


    /**
     * Method overloading - pass a Vehicle object as its parameter and add object
     */
    public void addVehicleToInventory(Vehicle add){
        inventory.add(add);
    }

    /**
     * Method overloading - Pass no parameters but instead prompt user for information
     */
    public void addVehicleToInventory(){
        int vin = Console.PromptForInt("Enter Vin: ");
        int year = Console.PromptForInt("Enter year: ");
        String make = Console.PromptForString("Enter make: ");
        String model = Console.PromptForString("Enter model: ");
        String vehicleType = Console.PromptForString("Enter vehicle type: ");
        String color = Console.PromptForString("Enter color:  ");
        int odometer = Console.PromptForInt("Enter odometer: ");
        double price = Console.PromptForDouble("Enter price: ");
        Vehicle vehicle = new Vehicle(vin,year, make, model, vehicleType, color, odometer, price);
        inventory.add(vehicle);
    }

    /**
     * Method overloading - pass a Vehicle object as its parameter and remove object
     */
    public void removeVehicleFromInventory(Vehicle remove){
        inventory.remove(remove);
    }

    /**
     * Method overloading - Pass no parameters but instead prompt user for information
     */
    public void removeVehicleFromInventory(){
        int vin = Console.PromptForInt("Enter vin number");
        Vehicle remove = this.getVehicleByVin(vin);
        inventory.remove(remove);
    }

    /**
     * Helper methods designed to loop through the ArrayList and return matching objects
     */
    public ArrayList<Vehicle> getVehiclesByPrice(double min, double max){
        ArrayList<Vehicle> result = new ArrayList<Vehicle>();
        for(Vehicle vehicle : this.inventory){
            if(vehicle.getPrice() >= min && vehicle.getPrice() <= max){
                result.add(vehicle);
            }
        }
        return result;
    }
    public ArrayList<Vehicle> getVehicleByColor(String color){
        ArrayList<Vehicle> result = new ArrayList<>();
        for (Vehicle vehicle : inventory){
            if(vehicle.getColor().equalsIgnoreCase(color)) {
                result.add(vehicle);
            }
        }
        return result;
    }
    public ArrayList<Vehicle> getVehiclesByType(String vehicleType){
        ArrayList<Vehicle> result = new ArrayList<>();
        for (Vehicle vehicle : inventory){
            if(vehicle.getVehicleType().equalsIgnoreCase(vehicleType)) {
                result.add(vehicle);
            }
        }
        return result;
    }
    public ArrayList<Vehicle> getVehicleByYear(int min, int max) {
        ArrayList<Vehicle> result = new ArrayList<Vehicle>();
        for(Vehicle vehicle : this.inventory){
            if(vehicle.getYear() >= min && vehicle.getYear() <= max){
                result.add(vehicle);
            }
        }
        return result;
    }
    public ArrayList<Vehicle> getVehiclesByMileage(int min, int max){
        ArrayList<Vehicle> result = new ArrayList<>();
        for (Vehicle vehicle : inventory){
            if(vehicle.getOdometer() > min && vehicle.getOdometer() < max) {
                result.add(vehicle);
            }
        }
        return result;
    }
    public ArrayList<Vehicle> getVehiclesByMakeModel(String make, String model) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        for (Vehicle vehicle: inventory) {
            if (vehicle.getMake().equalsIgnoreCase(make) && vehicle.getModel().equalsIgnoreCase(model)) {
                vehicles.add(vehicle);
            }
        }
        return vehicles;
    }

    public Vehicle getVehicleByVin(int vin) {
        for(Vehicle v : this.getInventory()){
            if(v.getVin() == vin){
                return v;
            }
        }
        return null;
    }

    public ArrayList<Vehicle> searchVehicleByVin(int vin) {
        ArrayList<Vehicle> result = new ArrayList<>();
        for (Vehicle vehicle : inventory){
            if(vehicle.getVin() == vin) {
                result.add(vehicle);
            }
        }
        return result;
    }

}
