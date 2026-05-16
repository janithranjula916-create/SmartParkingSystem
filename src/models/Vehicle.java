package models;

public class Vehicle {

    private String vehicleNumber;
    private String ownerName;
    private String vehicleType;
    private long entryTime;
    private int priorityLevel;

    public Vehicle(String vehicleNumber,
                   String ownerName,
                   String vehicleType,
                   int priorityLevel) {

        this.vehicleNumber = vehicleNumber;
        this.ownerName = ownerName;
        this.vehicleType = vehicleType;
        this.priorityLevel = priorityLevel;
        this.entryTime = System.currentTimeMillis();
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void displayVehicleInfo() {

        System.out.println("Vehicle Number : " + vehicleNumber);
        System.out.println("Owner Name     : " + ownerName);
        System.out.println("Vehicle Type   : " + vehicleType);
        System.out.println("Priority Level : " + priorityLevel);
    }
}