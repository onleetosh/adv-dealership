package com.yearup.dealership.util;

import com.yearup.dealership.Contract;
import com.yearup.dealership.Vehicle;
import com.yearup.dealership.contract.LeaseContract;
import com.yearup.dealership.contract.SalesContract;
import com.yearup.dealership.filemanager.ContractFileManager;
import com.yearup.dealership.filemanager.DealershipFileManager;

import static com.yearup.dealership.util.UI.*;

public class Request {
    public static void processRemoveVehicleRequest() {
        System.out.println("Processing vehicle remove request");
        currentDealership.removeVehicleFromInventory();
        DealershipFileManager.saveInventoryCSV(currentDealership, inventoryFileName);
    }
    //move this method to the Dealership class
    public static void processAddVehicleRequest() {

        System.out.println("Processing vehicle add request");
        currentDealership.addVehicleToInventory();
        DealershipFileManager.saveInventoryCSV(currentDealership, inventoryFileName);

    }
    public static void processGetByVehicleTypeRequest() {
        String vehicleType = Console.PromptForString("Enter vehicle type (Sedan, Truck): ");

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

        for(Vehicle vehicle : currentDealership.getVehicleByYear(min, max)){
            displayVehicle(vehicle);
        }
    }
    public static void processGetByMakeModelRequest() {
        String make = Console.PromptForString("Enter make for vehicle: ");
        String model = Console.PromptForString("Enter model for vehicle: ");
        for (Vehicle vehicle : currentDealership.getVehiclesByMakeModel(make, model)) {
            displayVehicle(vehicle);
        }
    }
    public static void processGetByPriceRequest() {
        double min = Console.PromptForDouble("Enter min: ");
        double max = Console.PromptForDouble("Enter max: ");
        for(Vehicle vehicle : currentDealership.getVehiclesByPrice(min, max)){
            displayVehicle(vehicle);
        }
    }
    public static void processGetAllVehiclesRequest(){
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

}
