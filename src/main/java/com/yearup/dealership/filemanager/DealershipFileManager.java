package com.yearup.dealership.filemanager;

import com.yearup.dealership.Dealership;
import com.yearup.dealership.Vehicle;

import java.io.*;

public class DealershipFileManager {

    public static Dealership getFromCSV(String filename){

        Dealership dealership = null;

        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

            String fileLine;

            String[] dealershipTokens = bufferedReader.readLine().split("\\|");
            String name = dealershipTokens[0];
            String address = dealershipTokens[1];
            String phone = dealershipTokens[2];
            dealership = new Dealership(name, address, phone);

            while((fileLine = bufferedReader.readLine()) != null){
                String[] vehicleTokens = fileLine.split("\\|");
                if(vehicleTokens.length == 8){
                    int vin = Integer.parseInt(vehicleTokens[0]);
                    int year = Integer.parseInt(vehicleTokens[1]);
                    String make = vehicleTokens[2];
                    String model = vehicleTokens[3];
                    String vehicleType = vehicleTokens[4];
                    String color = vehicleTokens[5];
                    int odometer = Integer.parseInt(vehicleTokens[6]);
                    double price = Double.parseDouble(vehicleTokens[7]);
                    Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
                    dealership.addVehicleToInventory(vehicle);
                }
            }
            bufferedReader.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return dealership;
    }



    public static void saveToCSV(Dealership dealership, String filename){
        try {
            //Creating a file writer and assigning the file writer to the buffered writer.
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(getEncodedDealershipHeader(dealership));

            // Loop through transactions and write each one to the file
            for (Vehicle vehicle : dealership.getAllVehicles()) {
                bw.write(getEncodedVehicle(vehicle));
            }
            bw.close(); // Close the BufferedWriter

        } catch (Exception e){
            System.out.println("Error while saving Transactions: " + e.getMessage());
        }
    }

    private static String getEncodedDealershipHeader(Dealership dealership){
        return dealership.getName() + "|" + dealership.getAddress() + "|" + dealership.getPhone() + "\n";
    }

    private static String getEncodedVehicle(Vehicle vehicle){
        return new StringBuilder()
                .append(vehicle.getVin()).append("|")
                .append(vehicle.getYear()).append("|")
                .append(vehicle.getMake()).append("|")
                .append(vehicle.getModel()).append("|")
                .append(vehicle.getVehicleType()).append("|")
                .append(vehicle.getColor()).append("|")
                .append(vehicle.getOdometer()).append("|")
                .append(vehicle.getPrice()).append("\n").toString();
    }

}
