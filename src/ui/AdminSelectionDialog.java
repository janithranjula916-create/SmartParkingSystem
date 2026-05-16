package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminSelectionDialog extends JDialog {

    private String selectedOption = null;

    public AdminSelectionDialog(JFrame parent) {
        super(parent, "Dashboard Selection", true);

        setSize(500, 280);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        Color bgColor = new Color(8, 18, 35);
        Color panelColor = new Color(12, 25, 45);
        Color borderColor = new Color(0, 170, 255);
        Color textColor = Color.WHITE;
        Color subTextColor = new Color(180, 200, 220);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);
        mainPanel.setBorder(BorderFactory.createLineBorder(borderColor, 2));

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(panelColor);
        titlePanel.setPreferredSize(new Dimension(500, 70));
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("SELECT DASHBOARD");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel subLabel = new JLabel("Choose where you want to go");
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subLabel.setForeground(subTextColor);
        subLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subLabel);

        // Center panel
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBackground(bgColor);
        centerPanel.setBorder(new EmptyBorder(30, 30, 20, 30));

        JButton adminButton = createModernButton(
                "Admin Dashboard",
                new Color(0, 122, 255),
                new Color(30, 144, 255)
        );

        JButton analyticsButton = createModernButton(
                "Analytics Dashboard",
                new Color(0, 180, 120),
                new Color(0, 210, 140)
        );

        adminButton.addActionListener(e -> {
            selectedOption = "ADMIN";
            dispose();
        });

        analyticsButton.addActionListener(e -> {
            selectedOption = "ANALYTICS";
            dispose();
        });

        centerPanel.add(adminButton);
        centerPanel.add(analyticsButton);

        // Bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(bgColor);
        bottomPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(180, 40, 40));
        cancelButton.setPreferredSize(new Dimension(120, 35));
        cancelButton.setBorder(BorderFactory.createLineBorder(new Color(255, 80, 80), 1));
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        cancelButton.addActionListener(e -> {
            selectedOption = null;
            dispose();
        });

        bottomPanel.add(cancelButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JButton createModernButton(String text, Color bg, Color hover) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(bg);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 80));
        button.setBorder(BorderFactory.createLineBorder(hover, 2));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bg);
            }
        });

        return button;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public static String showDialog(JFrame parent) {
        AdminSelectionDialog dialog = new AdminSelectionDialog(parent);
        dialog.setVisible(true);
        return dialog.getSelectedOption();
    }
}