import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginPage extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JRadioButton patientRadio, doctorRadio, adminRadio;
    private JButton loginButton, signupButton;

    loginPage() {
        setTitle("Hospital Management System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1));

        JLabel titleLabel = new JLabel("Hospital Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel);

        JPanel rolePanel = new JPanel();
        patientRadio = new JRadioButton("Patient");
        doctorRadio = new JRadioButton("Doctor");
        adminRadio = new JRadioButton("Admin");
        ButtonGroup group = new ButtonGroup();
        group.add(patientRadio);
        group.add(doctorRadio);
        group.add(adminRadio);
        rolePanel.add(patientRadio);
        rolePanel.add(doctorRadio);
        rolePanel.add(adminRadio);
        add(rolePanel);

        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        add(new JLabel("Email or Username:"));
        add(emailField);
        add(new JLabel("Password:"));
        add(passwordField);

        loginButton = new JButton("Login");
        signupButton = new JButton("Sign Up");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        add(buttonPanel);

        loginButton.addActionListener(new LoginHandler());
        signupButton.addActionListener(new SignupHandler());
    }

    private class LoginHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Implement login functionality here
            JOptionPane.showMessageDialog(null, "Login Button Clicked");
        }
    }

    private class SignupHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (patientRadio.isSelected()) {
                JOptionPane.showMessageDialog(null, "Redirect to Patient Registration");
            } else if (doctorRadio.isSelected()) {
                JOptionPane.showMessageDialog(null, "Redirect to Doctor Registration");
            } else if (adminRadio.isSelected()) {
                JOptionPane.showMessageDialog(null, "Admins are manually registered");
            } else {
                JOptionPane.showMessageDialog(null, "Please select a role!");
            }
        }
    }

}

