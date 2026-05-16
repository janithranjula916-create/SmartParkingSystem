package ui;

import models.Vehicle;
import models.ParkingSlot;
import services.ParkingManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import utils.AppData;
import services.RecordManager;

public class UserDashboard extends JFrame {

        private ParkingManager manager;

        private JPanel slotPanel;
        private JTextField vehicleField;
        private JTextArea outputArea;

        public UserDashboard() {

                RecordManager.syncAppDataFromDatabase();
                manager = AppData.manager;

                setTitle("User Parking Dashboard");
                setSize(1300, 760);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                setLayout(new BorderLayout());
                getContentPane().setBackground(new Color(18, 24, 24));

                // =============================
                // HEADER
                // =============================

                JPanel headerPanel = new JPanel(new BorderLayout());

                headerPanel.setBackground(
                                new Color(15, 22, 22));

                headerPanel.setBorder(
                                BorderFactory.createEmptyBorder(
                                                25, 30, 25, 30));

                JLabel title = new JLabel(
                                "USER PARKING DASHBOARD",
                                SwingConstants.CENTER);

                title.setForeground(Color.WHITE);

                title.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                32));

                headerPanel.add(title, BorderLayout.CENTER);

                add(headerPanel, BorderLayout.NORTH);

                // =============================
                // LEFT PANEL
                // =============================

                JPanel leftPanel = new JPanel();

                leftPanel.setPreferredSize(
                                new Dimension(330, 600));

                leftPanel.setBackground(
                                new Color(25, 30, 30));

                leftPanel.setLayout(
                                new BoxLayout(
                                                leftPanel,
                                                BoxLayout.Y_AXIS));

                leftPanel.setBorder(
                                BorderFactory.createEmptyBorder(
                                                30, 25, 30, 25));

                JLabel vehicleLabel = new JLabel("Vehicle Number");

                vehicleLabel.setForeground(Color.WHITE);

                vehicleLabel.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                20));

                vehicleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                vehicleField = new JTextField();

                vehicleField.setMaximumSize(
                                new Dimension(280, 40));

                vehicleField.setPreferredSize(
                                new Dimension(280, 40));

                vehicleField.setFont(
                                new Font(
                                                "Arial",
                                                Font.PLAIN,
                                                16));

                vehicleField.setAlignmentX(Component.LEFT_ALIGNMENT);

                JButton reserveButton = createButton(
                                "Reserve Slot",
                                "park.png");

                JButton searchButton = createButton(
                                "Search Vehicle",
                                "refresh.png");

                JButton feeButton = createButton(
                                "View Fee",
                                "free.png");

                JButton refreshButton = createButton(
                                "Refresh",
                                "refresh.png");

                leftPanel.add(vehicleLabel);
                leftPanel.add(Box.createVerticalStrut(10));

                leftPanel.add(vehicleField);
                leftPanel.add(Box.createVerticalStrut(30));

                leftPanel.add(reserveButton);
                leftPanel.add(Box.createVerticalStrut(15));

                leftPanel.add(searchButton);
                leftPanel.add(Box.createVerticalStrut(15));

                leftPanel.add(feeButton);
                leftPanel.add(Box.createVerticalStrut(15));

                leftPanel.add(refreshButton);

                add(leftPanel, BorderLayout.WEST);

                // =============================
                // CENTER SLOT PANEL
                // =============================

                JPanel centerPanel = new JPanel(new BorderLayout());

                centerPanel.setBackground(
                                new Color(18, 24, 24));

                centerPanel.setBorder(
                                BorderFactory.createEmptyBorder(
                                                20, 25, 20, 25));

                JLabel slotTitle = new JLabel("Available Parking Slots");

                slotTitle.setForeground(Color.WHITE);

                slotTitle.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                18));

                slotTitle.setBorder(
                                BorderFactory.createEmptyBorder(
                                                0, 0, 12, 0));

                slotPanel = new JPanel();

                slotPanel.setBackground(
                                new Color(18, 24, 24));

                slotPanel.setLayout(
                                new GridLayout(3, 4, 22, 22));

                centerPanel.add(slotTitle, BorderLayout.NORTH);
                centerPanel.add(slotPanel, BorderLayout.CENTER);

                add(centerPanel, BorderLayout.CENTER);

                // =============================
                // OUTPUT AREA
                // =============================

                outputArea = new JTextArea();

                outputArea.setEditable(false);

                outputArea.setBackground(
                                new Color(8, 12, 15));

                outputArea.setForeground(
                                new Color(80, 255, 100));

                outputArea.setFont(
                                new Font(
                                                "Consolas",
                                                Font.PLAIN,
                                                15));

                outputArea.setBorder(
                                BorderFactory.createEmptyBorder(
                                                10, 15, 10, 15));

                JScrollPane scrollPane = new JScrollPane(outputArea);

                scrollPane.setPreferredSize(
                                new Dimension(1300, 110));

                add(scrollPane, BorderLayout.SOUTH);

                // =============================
                // BOTTOM NAVIGATION PANEL
                // =============================

                JPanel bottomPanel = new JPanel();

                bottomPanel.setBackground(
                                new Color(235, 235, 235));

                JButton backButton = new JButton("< Back");

                JButton exitSystemButton = new JButton("Power Off");

                backButton.setPreferredSize(
                                new Dimension(140, 35));

                exitSystemButton.setPreferredSize(
                                new Dimension(160, 35));

                bottomPanel.add(backButton);
                bottomPanel.add(exitSystemButton);

                add(bottomPanel, BorderLayout.PAGE_END);

                // =============================
                // ACTIONS
                // =============================

                reserveButton.addActionListener(e -> {

                        String number = vehicleField.getText().trim();

                        if (number.isEmpty()) {

                                showMessage(
                                                "WARNING",
                                                "Please enter vehicle number!",
                                                new Color(255, 180, 40));

                                return;
                        }

                        Vehicle vehicle = new Vehicle(
                                        number,
                                        "User",
                                        "Car",
                                        1);

                        manager.parkVehicle(vehicle);

                        updateSlots();

                        outputArea.append(
                                        "\n[RESERVED] Slot reserved for vehicle : "
                                                        + number);

                        showMessage(
                                        "SUCCESS",
                                        "Slot Reserved Successfully!",
                                        new Color(80, 230, 110));
                });

                searchButton.addActionListener(e -> {

                        String number = vehicleField.getText().trim();

                        if (number.isEmpty()) {

                                showMessage(
                                                "WARNING",
                                                "Please enter vehicle number!",
                                                new Color(255, 180, 40));

                                return;
                        }

                        manager.searchVehicle(number);

                        outputArea.append(
                                        "\n[SEARCH] Vehicle searched : "
                                                        + number);

                        showMessage(
                                        "SEARCH",
                                        "Vehicle search completed!",
                                        new Color(0, 180, 255));
                });

                feeButton.addActionListener(e -> {

                        String number = vehicleField.getText().trim();

                        if (number.isEmpty()) {

                                showMessage(
                                                "WARNING",
                                                "Please enter vehicle number!",
                                                new Color(255, 180, 40));

                                return;
                        }

                        outputArea.append(
                                        "\n[FEE] Vehicle : "
                                                        + number
                                                        + " | Parking Fee : Rs. 200");

                        showMessage(
                                        "PARKING FEE",
                                        "Parking Fee : Rs. 200",
                                        new Color(255, 185, 35));
                });

                refreshButton.addActionListener(e -> {

                        updateSlots();

                        outputArea.append(
                                        "\n[INFO] Dashboard refreshed");

                        showMessage(
                                        "REFRESHED",
                                        "Dashboard refreshed successfully!",
                                        new Color(0, 180, 255));
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

                for (ParkingSlot slot : slots) {

                        SlotCard card = new SlotCard(
                                        slot.getSlotId(),
                                        slot.isOccupied());

                        slotPanel.add(card);
                }

                slotPanel.revalidate();
                slotPanel.repaint();
        }

        // =============================
        // SLOT CARD
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
                                        new Dimension(240, 170));

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

                        g2.setColor(
                                        new Color(28, 34, 34));

                        g2.fillRoundRect(
                                        5,
                                        5,
                                        w - 10,
                                        h - 10,
                                        18,
                                        18);

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
                                        32);

                        // Parking white lines
                        g2.setStroke(
                                        new BasicStroke(4));

                        g2.setColor(Color.WHITE);

                        g2.drawLine(
                                        w / 2 - 60,
                                        55,
                                        w / 2 - 60,
                                        135);

                        g2.drawLine(
                                        w / 2 + 60,
                                        55,
                                        w / 2 + 60,
                                        135);

                        // Car image
                        g2.drawImage(
                                        carImage,
                                        w / 2 - 40,
                                        50,
                                        80,
                                        90,
                                        this);

                        String status = occupied
                                        ? "OCCUPIED"
                                        : "AVAILABLE";

                        Color badgeColor = occupied
                                        ? new Color(210, 70, 60)
                                        : new Color(55, 150, 65);

                        g2.setColor(badgeColor);

                        RoundRectangle2D badge = new RoundRectangle2D.Double(
                                        w / 2 - 58,
                                        h - 38,
                                        116,
                                        28,
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
                                        h - 19);
                }
        }

        // =============================
        // BUTTON STYLE
        // =============================

        private JButton createButton(String text,
                        String iconName) {

                JButton button = new JButton(
                                text,
                                loadIcon(iconName, 28, 28));

                button.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                19));

                button.setForeground(Color.WHITE);

                button.setBackground(
                                new Color(35, 100, 170));

                button.setFocusPainted(false);

                button.setBorderPainted(false);

                button.setMaximumSize(
                                new Dimension(280, 55));

                button.setPreferredSize(
                                new Dimension(280, 55));

                button.setHorizontalAlignment(
                                SwingConstants.LEFT);

                button.setIconTextGap(22);

                button.setAlignmentX(
                                Component.LEFT_ALIGNMENT);

                return button;
        }

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

        // =============================
        // MODERN MESSAGE
        // =============================

        private void showMessage(String title,
                        String message,
                        Color accentColor) {

                JDialog dialog = new JDialog(this, true);

                dialog.setUndecorated(true);
                dialog.setSize(380, 200);
                dialog.setLocationRelativeTo(this);

                JPanel panel = new JPanel(new BorderLayout());

                panel.setBackground(
                                new Color(8, 15, 25));

                panel.setBorder(
                                BorderFactory.createLineBorder(
                                                accentColor,
                                                2));

                JLabel titleLabel = new JLabel(
                                title,
                                SwingConstants.CENTER);

                titleLabel.setForeground(accentColor);

                titleLabel.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                24));

                titleLabel.setBorder(
                                BorderFactory.createEmptyBorder(
                                                25, 10, 10, 10));

                JLabel messageLabel = new JLabel(
                                message,
                                SwingConstants.CENTER);

                messageLabel.setForeground(Color.WHITE);

                messageLabel.setFont(
                                new Font(
                                                "Arial",
                                                Font.PLAIN,
                                                17));

                JButton okButton = new JButton("OK");

                okButton.setForeground(Color.WHITE);
                okButton.setBackground(accentColor);
                okButton.setFocusPainted(false);
                okButton.setBorderPainted(false);

                JPanel buttonPanel = new JPanel();

                buttonPanel.setBackground(
                                new Color(8, 15, 25));

                buttonPanel.add(okButton);

                panel.add(titleLabel, BorderLayout.NORTH);
                panel.add(messageLabel, BorderLayout.CENTER);
                panel.add(buttonPanel, BorderLayout.SOUTH);

                okButton.addActionListener(e -> {
                        dialog.dispose();
                });

                dialog.add(panel);
                dialog.setVisible(true);
        }
}