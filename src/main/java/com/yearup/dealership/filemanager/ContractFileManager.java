package com.yearup.dealership.filemanager;

import com.yearup.dealership.Contract;
import com.yearup.dealership.Vehicle;

import com.yearup.dealership.contract.LeaseContract;
import com.yearup.dealership.contract.SalesContract;
import com.yearup.dealership.util.UI;

import java.io.*;
import java.util.ArrayList;

public class ContractFileManager {

    static ArrayList<Contract> contractsFromCSV = getContractsFromCSV(UI.contactFileName);
    public static void main(String[] args) {
        printListOfContracts(contractsFromCSV);
    }

    public static void printListOfContracts(ArrayList<Contract> contractsList) {
        for (Contract contract : contractsList) {
            System.out.println(contract);  // This calls contract.toString() and prints without brackets or commas
        }
    }

    public static ArrayList<Contract> getContractsFromCSV(String file) {
        ArrayList<Contract> contracts = new ArrayList<>();  // Initialize ArrayList for contracts

        try  {
            BufferedReader bf = new BufferedReader(new FileReader(file));

            String line;

            while ((line = bf.readLine()) != null) {
                String[] tokens = line.split("\\|");
                if (tokens.length == 18) {
                    contracts.add(parseSalesContract(tokens));

                }
                else if (tokens.length == 16) {
                    contracts.add(parseLeaseContract(tokens));
                }
            }
            bf.close();

        } catch (Exception e) {
            e.printStackTrace();  // Print stack trace for debugging
        }

        return contracts;
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
            bw.close(); // Close the BufferedWriter
        } catch (Exception e) {
            System.out.println("File write error");
            e.printStackTrace();
        }
    }


    private static SalesContract parseSalesContract(String[] tokens) {
        // Extract sales contract details from tokens
        Vehicle vehicleSold = new Vehicle(
                Integer.parseInt(tokens[4]), //year
                Integer.parseInt(tokens[5]), //vine
                tokens[6], //make
                tokens[7], //model
                tokens[8], //type
                tokens[9], //color
                Integer.parseInt(tokens[10]), //odometer
                Double.parseDouble(tokens[11]) //price
        );



        return new SalesContract(
                tokens[1], //date
                tokens[2], //name
                tokens[3], //email
                vehicleSold,
                Double.parseDouble(tokens[12]),// sales tax
                Double.parseDouble(tokens[13]),//processing fee
                Double.parseDouble(tokens[14]), //recording fee
                Boolean.parseBoolean(tokens[16]) // finance - yes or no
        );
    }

    private static String encodeSaleContractFormat(SalesContract sales){
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

    private static LeaseContract parseLeaseContract(String[] tokens) {
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
                tokens[1],
                tokens[2],
                tokens[3],
                vehicleSold,
                Double.parseDouble(tokens[12]),
                Double.parseDouble(tokens[13])
        );
    }

    private static String encodeLeaseContractFormat(LeaseContract lease) {
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
