package models;

public class ParkingRecord {

    private String vehicleNumber;
    private String ownerName;

    public ParkingRecord(String vehicleNumber,
            String ownerName) {

        this.vehicleNumber = vehicleNumber;
        this.ownerName = ownerName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void displayRecord() {

        System.out.println(
                vehicleNumber + " - " + ownerName);
    }
}