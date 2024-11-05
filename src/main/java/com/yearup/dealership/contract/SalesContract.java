package com.yearup.dealership.contract;

import java.util.ArrayList;


import com.yearup.dealership.Contract;
import com.yearup.dealership.Vehicle;

public class SalesContract extends Contract {

    /**
     * Sales Contract Information
     */

    private final double salesTaxPercentage = 0.05;
    private double salesTax; // %5
    private double recordingFee; // $100.00
    private double processingFee; // a fee of $295 if vehicle under $10k, else $495
    private boolean isFinance; // do they want to finance (yes/no)

    public SalesContract(String date,
                         String customerName,
                         String customerEmail,
                         Vehicle vehicleSold,
                         boolean isFinance)
    {
        super(date, customerName, customerEmail, vehicleSold);
        this.salesTax = vehicleSold.getPrice() * salesTaxPercentage;
        this.recordingFee = 100;
        this.processingFee = (vehicleSold.getPrice() < 10000) ? 295 : 495;
        this.isFinance = isFinance;
    }

    public SalesContract(String date,
                         String customerName,
                         String customerEmail,
                         Vehicle vehicleSold,
                         double salesTax,
                         double recordingFee,
                         double processingFee,
                         boolean isFinance) {
        super(date, customerName, customerEmail, vehicleSold);
        this.salesTax = salesTax;
        this.isFinance = isFinance;
        this.processingFee = processingFee;
        this.recordingFee = recordingFee;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public boolean isFinance() {
        return isFinance;
    }

    public double getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(double salesTax) {
        this.salesTax = salesTax;
    }

    public void setRecordingFee(double recordingFee) {
        this.recordingFee = recordingFee;
    }

    public void setFinance(boolean finance) {
        isFinance = finance;
    }

    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }

    /*
    public void processSalesContract(){

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

        SalesContract newSales = new SalesContract(date,
                name,
                email,
                vehicleSold,
                totalPrice,
                monthlyPayment,
                salesTax,
                recordingFee,
                processingFee,
                finance);

    }

     */
    // 4.25% for 48mos if the price is $10k+ , else 5.25% for 24mos
    @Override
    public double getTotalPrice(){
        return getVehicleSold().getPrice() + this.salesTax + this.processingFee + this.recordingFee;
    }

    @Override
    public double getMonthlyPayment(){
        double payment = 0;
        boolean finance = true;
        if(finance) {
            int months = 0;
            double interestRate = 0;
            if(months >= 48 && vehicleSold.getPrice() > 10000){
                interestRate = 4.25 / 100;
                payment = ((vehicleSold.getPrice() / 2) * interestRate) /48;
            }
            else if ( months <= 24 && vehicleSold.getPrice() < 10000) {
                interestRate = 5.25 / 100;
                payment = ((vehicleSold.getPrice() / 2) * interestRate) /48;
            }
            return payment;
        }
        else {
            setFinance(false);
            System.out.println("Finance declined");
            return 0;
        }
    }

    @Override
    public String toString() {

        String financeDecision = isFinance ? "YES" : "NO";

        // 0  1  2  3  4  5  6  7  8  9  10  11   12   13   14  15   16  17
        return String.format("SALES|%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%.2f|%s|%.2f",
                this.date, //1
                this.customerName,//2
                this.customerEmail, //3
                this.vehicleSold.getVin(),//4
                this.vehicleSold.getYear(),//5
                this.vehicleSold.getMake(),//6
                this.vehicleSold.getModel(),//7
                this.vehicleSold.getVehicleType(),//8
                this.vehicleSold.getColor(),//9
                this.vehicleSold.getOdometer(),//10
                this.vehicleSold.getPrice(), //11
                this.salesTax, //12
                this.recordingFee, //13
                this.processingFee, //14
                getTotalPrice(), //15
                financeDecision, //16
                getMonthlyPayment()); //17
    }

}