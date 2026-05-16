package services;

public class FeeCalculator {

    // Parking fee per hour
    private static final double RATE_PER_HOUR = 100.0;

    // Calculate parking fee
    public double calculateFee(long entryTime) {

        long exitTime = System.currentTimeMillis();

        // Calculate parked duration in milliseconds
        long duration = exitTime - entryTime;

        // Convert milliseconds to hours
        double hours = duration / (1000.0 * 60 * 60);

        // Minimum 1 hour charge
        if (hours < 1) {
            hours = 1;
        }

        return hours * RATE_PER_HOUR;
    }
}