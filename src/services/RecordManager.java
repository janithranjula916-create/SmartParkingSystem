package services;

import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import models.Vehicle;
import utils.AppData;

public class RecordManager {

    // =========================
    // SAVE VEHICLE ENTRY
    // =========================

    public static void saveEntry(String vehicleNumber,
            String ownerName,
            String vehicleType,
            String slotNumber) {

        try {

            Connection con = DBConnection.getConnection();
            String checkSql = "SELECT COUNT(*) FROM parking_records " +
                    "WHERE vehicle_number = ? AND status = 'PARKED'";

            PreparedStatement checkPst = con.prepareStatement(checkSql);

            checkPst.setString(1, vehicleNumber);

            ResultSet checkRs = checkPst.executeQuery();

            if (checkRs.next() && checkRs.getInt(1) > 0) {
                return;
            }

            String sql = "INSERT INTO parking_records " +
                    "(vehicle_number, owner_name, vehicle_type, slot_number, status) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, vehicleNumber);
            pst.setString(2, ownerName);
            pst.setString(3, vehicleType);
            pst.setString(4, slotNumber);
            pst.setString(5, "PARKED");

            pst.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =========================
    // SAVE VEHICLE EXIT
    // =========================

    public static void saveExit(String vehicleNumber, double fee) {

        try {

            Connection con = DBConnection.getConnection();

            // 1. Get parked vehicle slot
            String selectSql = "SELECT slot_number FROM parking_records " +
                    "WHERE vehicle_number = ? AND status = 'PARKED' " +
                    "ORDER BY id DESC LIMIT 1";

            PreparedStatement selectPst = con.prepareStatement(selectSql);
            selectPst.setString(1, vehicleNumber);

            ResultSet rs = selectPst.executeQuery();

            String slotNumber = null;

            if (rs.next()) {
                slotNumber = rs.getString("slot_number");
            }

            // 2. Update parking_records
            String updateSql = "UPDATE parking_records " +
                    "SET exit_time = NOW(), fee = ?, status = 'EXITED' " +
                    "WHERE vehicle_number = ? AND status = 'PARKED'";

            PreparedStatement updatePst = con.prepareStatement(updateSql);
            updatePst.setDouble(1, fee);
            updatePst.setString(2, vehicleNumber);

            int rows = updatePst.executeUpdate();

            System.out.println("Parking record updated rows: " + rows);

            // 3. Insert into exit_records table
            if (rows > 0 && slotNumber != null) {

                String insertExitSql = "INSERT INTO exit_records " +
                        "(vehicle_number, slot_number, exit_time, fee) " +
                        "VALUES (?, ?, NOW(), ?)";

                PreparedStatement exitPst = con.prepareStatement(insertExitSql);
                exitPst.setString(1, vehicleNumber);
                exitPst.setString(2, slotNumber);
                exitPst.setDouble(3, fee);

                int exitRows = exitPst.executeUpdate();

                System.out.println("Exit record inserted rows: " + exitRows);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =========================
    // TOTAL VEHICLES
    // =========================

    public static int getTotalVehicles() {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT COUNT(*) FROM parking_records";

            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }

    // =========================
    // OCCUPIED SLOTS
    // =========================

    public static int getOccupiedSlots() {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT COUNT(*) FROM parking_records WHERE status = 'PARKED'";

            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }

    // =========================
    // AVAILABLE SLOTS
    // =========================

    public static int getAvailableSlots() {

        int totalSlots = 12;

        int occupied = getOccupiedSlots();

        return totalSlots - occupied;
    }

    // =========================
    // TOTAL REVENUE
    // =========================

    public static double getRevenue() {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT SUM(fee) FROM parking_records";

            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }
    // =========================
    // SEARCH VEHICLE RECORD
    // =========================

    public static String searchVehicle(String vehicleNumber) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM parking_records " +
                    "WHERE vehicle_number = ? " +
                    "ORDER BY id DESC LIMIT 1";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, vehicleNumber);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                return "\n[VEHICLE FOUND]"
                        + "\nVehicle Number : " + rs.getString("vehicle_number")
                        + "\nOwner Name     : " + rs.getString("owner_name")
                        + "\nVehicle Type   : " + rs.getString("vehicle_type")
                        + "\nSlot Number    : " + rs.getString("slot_number")
                        + "\nEntry Time     : " + rs.getString("entry_time")
                        + "\nExit Time      : " + rs.getString("exit_time")
                        + "\nFee            : Rs. " + rs.getDouble("fee")
                        + "\nStatus         : " + rs.getString("status")
                        + "\n----------------------------------";
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "\n[NOT FOUND] No record found for vehicle : "
                + vehicleNumber
                + "\n----------------------------------";
    }
    // =========================
    // SYNC APPDATA FROM DATABASE
    // =========================

    public static void syncAppDataFromDatabase() {

        try {

            AppData.manager = new ParkingManager(12);

            Connection con = DBConnection.getConnection();

            String sql = "SELECT vehicle_number, owner_name, vehicle_type, slot_number " +
                    "FROM parking_records " +
                    "WHERE status = 'PARKED' " +
                    "ORDER BY id ASC " +
                    "LIMIT 12";

            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Vehicle vehicle = new Vehicle(
                        rs.getString("vehicle_number"),
                        rs.getString("owner_name"),
                        rs.getString("vehicle_type"),
                        1);

                AppData.manager.loadParkedVehicle(
                        vehicle,
                        rs.getString("slot_number"));
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void saveReservation(String vehicleNumber,
            String ownerName,
            String vehicleType,
            String slotNumber) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO reservations " +
                    "(vehicle_number, owner_name, vehicle_type, slot_number, reservation_time, status) " +
                    "VALUES (?, ?, ?, ?, NOW(), ?)";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, vehicleNumber);
            pst.setString(2, ownerName);
            pst.setString(3, vehicleType);
            pst.setString(4, slotNumber);
            pst.setString(5, "RESERVED");

            int rows = pst.executeUpdate();

            System.out.println("Reservation inserted rows: " + rows);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}