import javax.swing.*;
import java.awt.*;

public class MainPortalPage extends JFrame {
    private int userId;
    private String userName;

    public MainPortalPage(String name, int user_id) {
        this.userId = user_id;
        this.userName = name;

        setTitle("Doctor Appointment Portal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(33, 150, 243));
        headerPanel.setPreferredSize(new Dimension(600, 60));
        JLabel titleLabel = new JLabel("Welcome, " + userName + "!");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Main Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JButton appointDoctorBtn = createStyledButton("Appoint a Doctor");
        JButton seeAppointmentsBtn = createStyledButton("See Your Appointments");
        JButton logoutBtn = createStyledButton("Logout");

        buttonPanel.add(appointDoctorBtn);
        buttonPanel.add(seeAppointmentsBtn);
        buttonPanel.add(logoutBtn);
        add(buttonPanel, BorderLayout.CENTER);

        // Button Actions
        appointDoctorBtn.addActionListener(e -> {
            new AppointDoctorPage(userId, userName);
            dispose();
        });

        seeAppointmentsBtn.addActionListener(e -> {
            new SeeAppointmentsPage(userId);
            dispose();
        });

        logoutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Logged Out Successfully!");
            dispose();
            new MainPage();
        });

        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBackground(new Color(76, 175, 80));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(56, 142, 60));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(76, 175, 80));
            }
        });
        return button;
    }
}