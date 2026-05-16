package ui;

import models.ParkingSlot;
import services.ParkingManager;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import utils.AppData;
import services.RecordManager;

public class AnalyticsDashboard extends JFrame {

    private ParkingManager manager;

    private StatCard totalCard;
    private StatCard occupiedCard;
    private StatCard availableCard;
    private StatCard revenueCard;

    private JLabel timeLabel;

    public AnalyticsDashboard() {

        manager = AppData.manager;

        setTitle("Smart Parking Management System");
        setSize(1450, 820);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(6, 16, 26));

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(5, 13, 22));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        JLabel logo = new JLabel("P");
        logo.setForeground(new Color(0, 180, 255));
        logo.setFont(new Font("Arial", Font.BOLD, 42));
        logo.setPreferredSize(new Dimension(120, 70));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("PARKING ANALYTICS DASHBOARD");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 34));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Real-time parking statistics and revenue overview");
        subtitle.setForeground(new Color(170, 185, 200));
        subtitle.setFont(new Font("Arial", Font.PLAIN, 17));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(8));
        titlePanel.add(subtitle);

        JPanel livePanel = new JPanel(new GridLayout(2, 1));
        livePanel.setPreferredSize(new Dimension(170, 75));
        livePanel.setBackground(new Color(10, 25, 38));
        livePanel.setBorder(BorderFactory.createLineBorder(new Color(35, 65, 90), 1));

        JLabel liveLabel = new JLabel("Live", SwingConstants.CENTER);
        liveLabel.setForeground(new Color(80, 255, 100));
        liveLabel.setFont(new Font("Arial", Font.BOLD, 18));

        timeLabel = new JLabel("", SwingConstants.CENTER);
        timeLabel.setForeground(new Color(180, 190, 200));
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        livePanel.add(liveLabel);
        livePanel.add(timeLabel);

        headerPanel.add(logo, BorderLayout.WEST);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        headerPanel.add(livePanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        startClock();

        // =========================
        // CENTER CARDS
        // =========================

        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 30, 30));
        centerPanel.setBackground(new Color(6, 16, 26));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(35, 55, 35, 55));

        totalCard = new StatCard(
                "TOTAL VEHICLES",
                "0",
                "Vehicles entered today",
                "CAR",
                new Color(0, 180, 255));

        occupiedCard = new StatCard(
                "OCCUPIED SLOTS",
                "0",
                "Currently used parking slots",
                "P",
                new Color(255, 75, 85));

        availableCard = new StatCard(
                "AVAILABLE SLOTS",
                "12",
                "Free slots for users",
                "SLOT",
                new Color(80, 230, 110));

        revenueCard = new StatCard(
                "TODAY'S REVENUE",
                "Rs. 0.0",
                "Total parking fee collection",
                "RS",
                new Color(255, 185, 35));

        centerPanel.add(totalCard);
        centerPanel.add(occupiedCard);
        centerPanel.add(availableCard);
        centerPanel.add(revenueCard);

        add(centerPanel, BorderLayout.CENTER);

        // =========================
        // BOTTOM BUTTONS
        // =========================

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(7, 18, 28));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(18, 10, 20, 10));

        JButton refreshButton = createMainButton("Refresh Analytics");
        JButton backButton = createDarkButton("Back");
        JButton exitButton = createDarkButton("Power Off");

        bottomPanel.add(refreshButton);
        bottomPanel.add(backButton);
        bottomPanel.add(exitButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // =========================
        // ACTIONS
        // =========================

        refreshButton.addActionListener(e -> refreshAnalytics());

        backButton.addActionListener(e -> {

            LoginForm login = new LoginForm();
            login.setVisible(true);

            dispose();
        });

        exitButton.addActionListener(e -> System.exit(0));

        refreshAnalytics();
    }

    // =========================
    // REFRESH REAL DATA
    // =========================

    private void refreshAnalytics() {

        int totalVehicles = RecordManager.getTotalVehicles();

        int occupied = RecordManager.getOccupiedSlots();

        int totalSlots = 12;

        if (occupied > totalSlots) {
            occupied = totalSlots;
        }

        int available = totalSlots - occupied;

        if (available < 0) {
            available = 0;
        }

        double revenue = RecordManager.getRevenue();

        int occupiedPercent = occupied * 100 / totalSlots;

        int availablePercent = available * 100 / totalSlots;

        totalCard.updateData(
                String.valueOf(totalVehicles),
                totalVehicles == 0 ? 0 : 100);

        occupiedCard.updateData(
                String.valueOf(occupied),
                occupiedPercent);

        availableCard.updateData(
                String.valueOf(available),
                availablePercent);

        revenueCard.updateData(
                "Rs. " + revenue,
                revenue == 0 ? 0 : 100);
    }

    // =========================
    // CLOCK
    // =========================

    private void startClock() {

        Timer timer = new Timer(
                1000,
                e -> {

                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");

                    timeLabel.setText(
                            sdf.format(new Date()));
                });

        timer.start();
    }

    // =========================
    // BUTTONS
    // =========================

    private JButton createMainButton(String text) {

        JButton button = new JButton(text);

        button.setPreferredSize(new Dimension(280, 55));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 95, 210));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 160, 255), 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private JButton createDarkButton(String text) {

        JButton button = new JButton(text);

        button.setPreferredSize(new Dimension(220, 55));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(12, 28, 42));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(45, 75, 100), 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    // =========================
    // MODERN STAT CARD
    // =========================

    private static class StatCard extends JPanel {

        private JLabel valueLabel;
        private JLabel percentLabel;
        private JProgressBar progressBar;

        private Color accentColor;

        public StatCard(String title,
                String value,
                String description,
                String iconText,
                Color accentColor) {

            this.accentColor = accentColor;

            setOpaque(false);
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(30, 35, 30, 35));

            // TOP CONTENT
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setOpaque(false);

            // ICON PANEL
            CircleIcon iconPanel = new CircleIcon(iconText, accentColor);

            iconPanel.setPreferredSize(new Dimension(150, 150));

            contentPanel.add(iconPanel, BorderLayout.WEST);

            // TEXT PANEL
            JPanel textPanel = new JPanel();
            textPanel.setOpaque(false);
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            textPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));

            JLabel titleLabel = new JLabel(title);
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            valueLabel = new JLabel(value);
            valueLabel.setForeground(accentColor);
            valueLabel.setFont(new Font("Arial", Font.BOLD, 50));
            valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel descLabel = new JLabel(description);
            descLabel.setForeground(new Color(170, 185, 200));
            descLabel.setFont(new Font("Arial", Font.PLAIN, 17));
            descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            textPanel.add(titleLabel);
            textPanel.add(Box.createVerticalStrut(20));
            textPanel.add(valueLabel);
            textPanel.add(Box.createVerticalStrut(12));
            textPanel.add(descLabel);

            contentPanel.add(textPanel, BorderLayout.CENTER);

            // PERCENT PANEL
            JPanel percentPanel = new JPanel(new GridLayout(2, 1));
            percentPanel.setPreferredSize(new Dimension(130, 80));
            percentPanel.setBackground(new Color(10, 25, 38));
            percentPanel.setBorder(BorderFactory.createLineBorder(new Color(45, 75, 100), 1));

            percentLabel = new JLabel("0%", SwingConstants.CENTER);
            percentLabel.setForeground(accentColor);
            percentLabel.setFont(new Font("Arial", Font.BOLD, 20));

            JLabel percentSub = new JLabel("usage rate", SwingConstants.CENTER);
            percentSub.setForeground(new Color(180, 190, 200));
            percentSub.setFont(new Font("Arial", Font.PLAIN, 14));

            percentPanel.add(percentLabel);
            percentPanel.add(percentSub);

            contentPanel.add(percentPanel, BorderLayout.EAST);

            add(contentPanel, BorderLayout.CENTER);

            // BOTTOM REAL PROGRESS BAR
            progressBar = new JProgressBar(0, 100);
            progressBar.setValue(0);
            progressBar.setStringPainted(true);
            progressBar.setForeground(accentColor);
            progressBar.setBackground(new Color(25, 45, 60));
            progressBar.setBorderPainted(false);
            progressBar.setPreferredSize(new Dimension(100, 18));

            add(progressBar, BorderLayout.SOUTH);
        }

        public void updateData(String value,
                int percentage) {

            valueLabel.setText(value);

            percentLabel.setText(percentage + "%");

            progressBar.setValue(percentage);

            progressBar.setString(percentage + "%");
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

            // Shadow
            g2.setColor(new Color(0, 0, 0, 100));
            g2.fillRoundRect(14, 16, w - 28, h - 30, 30, 30);

            // Background gradient
            GradientPaint gp = new GradientPaint(
                    0,
                    0,
                    new Color(12, 30, 45),
                    w,
                    h,
                    new Color(6, 18, 28));

            g2.setPaint(gp);
            g2.fillRoundRect(5, 5, w - 14, h - 20, 30, 30);

            // Border
            g2.setColor(accentColor);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(5, 5, w - 14, h - 20, 30, 30);

            // Top glow line
            g2.setColor(
                    new Color(
                            accentColor.getRed(),
                            accentColor.getGreen(),
                            accentColor.getBlue(),
                            120));

            g2.fillRoundRect(60, 5, w - 120, 5, 10, 10);
        }
    }

    // =========================
    // CIRCLE ICON
    // =========================

    private static class CircleIcon extends JPanel {

        private String text;
        private Color color;

        public CircleIcon(String text,
                Color color) {

            this.text = text;
            this.color = color;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int size = Math.min(getWidth(), getHeight()) - 20;
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;

            g2.setColor(
                    new Color(
                            color.getRed(),
                            color.getGreen(),
                            color.getBlue(),
                            35));

            g2.fillOval(x, y, size, size);

            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(x, y, size, size);

            g2.setFont(new Font("Arial", Font.BOLD, 32));

            FontMetrics fm = g2.getFontMetrics();

            int textWidth = fm.stringWidth(text);

            g2.drawString(
                    text,
                    getWidth() / 2 - textWidth / 2,
                    getHeight() / 2 + 12);
        }
    }
}