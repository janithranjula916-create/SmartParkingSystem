package services;

import models.Vehicle;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class QueueManager {

    // Normal waiting queue (FIFO)
    private Queue<Vehicle> normalQueue;

    // VIP queue (priority-based)
    private PriorityQueue<Vehicle> vipQueue;

    public QueueManager() {

        normalQueue = new LinkedList<>();

        vipQueue = new PriorityQueue<>(
                (v1, v2) -> v2.getPriorityLevel() - v1.getPriorityLevel());
    }

    // Add vehicle to queue
    public void addVehicle(Vehicle vehicle) {

        if (vehicle.getPriorityLevel() > 1) {

            vipQueue.add(vehicle);
            System.out.println("Added to VIP Queue");

        } else {

            normalQueue.add(vehicle);
            System.out.println("Added to Normal Queue");
        }
    }

    // Get next vehicle (VIP first)
    public Vehicle getNextVehicle() {

        if (!vipQueue.isEmpty()) {
            return vipQueue.poll();
        }

        return normalQueue.poll();
    }

    public void displayQueues() {

        System.out.println("\n--- VIP Queue ---");
        for (Vehicle v : vipQueue) {
            System.out.println(v.getVehicleNumber());
        }

        System.out.println("\n--- Normal Queue ---");
        for (Vehicle v : normalQueue) {
            System.out.println(v.getVehicleNumber());
        }
    }
}