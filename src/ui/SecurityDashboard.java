package ui;

import models.Vehicle;
import services.ParkingManager;

import javax.swing.*;
import java.awt.*;
import utils.AppData;
import services.RecordManager;

public class SecurityDashboard extends JFrame {

    private ParkingManager manager;

    private JTextField vehicleField;
    private JTextField ownerField;
    private JTextArea logArea;

    public SecurityDashboard() {

        RecordManager.syncAppDataFromDatabase();
        manager = AppData.manager;

        setTitle("Security Dashboard");
        setSize(1350, 760);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        getContentPane().setBackground(new Color(5, 12, 22));

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(5, 12, 22));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        JLabel leftLogo = new JLabel("P");
        leftLogo.setForeground(new Color(0, 170, 255));
        leftLogo.setFont(new Font("Arial", Font.BOLD, 42));
        leftLogo.setPreferredSize(new Dimension(100, 70));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("SECURITY GATE DASHBOARD");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 42));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Smart parking entry and exit control panel");
        subtitle.setForeground(new Color(150, 175, 195));
        subtitle.setFont(new Font("Arial", Font.PLAIN, 17));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(8));
        titlePanel.add(subtitle);

        JLabel rightLogo = new JLabel(
                "<html><center>P<br><span style='font-size:12px;'>SMART PARKING<br>MANAGEMENT SYSTEM</span></center></html>",
                SwingConstants.CENTER);

        rightLogo.setForeground(new Color(0, 170, 255));
        rightLogo.setFont(new Font("Arial", Font.BOLD, 34));
        rightLogo.setPreferredSize(new Dimension(210, 90));

        headerPanel.add(leftLogo, BorderLayout.WEST);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        headerPanel.add(rightLogo, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // =========================
        // MAIN PANEL
        // =========================

        RoundedPanel mainPanel = new RoundedPanel(28, new Color(7, 20, 34), new Color(0, 120, 255));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(35, 45, 35, 45));

        // =========================
        // FORM PANEL
        // =========================

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 30, 25));
        formPanel.setOpaque(false);

        JLabel vehicleLabel = createFormLabel("Vehicle Number", "CAR");
        JLabel ownerLabel = createFormLabel("Owner Name", "USER");

        vehicleField = createInputField("Enter vehicle number...");
        ownerField = createInputField("Enter owner name...");

        formPanel.add(vehicleLabel);
        formPanel.add(vehicleField);

        formPanel.add(ownerLabel);
        formPanel.add(ownerField);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        // =========================
        // BUTTON GRID
        // =========================

        JPanel buttonGrid = new JPanel(new GridLayout(2, 2, 30, 30));
        buttonGrid.setOpaque(false);
        buttonGrid.setBorder(BorderFactory.createEmptyBorder(45, 10, 25, 10));

        ActionButton entryButton = new ActionButton(
                "Allow Entry",
                "IN",
                new Color(80, 255, 120));

        ActionButton exitButton = new ActionButton(
                "Allow Exit",
                "OUT",
                new Color(0, 180, 255));

        ActionButton searchButton = new ActionButton(
                "Search Vehicle",
                "SEARCH",
                new Color(170, 120, 255));

        ActionButton clearButton = new ActionButton(
                "Clear Log",
                "DEL",
                new Color(255, 120, 70));

        buttonGrid.add(entryButton);
        buttonGrid.add(exitButton);
        buttonGrid.add(searchButton);
        buttonGrid.add(clearButton);

        mainPanel.add(buttonGrid, BorderLayout.CENTER);

        // =========================
        // LOG AREA
        // =========================

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBackground(new Color(3, 10, 18));
        logArea.setForeground(new Color(80, 255, 120));
        logArea.setFont(new Font("Consolas", Font.PLAIN, 15));
        logArea.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(new Dimension(1000, 90));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(30, 70, 100)));

        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // =========================
        // BOTTOM NAVIGATION
        // =========================

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(5, 12, 22));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 25, 10));

        JButton backButton = createNavButton("Back", new Color(0, 120, 255));
        JButton exitSystemButton = createNavButton("Exit System", new Color(255, 75, 85));

        bottomPanel.add(backButton);
        bottomPanel.add(Box.createHorizontalStrut(30));
        bottomPanel.add(exitSystemButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // =========================
        // ACTIONS
        // =========================

        entryButton.addActionListener(e -> {

            String number = vehicleField.getText().trim();
            String owner = ownerField.getText().trim();

            if (number.isEmpty() || number.equals("Enter vehicle number...")
                    || owner.isEmpty() || owner.equals("Enter owner name...")) {

                showModernMessage(
                        "WARNING",
                        "Please enter vehicle number and owner name!",
                        new Color(255, 180, 40));

                return;
            }

            Vehicle vehicle = new Vehicle(
                    number,
                    owner,
                    "Car",
                    1);

            manager.parkVehicle(vehicle);

            logArea.append("\n[ENTRY ALLOWED] Vehicle : " + number + " | Owner : " + owner);

            showModernMessage(
                    "ENTRY ALLOWED",
                    "Vehicle successfully entered!",
                    new Color(80, 255, 120));
        });

        exitButton.addActionListener(e -> {

            String number = vehicleField.getText().trim();

            if (number.isEmpty() || number.equals("Enter vehicle number...")) {

                showModernMessage(
                        "WARNING",
                        "Please enter vehicle number!",
                        new Color(255, 180, 40));

                return;
            }

            manager.exitVehicle(number);

            logArea.append("\n[EXIT ALLOWED] Vehicle : " + number);

            showModernMessage(
                    "EXIT ALLOWED",
                    "Vehicle successfully exited!",
                    new Color(0, 180, 255));
        });

        searchButton.addActionListener(e -> {

            String number = vehicleField.getText().trim();

            if (number.isEmpty()
                    || number.equals("Enter vehicle number...")) {

                showModernMessage(
                        "WARNING",
                        "Please enter vehicle number!",
                        new Color(255, 180, 40));

                return;
            }

            String result = RecordManager.searchVehicle(number);

            logArea.append(result);

            showModernMessage(
                    "SEARCH COMPLETE",
                    "Vehicle search result displayed in log.",
                    new Color(170, 120, 255));
        });
        clearButton.addActionListener(e -> {

            logArea.setText("");
        });

        backButton.addActionListener(e -> {

            LoginForm login = new LoginForm();
            login.setVisible(true);

            dispose();
        });

        exitSystemButton.addActionListener(e -> {

            System.exit(0);
        });
    }

    // =========================
    // LABEL
    // =========================

    private JLabel createFormLabel(String text, String iconText) {

        JLabel label = new JLabel(iconText + "   " + text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        return label;
    }

    // =========================
    // INPUT FIELD
    // =========================

    private JTextField createInputField(String placeholder) {

        JTextField field = new JTextField();

        field.setText(placeholder);
        field.setForeground(new Color(160, 175, 190));
        field.setBackground(new Color(3, 12, 22));
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Arial", Font.PLAIN, 20));

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(0, 140, 255),
                                1),
                        BorderFactory.createEmptyBorder(
                                10,
                                18,
                                10,
                                18)));

        field.addFocusListener(new java.awt.event.FocusAdapter() {

            public void focusGained(java.awt.event.FocusEvent e) {

                if (field.getText().equals(placeholder)) {

                    field.setText("");
                    field.setForeground(Color.WHITE);
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {

                if (field.getText().isEmpty()) {

                    field.setText(placeholder);
                    field.setForeground(new Color(160, 175, 190));
                }
            }
        });

        return field;
    }

    // =========================
    // NAV BUTTON
    // =========================

    private JButton createNavButton(String text, Color color) {

        JButton button = new JButton(text);

        button.setPreferredSize(new Dimension(260, 55));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(8, 22, 35));
        button.setFont(new Font("Arial", Font.BOLD, 22));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setBorder(
                BorderFactory.createLineBorder(
                        color,
                        1));

        return button;
    }

    // =========================
    // MODERN MESSAGE
    // =========================

    private void showModernMessage(String title,
            String message,
            Color accentColor) {

        JDialog dialog = new JDialog(this, true);

        dialog.setUndecorated(true);
        dialog.setSize(420, 220);
        dialog.setLocationRelativeTo(this);

        RoundedPanel panel = new RoundedPanel(
                24,
                new Color(8, 18, 30),
                accentColor);

        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);

        titleLabel.setForeground(accentColor);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));

        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);

        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 17));

        JButton okButton = new JButton("OK");

        okButton.setPreferredSize(new Dimension(120, 38));
        okButton.setBackground(accentColor);
        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Arial", Font.BOLD, 16));
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);

        JPanel buttonPanel = new JPanel();

        buttonPanel.setOpaque(false);
        buttonPanel.add(okButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        okButton.addActionListener(e -> dialog.dispose());

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // =========================
    // ROUNDED PANEL
    // =========================

    private static class RoundedPanel extends JPanel {

        private int radius;
        private Color bgColor;
        private Color borderColor;

        public RoundedPanel(int radius,
                Color bgColor,
                Color borderColor) {

            this.radius = radius;
            this.bgColor = bgColor;
            this.borderColor = borderColor;

            setOpaque(false);
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

            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, w - 1, h - 1, radius, radius);

            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(0, 0, w - 1, h - 1, radius, radius);
        }
    }

    // =========================
    // ACTION BUTTON
    // =========================

    private static class ActionButton extends JButton {

        private String text;
        private String iconText;
        private Color accentColor;

        public ActionButton(String text,
                String iconText,
                Color accentColor) {

            this.text = text;
            this.iconText = iconText;
            this.accentColor = accentColor;

            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g;

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            g2.setColor(
                    new Color(
                            accentColor.getRed(),
                            accentColor.getGreen(),
                            accentColor.getBlue(),
                            35));

            g2.fillRoundRect(5, 5, w - 10, h - 10, 26, 26);

            g2.setColor(accentColor);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(5, 5, w - 10, h - 10, 26, 26);

            // icon circle
            g2.setColor(
                    new Color(
                            accentColor.getRed(),
                            accentColor.getGreen(),
                            accentColor.getBlue(),
                            45));

            g2.fillOval(35, 25, 75, 75);

            g2.setColor(accentColor);
            g2.drawOval(35, 25, 75, 75);

            g2.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics fmIcon = g2.getFontMetrics();
            int iconWidth = fmIcon.stringWidth(iconText);

            g2.drawString(
                    iconText,
                    72 - iconWidth / 2,
                    72);

            // separator
            g2.setColor(new Color(70, 90, 110));
            g2.drawLine(145, 30, 145, h - 30);

            // text
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 25));

            g2.drawString(
                    text,
                    185,
                    h / 2 + 10);

            // arrow
            g2.setColor(accentColor);
            g2.setFont(new Font("Arial", Font.BOLD, 34));
            g2.drawString(">", w - 65, h / 2 + 13);
        }
    }
}