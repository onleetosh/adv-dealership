package com.yearup.dealership.contract;

import com.yearup.dealership.Contract;

public class SalesContract extends Contract {

    /**
     * Contract Information
     */
    private double salesTax; // %5
    private double recordingFee; //%100.00
    private double processingFee; // a fee of $295 if vehicle under $10k, else $495
    private boolean isFinance; // do they want to finance (yes/no)
    // 4.25% for 48mos if the price is $10k+ , else 5.25% for 24mos


    public SalesContract(String date,
                         String customerName,
                         String customerEmail,
                         String vehicleSold,
                         double totalPrice,
                         double monthlyPayment,
                         double salesTax,
                         double recordingFee,
                         double processingFee,
                         boolean isFinance) {
        super(date, customerName, customerEmail, vehicleSold, totalPrice, monthlyPayment);
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

    @Override
    public double getTotalPrice(){
        return 0;
    }
    @Override
    public double getMonthlyPayment(){
        return 0;
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


}
