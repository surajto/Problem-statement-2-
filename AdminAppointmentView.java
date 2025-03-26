import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminAppointmentView extends JFrame {
    private JTable appointmentTable;
    private DefaultTableModel appointmentTableModel;

    public AdminAppointmentView() {
        setTitle("Doctor-Patient Appointments");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ** Header Panel **
        JLabel titleLabel = new JLabel("Doctor-Patient Appointments", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0, 128, 255));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        // ** Table Setup **
        appointmentTableModel = new DefaultTableModel();
        appointmentTableModel.setColumnIdentifiers(new String[]{"Doctor Name", "Patient Name", "Date"});
        appointmentTable = new JTable(appointmentTableModel);
        appointmentTable.setRowHeight(25);
        appointmentTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        appointmentTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        appointmentTable.getTableHeader().setBackground(new Color(30, 144, 255));
        appointmentTable.getTableHeader().setForeground(Color.WHITE);

        // ** Alternating Row Colors **
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    comp.setBackground(row % 2 == 0 ? new Color(230, 240, 255) : Color.WHITE);
                }
                return comp;
            }
        };
        for (int i = 0; i < appointmentTable.getColumnCount(); i++) {
            appointmentTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        add(scrollPane, BorderLayout.CENTER);

        fetchAppointments();

        setVisible(true);
    }

    private void fetchAppointments() {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT d.name AS doctor, p.name AS patient, a.appointment_date " +
                             "FROM appointments a " +
                             "JOIN doctors d ON a.doctor_id = d.doctor_id " +
                             "JOIN patients p ON a.patient_id = p.patient_id")) {

            while (rs.next()) {
                appointmentTableModel.addRow(new Object[]{
                        rs.getString("doctor"),
                        rs.getString("patient"),
                        rs.getString("appointment_date")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
