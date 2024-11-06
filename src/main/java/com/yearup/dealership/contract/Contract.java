package com.yearup.dealership.contract;

import com.yearup.dealership.Vehicle;

import java.util.ArrayList;

public abstract class Contract {

    /**
     * Contract Information
     */

    protected String date;
    protected String customerName;
    protected String customerEmail;
    protected Vehicle vehicleSold;
    protected double totalPrice;
    protected double monthlyPayment;

    /**
     * Constructor for Contract object
     */

    public Contract(String date,
                    String customerName,
                    String customerEmail,
                    Vehicle vehicleSold) {
        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicleSold = vehicleSold;
    }

    /**
     * Getters
     */

    public String getDate() {
        return date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Vehicle getVehicleSold() {
        return vehicleSold;
    }

    /**
     * Setters
     */

    public void setDate(String date) {
        this.date = date;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setVehicleSold(Vehicle vehicleSold) {
        this.vehicleSold = vehicleSold;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setMonthlyPayment(float monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    /**
     * Abstract methods will return a computed value based on the type of contract
     */
    public abstract double getTotalPrice();

    public abstract double getMonthlyPayment();

    /**
     * Helper method designed to print all objects in the ArrayList
     */
    public static void displayAllContracts(ArrayList<Contract> contractsList) {
        for (Contract contract : contractsList) {
            // Use println to convert and print contract objects as a string,
            // otherwise objects will print as an ArrayList [brackets, commas]
            System.out.println(contract);
        }
    }


}