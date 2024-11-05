package com.yearup.dealership.contract;

import com.yearup.dealership.Vehicle;
import com.yearup.dealership.util.Console;

import java.util.ArrayList;

public abstract class Contract {

    /**
     * Contract Information
     */

  //  protected String type;
    protected String date;
    protected String customerName;
    protected String customerEmail;
    protected static Vehicle vehicleSold;


    /**
     * Constructor for Contract object
     */

    protected ArrayList<Contract> listOfContracts;

    public Contract(String date,
                    String customerName,
                    String customerEmail,
                    Vehicle vehicleSold) {

        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicleSold = vehicleSold;
        this.listOfContracts = new ArrayList<Contract>();

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
     * Abstract methods will return a computed value based on the type of contract
     */
    public abstract double getTotalPrice();

    public abstract double getMonthlyPayment();

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

   // public void setTotalPrice(float totalPrice) { this.totalPrice = totalPrice;}

   // public void setMonthlyPayment(float monthlyPayment) { this.monthlyPayment = monthlyPayment; }

    public ArrayList<Contract> getListOfContracts() {
        return listOfContracts;
    }

    //TODO: perform calculations to get monthly payment
    public void addNewContract() {
        ArrayList<Contract> newContract = new ArrayList<>();

            String choice = Console.PromptForString("Enter [S] to Sell or [L] to Lease vehicle");
            String contractType = choice.equalsIgnoreCase("S") ? "SALES" : "LEASE";

            String date = Console.PromptForString("Enter date (MM-dd-YYYY): ");
            String name = Console.PromptForString("Enter your name: ");
            String email = Console.PromptForString("Enter e-mail address: ");

            Vehicle vehicle = Vehicle.getVehicleDetails();
            Contract contract = null;
            if (contractType.equals("SALES")) {
               contract = SalesContract.processSalesContract(date, name, email, vehicle);
            } else {
                contract = LeaseContract.processLeaseContract(date, name, email, vehicle);
            }
            listOfContracts.add(contract);
    }
}
