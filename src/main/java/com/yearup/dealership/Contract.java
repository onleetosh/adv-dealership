package com.yearup.dealership;

import com.yearup.dealership.contract.LeaseContract;
import com.yearup.dealership.contract.SalesContract;
import com.yearup.dealership.filemanager.ContractFileManager;
import com.yearup.dealership.util.Console;
import com.yearup.dealership.util.UI;

import java.util.ArrayList;

public abstract class Contract {

    /**
     * Contract Information
     */

  //  protected String type;
    protected String contractType;
    protected String date;
    protected String customerName;
    protected String customerEmail;
    protected Vehicle vehicleSold;
    protected double totalPrice;
    protected double monthlyPayment;

    /**
     * Constructor for Contract object
     */

    protected ArrayList<Contract> listOfContracts;

    public Contract(String contractType,
                    String date,
                    String customerName,
                    String customerEmail,
                    Vehicle vehicleSold,
                    double totalPrice,
                    double monthlyPayment) {

        this.contractType = contractType;
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

    public String getContractType() {
        return contractType;
    }

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

    public void setContractType(String contractType) {
        this.contractType = contractType;
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

            Vehicle vehicle = getVehicleDetails();
            Contract contract;
            if (contractType.equals("SALES")) {
               contract = processSalesContract(date, name, email, vehicle);
            } else {
                contract = processLeaseContract(date, name, email, vehicle);
            }
            newContract.add(contract);

    }

    private Vehicle getVehicleDetails() {
        System.out.println("Enter vehicle details:");
        int vin = Console.PromptForInt("Enter Vin: ");
        int year = Console.PromptForInt("Enter year: ");
        String make = Console.PromptForString("Enter make: ");
        String model = Console.PromptForString("Enter model: ");
        String vehicleType = Console.PromptForString("Enter vehicle type: ");
        String color = Console.PromptForString("Enter color: ");
        int odometer = Console.PromptForInt("Enter odometer: ");
        double price = Console.PromptForDouble("Enter price: ");
        return new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
    }

    private SalesContract processSalesContract(String date, String name, String email, Vehicle vehicle) {
        double totalPrice = getTotalPrice();
        double monthlyPayment = getMonthlyPayment();
        double salesTax = vehicleSold.getPrice() * (5.0/100);
        double processingFee = (vehicleSold.getPrice() < 10000) ? (295) : (495);
        double recordingFee = 100;
        boolean finance = Console.PromptForYesNo("Interested in financing?");

        SalesContract newSales = new SalesContract(
                "SALES",
                date,
                name,
                email,
                vehicle,
                salesTax,
                recordingFee,
                processingFee,
                totalPrice,
                finance,
                monthlyPayment);

        return  newSales;
    }

    private  LeaseContract processLeaseContract(String date, String name, String email, Vehicle vehicle) {
      double expectEndValue = vehicleSold.getPrice() * (50/100);
      double leaseFee =  vehicleSold.getPrice() * (7.0/100);
      double total = getTotalPrice();
      double monthlyPayment = getMonthlyPayment();

        LeaseContract newLease = new LeaseContract(
                "LEASE",
                date,
                name,
                email,
                vehicle,
                expectEndValue,
                leaseFee,
                total,
                monthlyPayment);

        return newLease;
    }

}
