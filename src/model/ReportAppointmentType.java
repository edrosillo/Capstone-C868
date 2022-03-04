package model;

public class ReportAppointmentType {

    /**
     * Group Appointments by Type
     */
    public String appointmentType;
    /**
     * Tally the total Appointments by Type
     */
    public int appointmentTotal;

    /**
     * Report Appointment by Type Constructor
     * @param appointmentType the appointment type to set
     * @param appointmentTotal the appointment total to set
     */
    public ReportAppointmentType(String appointmentType, int appointmentTotal) {
        this.appointmentType = appointmentType;
        this.appointmentTotal = appointmentTotal;
    }

    /**
     * @return Appointments by Type
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * @return Total Appointments by Type
     */
    public int getAppointmentTotal() {
        return appointmentTotal;
    }

}
