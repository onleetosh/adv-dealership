package com.yearup.dealership.contract;

import com.yearup.dealership.Calculation;
import com.yearup.dealership.Vehicle;
import com.yearup.dealership.util.Console;

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
                         boolean isFinance) {
        super( date, customerName, customerEmail, vehicleSold);
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
        this.recordingFee = recordingFee;
        this.processingFee = processingFee;
        //skip total cost
        this.isFinance = isFinance;
        //skip monthly payment
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
      return getVehicleSold().getPrice() + salesTax + processingFee + recordingFee;
    }

    @Override
    public double getMonthlyPayment(){

        if (isFinance) {
            double financeRate = (getVehicleSold().getPrice() < 10000) ? 0.0525 : 0.0425;
            double financeTerm = (getVehicleSold().getPrice() < 10000) ? 24 : 48;
            return Calculation.calculateLoanPayment(getTotalPrice(), financeRate, financeTerm);
        }
        else {
            return 0;
        }

    }
    public static SalesContract processSalesContract(String date, String name, String email, Vehicle vehicle) {

        double salesTax = vehicleSold.getPrice() * (5.0/100);
        double processingFee = (vehicleSold.getPrice() < 10000) ? (295) : (495);
        double recordingFee = 100;
        boolean finance = Console.PromptForYesNo("Interested in financing?");
        SalesContract newSales = new SalesContract(
                date,
                name,
                email,
                vehicle,
                salesTax,
                recordingFee,
                processingFee,
                finance);
        return  newSales;
    }
    @Override
    public String toString() {

        String financeStatus = isFinance ? "YES" : "NO";

        return "SALES|" +
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
                "|" + getTotalPrice() +
                "|" + financeStatus +
                "|" + getMonthlyPayment();
    }

}
