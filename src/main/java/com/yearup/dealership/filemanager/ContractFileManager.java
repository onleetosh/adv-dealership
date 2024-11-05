package com.yearup.dealership.filemanager;

import com.yearup.dealership.contract.Contract;
import com.yearup.dealership.Vehicle;

import com.yearup.dealership.contract.LeaseContract;
import com.yearup.dealership.contract.SalesContract;

import java.io.*;
import java.util.ArrayList;

public class ContractFileManager {

   // ArrayList<Contract> contracts = getContractsFromCSV(UI.contactFileName);;

    public static ArrayList<Contract> getContractsFromCSV(String file) {
        ArrayList<Contract> contracts = new ArrayList<>();  // Initialize ArrayList for contracts

        try  {
            BufferedReader bf = new BufferedReader(new FileReader(file));

            String line;

            while ((line = bf.readLine()) != null) {
                String[] tokens = line.split("\\|");
                if (tokens.length >=16) {
                    if(tokens[0].equalsIgnoreCase("SALES")){
                        contracts.add(parseSalesContract(tokens));
                    }
                } else if (tokens[0].equalsIgnoreCase("LEASE")) {
                    contracts.add(parseLeaseContract(tokens));
                }
                else {
                    //invalid
                }
            }
            bf.close();

        } catch (Exception e) {
            e.printStackTrace();  // Print stack trace for debugging
        }

        return contracts;
    }




    //TODO: does not properly save to file
    /**
     * saveContract() method accepts a Contract parameter; instanceOf checks the type of contract
     * before writing file changes
     */
    public static void saveContractToCSV(ArrayList<Contract> contracts, String file) {
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Contract contract : contracts) {
                if (contract instanceof SalesContract) {
                    bw.write(encodeSaleContractFormat((SalesContract) contract));
                } else if (contract instanceof LeaseContract) {
                    bw.write(encodeLeaseContractFormat((LeaseContract) contract));
                }
            }bw.close();

        } catch (Exception e) {
            System.out.println("File write error");
            e.printStackTrace();
        }
    }

    private static SalesContract parseSalesContract(String[] tokens) {
        // Extract sales contract details from tokens
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

        return new SalesContract(
                tokens[1],
                tokens[2],
                tokens[3],
                vehicleSold,
                Double.parseDouble(tokens[12]),
                Double.parseDouble(tokens[13]),
                Double.parseDouble(tokens[14]),
                Boolean.parseBoolean(tokens[16]));
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
                "|" + lease.getMonthlyPayment() + "\n";
    }


}
