package model;

public class ReportAppointmentByMonth {

    /**
     * Group Appointments by Unique Month
     */
    public String appointmentMonth;
    /**
     * Tally the total appointments each month
     */
    public int appointmentTotalMonth;

    /**
     * Report Appointment By Month Constructor
     * @param appointmentMonth the appointment month to set
     * @param appointmentTotalMonth the appointment total to set
     */
    public ReportAppointmentByMonth(String appointmentMonth, int appointmentTotalMonth) {
        this.appointmentMonth = appointmentMonth;
        this.appointmentTotalMonth = appointmentTotalMonth;
    }

    /**
     * @return Appointment Month
     */
    public String getAppointmentMonth() {
        return appointmentMonth;
    }

    /**
     *
     * @return Appointment Total (By Month)
     */
    public int getAppointmentTotalMonth() {
        return appointmentTotalMonth;
    }

}
