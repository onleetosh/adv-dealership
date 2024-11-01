package com.yearup.dealership.contract;

import com.yearup.dealership.Contract;
import com.yearup.dealership.Vehicle;
import com.yearup.dealership.util.Console;

import java.util.ArrayList;

public class LeaseContract extends Contract {

    /**
     * Lease Contract Information
     */
    private double expectEndingValue; //  e = p * (50/100)
    private double leaseFee; // f = p * (7/100)

    public LeaseContract(String contractType,
                         String date,
                         String customerName,
                         String customerEmail,
                         Vehicle vehicleSold,
                         double totalPrice,
                         double monthlyPayment,
                         double expectEndingValue,
                         double leaseFee) {
        super(contractType, date, customerName, customerEmail, vehicleSold, totalPrice, monthlyPayment);
        this.expectEndingValue = expectEndingValue;
        this.leaseFee = leaseFee;
    }

    public double getExpectEndingValue() {
        return expectEndingValue;
    }

    public double getLeaseFee() {
        return leaseFee;
    }

    public void setLeaseFee(double leaseFee) {
        this.leaseFee = leaseFee;
    }

    public void setExpectEndingValue(double expectEndingValue) {
        this.expectEndingValue = expectEndingValue;
    }

    public ArrayList<Contract> getAllLeaseContracts(){
        return listOfContracts;
    }

    public void addLeaseContractToList(LeaseContract l){
        listOfContracts.add(l); //l for lease contract
    }

    /*
    public void processLeaseContract(){
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

     */
    //all leases are financed at 4.0% for 36 mos
    @Override
    public double getTotalPrice(){
        return expectEndingValue + leaseFee;
    }
    @Override
    public double getMonthlyPayment(){

        int months = 36;
        double interestRate = 4.0/100;

        double payment= vehicleSold.getPrice();

        return payment;
    }

    @Override
    public String toString(){
        setContractType("LEASE");
        return this.contractType +
                "|" + this.date +
                "|" + this.customerName +
                "|" + this.customerEmail +
                "|" + this.vehicleSold.getVin() +
                "|" + this.vehicleSold.getYear() +
                "|" + this.vehicleSold.getVehicleType() +
                "|" + this.vehicleSold.getColor() +
                "|" + this.vehicleSold.getOdometer() +
                "|" + this.vehicleSold.getPrice() +
                "|" + this.expectEndingValue +
                "|" + this.leaseFee +
                "|" + this.totalPrice +
                "|" + this.monthlyPayment + "\n";

    }
}


