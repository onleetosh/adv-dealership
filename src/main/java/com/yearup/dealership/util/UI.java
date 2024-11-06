package com.yearup.dealership.util;

import com.yearup.dealership.Contract;
import com.yearup.dealership.Dealership;
import com.yearup.dealership.Vehicle;
import com.yearup.dealership.contract.LeaseContract;
import com.yearup.dealership.contract.SalesContract;
import com.yearup.dealership.filemanager.ContractFileManager;
import com.yearup.dealership.filemanager.DealershipFileManager;

import java.util.ArrayList;

public class UI {



    public static String inventoryFileName = "inventory.csv";
    public static String contractFileName = "contracts.csv";

    public static Dealership currentDealership;
    //private ArrayList<Contract> contracts;
    public static ArrayList<Contract> contracts;


    /**
     * Constructor - initialize and load the file content to ensure the program starts with
     * a dealership with inventory and a list of contract (objects)
     */
    public UI(){
        currentDealership = DealershipFileManager.getDealershipFromCSV(inventoryFileName);
        contracts = ContractFileManager.getContractsFromCSV(contractFileName);
    }

    public void display(){

        //represent choices as a String
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
                case 1 -> processGetByPriceRequest();
                case 2 -> processGetByMakeModelRequest();
                case 3 -> processGetByYearRequest();
                case 4 -> processGetByColorRequest();
                case 5 -> processGetByMileageRequest();
                case 6 -> processGetByVehicleTypeRequest();
                case 7 -> processGetAllVehiclesRequest();
                case 8 -> processAddVehicleRequest();
                case 9 -> processRemoveVehicleRequest();
                case 10 -> processSellOrLeaseRequestWithHelpers();
                case 11 -> Contract.displayAllContracts(contracts);
                case 0 -> System.exit(0);
                default -> System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    public static void displayVehicle(Vehicle vehicle){
        displayHeader();
        System.out.println(vehicle);
    }

    private void processRemoveVehicleRequest() {
        System.out.println("Processing vehicle remove request");
        currentDealership.removeVehicleFromInventory();
        DealershipFileManager.saveInventoryCSV(currentDealership, inventoryFileName);
    }
    //move this method to the Dealership class
    private void processAddVehicleRequest() {

        System.out.println("Processing vehicle add request");
        currentDealership.addVehicleToInventory();
        DealershipFileManager.saveInventoryCSV(currentDealership, inventoryFileName);

    }
    private void processGetByVehicleTypeRequest() {
        String vehicleType = Console.PromptForString("Enter vehicle type (Sedan, Truck): ");

        for(Vehicle vehicle : currentDealership.getVehiclesByType(vehicleType)){
            displayVehicle(vehicle);
        }
    }
    private void processGetByMileageRequest() {
        int min = Console.PromptForInt("Enter min: ");
        int max = Console.PromptForInt("Enter max: ");
        displayHeader();

        for(Vehicle vehicle : currentDealership.getVehiclesByMileage(min, max)){
            displayVehicle(vehicle);
        }
    }
    private void processGetByColorRequest() {
        String color = Console.PromptForString("Enter color for vehicle: ");

        displayHeader();
        for (Vehicle vehicle : currentDealership.getVehicleByColor(color)){
            displayVehicle(vehicle);
        }
    }
    private void processGetByYearRequest() {
        int min = Console.PromptForInt("Enter min: ");
        int max = Console.PromptForInt("Enter max: ");

        for(Vehicle vehicle : currentDealership.getVehicleByYear(min, max)){
            displayVehicle(vehicle);
        }
    }
    private void processGetByMakeModelRequest() {
        String make = Console.PromptForString("Enter make for vehicle: ");
        String model = Console.PromptForString("Enter model for vehicle: ");
        for (Vehicle vehicle : currentDealership.getVehiclesByMakeModel(make, model)) {
            displayVehicle(vehicle);
        }
    }
    private void processGetByPriceRequest() {
        double min = Console.PromptForDouble("Enter min: ");
        double max = Console.PromptForDouble("Enter max: ");
        for(Vehicle vehicle : currentDealership.getVehiclesByPrice(min, max)){
            displayVehicle(vehicle);
        }
    }
    private void processGetAllVehiclesRequest(){
        for(Vehicle vehicle : currentDealership.getInventory()){
            displayVehicle(vehicle);
        }
    }



    /*
    public static void printListOfContracts(ArrayList<Contract> contractsList) {
        for (Contract contract : contractsList) {
            System.out.println(contract);  // This calls contract.toString() and prints without brackets or commas
        }
    }
     */

    public void processSellOrLeaseRequestWithHelpers() {
        int vin = getVinFromUser();
        if (vin == 0) return; // VIN input was cancelled

        Vehicle vehicle = currentDealership.getVehicleByVin(vin);
        if (vehicle == null) {
            System.out.println("Vehicle not found. Returning to the main menu.");
            return;
        }

        String contractType = getContractTypeFromUser();
        if (contractType == null) return; // Contract input was cancelled

        String customerName = getInput("Enter customer name (or 'q' to cancel): ");
        if (customerName == null) return; // User ends process

        String customerEmail = getInput("Enter customer email (or 'q' to cancel): ");
        if (customerEmail == null) return; // User ends process

        String date = getDateFromUser();
        if (date == null) return; //User end process

        Contract newContract;
        if (contractType.equalsIgnoreCase("sale")) {
            boolean isFinanced = getFinancingOption();
            newContract = new SalesContract(date, customerName, customerEmail, vehicle, isFinanced);
        } else {
            newContract = new LeaseContract(date, customerName, customerEmail, vehicle);
        }

        // Add the new contract to the list of contracts
        contracts.add(newContract);

        System.out.println("Adding contract >>>> " + newContract);

        currentDealership.removeVehicleFromInventory();
        // Save and update contract.csv file
        ContractFileManager.saveContractCSV(newContract, contractFileName);
    }


    /***
     * Helper methods for processing a contract
     */

    private int getVinFromUser() {
        String input;
        do {
            input = Console.PromptForString("Enter VIN of the vehicle to sell/lease (or 'v' to view all vehicles or 'q' to cancel): ");
            if (input.equalsIgnoreCase("q"))
                return 0;
            if (input.equalsIgnoreCase("v")) {
                processGetAllVehiclesRequest(); // View all vehicles
                continue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid VIN. Please enter a valid number.");
            }
        } while (true);
    }

    private String getContractTypeFromUser() {
        String input;
        do {
            input = Console.PromptForString("Enter contract type (sale/lease) (or 'q' to cancel): ");
            if (input.equalsIgnoreCase("q"))
                return null; // User ends request
            if (input.equalsIgnoreCase("sale") || input.equalsIgnoreCase("lease"))
                return input; // return and set contract type to sale or lease
            System.out.println("Invalid contract type. Please enter 'sale' or 'lease'.");
        } while (true);
    }

    private String getInput(String prompt) {
        String input;
        do {
            input = Console.PromptForString(prompt);
            if (input.equalsIgnoreCase("q"))
                return null; // User ends request
        } while (input.isEmpty());
        return input;
    }

    private String getDateFromUser() {
        String date;
        do {
            date = Console.PromptForString("Enter date (YYYYMMDD) (or 'q' to cancel): ");
            if (date.equalsIgnoreCase("q"))
                return null; // User ends request
            if (date.length() != 8 || !date.matches("\\d{8}")) { // ensure format
                System.out.println("Invalid date format. Please use YYYYMMDD (e.g., 20210928).");
                continue;
            }
            return date;
        } while (true);
    }

    private boolean getFinancingOption() {
        String input;
        do {
            input = Console.PromptForString("Will this be financed? (yes/no) (or 'q' to cancel): ");
            if (input.equalsIgnoreCase("q"))
                return false; // User ends request
            if ("yes".equalsIgnoreCase(input))
                return true;    // return and set finance to Yes
            if ("no".equalsIgnoreCase(input))
                return false;    //return and set finance to No
            System.out.println("Please enter 'yes' or 'no'.");
        } while (true);
    }


    public void processSellOrLeaseRequestWithoutHelpers() {
        int vin = 0;
        String input;

        // Get all the info we need from the user
        // Get VIN
        do {
            input = Console.PromptForString("Enter VIN of the vehicle to sell/lease (or 'v' to view all vehicles or 'q' to cancel): ");
            if (input.equalsIgnoreCase("q")) return;
            if (input.equalsIgnoreCase("v")) {
                processGetAllVehiclesRequest();
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
                } else if (financeInput.equalsIgnoreCase("no")) {
                    isFinanced = false;
                    break;
                }
                System.out.println("Please enter 'yes' or 'no'.");
            } while (true);

            newContract = new SalesContract(date, customerName, customerEmail, vehicle, isFinanced);
        } else {
            newContract = new LeaseContract(date, customerName, customerEmail, vehicle);
        }

        // Add the new contract to the list of contracts
        contracts.add(newContract);

        System.out.println("Adding contract >>>> " + newContract);

        // Save and update the contract to CSV file
        ContractFileManager.saveContractCSV(newContract, contractFileName);
    }



    public static void displayHeader() {
        System.out.println("vin | year | make | model | type | color | odometer | price ");
    }



}