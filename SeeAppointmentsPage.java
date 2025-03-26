import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SeeAppointmentsPage extends JFrame {
    private int userId;
    private DefaultListModel<String> appointmentListModel;

    public SeeAppointmentsPage(int userId) {
        this.userId = userId;

        setTitle("Your Appointments");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel();
        JLabel headerLabel = new JLabel("Your Appointments", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        appointmentListModel = new DefaultListModel<>();
        JList<String> appointmentList = new JList<>(appointmentListModel);
        appointmentList.setFont(new Font("Arial", Font.PLAIN, 14));
        appointmentList.setSelectionBackground(new Color(173, 216, 230));
        JScrollPane scrollPane = new JScrollPane(appointmentList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new MainPortalPage("User", userId);
            dispose();
        });
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        fetchAppointments();
        setVisible(true);
    }

    private void fetchAppointments() {
        appointmentListModel.clear(); // Clear previous entries

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT doctors.name, appointments.appointment_date " +
                             "FROM appointments " +
                             "INNER JOIN doctors ON appointments.doctor_id = doctors.doctor_id " +
                             "WHERE appointments.patient_id = ?")) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            boolean hasAppointments = false;

            while (rs.next()) {
                String doctorName = rs.getString("name");
                String appointmentDate = rs.getString("appointment_date");

                System.out.println("Fetched: " + doctorName + " | " + appointmentDate);

                appointmentListModel.addElement("Doctor: " + doctorName + " | Date: " + appointmentDate);
                hasAppointments = true;
            }

            if (!hasAppointments) {
                System.out.println("No appointments found for userId: " + userId);
            } else {
                Component appointmentList = null;
                appointmentList.revalidate();
                appointmentList.repaint();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}