package ui;

import utils.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import ui.AdminSelectionDialog;

public class LoginForm extends JFrame {

        private JTextField usernameField;
        private JPasswordField passwordField;
        private JCheckBox rememberBox;

        public LoginForm() {

                setTitle("Smart Parking Login");
                setSize(1280, 720);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                setResizable(false);

                BackgroundPanel backgroundPanel = new BackgroundPanel("src/assets/login_bg.jpg");

                backgroundPanel.setLayout(new GridBagLayout());

                add(backgroundPanel);

                RoundedPanel loginCard = new RoundedPanel(35);

                loginCard.setPreferredSize(
                                new Dimension(560, 590));

                loginCard.setBackground(
                                new Color(5, 12, 22, 220));

                loginCard.setLayout(
                                new BoxLayout(
                                                loginCard,
                                                BoxLayout.Y_AXIS));

                loginCard.setBorder(
                                new EmptyBorder(
                                                25,
                                                45,
                                                25,
                                                45));

                JLabel logo = new JLabel("P", SwingConstants.CENTER);

                logo.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                54));

                logo.setForeground(
                                new Color(30, 140, 255));

                logo.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                JLabel title = new JLabel(
                                "SMART PARKING",
                                SwingConstants.CENTER);

                title.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                38));

                title.setForeground(Color.WHITE);

                title.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                JLabel loginText = new JLabel(
                                "LOGIN",
                                SwingConstants.CENTER);

                loginText.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                26));

                loginText.setForeground(
                                new Color(40, 140, 255));

                loginText.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                JLabel subtitle = new JLabel(
                                "Welcome back! Please login to continue",
                                SwingConstants.CENTER);

                subtitle.setFont(
                                new Font(
                                                "Arial",
                                                Font.PLAIN,
                                                16));

                subtitle.setForeground(
                                new Color(180, 190, 200));

                subtitle.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                usernameField = createTextField(
                                "Username");

                passwordField = createPasswordField(
                                "Password");

                JPanel optionPanel = new JPanel(
                                new BorderLayout());

                optionPanel.setOpaque(false);

                rememberBox = new JCheckBox(
                                "Remember me");

                rememberBox.setOpaque(false);

                rememberBox.setForeground(
                                new Color(190, 200, 210));

                rememberBox.setFont(
                                new Font(
                                                "Arial",
                                                Font.PLAIN,
                                                15));

                JLabel forgotLabel = new JLabel(
                                "Forgot Password?");

                forgotLabel.setForeground(
                                new Color(40, 140, 255));

                forgotLabel.setFont(
                                new Font(
                                                "Arial",
                                                Font.PLAIN,
                                                15));

                optionPanel.add(
                                rememberBox,
                                BorderLayout.WEST);

                optionPanel.add(
                                forgotLabel,
                                BorderLayout.EAST);

                optionPanel.setMaximumSize(
                                new Dimension(470, 35));

                JButton loginButton = createLoginButton(
                                "LOGIN");

                JLabel orLabel = new JLabel("OR");

                orLabel.setForeground(
                                new Color(160, 170, 180));

                orLabel.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                16));

                orLabel.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                JButton exitButton = createExitButton(
                                "EXIT");

                JLabel footer = new JLabel(
                                "© 2026 Smart Parking Management System");

                footer.setForeground(
                                new Color(160, 170, 180));

                footer.setFont(
                                new Font(
                                                "Arial",
                                                Font.PLAIN,
                                                14));

                footer.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                loginCard.add(logo);
                loginCard.add(Box.createVerticalStrut(10));

                loginCard.add(title);
                loginCard.add(Box.createVerticalStrut(5));

                loginCard.add(loginText);
                loginCard.add(Box.createVerticalStrut(14));

                loginCard.add(subtitle);
                loginCard.add(Box.createVerticalStrut(25));

                loginCard.add(usernameField);
                loginCard.add(Box.createVerticalStrut(18));

                loginCard.add(passwordField);
                loginCard.add(Box.createVerticalStrut(12));

                loginCard.add(optionPanel);
                loginCard.add(Box.createVerticalStrut(22));

                loginCard.add(loginButton);
                loginCard.add(Box.createVerticalStrut(20));

                loginCard.add(orLabel);
                loginCard.add(Box.createVerticalStrut(20));

                loginCard.add(exitButton);
                loginCard.add(Box.createVerticalStrut(18));

                loginCard.add(footer);

                backgroundPanel.add(loginCard);

                loginButton.addActionListener(e -> {

                        loginUser();
                });

                exitButton.addActionListener(e -> {

                        System.exit(0);
                });
        }

        // =========================
        // TEXT FIELD
        // =========================

        private JTextField createTextField(String placeholder) {

                JTextField field = new JTextField();

                field.setText(placeholder);

                field.setMaximumSize(
                                new Dimension(470, 55));

                field.setPreferredSize(
                                new Dimension(470, 55));

                field.setFont(
                                new Font(
                                                "Arial",
                                                Font.PLAIN,
                                                18));

                field.setForeground(
                                new Color(190, 200, 210));

                field.setBackground(
                                new Color(8, 15, 25));

                field.setCaretColor(Color.WHITE);

                field.setBorder(
                                BorderFactory.createCompoundBorder(
                                                BorderFactory.createLineBorder(
                                                                new Color(40, 70, 100),
                                                                1),
                                                BorderFactory.createEmptyBorder(
                                                                8,
                                                                18,
                                                                8,
                                                                18)));

                field.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                field.addFocusListener(
                                new java.awt.event.FocusAdapter() {

                                        public void focusGained(
                                                        java.awt.event.FocusEvent e) {

                                                if (field.getText()
                                                                .equals(placeholder)) {

                                                        field.setText("");
                                                        field.setForeground(Color.WHITE);
                                                }
                                        }

                                        public void focusLost(
                                                        java.awt.event.FocusEvent e) {

                                                if (field.getText()
                                                                .isEmpty()) {

                                                        field.setText(placeholder);

                                                        field.setForeground(
                                                                        new Color(190, 200, 210));
                                                }
                                        }
                                });

                return field;
        }

        // =========================
        // PASSWORD FIELD
        // =========================

        private JPasswordField createPasswordField(
                        String placeholder) {

                JPasswordField field = new JPasswordField();

                field.setText(placeholder);

                field.setEchoChar((char) 0);

                field.setMaximumSize(
                                new Dimension(470, 55));

                field.setPreferredSize(
                                new Dimension(470, 55));

                field.setFont(
                                new Font(
                                                "Arial",
                                                Font.PLAIN,
                                                18));

                field.setForeground(
                                new Color(190, 200, 210));

                field.setBackground(
                                new Color(8, 15, 25));

                field.setCaretColor(Color.WHITE);

                field.setBorder(
                                BorderFactory.createCompoundBorder(
                                                BorderFactory.createLineBorder(
                                                                new Color(40, 70, 100),
                                                                1),
                                                BorderFactory.createEmptyBorder(
                                                                8,
                                                                18,
                                                                8,
                                                                18)));

                field.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                field.addFocusListener(
                                new java.awt.event.FocusAdapter() {

                                        public void focusGained(
                                                        java.awt.event.FocusEvent e) {

                                                String value = String.valueOf(
                                                                field.getPassword());

                                                if (value.equals(placeholder)) {

                                                        field.setText("");

                                                        // Use * to avoid encoding errors
                                                        field.setEchoChar('*');

                                                        field.setForeground(Color.WHITE);
                                                }
                                        }

                                        public void focusLost(
                                                        java.awt.event.FocusEvent e) {

                                                String value = String.valueOf(
                                                                field.getPassword());

                                                if (value.isEmpty()) {

                                                        field.setText(placeholder);

                                                        field.setEchoChar((char) 0);

                                                        field.setForeground(
                                                                        new Color(190, 200, 210));
                                                }
                                        }
                                });

                return field;
        }

        // =========================
        // LOGIN BUTTON
        // =========================

        private JButton createLoginButton(String text) {

                JButton button = new JButton(text);

                button.setMaximumSize(
                                new Dimension(470, 60));

                button.setPreferredSize(
                                new Dimension(470, 60));

                button.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                22));

                button.setForeground(Color.WHITE);

                button.setBackground(
                                new Color(20, 100, 230));

                button.setFocusPainted(false);

                button.setBorderPainted(false);

                button.setCursor(
                                new Cursor(
                                                Cursor.HAND_CURSOR));

                button.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                return button;
        }

        // =========================
        // EXIT BUTTON
        // =========================

        private JButton createExitButton(String text) {

                JButton button = new JButton(text);

                button.setMaximumSize(
                                new Dimension(470, 55));

                button.setPreferredSize(
                                new Dimension(470, 55));

                button.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                22));

                button.setForeground(
                                new Color(255, 80, 90));

                button.setBackground(
                                new Color(8, 15, 25));

                button.setFocusPainted(false);

                button.setBorder(
                                BorderFactory.createLineBorder(
                                                new Color(220, 50, 70),
                                                1));

                button.setCursor(
                                new Cursor(
                                                Cursor.HAND_CURSOR));

                button.setAlignmentX(
                                Component.CENTER_ALIGNMENT);

                return button;
        }

        // =========================
        // ROLE BASED LOGIN LOGIC
        // =========================

        private void loginUser() {

                String username = usernameField.getText().trim();

                String password = String.valueOf(
                                passwordField.getPassword()).trim();

                if (username.equals("Username")
                                || username.isEmpty()
                                || password.equals("Password")
                                || password.isEmpty()) {

                        showModernMessage(
                                        "WARNING",
                                        "Please enter username and password!",
                                        new Color(255, 180, 40));

                        return;
                }

                try {

                        Connection con = DBConnection.getConnection();

                        String sql = "SELECT * FROM users "
                                        + "WHERE username=? "
                                        + "AND password=?";

                        PreparedStatement pst = con.prepareStatement(sql);

                        pst.setString(1, username);
                        pst.setString(2, password);

                        ResultSet rs = pst.executeQuery();

                        if (rs.next()) {

                                String role = rs.getString("role");

                                showModernMessage(
                                                "SUCCESS",
                                                "Login Successful!",
                                                new Color(40, 140, 255));

                                if (role.equalsIgnoreCase("ADMIN")) {

                                        String choice = AdminSelectionDialog.showDialog(this);

                                        if ("ADMIN".equals(choice)) {
                                                new MainDashboard().setVisible(true);
                                                dispose();
                                        } else if ("ANALYTICS".equals(choice)) {
                                                new AnalyticsDashboard().setVisible(true);
                                                dispose();
                                        }

                                        if ("ADMIN".equals(choice)) {

                                                new MainDashboard()
                                                                .setVisible(true);

                                        } else if ("ANALYTICS".equals(choice)) {

                                                new AnalyticsDashboard()
                                                                .setVisible(true);
                                        }

                                } else if (role.equalsIgnoreCase("USER")) {

                                        new UserDashboard()
                                                        .setVisible(true);

                                } else if (role.equalsIgnoreCase("SECURITY")) {

                                        new SecurityDashboard()
                                                        .setVisible(true);

                                } else {

                                        showModernMessage(
                                                        "ERROR",
                                                        "Unknown user role!",
                                                        new Color(255, 70, 90));

                                        return;
                                }

                                dispose();

                        } else {

                                showModernMessage(
                                                "ERROR",
                                                "Invalid Username or Password!",
                                                new Color(255, 70, 90));
                        }

                } catch (Exception e) {

                        e.printStackTrace();

                        showModernMessage(
                                        "DATABASE ERROR",
                                        "Database connection error!",
                                        new Color(255, 70, 90));
                }
        }

        // =========================
        // MODERN MESSAGE DIALOG
        // =========================

        private void showModernMessage(String title,
                        String message,
                        Color accentColor) {

                JDialog dialog = new JDialog(this, true);

                dialog.setUndecorated(true);

                dialog.setSize(420, 220);

                dialog.setLocationRelativeTo(this);

                RoundedPanel panel = new RoundedPanel(24);

                panel.setBackground(
                                new Color(8, 18, 30));

                panel.setLayout(
                                new BorderLayout());

                panel.setBorder(
                                BorderFactory.createEmptyBorder(
                                                25,
                                                25,
                                                25,
                                                25));

                JLabel titleLabel = new JLabel(
                                title,
                                SwingConstants.CENTER);

                titleLabel.setForeground(accentColor);

                titleLabel.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                26));

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

                okButton.setPreferredSize(
                                new Dimension(120, 38));

                okButton.setBackground(accentColor);

                okButton.setForeground(Color.WHITE);

                okButton.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                16));

                okButton.setFocusPainted(false);

                okButton.setBorderPainted(false);

                JPanel buttonPanel = new JPanel();

                buttonPanel.setOpaque(false);

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

        // =========================
        // BACKGROUND PANEL
        // =========================

        class BackgroundPanel extends JPanel {

                private Image backgroundImage;

                public BackgroundPanel(String imagePath) {

                        backgroundImage = new ImageIcon(
                                        imagePath).getImage();
                }

                @Override
                protected void paintComponent(Graphics g) {

                        super.paintComponent(g);

                        Graphics2D g2 = (Graphics2D) g;

                        g2.drawImage(
                                        backgroundImage,
                                        0,
                                        0,
                                        getWidth(),
                                        getHeight(),
                                        this);

                        g2.setColor(
                                        new Color(
                                                        0,
                                                        0,
                                                        0,
                                                        120));

                        g2.fillRect(
                                        0,
                                        0,
                                        getWidth(),
                                        getHeight());
                }
        }

        // =========================
        // ROUNDED PANEL
        // =========================

        class RoundedPanel extends JPanel {

                private int radius;

                public RoundedPanel(int radius) {

                        this.radius = radius;

                        setOpaque(false);
                }

                @Override
                protected void paintComponent(Graphics g) {

                        super.paintComponent(g);

                        Graphics2D g2 = (Graphics2D) g;

                        g2.setRenderingHint(
                                        RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);

                        g2.setColor(
                                        getBackground());

                        g2.fillRoundRect(
                                        0,
                                        0,
                                        getWidth(),
                                        getHeight(),
                                        radius,
                                        radius);

                        g2.setColor(
                                        new Color(
                                                        20,
                                                        100,
                                                        230,
                                                        160));

                        g2.setStroke(
                                        new BasicStroke(2));

                        g2.drawRoundRect(
                                        0,
                                        0,
                                        getWidth() - 1,
                                        getHeight() - 1,
                                        radius,
                                        radius);
                }
        }
}