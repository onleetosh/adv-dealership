package com.yearup.dealership.util;

import com.yearup.dealership.Dealership;
import com.yearup.dealership.Vehicle;
import com.yearup.dealership.contract.*;
import com.yearup.dealership.filemanager.*;

import java.util.ArrayList;

public class UI {

    public static String inventoryFileName = "inventory.csv";
    public static String contractFileName = "contracts.csv";

    public static Dealership currentDealership;
    public static ArrayList<Contract> contracts;

    /**
     * UI Constructor initializes and loads the file content to ensure the program starts with
     * a dealership with inventory and a list of contract (objects)
     */
    public UI(){
        currentDealership = DealershipFileManager.getDealershipFromCSV(inventoryFileName);
        contracts = ContractFileManager.getContractsFromCSV(contractFileName);
    }

    /**
     * Display user request options
     */

    public void display(){
        // option menu represented as a String
        String choices = """
                Please select from the following choices:
                1 - Find vehicles within a price range
                2 - Find vehicles by make / model
                3 - Find vehicles by year range
                4 - Find vehicles by color
                5 - Find vehicles by mileage range
                6 - Find vehicles by type (car, truck, SUV, van)
                7 - List ALL vehicles
                8 - Add a vehicle
                9 - Remove a vehicle
                10 - Sell/Lease a vehicle
                11 - Display Contracts
           
                0 - Quit

                >>>\s""";

        int request;

        // User Interface Loop
        while (true){
            System.out.println("Welcome to " + currentDealership.getName() + "!");
            request = Console.PromptForInt(choices);
            switch (request) {
                case 1 -> Request.processGetByPriceRequest();
                case 2 -> Request.processGetByMakeModelRequest();
                case 3 -> Request.processGetByYearRequest();
                case 4 -> Request.processGetByColorRequest();
                case 5 -> Request.processGetByMileageRequest();
                case 6 -> Request.processGetByVehicleTypeRequest();
                case 7 -> Request.processGetAllVehiclesRequest();
                case 8 -> Request.processAddVehicleRequest();
                case 9 -> Request.processRemoveVehicleRequest();
                case 10 -> Request.processSellOrLeaseRequest();
                case 11 -> Contract.displayAllContracts(contracts);
                case 0 -> System.exit(0);
                default -> System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    /**
     * processSellOrLeaseRequest() method without helpers
     */
    public void processSellOrLeaseRequest2() {
        int vin = 0;
        String input;

        // Get all the info we need from the user
        // Get VIN
        do {
            input = Console.PromptForString("Enter VIN of the vehicle to sell/lease (or 'v' to view all vehicles or 'q' to cancel): ");
            if (input.equalsIgnoreCase("q")) return;
            if (input.equalsIgnoreCase("v")) {
                Request.processGetAllVehiclesRequest();
                input = "";
                continue;
            }

            try {
                vin = Integer.parseInt(input);
                Vehicle vehicleToSell = currentDealership.getVehicleByVin(vin);
                if (vehicleToSell == null) {
                    System.out.println("Vehicle not found. Please try again.");
                    input = ""; // Reset input
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number. or 'v' to list vehicles, or 'q' to return to main menu.");
                input = ""; // Reset input
            }
        } while (input.isEmpty());

        System.out.println(vin);
        System.out.println(currentDealership.getVehicleByVin(vin));
        // At this point, we should have a vin...

        // Get contract type
        String contractType;
        do {
            contractType = Console.PromptForString("Enter contract type (sale/lease) (or 'q' to cancel): ");
            if (contractType.equalsIgnoreCase("q")) return;
            if (!contractType.equalsIgnoreCase("sale") && !contractType.equalsIgnoreCase("lease")) {
                System.out.println("Invalid contract type. Please enter 'sale' or 'lease'.");
                contractType = ""; // Reset input
            }
        } while (contractType.isEmpty());

        // Get customer name
        String customerName;
        do {
            customerName = Console.PromptForString("Enter customer name (or 'q' to cancel): ");
            if (customerName.equalsIgnoreCase("q")) return;
        } while (customerName.isEmpty());

        // Get customer email
        String customerEmail;
        do {
            customerEmail = Console.PromptForString("Enter customer email (or 'q' to cancel): ");
            if (customerEmail.equalsIgnoreCase("q")) return;
        } while (customerEmail.isEmpty());

        // Get date
        String date;
        do {
            date = Console.PromptForString("Enter date (YYYYMMDD) (or 'q' to cancel): ");
            if (date.equalsIgnoreCase("q")) return;
            if (date.length() != 8 || !date.matches("\\d{8}")) {
                System.out.println("Invalid date format. Please use YYYYMMDD (e.g., 20210928)");
                date = ""; // Reset input
                continue;
            }
        } while (date.isEmpty());

        Vehicle vehicle = currentDealership.getVehicleByVin(vin);

        // Declare contract as a single Contract object
        Contract newContract = null;

        // Create appropriate contract type
        if (contractType.equalsIgnoreCase("sale")) {
            String financeInput;
            boolean isFinanced;
            do {
                financeInput = Console.PromptForString("Will this be financed? (yes/no) (or 'q' to cancel): ");
                if (financeInput.equalsIgnoreCase("q")) return;
                if (financeInput.equalsIgnoreCase("yes")) {
                    isFinanced = true;
                    break;
                }
                else if (financeInput.equalsIgnoreCase("no")) {
                    isFinanced = false;
                    break;
                }
                System.out.println("Please enter 'yes' or 'no'.");
            }
            while (true);
            newContract = new SalesContract(date, customerName, customerEmail, vehicle, isFinanced);
        }
        else {
            newContract = new LeaseContract(date, customerName, customerEmail, vehicle);
        }

        // Add new contract to ArrayList
        contracts.add(newContract);
        System.out.println("Adding contract >>>> " + newContract);
        //remove vehicle sold from inventory
        currentDealership.removeVehicleFromInventory(vehicle);
        // Save both files
        DealershipFileManager.saveInventoryCSV(currentDealership, inventoryFileName);
        ContractFileManager.saveContractCSV(newContract, contractFileName);
    }





}