package com.yearup.dealership.contract;

import com.yearup.dealership.Contract;

public class LeaseContract extends Contract {

    /**
     * Contract Information
     */
    private double expectEndingValue; // 50% of the original price
    private double leaseFee; // 7% of the original price
   //all leases are financed at 4.0% for 36 mos

    public LeaseContract(String date,
                         String customerName,
                         String customerEmail,
                         String vehicleSold,
                         double totalPrice,
                         double monthlyPayment,
                         double expectEndingValue,
                         double leaseFee) {
        super(date, customerName, customerEmail, vehicleSold, totalPrice, monthlyPayment);
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

    @Override
    public double getTotalPrice(){
        return 0;
    }
    @Override
    public double getMonthlyPayment(){
        return 0;
    }
}


