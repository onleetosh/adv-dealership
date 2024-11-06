package com.yearup.dealership.util;
import com.yearup.dealership.Vehicle;
import com.yearup.dealership.contract.*;
import com.yearup.dealership.filemanager.*;

import static com.yearup.dealership.util.UI.*;

public class Request {

    /**
     * Helper method designed to remove a vehicle from the inventory by vin and save changes to inventory.csv
     */
    public static void processRemoveVehicleRequest() {
        System.out.println("Processing vehicle remove request");
        currentDealership.removeVehicleFromInventory();
        DealershipFileManager.saveInventoryCSV(currentDealership, inventoryFileName);
    }
    /**
     * Helper method designed to process adding a vehicle to the inventory and save changes to inventory.csv
     */
    public static void processAddVehicleRequest() {
        System.out.println("Processing vehicle add request");
        currentDealership.addVehicleToInventory();
        DealershipFileManager.saveInventoryCSV(currentDealership, inventoryFileName);
        System.out.println("");

    }

    /**
     * Helper methods designed to process search request, by vehicle by type, mileage, color, year, make, model, and price
     */
    public static void processGetByVehicleTypeRequest() {
        String vehicleType = Console.PromptForString("Enter vehicle type (Sedan, Truck): ");
        displayHeader();
        for(Vehicle vehicle : currentDealership.getVehiclesByType(vehicleType)){
            displayVehicle(vehicle);
        }
    }

    public static void processGetByMileageRequest() {
        int min = Console.PromptForInt("Enter min: ");
        int max = Console.PromptForInt("Enter max: ");
        displayHeader();
        for(Vehicle vehicle : currentDealership.getVehiclesByMileage(min, max)){
            displayVehicle(vehicle);
        }
    }

    public static void processGetByColorRequest() {
        String color = Console.PromptForString("Enter color for vehicle: ");
        displayHeader();
        for (Vehicle vehicle : currentDealership.getVehicleByColor(color)){
            displayVehicle(vehicle);
        }
    }

    public static void processGetByYearRequest() {
        int min = Console.PromptForInt("Enter min: ");
        int max = Console.PromptForInt("Enter max: ");
        displayHeader();
        for(Vehicle vehicle : currentDealership.getVehicleByYear(min, max)){
            displayVehicle(vehicle);
        }
    }
    public static void processGetByMakeModelRequest() {
        String make = Console.PromptForString("Enter make for vehicle: ");
        String model = Console.PromptForString("Enter model for vehicle: ");
        displayHeader();
        for (Vehicle vehicle : currentDealership.getVehiclesByMakeModel(make, model)) {
            displayVehicle(vehicle);
        }
    }

    public static void processGetByPriceRequest() {
        double min = Console.PromptForDouble("Enter min: ");
        double max = Console.PromptForDouble("Enter max: ");
        displayHeader();
        for(Vehicle vehicle : currentDealership.getVehiclesByPrice(min, max)){
            displayVehicle(vehicle);
        }
    }

    /**
     * Helper method designed to loop through an ArrayList and display all objects
     */
    public static void processGetAllVehiclesRequest(){
        displayHeader();
        for(Vehicle vehicle : currentDealership.getInventory()){
            displayVehicle(vehicle);
        }
    }

    /**
     * Helper method used to print a single vehicle object
     */
    public static void displayVehicle(Vehicle vehicle){
        System.out.println(vehicle);
    }

    public static void displayHeader() {
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("                                  VEHICLES");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.printf("%6s | %5s | %10s | %10s | %7s | %6s | %7s | %6s \n",
                         "VIN","YEAR","MAKE", "MODEL","TYPE", "COLOR","ODOMETER","PRICE");
        System.out.println("-------------------------------------------------------------------------------------");
    }



    /**
     * processSellOrLeaseRequest() method with helper methods
     */
    public static void processSellOrLeaseRequest() {

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


    /***
     * Helper methods designed to prompt user for information
     */

    private static int getVinFromUser() {
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

    private static String getContractTypeFromUser() {
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

    private static String getInput(String prompt) {
        String input;
        do {
            input = Console.PromptForString(prompt);
            if (input.equalsIgnoreCase("q"))
                return null; // User ends request
        } while (input.isEmpty());
        return input;
    }

    private static String getDateFromUser() {
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

    private static boolean getFinancingOption() {
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

}
