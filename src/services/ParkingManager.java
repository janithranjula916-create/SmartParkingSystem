package services;

import models.Vehicle;
import models.ParkingSlot;

import java.util.HashMap;

public class ParkingManager {

    private ParkingSlot[] slots;

    private HashMap<String, Vehicle> vehicleMap;
    private HashMap<String, String> vehicleSlotMap;

    private FeeCalculator feeCalculator;
    private QueueManager queueManager;

    // Constructor
    public ParkingManager(int totalSlots) {

        slots = new ParkingSlot[totalSlots];

        vehicleMap = new HashMap<>();
        vehicleSlotMap = new HashMap<>();

        feeCalculator = new FeeCalculator();
        queueManager = new QueueManager();

        // Create slots
        for (int i = 0; i < totalSlots; i++) {
            slots[i] = new ParkingSlot("S" + (i + 1));
        }
    }

    // =========================
    // PARK VEHICLE
    // =========================
    public void parkVehicle(Vehicle vehicle) {

        // Prevent same vehicle parking twice
        if (vehicleMap.containsKey(vehicle.getVehicleNumber())) {
            System.out.println("Vehicle already parked!");
            return;
        }

        for (ParkingSlot slot : slots) {

            if (!slot.isOccupied()) {

                slot.occupySlot();

                vehicleMap.put(
                        vehicle.getVehicleNumber(),
                        vehicle);

                vehicleSlotMap.put(
                        vehicle.getVehicleNumber(),
                        slot.getSlotId());

                System.out.println("\nVehicle Parked Successfully!");
                System.out.println("Allocated Slot : " + slot.getSlotId());

                // SAVE ENTRY TO DATABASE
                RecordManager.saveEntry(
                        vehicle.getVehicleNumber(),
                        vehicle.getOwnerName(),
                        vehicle.getVehicleType(),
                        slot.getSlotId());

                return;
            }
        }

        // No slot available -> add to queue
        System.out.println("\nParking Full!");
        queueManager.addVehicle(vehicle);
    }

    // =========================
    // LOAD PARKED VEHICLE FROM DATABASE
    // =========================
    // Important:
    // This method is only for loading existing PARKED records from DB.
    // It does NOT insert again into database.
    public void loadParkedVehicle(Vehicle vehicle, String slotNumber) {

        for (ParkingSlot slot : slots) {

            if (slot.getSlotId().equals(slotNumber)) {

                slot.occupySlot();

                vehicleMap.put(
                        vehicle.getVehicleNumber(),
                        vehicle);

                vehicleSlotMap.put(
                        vehicle.getVehicleNumber(),
                        slotNumber);

                return;
            }
        }
    }

    // =========================
    // EXIT VEHICLE
    // =========================
    public void exitVehicle(String vehicleNumber) {

        Vehicle vehicle = vehicleMap.get(vehicleNumber);

        if (vehicle == null) {
            System.out.println("Vehicle Not Found!");
            return;
        }

        // Calculate fee
        double fee = feeCalculator.calculateFee(
                vehicle.getEntryTime());

        // Find slot
        String slotId = vehicleSlotMap.get(vehicleNumber);

        // SAVE EXIT TO DATABASE
        RecordManager.saveExit(vehicleNumber, fee);

        // Free slot
        for (ParkingSlot slot : slots) {

            if (slot.getSlotId().equals(slotId)) {

                slot.freeSlot();

                System.out.println("\nVehicle Exited Successfully!");
                System.out.println("Freed Slot : " + slotId);
                System.out.println("Parking Fee : Rs." + fee);

                break;
            }
        }

        // Remove vehicle data from memory
        vehicleMap.remove(vehicleNumber);
        vehicleSlotMap.remove(vehicleNumber);

        // Automatically park next waiting vehicle
        Vehicle nextVehicle = queueManager.getNextVehicle();

        if (nextVehicle != null) {

            System.out.println("\nAllocating Slot to Waiting Vehicle...");

            parkVehicle(nextVehicle);
        }
    }

    // =========================
    // DISPLAY SLOTS
    // =========================
    public void displaySlots() {

        System.out.println("\n===== PARKING SLOTS =====");

        for (ParkingSlot slot : slots) {

            slot.displaySlotInfo();

            System.out.println("-------------------");
        }
    }

    // =========================
    // SEARCH VEHICLE
    // =========================
    public void searchVehicle(String vehicleNumber) {

        Vehicle vehicle = vehicleMap.get(vehicleNumber);

        if (vehicle != null) {

            System.out.println("\nVehicle Found!");
            vehicle.displayVehicleInfo();

            String slotId = vehicleSlotMap.get(vehicleNumber);
            System.out.println("Slot Number    : " + slotId);

        } else {

            System.out.println("Vehicle Not Found!");
        }
    }

    // =========================
    // DISPLAY QUEUES
    // =========================
    public void displayQueues() {
        queueManager.displayQueues();
    }

    // =========================
    // GET ALL SLOTS
    // =========================
    public ParkingSlot[] getSlots() {
        return slots;
    }

    // =========================
    // GET SLOT BY VEHICLE NUMBER
    // =========================
    public String getSlotByVehicleNumber(String vehicleNumber) {
        return vehicleSlotMap.get(vehicleNumber);
    }

    // =========================
    // GET VEHICLE COUNT
    // =========================
    public int getParkedVehicleCount() {
        return vehicleMap.size();
    }
}