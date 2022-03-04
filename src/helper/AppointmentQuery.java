package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public abstract class AppointmentQuery {

    /**
     * @return Creates and Observable List of all appointment data from database using a Select statement timestamps are then changed to local time
     * @throws SQLException if exception has occurred
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> appointmentsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");

            // times converted to local time of user
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();

            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription,
                    appointmentLocation, appointmentType, start, end, customerID, userID, contactID);
            appointmentsObservableList.add(appointment);
        }
        return appointmentsObservableList;
    }


}
