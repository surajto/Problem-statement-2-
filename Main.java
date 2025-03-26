import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Loading...");
        setSize(640, 640);
        setLocationRelativeTo(null);
        // Load the main image
        ImageIcon mainImg = new ImageIcon("C:\\Users\\hplap\\Downloads\\8c331fd3-1a1f-41eb-87a4-f786b7036e1d.jpeg");
        JLabel mainLabel = new JLabel(mainImg);
        add(mainLabel);
        setVisible(true);
        Timer timer = new Timer(2000, e -> {
            dispose();
            new MainPage();
        });
        timer.setRepeats(false);
        timer.start();
    }
    public static void main(String[] args) {
        new Main();
    }
}

class MainPage extends JFrame {
    public MainPage() {
        setTitle("Hospital Management - Login");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 153, 76));
        JLabel titleLabel = new JLabel("Hospital Management", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1, 10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JRadioButton doctorBtn = new JRadioButton("Doctor");
        JRadioButton patientBtn = new JRadioButton("Patient");
        JRadioButton adminBtn = new JRadioButton("Admin");

        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(doctorBtn);
        roleGroup.add(patientBtn);
        roleGroup.add(adminBtn);

        JPanel rolePanel = new JPanel();
        rolePanel.setBackground(Color.WHITE);
        rolePanel.add(doctorBtn);
        rolePanel.add(patientBtn);
        rolePanel.add(adminBtn);
        mainPanel.add(rolePanel);

        JTextField usernameField = new JTextField(15);
        usernameField.setBorder(BorderFactory.createTitledBorder("Email"));
        mainPanel.add(usernameField);

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        mainPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 153, 76));
        loginButton.setForeground(Color.WHITE);
        mainPanel.add(loginButton);

        loginButton.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(MainPage.this, "Please enter all fields!", "Empty Fields", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (doctorBtn.isSelected()){
                try {
                    Connection conn = Database.getConnection();
                    if (conn == null) {
                        JOptionPane.showMessageDialog(MainPage.this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String sql_query = "SELECT * FROM doctors WHERE email = ?";
                    PreparedStatement pst = conn.prepareStatement(sql_query);
                    pst.setString(1, user);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        int doctorId = Integer.parseInt(rs.getString("doctor_id"));
                        String dbPass = rs.getString("password");
                        if (pass.equals(dbPass)) {
                            String name = rs.getString("name");
                            new DoctorPage(doctorId,name);
                        } else {
                            JOptionPane.showMessageDialog(MainPage.this, "Incorrect password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                            dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(MainPage.this, "User not found!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }

                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(MainPage.this, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
                    dispose();
                }

            }
            else if (patientBtn.isSelected()) {
                try {
                    Connection conn = Database.getConnection();
                    if (conn == null) {
                        JOptionPane.showMessageDialog(MainPage.this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String sql_query = "SELECT * FROM patients WHERE email = ?";
                    PreparedStatement pst = conn.prepareStatement(sql_query);
                    pst.setString(1, user);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        int user_id = Integer.parseInt(rs.getString("patient_id"));
                        String user_name = rs.getString("name");
                        String dbPass = rs.getString("password");
                        String user_mail = rs.getString("email");
                        if (pass.equals(dbPass)) {
                            String name = rs.getString("name");
                            new MainPortalPage(name,user_id);
                        } else {
                            JOptionPane.showMessageDialog(MainPage.this, "Incorrect password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                            dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(MainPage.this, "User not found!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(MainPage.this, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
                    dispose();
                }
            }
            else if (adminBtn.isSelected()) {
                try {
                    Connection conn = Database.getConnection();
                    if (conn == null) {
                        JOptionPane.showMessageDialog(MainPage.this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String sql_query = "SELECT * FROM admins WHERE name = ?";
                    PreparedStatement pst = conn.prepareStatement(sql_query);
                    pst.setString(1, user);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        String dbPass = rs.getString("password");
                        if (pass.equals(dbPass)) {
                            String name = rs.getString("name");
                            new AdminPage(name);
                        } else {
                            JOptionPane.showMessageDialog(MainPage.this, "Incorrect password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                            dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(MainPage.this, "User not found!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }

                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(MainPage.this, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(MainPage.this, "Choose a role!", "Role Selection Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton signinButton = new JButton("Register(user)");
        signinButton.setBackground(Color.WHITE);
        mainPanel.add(signinButton);

        signinButton.addActionListener(e -> {
            if (doctorBtn.isSelected()) {
                JOptionPane.showMessageDialog(MainPage.this, "Only patients can register", "Signin Error", JOptionPane.ERROR_MESSAGE);
            } else if (patientBtn.isSelected()) {
                new PatientSignInPage();
            } else {
                JOptionPane.showMessageDialog(MainPage.this, "Only patients can register", "Signin Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}