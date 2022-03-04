package model;
import java.time.LocalDateTime;

public class Appointment {
    /**
     * Appointment ID
     */
    private int appointmentID;
    /**
     * Title of the Appointment
     */
    private String appointmentTitle;
    /**
     * Description of the Appointment
     */
    private String appointmentDescription;
    /**
     * Location of the Appointment
     */
    private String appointmentLocation;
    /**
     * Type of Appointment
     */
    private String appointmentType;
    /**
     * Appointment Start Time
     */
    private LocalDateTime start;
    /**
     * Appointment End Time
     */
    private LocalDateTime end;
    /**
     * Customer ID (FK)
     */
    public int customerID;
    /**
     * User ID (FK)
     */
    public int userID;
    /**
     * Contact ID (FK)
     */
    public int contactID;

    /**
     * Appointment Constructor
     * @param appointmentID ID of the Appointment
     * @param appointmentTitle Title of the Appointment
     * @param appointmentDescription Description of the Appointment
     * @param appointmentLocation Location of the Appointment
     * @param appointmentType Type of Appointment
     * @param start Appointment Start Time
     * @param end Appointment End Time
     * @param customerID Customer ID Foreign Key
     * @param userID User ID Foreign Key
     * @param contactID Contact ID Foreign Key
     */
    public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription,
                        String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end, int customerID,
                        int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * @return appointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * @return appointment title
     */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    /**
     * @return appointment description
     */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    /**
     * @return appointment location
     */
    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    /**
     * @return appointment type
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * @return start LocalDateTime
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * @return  end LocalDateTime
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * @return  customer ID
     */
    public int getCustomerID () {
        return customerID;
    }

    /**
     * @return  user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @return contact ID
     */
    public int getContactID() {
        return contactID;
    }
}