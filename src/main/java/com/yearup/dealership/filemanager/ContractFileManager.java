package com.yearup.dealership.filemanager;

import com.yearup.dealership.Contract;
import com.yearup.dealership.Vehicle;

import com.yearup.dealership.contract.LeaseContract;
import com.yearup.dealership.contract.SalesContract;

import java.io.*;
import java.util.ArrayList;

public class ContractFileManager {

    public static ArrayList<Contract> getContractsFromCSV(String file) {
        ArrayList<Contract> contractsList = new ArrayList<>();  // Initialize ArrayList for contracts

        try  {
            BufferedReader bf = new BufferedReader(new FileReader(file));

            String line;

            while ((line = bf.readLine()) != null) {
                String[] tokens = line.split("\\|");

                if (tokens.length == 18) {  // Parsing a SALE contract
                    String contractType = tokens[0];
                    String date = tokens[1];
                    String name = tokens[2];
                    String email = tokens[3];

                    int vin = Integer.parseInt(tokens[4]);
                    int year = Integer.parseInt(tokens[5]);
                    String make = tokens[6];
                    String model = tokens[7];
                    String vehicleType = tokens[8];
                    String color = tokens[9];
                    int odometer = Integer.parseInt(tokens[10]);
                    double price = Double.parseDouble(tokens[11]);
                    Vehicle vehicleSold = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);

                    double salesTax = Double.parseDouble(tokens[12]);
                    double recordingFee = Double.parseDouble(tokens[13]);
                    double processingFee = Double.parseDouble(tokens[14]);
                    double totalPrice = Double.parseDouble(tokens[15]);

                    // Handle finance status manually (YES/NO)
                    boolean isFinance = tokens[16].equalsIgnoreCase("YES");
                    double payments = Double.parseDouble(tokens[17]);

                    Contract contract = new SalesContract(
                            contractType,
                            date,
                            name,
                            email,
                            vehicleSold,
                            salesTax,
                            processingFee,
                            recordingFee,
                            totalPrice,
                            isFinance,
                            payments
                    );

                    contractsList.add(contract);  // Add to the list

                } else if (tokens.length == 16) {  // Parsing a LEASE contract
                    String contractType = tokens[0];
                    String date = tokens[1];
                    String name = tokens[2];
                    String email = tokens[3];

                    int vin = Integer.parseInt(tokens[4]);
                    int year = Integer.parseInt(tokens[5]);
                    String make = tokens[6];
                    String model = tokens[7];
                    String vehicleType = tokens[8];
                    String color = tokens[9];
                    int odometer = Integer.parseInt(tokens[10]);
                    double price = Double.parseDouble(tokens[11]);
                    Vehicle vehicleSold = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);

                    double endValue = Double.parseDouble(tokens[12]);
                    double leaseFee = Double.parseDouble(tokens[13]);
                    double totalPrice = Double.parseDouble(tokens[14]);
                    double payments = Double.parseDouble(tokens[15]);

                    Contract contract = new LeaseContract(
                            contractType,
                            date,
                            name,
                            email,
                            vehicleSold,
                            endValue,
                            leaseFee,
                            totalPrice,
                            payments
                    );

                    contractsList.add(contract);  // Add to the list
                }
            } bf.close();

        } catch (Exception e) {
            e.printStackTrace();  // Print stack trace for debugging
        }

        return contractsList;  // Return the list of contracts
    }





    /**
     * saveContract() method accepts a Contract parameter; instanceOf checks the type of contract
     * before writing file changes
     */

    private static String encodeSaleContractFormat(SalesContract s){
        return "SALE" + s.getDate() +
                " | " + s.getCustomerName() +
                "|" + s.getCustomerEmail() +
                "|" + s.getVehicleSold() +
                "|" + s.getSalesTax() +
                "|" + s.getRecordingFee() +
                "|" + s.getProcessingFee() +
                "|" + s.getTotalPrice() +
                "|" + s.isFinance() +
                "|" + s.getMonthlyPayment();
     }
     private static String encodeLeaseContractFormat(LeaseContract l) {
        return "LEASE" + l.getDate() +
                " | " + l.getCustomerName() +
                "|" + l.getCustomerEmail() +
                "|" + l.getVehicleSold() +
                "|" + l.getExpectEndingValue() +
                "|" + l.getLeaseFee() +
                "|" + l.getTotalPrice() +
                "|" + l.getMonthlyPayment();
     }

}
