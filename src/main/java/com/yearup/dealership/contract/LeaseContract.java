package com.yearup.dealership.contract;

import com.yearup.dealership.Calculation;
import com.yearup.dealership.Vehicle;

public class LeaseContract extends Contract {

    /**
     * Lease Contract Information
     */
    private double expectEndingValue; //  e = p * (50/100)
    private double leaseFee; // f = p * (7/100)'

    private double expectEndingValuePercentage = 0.5;
    private double leaseFeePercentage = 0.07;

    public LeaseContract(String date,
                         String customerName,
                         String customerEmail,
                         Vehicle vehicleSold) {
        super(date, customerName, customerEmail, vehicleSold);
        this.expectEndingValue = vehicleSold.getPrice() * expectEndingValuePercentage;
        this.leaseFee = vehicleSold.getPrice() * leaseFeePercentage;
    }

    public LeaseContract(String date,
                         String customerName,
                         String customerEmail,
                         Vehicle vehicleSold,
                         double expectEndingValue,
                         double leaseFee) {
        super(date, customerName, customerEmail, vehicleSold);
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
        double financeRate = 0.04;
        double financeTerm = 36;
        return Calculation.calculateLoanPayment(getTotalPrice(), financeRate, financeTerm);
    }

    public static LeaseContract processLeaseContract(String date, String name, String email, Vehicle vehicle) {

        LeaseContract newLease = new LeaseContract(
                date,
                name,
                email,
                vehicle);

        return newLease;
    }

    @Override
    public String toString(){
        return "LEASE" +
                "|" + this.date +
                "|" + this.customerName +
                "|" + this.customerEmail +
                "|" + this.vehicleSold.getVin() +
                "|" + this.vehicleSold.getYear() +
                "|" + this.vehicleSold.getMake() +
                "|" + this.vehicleSold.getModel() +
                "|" + this.vehicleSold.getVehicleType() +
                "|" + this.vehicleSold.getColor() +
                "|" + this.vehicleSold.getOdometer() +
                "|" + this.vehicleSold.getPrice() +
                "|" + this.expectEndingValue +
                "|" + this.leaseFee +
                "|" + getTotalPrice() +
                "|" + getMonthlyPayment() + "\n";

    }
}


