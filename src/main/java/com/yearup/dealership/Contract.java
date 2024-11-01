package com.yearup.dealership;

import com.yearup.dealership.contract.LeaseContract;
import com.yearup.dealership.contract.SalesContract;
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
    protected Vehicle vehicleSold;
    protected double totalPrice; // amount paid before monthly
    protected double monthlyPayment;

    /**
     * Constructor for Contract object
     */

    protected ArrayList<Contract> listOfContracts;

    public Contract(String date,
                    String customerName,
                    String customerEmail,
                    Vehicle vehicleSold,
                    double totalPrice,
                    double monthlyPayment) {

        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicleSold = vehicleSold;
        this.totalPrice = totalPrice;
        this.monthlyPayment = monthlyPayment;
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

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setMonthlyPayment(float monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public ArrayList<Contract> getListOfContracts() {
        return listOfContracts;
    }

    //TODO: perform calculations to get monthly payment
    public void processTypeOfContract(){

        String choice = Console.PromptForString("enter [S] to Sell or [L] to Leasing vehicle");
        if (choice.equalsIgnoreCase("S")){

            String date = Console.PromptForString("Enter date (MM-dd-YYYY): ");
            String name = Console.PromptForString("Enter your name" );
            String email = Console.PromptForString("Enter e-mail address: ");

            System.out.println("Vehicle being sold.");
            int vin = Console.PromptForInt("Enter Vin: ");
            int year = Console.PromptForInt("Enter year: ");
            String make = Console.PromptForString("Enter make: ");
            String model = Console.PromptForString("Enter model: ");
            String vehicleType = Console.PromptForString("Enter vehicle type: ");
            String color = Console.PromptForString("Enter color:  ");
            int odometer = Console.PromptForInt("Enter odometer: ");
            double price = Console.PromptForDouble("Enter price: ");
            Vehicle vehicleSold = new Vehicle(vin,year, make, model, vehicleType, color, odometer, price);

            double totalPrice = Console.PromptForDouble("Enter total price: ");
            double monthlyPayment = Console.PromptForDouble("Enter monthly payment: ");
            double salesTax = Console.PromptForDouble("Enter sales tax");
            double recordingFee = Console.PromptForDouble("Enter recording fee");
            double processingFee = Console.PromptForDouble("Enter processing fee");
            boolean finance = Console.PromptForYesNo("Interested in financing?");

            SalesContract newSales = new SalesContract(
                    date,
                    name,
                    email,
                    vehicleSold,
                    salesTax,
                    recordingFee,
                    processingFee,
                    totalPrice,
                    finance,
                    monthlyPayment);


        }
        else if (choice.equalsIgnoreCase("L")){

            String date = Console.PromptForString("Enter date (MM-dd-YYYY): ");
            String name = Console.PromptForString("Enter your name" );
            String email = Console.PromptForString("Enter e-mail address: ");

            System.out.println("Vehicle being Leased.");
            int vin = Console.PromptForInt("Enter Vin: ");
            int year = Console.PromptForInt("Enter year: ");
            String make = Console.PromptForString("Enter make: ");
            String model = Console.PromptForString("Enter model: ");
            String vehicleType = Console.PromptForString("Enter vehicle type: ");
            String color = Console.PromptForString("Enter color:  ");
            int odometer = Console.PromptForInt("Enter odometer: ");
            double price = Console.PromptForDouble("Enter price: ");
            Vehicle vehicleSold = new Vehicle(vin,year, make, model, vehicleType, color, odometer, price);

            double totalPrice = Console.PromptForDouble("Enter total price: ");
            double monthlyPayment = Console.PromptForDouble("Enter monthly payment: ");
            double expectEndValue = Console.PromptForDouble("Expect end value");
            double leaseFee = Console.PromptForDouble("");
            LeaseContract newLease = new LeaseContract(date,
                    name,
                    email,
                    vehicleSold,
                    totalPrice,
                    monthlyPayment,
                    expectEndValue,
                    leaseFee);
        }
    }

    @Override
    public String toString(){
        return date + " | " + customerName + "|" + customerEmail;
    }
}
