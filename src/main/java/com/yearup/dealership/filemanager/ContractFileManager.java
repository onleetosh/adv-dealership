package com.yearup.dealership.filemanager;

import com.yearup.dealership.Contract;
import com.yearup.dealership.Vehicle;

import com.yearup.dealership.contract.LeaseContract;
import com.yearup.dealership.contract.SalesContract;
import com.yearup.dealership.util.UI;

import java.io.*;
import java.util.ArrayList;

public class ContractFileManager {

    /**
     * getContractsFromCSV reads a file and return the data as objects of an ArrayList
     */
    public static ArrayList<Contract> getContractsFromCSV(String fileName) {
        ArrayList<Contract> contracts = new ArrayList<>();  // Initialize ArrayList for contracts

        try  {
            BufferedReader bf = new BufferedReader(new FileReader(fileName));

            String fileLine;

            while ((fileLine = bf.readLine()) != null) {
                // split and token string between "|"
                String[] tokens = fileLine.split("\\|");
                if (tokens.length == 18) { // if string length contains 18 tokens (Sales)
                    contracts.add(parseSalesContract(tokens)); //add Sales Contract to ArrayList
                }
                else if (tokens.length == 16) { //if string length contains 16 tokens (Lease)
                    contracts.add(parseLeaseContract(tokens)); // add Lease Contract to ArrayList
                }
            }
            bf.close();
        }
        catch (Exception e) {
            e.printStackTrace();  // Print stack trace for debugging
        }
        return contracts;
    }

    /**
     * Helper; extract Sale and Lease Contract information from tokens of an array strings and return the parse data
     */
    private static SalesContract parseSalesContract(String[] tokens) {
        // create a new vehicle object and parse the information using tokens
        Vehicle parseVehicle = new Vehicle(
                Integer.parseInt(tokens[4]), //year
                Integer.parseInt(tokens[5]), //vine
                tokens[6], //make
                tokens[7], //model
                tokens[8], //type
                tokens[9], //color
                Integer.parseInt(tokens[10]), //odometer
                Double.parseDouble(tokens[11]) //price
        );

        //create and return a new Sales Contract
        return new SalesContract(
                tokens[1], //date
                tokens[2], //name
                tokens[3], //email
                parseVehicle,
                Double.parseDouble(tokens[12]),// sales tax
                Double.parseDouble(tokens[13]),//processing fee
                Double.parseDouble(tokens[14]), //recording fee
                Boolean.parseBoolean(tokens[16]) // finance - yes or no
        );
    }

    private static LeaseContract parseLeaseContract(String[] tokens) {
        // create a new vehicle object and parse the information using tokens
        Vehicle parseVehicle = new Vehicle(
                Integer.parseInt(tokens[4]), //vin
                Integer.parseInt(tokens[5]), //year
                tokens[6], //make
                tokens[7], //model
                tokens[8], //vehicle type
                tokens[9], //color
                Integer.parseInt(tokens[10]), //odometer
                Double.parseDouble(tokens[11]) //price
        );

        //create and return a new Lease Contract
        return new LeaseContract(
                tokens[1], //date
                tokens[2], //customer name
                tokens[3], //customer email
                parseVehicle, //vehicle sold
                Double.parseDouble(tokens[12]), // expecting end value
                Double.parseDouble(tokens[13]) // lease fee
        );
    }

    /**
     * saveContract() method accepts a Contract parameter; instanceOf checks the type of contract
     * before writing file changes
     */
    public static void saveContractCSV(Contract contracts, String fileName){
        try {
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);

                if (contracts instanceof SalesContract) {
                    //write and convert a Sales Contract to a formatted string
                    bw.write(encodeSalesContractToString((SalesContract) contracts));
                }
                else if (contracts instanceof LeaseContract) {
                    //write and convert Lease Contract to a formatted string
                    bw.write(encodeLeaseContractToString((LeaseContract) contracts) );
                }
            bw.close(); // Close the BufferedWriter and release data
        } catch (Exception e) {
            System.out.println("File write error");
            e.printStackTrace();
        }
    }

    /**
     * Helper; set and return a string format for Sale and Lease Contract objects
     */
    private static String encodeSalesContractToString(SalesContract sales){
        return "SALE|" + sales.getDate() +
                "|" + sales.getCustomerName() +
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
                "|" + sales.getMonthlyPayment() + "\n";
    }

    private static String encodeLeaseContractToString(LeaseContract lease) {
        return "LEASE|" + lease.getDate() +
                "|" + lease.getCustomerName() +
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
                "|" + lease.getMonthlyPayment() + "\n";
    }

}
