import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {

    // Method to send an email
    public static void sendEmail(String to, String subject, String messageText) {
        final String from = "your-email@gmail.com";
        final String password = "your-email-password";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);
            System.out.println("Email sent successfully to " + to);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to fetch emails and send notifications
    public static void sendAppointmentEmail(int slotId, int userId, String doctorName, String userName, String appointmentTime) {
        String doctorEmail = null;
        String userEmail = null;

        // Database Query to fetch emails
        String query = "SELECT d.email AS doctor_email, u.email AS user_email " +
                "FROM doctor_slots ds " +
                "JOIN doctors d ON ds.doctor_id = d.doctor_id " +
                "JOIN users u ON u.user_id = ? " +
                "WHERE ds.slot_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, slotId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                doctorEmail = rs.getString("doctor_email");
                userEmail = rs.getString("user_email");
            }

            if (doctorEmail != null && userEmail != null) {
                // Doctor Email
                String doctorSubject = "New Appointment Booked";
                String doctorMessage = "Dear Dr. " + doctorName + ",\n\n"
                        + "A new appointment has been booked.\n\n"
                        + "Patient Name: " + userName + "\n"
                        + "Appointment Slot: " + appointmentTime + "\n\n"
                        + "Please check your dashboard for details.\n\nBest Regards,\nHospital Management System";

                sendEmail(doctorEmail, doctorSubject, doctorMessage);

                // User Email
                String userSubject = "Appointment Confirmation";
                String userMessage = "Dear " + userName + ",\n\n"
                        + "Your appointment has been successfully booked.\n\n"
                        + "Doctor: Dr. " + doctorName + "\n"
                        + "Appointment Slot: " + appointmentTime + "\n\n"
                        + "Please check your dashboard for details.\n\nBest Regards,\nHospital Management System";

                sendEmail(userEmail, userSubject, userMessage);
            } else {
                System.out.println("Doctor or User email not found for slotId: " + slotId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
