import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class DoctorPage extends JFrame {
    private JTable appointmentsTable;
    private DefaultTableModel tableModel;
    private int doctorId;
    private String doctorName;

    DoctorPage(int doctorId, String doctorName) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;

        // Frame Setup
        setTitle("Doctor Dashboard - Dr. " + doctorName);
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.decode("#f5f5f5"));

        // Sidebar Panel
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(5, 1, 10, 10));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBackground(Color.decode("#2c3e50"));

        JLabel profileLabel = new JLabel("Dr. " + doctorName, JLabel.CENTER);
        profileLabel.setForeground(Color.WHITE);
        profileLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JButton refreshButton = new JButton("Refresh Appointments");
        refreshButton.addActionListener(e -> fetchAppointments());

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logged Out!");
            dispose();
        });

        sidebar.add(profileLabel);
        sidebar.add(refreshButton);
        sidebar.add(logoutButton);

        // Table Setup
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Patient Name");
        tableModel.addColumn("Patient Email");
        tableModel.addColumn("Appointment Date");
        tableModel.addColumn("Status");

        appointmentsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Today's Appointments"));

        // Add Components
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
        // Fetch and Display Appointments
        fetchAppointments();
    }

    private void fetchAppointments() {
        tableModel.setRowCount(0); // Clear existing data
        try (Connection conn =Database.getConnection();) {
            String query = "SELECT p.name AS patient_name, p.email AS patient_email, " +
                    "a.appointment_date, a.status FROM appointments a " +
                    "JOIN patients p ON a.patient_id = p.patient_id " +
                    "WHERE a.doctor_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String patientName = rs.getString("patient_name");
                String patientEmail = rs.getString("patient_email");
                String appointmentDate = rs.getString("appointment_date");
                String status = rs.getString("status");

                tableModel.addRow(new Object[]{patientName, patientEmail, appointmentDate, status});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error Fetching Appointments: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
