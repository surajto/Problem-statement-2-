import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SelectSlotPage extends JFrame {
    private int userId, doctorId;
    private String userName, doctorName;
    private DefaultListModel<String> slotListModel;
    private JList<String> slotList;
    private JButton bookSlotBtn, backButton;

    public SelectSlotPage(int userId, String userName, int doctorId, String doctorName) {
        this.userId = userId;
        this.userName = userName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;

        setTitle("Select a Slot for Dr. " + doctorName);
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        slotListModel = new DefaultListModel<>();
        slotList = new JList<>(slotListModel);
        JScrollPane scrollPane = new JScrollPane(slotList);

        // Book Slot Button
        bookSlotBtn = new JButton("Book Slot");
        bookSlotBtn.addActionListener(e -> bookSlot());

        // Back Button
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new MainPortalPage(userName, userId); // Navigate back
            dispose();
        });

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(bookSlotBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        fetchAvailableSlots();

        setVisible(true);
    }

    private void fetchAvailableSlots() {
        slotListModel.clear();
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT slot_id, slot_time FROM doctor_slots WHERE doctor_id = ? AND is_booked = 0")) {

            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                slotListModel.addElement(rs.getInt("slot_id") + " - " + rs.getString("slot_time"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void bookSlot() {
        String selectedSlot = slotList.getSelectedValue();
        if (selectedSlot == null) {
            JOptionPane.showMessageDialog(this, "Please select a slot!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int slotId = Integer.parseInt(selectedSlot.split(" - ")[0]);

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE doctor_slots SET is_booked = 1 WHERE slot_id = ?")) {
            stmt.setInt(1, slotId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Slot Booked Successfully!");
            new MainPortalPage(userName, userId); // Navigate to main portal
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
