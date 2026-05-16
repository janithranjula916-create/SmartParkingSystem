package ui;

import models.ParkingSlot;
import models.Vehicle;
import services.ParkingManager;
import services.RecordManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Date;
import utils.AppData;

public class MainDashboard extends JFrame {

        private ParkingManager manager;

        private JTextField vehicleField;
        private JTextField ownerField;
        private JComboBox<String> typeBox;

        private JPanel slotPanel;

        private JLabel totalLabel;
        private JLabel availableLabel;
        private JLabel occupiedLabel;
        private JLabel clockLabel;

        public MainDashboard() {

                RecordManager.syncAppDataFromDatabase();
                manager = AppData.manager;

                setTitle("Smart Parking Management System");
                setSize(1450, 850);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                setLayout(new BorderLayout());

                getContentPane().setBackground(new Color(18, 24, 24));

                // =============================
                // HEADER
                // =============================

                JPanel headerPanel = new JPanel(new BorderLayout());
                headerPanel.setBackground(new Color(15, 22, 22));
                headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

                JLabel title = new JLabel("SMART PARKING MANAGEMENT SYSTEM");
                title.setForeground(Color.WHITE);
                title.setFont(new Font("Arial", Font.BOLD, 32));

                clockLabel = new JLabel();
                clockLabel.setForeground(new Color(80, 255, 60));
                clockLabel.setFont(new Font("Consolas", Font.BOLD, 26));

                headerPanel.add(title, BorderLayout.WEST);
                headerPanel.add(clockLabel, BorderLayout.EAST);

                add(headerPanel, BorderLayout.NORTH);

                startClock();

                // =============================
                // LEFT PANEL
                // =============================

                JPanel leftPanel = new JPanel();
                leftPanel.setPreferredSize(new Dimension(360, 650));
                leftPanel.setBackground(new Color(18, 27, 27));
                leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
                leftPanel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

                JLabel vehicleLabel = createLabel(
                                "Vehicle Number",
                                "vehicle.png");

                vehicleField = createTextField();

                JLabel ownerLabel = createLabel(
                                "Owner Name",
                                "owner.png");

                ownerField = createTextField();

                JLabel typeLabel = createLabel(
                                "Vehicle Type",
                                "type.png");

                typeBox = new JComboBox<>(
                                new String[] {
                                                "Select Type",
                                                "Car",
                                                "Van",
                                                "Bike",
                                                "SUV"
                                });

                typeBox.setMaximumSize(new Dimension(300, 38));
                typeBox.setPreferredSize(new Dimension(300, 38));
                typeBox.setFont(new Font("Arial", Font.PLAIN, 16));
                typeBox.setAlignmentX(Component.LEFT_ALIGNMENT);

                JButton parkButton = createBlueButton(
                                "Park Vehicle",
                                "park.png");

                JButton exitButton = createBlueButton(
                                "Exit Vehicle",
                                "exit.png");

                JButton refreshButton = createBlueButton(
                                "Refresh Dashboard",
                                "refresh.png");

                totalLabel = createStatLabel(
                                "total.png",
                                "Total Slots : 12",
                                new Color(0, 200, 255));

                availableLabel = createStatLabel(
                                "free.png",
                                "Available Slots : 12",
                                new Color(110, 220, 80));

                occupiedLabel = createStatLabel(
                                "occupied.png",
                                "Occupied Slots : 0",
                                new Color(240, 80, 70));

                leftPanel.add(vehicleLabel);
                leftPanel.add(Box.createVerticalStrut(8));
                leftPanel.add(vehicleField);
                leftPanel.add(Box.createVerticalStrut(20));

                leftPanel.add(ownerLabel);
                leftPanel.add(Box.createVerticalStrut(8));
                leftPanel.add(ownerField);
                leftPanel.add(Box.createVerticalStrut(20));

                leftPanel.add(typeLabel);
                leftPanel.add(Box.createVerticalStrut(8));
                leftPanel.add(typeBox);
                leftPanel.add(Box.createVerticalStrut(30));

                leftPanel.add(parkButton);
                leftPanel.add(Box.createVerticalStrut(15));

                leftPanel.add(exitButton);
                leftPanel.add(Box.createVerticalStrut(15));

                leftPanel.add(refreshButton);
                leftPanel.add(Box.createVerticalStrut(35));

                JSeparator separator = new JSeparator();
                separator.setMaximumSize(new Dimension(310, 2));
                leftPanel.add(separator);

                leftPanel.add(Box.createVerticalStrut(25));

                leftPanel.add(totalLabel);
                leftPanel.add(Box.createVerticalStrut(15));

                leftPanel.add(availableLabel);
                leftPanel.add(Box.createVerticalStrut(15));

                leftPanel.add(occupiedLabel);

                add(leftPanel, BorderLayout.WEST);

                // =============================
                // CENTER SLOT PANEL
                // =============================

                JPanel centerWrapper = new JPanel(new BorderLayout());
                centerWrapper.setBackground(new Color(18, 24, 24));
                centerWrapper.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 35));

                JLabel slotTitle = new JLabel("Parking Slots");
                slotTitle.setForeground(Color.WHITE);
                slotTitle.setFont(new Font("Arial", Font.BOLD, 18));
                slotTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

                slotPanel = new JPanel();
                slotPanel.setBackground(new Color(18, 24, 24));
                slotPanel.setLayout(new GridLayout(3, 4, 22, 22));

                centerWrapper.add(slotTitle, BorderLayout.NORTH);
                centerWrapper.add(slotPanel, BorderLayout.CENTER);

                add(centerWrapper, BorderLayout.CENTER);

                // =============================
                // BOTTOM PANEL
                // =============================

                JPanel bottomPanel = new JPanel();
                bottomPanel.setBackground(new Color(235, 235, 235));

                JButton backButton = new JButton("< Back");
                JButton exitSystemButton = new JButton("Power Off");

                backButton.setPreferredSize(new Dimension(150, 35));
                exitSystemButton.setPreferredSize(new Dimension(180, 35));

                bottomPanel.add(backButton);
                bottomPanel.add(exitSystemButton);

                add(bottomPanel, BorderLayout.SOUTH);

                // =============================
                // ACTIONS
                // =============================

                parkButton.addActionListener(e -> {

                        String number = vehicleField.getText();

                        String owner = ownerField.getText();

                        String type = typeBox.getSelectedItem().toString();

                        if (number.isEmpty()
                                        || owner.isEmpty()
                                        || type.equals("Select Type")) {

                                JOptionPane.showMessageDialog(
                                                this,
                                                "Please enter all vehicle details!");

                                return;
                        }

                        Vehicle vehicle = new Vehicle(
                                        number,
                                        owner,
                                        type,
                                        1);

                        manager.parkVehicle(vehicle);
                        RecordManager.saveEntry(
                                        number,
                                        owner,
                                        type,
                                        "AUTO");

                        updateSlots();
                });

                exitButton.addActionListener(e -> {

                        String number = vehicleField.getText();

                        if (number.isEmpty()) {

                                JOptionPane.showMessageDialog(
                                                this,
                                                "Please enter vehicle number!");

                                return;
                        }

                        manager.exitVehicle(number);
                        RecordManager.saveExit(
                                        number,
                                        500);

                        updateSlots();
                });

                refreshButton.addActionListener(e -> {

                        updateSlots();
                });

                backButton.addActionListener(e -> {

                        LoginForm login = new LoginForm();

                        login.setVisible(true);

                        dispose();
                });

                exitSystemButton.addActionListener(e -> {

                        System.exit(0);
                });

                updateSlots();
        }

        // =============================
        // UPDATE SLOT DISPLAY
        // =============================

        private void updateSlots() {

                slotPanel.removeAll();

                ParkingSlot[] slots = manager.getSlots();

                int occupied = 0;

                for (ParkingSlot slot : slots) {

                        if (slot.isOccupied()) {
                                occupied++;
                        }

                        SlotCard card = new SlotCard(
                                        slot.getSlotId(),
                                        slot.isOccupied());

                        slotPanel.add(card);
                }

                int total = slots.length;

                int available = total - occupied;

                totalLabel.setText(
                                "Total Slots : " + total);

                availableLabel.setText(
                                "Available Slots : " + available);

                occupiedLabel.setText(
                                "Occupied Slots : " + occupied);

                slotPanel.revalidate();
                slotPanel.repaint();
        }

        // =============================
        // SLOT CARD CLASS
        // =============================

        private static class SlotCard extends JPanel {

                private String slotId;
                private boolean occupied;
                private Image carImage;

                public SlotCard(String slotId,
                                boolean occupied) {

                        this.slotId = slotId;
                        this.occupied = occupied;

                        setPreferredSize(
                                        new Dimension(260, 190));

                        setOpaque(false);

                        carImage = new ImageIcon(
                                        "src/assets/car.png").getImage();
                }

                @Override
                protected void paintComponent(Graphics g) {

                        super.paintComponent(g);

                        Graphics2D g2 = (Graphics2D) g;

                        g2.setRenderingHint(
                                        RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);

                        int w = getWidth();
                        int h = getHeight();

                        // Card background
                        g2.setColor(
                                        new Color(28, 34, 34));

                        g2.fillRoundRect(
                                        5,
                                        5,
                                        w - 10,
                                        h - 10,
                                        18,
                                        18);

                        // Card border
                        if (occupied) {
                                g2.setColor(
                                                new Color(220, 70, 60));
                        } else {
                                g2.setColor(
                                                new Color(190, 190, 190));
                        }

                        g2.drawRoundRect(
                                        5,
                                        5,
                                        w - 10,
                                        h - 10,
                                        18,
                                        18);

                        // Slot ID
                        g2.setColor(Color.WHITE);

                        g2.setFont(
                                        new Font(
                                                        "Arial",
                                                        Font.BOLD,
                                                        24));

                        FontMetrics fm = g2.getFontMetrics();

                        int textWidth = fm.stringWidth(slotId);

                        g2.drawString(
                                        slotId,
                                        (w - textWidth) / 2,
                                        34);

                        // Parking lines
                        g2.setStroke(
                                        new BasicStroke(4));

                        g2.setColor(Color.WHITE);

                        g2.drawLine(
                                        w / 2 - 65,
                                        58,
                                        w / 2 - 65,
                                        145);

                        g2.drawLine(
                                        w / 2 + 65,
                                        58,
                                        w / 2 + 65,
                                        145);

                        // Car image
                        g2.drawImage(
                                        carImage,
                                        w / 2 - 42,
                                        50,
                                        84,
                                        95,
                                        this);

                        // Status
                        String status = occupied
                                        ? "OCCUPIED"
                                        : "AVAILABLE";

                        Color badgeColor = occupied
                                        ? new Color(210, 70, 60)
                                        : new Color(55, 150, 65);

                        g2.setColor(badgeColor);

                        RoundRectangle2D badge = new RoundRectangle2D.Double(
                                        w / 2 - 60,
                                        h - 40,
                                        120,
                                        30,
                                        8,
                                        8);

                        g2.fill(badge);

                        g2.setColor(Color.WHITE);

                        g2.setFont(
                                        new Font(
                                                        "Arial",
                                                        Font.BOLD,
                                                        14));

                        FontMetrics statusFm = g2.getFontMetrics();

                        int statusWidth = statusFm.stringWidth(status);

                        g2.drawString(
                                        status,
                                        (w - statusWidth) / 2,
                                        h - 20);
                }
        }

        // =============================
        // HELPER METHODS
        // =============================

        private ImageIcon loadIcon(String fileName,
                        int width,
                        int height) {

                ImageIcon icon = new ImageIcon(
                                "src/assets/icons/" + fileName);

                Image image = icon.getImage()
                                .getScaledInstance(
                                                width,
                                                height,
                                                Image.SCALE_SMOOTH);

                return new ImageIcon(image);
        }

        private JLabel createLabel(String text,
                        String iconName) {

                JLabel label = new JLabel(
                                text,
                                loadIcon(iconName, 22, 22),
                                JLabel.LEFT);

                label.setForeground(Color.WHITE);

                label.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                20));

                label.setIconTextGap(12);

                label.setAlignmentX(Component.LEFT_ALIGNMENT);

                return label;
        }

        private JTextField createTextField() {

                JTextField field = new JTextField();

                field.setMaximumSize(
                                new Dimension(300, 38));

                field.setPreferredSize(
                                new Dimension(300, 38));

                field.setFont(
                                new Font(
                                                "Arial",
                                                Font.PLAIN,
                                                16));

                field.setAlignmentX(Component.LEFT_ALIGNMENT);

                return field;
        }

        private JButton createBlueButton(String text,
                        String iconName) {

                JButton button = new JButton(
                                text,
                                loadIcon(iconName, 28, 28));

                button.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                20));

                button.setForeground(Color.WHITE);

                button.setBackground(
                                new Color(35, 100, 170));

                button.setFocusPainted(false);

                button.setMaximumSize(
                                new Dimension(310, 55));

                button.setPreferredSize(
                                new Dimension(310, 55));

                button.setHorizontalAlignment(SwingConstants.LEFT);

                button.setIconTextGap(25);

                button.setAlignmentX(Component.LEFT_ALIGNMENT);

                return button;
        }

        private JLabel createStatLabel(String iconName,
                        String text,
                        Color color) {

                JLabel label = new JLabel(
                                text,
                                loadIcon(iconName, 28, 28),
                                JLabel.LEFT);

                label.setForeground(color);

                label.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                20));

                label.setIconTextGap(15);

                label.setAlignmentX(Component.LEFT_ALIGNMENT);

                return label;
        }

        private void startClock() {

                Timer timer = new Timer(
                                1000,
                                e -> {

                                        SimpleDateFormat sdf = new SimpleDateFormat(
                                                        "HH:mm:ss");

                                        clockLabel.setText(
                                                        sdf.format(
                                                                        new Date()));
                                });

                timer.start();
        }
}