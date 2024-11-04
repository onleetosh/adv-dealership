package com.yearup.dealership.filemanager;

import com.yearup.dealership.Contract;
import com.yearup.dealership.Vehicle;

import com.yearup.dealership.contract.LeaseContract;
import com.yearup.dealership.contract.SalesContract;
import com.yearup.dealership.util.UI;

import java.io.*;
import java.util.ArrayList;

public class ContractFileManager {

    ArrayList<Contract> contracts;

    public ContractFileManager(ArrayList<Contract> contracts) {
        this.contracts = getContractsFromCSV(UI.contactFileName);
    }


    public static ArrayList<Contract> getContractsFromCSV(String file) {
        ArrayList<Contract> contractsList = new ArrayList<>();  // Initialize ArrayList for contracts

        try  {
            BufferedReader bf = new BufferedReader(new FileReader(file));

            String line;

            while ((line = bf.readLine()) != null) {
                String[] tokens = line.split("\\|");

                // Parse a SALE contract
                if (tokens.length == 18) {
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

                    boolean isFinance = Boolean.parseBoolean(tokens[16]);
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

                }
                // Parsing a LEASE contract
                else if (tokens.length == 16) {
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

        return contractsList;
    }



    /**
     * saveContract() method accepts a Contract parameter; instanceOf checks the type of contract
     * before writing file changes
     */
    public static void saveContractToCSV(Contract contract, String file){
        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);

            if (contract instanceof SalesContract) {
                bw.write(encodeSaleContractFormat((SalesContract) contract));
            } else if (contract instanceof LeaseContract) {
                bw.write(encodeLeaseContractFormat((LeaseContract) contract));
            }
            bw.newLine(); // Ensure each contract is on a new line
        } catch (Exception e) {
            System.out.println("File write error");
            e.printStackTrace();
        }
    }

    private static String encodeSaleContractFormat(SalesContract sales){
        return "SALE" + sales.getDate() +
                "| " + sales.getCustomerName() +
                "|" + sales.getCustomerEmail() +
                "|" + sales.getVehicleSold().getVin() +
                "|" + sales.getVehicleSold().getYear() +
                "|" + sales.getVehicleSold().getMake() +
                "|" + sales.getVehicleSold().getModel() +
                "|" + sales.getVehicleSold().getVehicleType() +
                "|" + sales.getVehicleSold().getColor() +
                "|" + sales.getVehicleSold().getOdometer() +
                "|" + sales.getVehicleSold().getPrice() +
                "|" + sales.getSalesTax() +
                "|" + sales.getRecordingFee() +
                "|" + sales.getProcessingFee() +
                "|" + sales.getTotalPrice() +
                "|" + sales.isFinance() +
                "|" + sales.getMonthlyPayment();
     }


    private LeaseContract parseLeaseContract(String[] tokens) {
        // Extract lease contract details from tokens
        Vehicle vehicleSold = new Vehicle(
                Integer.parseInt(tokens[4]),
                Integer.parseInt(tokens[5]),
                tokens[6],
                tokens[7],
                tokens[8],
                tokens[9],
                Integer.parseInt(tokens[10]),
                Double.parseDouble(tokens[11])
        );

        return new LeaseContract(
                tokens[0],
                tokens[1],
                tokens[2],
                tokens[3],
                vehicleSold,
                Double.parseDouble(tokens[12]),
                Double.parseDouble(tokens[13]),
                Double.parseDouble(tokens[14]),
                Double.parseDouble(tokens[15])
        );
    }

    private static String encodeLeaseContractFormat(LeaseContract lease) {
        return "LEASE" + lease.getDate() +
                " | " + lease.getCustomerName() +
                "|" + lease.getCustomerEmail() +
                "|" + lease.getVehicleSold().getVin() +
                "|" + lease.getVehicleSold().getYear() +
                "|" + lease.getVehicleSold().getMake() +
                "|" + lease.getVehicleSold().getModel() +
                "|" + lease.getVehicleSold().getVehicleType() +
                "|" + lease.getVehicleSold().getColor() +
                "|" + lease.getVehicleSold().getOdometer() +
                "|" + lease.getVehicleSold().getPrice() +
                "|" + lease.getExpectEndingValue() +
                "|" + lease.getLeaseFee() +
                "|" + lease.getTotalPrice() +
                "|" + lease.getMonthlyPayment();
     }

}
