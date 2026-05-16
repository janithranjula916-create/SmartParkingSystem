package models;

public class ParkingSlot {

    private String slotId;
    private boolean occupied;

    public ParkingSlot(String slotId) {

        this.slotId = slotId;
        this.occupied = false;
    }

    public String getSlotId() {
        return slotId;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void occupySlot() {
        occupied = true;
    }

    public void freeSlot() {
        occupied = false;
    }

    public void displaySlotInfo() {

        System.out.println("Slot ID : " + slotId);

        if (occupied) {
            System.out.println("Status : Occupied");
        } else {
            System.out.println("Status : Available");
        }
    }

    @Override
    public String toString() {

        if (occupied) {
            return slotId + " (Occupied)";
        }

        return slotId + " (Available)";
    }

}