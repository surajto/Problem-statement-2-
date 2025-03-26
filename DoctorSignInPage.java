import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoctorSignInPage {
    DoctorSignInPage() {
        // Creating the Frame
        JFrame frame = new JFrame("Doctor Registration");
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Doctor Registration", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Fields
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);

        JLabel specLabel = new JLabel("Specialization:");
        JTextField specField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField(20);

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);

        // Buttons
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");

        registerButton.setBackground(new Color(30, 144, 255));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));

        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Adding Components to Panel
        gbc.gridx = 0; gbc.gridy = 0; panel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(specLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(specField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(emailLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(phoneLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; panel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panel.add(passLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4; panel.add(passField, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(registerButton, gbc);

        gbc.gridy = 6;
        panel.add(backButton, gbc);

        // Adding Panel to Frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Button Click Event
        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String specialization = specField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String password = new String(passField.getPassword());

            if (name.isEmpty() || specialization.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Connection con = Database.getConnection(); // Ensure this method exists
                    String query = "INSERT INTO doctors (name, specialization, email, phone, password) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setString(1, name);
                    pst.setString(2, specialization);
                    pst.setString(3, email);
                    pst.setString(4, phone);
                    pst.setString(5, password);

                    int rowsAffected = pst.executeUpdate(); // Execute the query

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(frame, "Doctor Registered Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Registration Failed!", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    pst.close();  // Close statement
                    con.close();  // Close connection
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Back Button Event
        backButton.addActionListener(e -> {
            frame.dispose();  // Close current frame
        });
    }
}
