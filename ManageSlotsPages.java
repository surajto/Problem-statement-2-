import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManageSlotsPages extends JFrame {
    private JComboBox<String> doctorDropdown;
    private JTextField slotTimeField;
    private JButton addSlotButton;

    public ManageSlotsPages() {
        setTitle("Manage Doctor Slots");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        // Fetch doctors from the database
        doctorDropdown = new JComboBox<>();
        loadDoctors();

        slotTimeField = new JTextField("YYYY-MM-DD HH:MM:SS");

        addSlotButton = new JButton("Add Slot");
        addSlotButton.addActionListener(e -> addSlot());

        // Add components
        add(new JLabel("Select Doctor:"));
        add(doctorDropdown);
        add(new JLabel("Enter Slot Time:"));
        add(slotTimeField);
        add(addSlotButton);

        setVisible(true);
    }

    private void loadDoctors() {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT doctor_id, name FROM doctors")) {

            while (rs.next()) {
                doctorDropdown.addItem(rs.getInt("doctor_id") + " - " + rs.getString("name"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addSlot() {
        String selectedDoctor = (String) doctorDropdown.getSelectedItem();
        int doctorId = Integer.parseInt(selectedDoctor.split(" - ")[0]); // Extract doctor ID
        String slotTime = slotTimeField.getText();

        // Validate DateTime format
        try {
            LocalDateTime.parse(slotTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            try (Connection conn = Database.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO doctor_slots (doctor_id, slot_time, is_booked) VALUES (?, ?, 0)")) {

                pstmt.setInt(1, doctorId);
                pstmt.setString(2, slotTime);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Slot added successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding slot.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid date-time format. Use YYYY-MM-DD HH:MM:SS");
        }
    }
}
