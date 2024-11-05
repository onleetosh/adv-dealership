package com.yearup.dealership.util;

import com.yearup.dealership.contract.Contract;
import com.yearup.dealership.Dealership;
import com.yearup.dealership.Vehicle;
import com.yearup.dealership.contract.LeaseContract;
import com.yearup.dealership.contract.SalesContract;
import com.yearup.dealership.filemanager.ContractFileManager;
import com.yearup.dealership.filemanager.DealershipFileManager;

import java.util.ArrayList;

public class UI {
    public static String inventoryFileName = "inventory.csv";
    public static String contactFileName = "contracts.csv";

    public Dealership currentDealership;
    public ArrayList<Contract> contracts;

    public UI(){
        currentDealership = DealershipFileManager.getFromCSV(inventoryFileName);
        contracts = ContractFileManager.getContractsFromCSV(contactFileName);
        displayContracts(contracts);
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
                case 10 -> processSellOrLeaseRequest();
                case 0 -> System.exit(0);
                default -> System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    public void processSellOrLeaseRequest(){
        ArrayList<Contract> newContract = new ArrayList<>();

        String choice = Console.PromptForString("Enter [S] to Sell or [L] to Lease vehicle");
        String contractType = choice.equalsIgnoreCase("S") ? "SALES" : "LEASE";

        String date = Console.PromptForString("Enter date (MM-dd-YYYY): ");
        String name = Console.PromptForString("Enter your name: ");
        String email = Console.PromptForString("Enter e-mail address: ");

        Vehicle vehicle = Vehicle.getVehicleDetails();
        Contract contract = null;
        if (contractType.equals("SALES")) {
            contract = SalesContract.processSalesContract(date, name, email, vehicle);
        } else {
            contract = LeaseContract.processLeaseContract(date, name, email, vehicle);
        }
        newContract.add(contract);
        ContractFileManager.saveContractToCSV(newContract, contactFileName);

    }

    private void processRemoveVehicleRequest() {
        System.out.println("Processing vehicle remove request");
        // int vin = Console.PromptForInt("Enter vin number");
        // currentDealership.removeVehicleFromInventory(vin);
        currentDealership.removeVehicleFromInventory();
        DealershipFileManager.saveToCSV(currentDealership, inventoryFileName);
    }
    //move this method to the Dealership class
    private void processAddVehicleRequest() {

        System.out.println("Processing vehicle add request");
       // Vehicle v = new Vehicle(vin,year, make, model, vehicleType, color, odometer, price);
       // currentDealership.addVehicleToInventory(v);
        currentDealership.addVehicleToInventory();
        DealershipFileManager.saveToCSV(currentDealership, inventoryFileName);

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
    public void processGetAllVehiclesRequest(){
        for(Vehicle vehicle : currentDealership.getInventory()){
            displayVehicle(vehicle);
        }
    }
    public void displayVehicle(Vehicle vehicle){
        displayHeader();
        System.out.println(vehicle);
    }


    public void ContractRequest(){
        System.out.println("Processing contract request");
        for(int i= 0; i< contracts.size(); i++) {
            contracts.get(i).addNewContract();
            ContractFileManager.saveContractToCSV(contracts, contactFileName);
            break;
        }
    }
    private void processContractRequest(){
        System.out.println("Processing contract request");
        for (Contract contract : contracts) {
            contract.addNewContract();
            ContractFileManager.saveContractToCSV(contracts, contactFileName);
            break;
        }
    }
    public void processShowAllContracts(){
     displayContracts(contracts);
    }

    public void displayContracts(ArrayList<Contract> contracts) {
        for (Contract contract : contracts) {
            System.out.println(contract);  // This calls contract.toString() and prints without brackets or commas
        }
    }

    public void displayHeader() {
        System.out.println("vin | year | make | model | type | color | odometer | price ");
    }



}
