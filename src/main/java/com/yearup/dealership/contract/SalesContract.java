package com.yearup.dealership.contract;

import com.yearup.dealership.Contract;
import com.yearup.dealership.Vehicle;
import com.yearup.dealership.util.Console;

import java.util.ArrayList;

public class SalesContract extends Contract {

    /**
     * Sales Contract Information
     */
    private double salesTax; // %5
    private double recordingFee; // $100.00
    private double processingFee; // a fee of $295 if vehicle under $10k, else $495
    private boolean isFinance; // do they want to finance (yes/no)

    public SalesContract(String contractType,
                         String date,
                         String customerName,
                         String customerEmail,
                         Vehicle vehicleSold,
                         double salesTax,
                         double recordingFee,
                         double processingFee,
                         double totalPrice,
                         boolean isFinance,
                         double monthlyPayment
    ) {
        super(contractType, date, customerName, customerEmail, vehicleSold, totalPrice, monthlyPayment);
        this.salesTax = salesTax;
        this.recordingFee = recordingFee;
        this.processingFee = processingFee;
        this.isFinance = isFinance;
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

    public ArrayList<Contract> getAllSalesContracts(){
        return listOfContracts;
    }
    public void addSalesContractToList(SalesContract s){
        listOfContracts.add(s); //s for sales contract
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
        double price = 0;

        if (vehicleSold.getPrice() < 10000) {
            setSalesTax((5.0/100) * vehicleSold.getPrice());
            setRecordingFee(100);
            setProcessingFee(295);
            price = vehicleSold.getPrice() + salesTax + recordingFee + processingFee;
        }

        else if(vehicleSold.getPrice() > 10000) {
            setSalesTax((5.0/100) * vehicleSold.getPrice());
            setRecordingFee(100);
            setRecordingFee(495);
            price = vehicleSold.getPrice() + salesTax + recordingFee + processingFee;
        }
        return price;
    }

    @Override
    public double getMonthlyPayment(){
        double payment = 0;
        boolean finance = true;
        if(finance) {
            setFinance(true);
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
        String financeStatus = " ";
        if(isFinance() == true){
            financeStatus = "YES";
        }
        else if (isFinance() == false){
            financeStatus = "NO";
        }
        setContractType("SALES");
        return this.contractType +
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
                "|" + this.salesTax +
                "|" + this.processingFee +
                "|" + this.recordingFee +
                "|" + this.totalPrice +
                "|" + financeStatus +
                "|" + this.monthlyPayment;
    }

}
