import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminPage extends JFrame {
    private JTable doctorTable, patientTable;
    private DefaultTableModel doctorTableModel, patientTableModel;
    private JButton viewAppointmentsBtn, logoutBtn, addDoctorBtn, refreshBtn;

    public AdminPage(String name) {
        setTitle("Admin Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JPanel headerPanel = new JPanel();
        JLabel titleLabel = new JLabel("Admin Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerPanel.add(titleLabel);

        // Doctor Table Panel
        doctorTableModel = new DefaultTableModel();
        doctorTableModel.setColumnIdentifiers(new String[]{"Doctor ID", "Name", "Specialization", "Phone"});
        doctorTable = new JTable(doctorTableModel);
        styleTable(doctorTable);
        JScrollPane doctorScrollPane = new JScrollPane(doctorTable);

        // Patient Table Panel
        patientTableModel = new DefaultTableModel();
        patientTableModel.setColumnIdentifiers(new String[]{"Patient ID", "Name", "Phone", "Email"});
        patientTable = new JTable(patientTableModel);
        styleTable(patientTable);
        JScrollPane patientScrollPane = new JScrollPane(patientTable);

        // Load Data
        fetchDoctors();
        fetchPatients();

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        viewAppointmentsBtn = new JButton("View Appointments");
        logoutBtn = new JButton("Logout");
        addDoctorBtn = new JButton("Add Doctor");
        refreshBtn = new JButton("Refresh");  // NEW Refresh Button

        viewAppointmentsBtn.setFont(new Font("Arial", Font.BOLD, 14));
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 14));
        addDoctorBtn.setFont(new Font("Arial", Font.BOLD, 14));
        refreshBtn.setFont(new Font("Arial", Font.BOLD, 14));  // Styling refresh button

        viewAppointmentsBtn.setBackground(Color.BLUE);
        viewAppointmentsBtn.setForeground(Color.WHITE);

        logoutBtn.setBackground(Color.RED);
        logoutBtn.setForeground(Color.WHITE);

        addDoctorBtn.setBackground(Color.GREEN);
        addDoctorBtn.setForeground(Color.WHITE);

        refreshBtn.setBackground(Color.ORANGE); // Different color for refresh
        refreshBtn.setForeground(Color.WHITE);

        // Button Actions
        viewAppointmentsBtn.addActionListener(e -> new AdminAppointmentView());
        logoutBtn.addActionListener(e -> {
            dispose();
            new loginPage(); // Redirect to Login Page
        });
        addDoctorBtn.addActionListener(e -> new DoctorSignInPage()); // Opens Doctor Sign-In Page

        // Refresh Button Action
        refreshBtn.addActionListener(e -> refreshTables()); // Call method to refresh tables

        // Adding Buttons to Panel
        buttonPanel.add(viewAppointmentsBtn);
        buttonPanel.add(addDoctorBtn);
        buttonPanel.add(refreshBtn); // Add Refresh button
        buttonPanel.add(logoutBtn);

        // Tables Layout
        JPanel tablePanel = new JPanel(new GridLayout(2, 1, 10, 10));
        tablePanel.add(createTablePanel("Doctors", doctorScrollPane));
        tablePanel.add(createTablePanel("Patients", patientScrollPane));

        // Add components to frame
        add(headerPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchDoctors() {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT doctor_id, name, specialization, phone FROM doctors")) {

            while (rs.next()) {
                doctorTableModel.addRow(new Object[]{
                        rs.getInt("doctor_id"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getString("phone")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void fetchPatients() {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT patient_id, name, phone, email FROM patients")) {

            while (rs.next()) {
                patientTableModel.addRow(new Object[]{
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Method to refresh tables
    private void refreshTables() {
        doctorTableModel.setRowCount(0); // Clear existing data
        patientTableModel.setRowCount(0); // Clear existing data
        fetchDoctors(); // Fetch updated doctor data
        fetchPatients(); // Fetch updated patient data
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.GRAY);
        table.getTableHeader().setForeground(Color.WHITE);
    }

    private JPanel createTablePanel(String title, JScrollPane tableScrollPane) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(tableScrollPane, BorderLayout.CENTER);
        return panel;
    }
}
