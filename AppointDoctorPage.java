import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AppointDoctorPage extends JFrame {
    private int userId;
    private String userName;
    private JComboBox<String> specializationBox;
    private DefaultListModel<String> doctorListModel;
    private JList<String> doctorList;

    public AppointDoctorPage(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;

        setTitle("Appoint a Doctor");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] specializations = {"Cardiologist", "Dentist", "Orthopedic", "Neurologist"};
        specializationBox = new JComboBox<>(specializations);
        JButton searchBtn = new JButton("Search");

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Specialization:"));
        topPanel.add(specializationBox);
        topPanel.add(searchBtn);

        doctorListModel = new DefaultListModel<>();
        doctorList = new JList<>(doctorListModel);
        JScrollPane scrollPane = new JScrollPane(doctorList);

        JButton bookAppointmentBtn = new JButton("Book Appointment");
        JButton backButton = new JButton("Back");

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(bookAppointmentBtn);
        bottomPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> fetchDoctors());
        bookAppointmentBtn.addActionListener(e -> bookAppointment());
        backButton.addActionListener(e -> {
            new MainPortalPage(userName, userId);
            dispose();
        });

        setVisible(true);
    }

    private void fetchDoctors() {
        doctorListModel.clear();
        String specialization = (String) specializationBox.getSelectedItem();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT doctor_id, name FROM doctors WHERE specialization = ?")) {

            stmt.setString(1, specialization);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                doctorListModel.addElement(rs.getInt("doctor_id") + " - " + rs.getString("name"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void bookAppointment() {
        String selectedDoctor = doctorList.getSelectedValue();
        if (selectedDoctor == null) {
            JOptionPane.showMessageDialog(this, "Please select a doctor!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int doctorId = Integer.parseInt(selectedDoctor.split(" - ")[0]);
        String doctorName = selectedDoctor.split(" - ")[1];

        // Open the Doctor Slots Page
        new SelectSlotPage(userId, userName, doctorId, doctorName);
        dispose();
    }
}
