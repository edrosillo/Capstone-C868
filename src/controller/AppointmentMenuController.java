package controller;

import helper.*;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class adds and controls all the functionality of the Appointments Menu.
 */

public class AppointmentMenuController implements Initializable {
    /**
     * The stage object that will be used to hold the GUI and data of the new screens.
     */
    Stage stage;

    /**
     * The scene object that will be used to generate the new screens.
     */
    Parent scene;

    @FXML
    private ToggleGroup sceneChangeTG;

    @FXML
    private Button saveAppointmentBtn;

    @FXML
    private Button modifyAppointmentBtn;

    @FXML
    private Button deleteAppointmentBtn;

    @FXML
    private TextField appointmentIDTF;

    @FXML
    private TextField appointmentTypeTF;

    @FXML
    private TextField appointmentTitleTF;

    @FXML
    private TextField appointmentDescriptionTF;

    @FXML
    private TextField appointmentUserIDTF;

    @FXML
    private TextField appointmentCustomerIDTF;

    @FXML
    private TextField appointmentLocationTF;

    @FXML
    private ComboBox<String> appointmentContactCB;

    @FXML
    private DatePicker appointmentStartDP;

    @FXML
    private DatePicker appointmentEndDP;

    @FXML
    private ComboBox<String> appointmentStartCB;

    @FXML
    private ComboBox<String> appointmentEndCB;

    // Definition of the All View Appointments Table and its columns
    @FXML
    public Tab apptAllTableTab;
    @FXML
    public TableView<Appointment> apptAllTable;
    @FXML
    public TableColumn<Appointment, Integer> apptAllAppointmentIDCol;
    @FXML
    public TableColumn<Appointment, String> apptAllTitleCol;
    @FXML
    public TableColumn<Appointment, String> apptAllDescriptionCol;
    @FXML
    public TableColumn<Appointment, String> apptAllLocationCol;
    @FXML
    public TableColumn<Appointment, Integer> apptAllContactCol;
    @FXML
    public TableColumn<Appointment, String> apptAllTypeCol;
    @FXML
    public TableColumn<Appointment, LocalDateTime> apptAllStartDATCol;
    @FXML
    public TableColumn<Appointment, LocalDateTime> apptAllEndDATCol;
    @FXML
    public TableColumn<Appointment, Integer> apptAllCustomerIDCol;
    @FXML
    public TableColumn<Appointment, Integer> apptAllUserIDCol;

    // Definition of the Month View Appointments Table and its columns
    @FXML
    public Tab apptMonthTableTab;
    @FXML
    public TableView<Appointment> apptMonthTable;
    @FXML
    public TableColumn<Appointment, Integer> apptMonthAppointmentIDCol;
    @FXML
    public TableColumn<Appointment, String> apptMonthTitleCol;
    @FXML
    public TableColumn<Appointment, String> apptMonthDescriptionCol;
    @FXML
    public TableColumn<Appointment, String> apptMonthLocationCol;
    @FXML
    public TableColumn<Appointment, Integer> apptMonthContactCol;
    @FXML
    public TableColumn<Appointment, String> apptMonthTypeCol;
    @FXML
    public TableColumn<Appointment, LocalDateTime> apptMonthStartDATCol;
    @FXML
    public TableColumn<Appointment, LocalDateTime> apptMonthEndDATCol;
    @FXML
    public TableColumn<Appointment, Integer> apptMonthCustomerIDCol;
    @FXML
    public TableColumn<Appointment, Integer> apptMonthUserIDCol;

    // Definition of the Week View Appointments Table and its columns
    @FXML
    public Tab apptWeekTableTab;
    @FXML
    public TableView<Appointment> apptWeekTable;
    @FXML
    public TableColumn<Appointment, Integer> apptWeekAppointmentIDCol;
    @FXML
    public TableColumn<Appointment, String> apptWeekTitleCol;
    @FXML
    public TableColumn<Appointment, String> apptWeekDescriptionCol;
    @FXML
    public TableColumn<Appointment, String> apptWeekLocationCol;
    @FXML
    public TableColumn<Appointment, Integer> apptWeekContactCol;
    @FXML
    public TableColumn<Appointment, String> apptWeekTypeCol;
    @FXML
    public TableColumn<Appointment, LocalDateTime> apptWeekStartDATCol;
    @FXML
    public TableColumn<Appointment, LocalDateTime> apptWeekEndDATCol;
    @FXML
    public TableColumn<Appointment, Integer> apptWeekCustomerIDCol;
    @FXML
    public TableColumn<Appointment, Integer> apptWeekUserIDCol;


    /**
     * Switches the scene to the Customer Menu
     * @param event The Radio Button click event
     * @throws IOException in case of an input or output exception
     */
    public void goToCustomerRB(ActionEvent event) throws IOException {
        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        scene.setStyle("-fx-font-family: 'SansSerif';");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Switches the scene to the Reports Menu
     * @param event The Radio Button click event
     * @throws IOException in case of an input or output exception
     */
    public void goToReportsRB(ActionEvent event) throws IOException {
        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportsMenu.fxml"));
        scene.setStyle("-fx-font-family: 'SansSerif';");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Checks for appointments within the next 15 minutes
     * Will generate a pop-up message to alert the user of any upcoming appointments within the next 15 minutes or
     * or if there is an ongoing appointment or no appointments
     * @throws SQLException if exception has occurred
     */
    public void appointmentNotification() throws SQLException {
        int currentUserID = 1;
        LocalDateTime currentLogInTime = LocalDateTime.now();
        LocalDateTime logInTimeMinusFifteen = LocalDateTime.now().minusMinutes(15);
        LocalDateTime logInTimePlusFifteen = LocalDateTime.now().plusMinutes(15);
        LocalDateTime startCheck;
        LocalDateTime endCheck;
        int appointmentID = 0;
        LocalDateTime startDisplay = null;
        LocalDateTime endDisplay = null;
        boolean upcomingAppointment = false;
        boolean ongoingAppointment = false;
        ObservableList<Appointment> appointmentsObservableList = AppointmentQuery.getAllAppointments();
        for (Appointment appointment : appointmentsObservableList) {
            startCheck = appointment.getStart();
            endCheck = appointment.getEnd();
            int userIDCheck = appointment.getUserID();
            if ((userIDCheck == currentUserID) &&
                    (startCheck.isAfter(logInTimeMinusFifteen) || startCheck.isEqual(logInTimeMinusFifteen)) &&
                    (startCheck.isBefore(logInTimePlusFifteen) || (startCheck.isEqual(logInTimePlusFifteen)))) {
                appointmentID = appointment.getAppointmentID();
                startDisplay = startCheck;
                endDisplay = endCheck;
                upcomingAppointment = true;
            }
            else if ((userIDCheck == currentUserID) &&
                    (currentLogInTime.isAfter(startCheck) || currentLogInTime.isEqual(startCheck)) &&
                    (currentLogInTime.isBefore(endCheck) || (currentLogInTime.isEqual(endCheck)))) {
                System.out.println(currentLogInTime);
                System.out.println(startCheck);
                System.out.println(endCheck);
                appointmentID = appointment.getAppointmentID();
                startDisplay = startCheck;
                endDisplay = endCheck;
                ongoingAppointment = true;
            }
        }
        if (upcomingAppointment) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            DialogPane dp = alert.getDialogPane();
            dp.setStyle("-fx-font-family:sans-serif");
            alert.setTitle("Upcoming Appointment");
            alert.setHeaderText("There is an appointment within 15 minutes" +
                    "\nAppointment ID: " + appointmentID +
                    "\nStart Date: " + startDisplay.toLocalDate() +
                    "\nEnd Date: " + endDisplay.toLocalDate() +
                    "\nStart Time: " + startDisplay.toLocalTime() +
                    "\nEnd Time: " + endDisplay.toLocalTime());
            alert.showAndWait();
        }
        else if (ongoingAppointment) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            DialogPane dp = alert.getDialogPane();
            dp.setStyle("-fx-font-family:sans-serif");
            alert.setTitle("Ongoing Appointment");
            alert.setHeaderText("There is a current appointment" +
                    "\nAppointment ID: " + appointmentID +
                    "\nStart Date: " + startDisplay.toLocalDate() +
                    "\nEnd Date: " + endDisplay.toLocalDate() +
                    "\nStart Time: " + startDisplay.toLocalTime() +
                    "\nEnd Time: " + endDisplay.toLocalTime());
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            DialogPane dp = alert.getDialogPane();
            dp.setStyle("-fx-font-family:sans-serif");
            alert.setTitle("Alert");
            alert.setHeaderText("There are currently no upcoming appointments.");
            alert.showAndWait();
        }
    }


    /**
     * The method is used to add the data to the Appointment Tables in the different tabs.
     * The data is retrieved from MySQL via the AppointmentsQuery class
     * @throws SQLException if exception has occurred
     */
    public void addAppointmentDataToTables() throws SQLException {
        ObservableList<Appointment> allAppointmentsList = AppointmentQuery.getAllAppointments();
        ObservableList<Appointment> appointmentsMonthList = FXCollections.observableArrayList();
        ObservableList<Appointment> appointmentsWeekList = FXCollections.observableArrayList();

        LocalDateTime currMonthStart = LocalDateTime.now().minusSeconds(1);
        LocalDateTime currMonthEnd = LocalDateTime.now().plusMonths(1);

        LocalDateTime currWeekStart = LocalDateTime.now().minusSeconds(1);
        LocalDateTime currWeekEnd = LocalDateTime.now().plusWeeks(1);

        for (Appointment appointment : allAppointmentsList) {
            if (appointment.getEnd().isAfter(currMonthStart) && appointment.getEnd().isBefore(currMonthEnd)) {
                appointmentsMonthList.add(appointment);
            }
            if (appointment.getEnd().isAfter(currWeekStart) && appointment.getEnd().isBefore(currWeekEnd)) {
                appointmentsWeekList.add(appointment);
            }
        }
        apptMonthTable.setItems(appointmentsMonthList);
        apptWeekTable.setItems(appointmentsWeekList);
        apptAllTable.setItems(allAppointmentsList);
    }

    /**
     * This method retrieves the data of a selected appointment and populates boxes
     * matches the Contact ID with the Contact Name and is displayed in the ComboBox
     * @throws SQLException if exception has occurred
     */
    public void addAppointmentDataToBoxes() throws SQLException {
        Appointment selectedAppointment = null;
        if (apptAllTableTab.isSelected()) {
            selectedAppointment = apptAllTable.getSelectionModel().getSelectedItem();
        }
        if (apptMonthTableTab.isSelected()) {
            selectedAppointment = apptMonthTable.getSelectionModel().getSelectedItem();
        }
        if (apptWeekTableTab.isSelected()) {
            selectedAppointment = apptWeekTable.getSelectionModel().getSelectedItem();
        }
        if (selectedAppointment != null) {
            appointmentIDTF.setText(String.valueOf((selectedAppointment.getAppointmentID())));
            appointmentTitleTF.setText(selectedAppointment.getAppointmentTitle());
            appointmentDescriptionTF.setText(selectedAppointment.getAppointmentDescription());
            appointmentLocationTF.setText(selectedAppointment.getAppointmentLocation());
            int displayContactID = selectedAppointment.getContactID();
            String contactNameToDisplay = "";
            ObservableList<Contact> contactsObservableList = ContactsQuery.getAllContacts();
            for (Contact contact : contactsObservableList) {
                if (displayContactID == contact.getId()) {
                    contactNameToDisplay = contact.getContactName();
                }
            }
            appointmentContactCB.setValue(contactNameToDisplay);
            appointmentTypeTF.setText(selectedAppointment.getAppointmentType());
            appointmentStartDP.setValue(selectedAppointment.getStart().toLocalDate());
            appointmentEndDP.setValue(selectedAppointment.getEnd().toLocalDate());
            appointmentStartCB.setValue(selectedAppointment.getStart().toLocalTime().toString());
            appointmentEndCB.setValue(selectedAppointment.getEnd().toLocalTime().toString());
            appointmentCustomerIDTF.setText(String.valueOf(selectedAppointment.getCustomerID()));
            appointmentUserIDTF.setText(String.valueOf(selectedAppointment.getUserID()));
        }
    }


    /**
     * Inserts the new Appointment into the Database based on the information provided in the Text Fields and Comboboxes.
     * Checks to see if it doesn't conflict with previously scheduled appointments and makes sure that nothing is scheduled outside of Business days/hours.
     * @param event Clicking the Save Appointment Button
     * @throws SQLException if exception has occurred
     */

    @FXML
    void onActionSaveAppointment(ActionEvent event) throws SQLException {
        int lastAppointmentID = 0;
        ObservableList<Appointment> allAppointmentsList = AppointmentQuery.getAllAppointments();
        for (Appointment appointment : allAppointmentsList) {
            lastAppointmentID = appointment.getAppointmentID();
        }
        int appointmentIDToAdd = lastAppointmentID + 1;
        String titleAdd = appointmentTitleTF.getText();
        String descriptionAdd = appointmentDescriptionTF.getText();
        String locationAdd = appointmentLocationTF.getText();
        String typeAdd = appointmentTypeTF.getText();
        String contactNameToAdd = appointmentContactCB.getValue();
        int contactIDAdd = 0;
        ObservableList<Contact> contactsObservableList = ContactsQuery.getAllContacts();
        for (Contact contact : contactsObservableList) {
            if (contactNameToAdd.equals(contact.getContactName())) {
                contactIDAdd = contact.getId();
            }
        }
        LocalDate startLocalDate = appointmentStartDP.getValue();
        LocalDate endLocalDate = appointmentEndDP.getValue();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startLocalTime = LocalTime.parse(appointmentStartCB.getValue(), timeFormatter);
        LocalTime endLocalTime = LocalTime.parse(appointmentEndCB.getValue(), timeFormatter);
        LocalDateTime startLocalDateTimeToAdd = LocalDateTime.of(startLocalDate, startLocalTime);
        LocalDateTime endLocalDateTimeToAdd = LocalDateTime.of(endLocalDate, endLocalTime);

        // Set and Convert Start Time
        ZonedDateTime startLDTToZDT = ZonedDateTime.of(startLocalDateTimeToAdd, ZoneId.systemDefault());
        ZonedDateTime startZDTToZDTEST = startLDTToZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalTime startAppointmentTimeCheck = startZDTToZDTEST.toLocalTime();
        DayOfWeek startAppointmentDayCheck = startZDTToZDTEST.toLocalDate().getDayOfWeek();
        int startAppointmentDayToCheckInt = startAppointmentDayCheck.getValue();
        // Sent and Convert End Time
        ZonedDateTime endLDTToZDT = ZonedDateTime.of(endLocalDateTimeToAdd, ZoneId.systemDefault());
        ZonedDateTime endZDTToZDTEST = endLDTToZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalTime endAppointmentTimeCheck = endZDTToZDTEST.toLocalTime();
        DayOfWeek endAppointmentDayToCheck = endZDTToZDTEST.toLocalDate().getDayOfWeek();
        int endAppointmentDayToCheckInt = endAppointmentDayToCheck.getValue();

        // Business hours/days
        LocalTime businessHoursStart = LocalTime.of(8, 0, 0);
        LocalTime businessHoursEnd = LocalTime.of(22, 0, 0);
        int startOfBusinessWeek = DayOfWeek.MONDAY.getValue();
        int endOfBusinessWeek = DayOfWeek.FRIDAY.getValue();
        // Verify that the new appointment isn't being created outside of business hours.
        if (startAppointmentTimeCheck.isBefore(businessHoursStart) || startAppointmentTimeCheck.isAfter(businessHoursEnd) || endAppointmentTimeCheck.isBefore(businessHoursStart) || endAppointmentTimeCheck.isAfter(businessHoursEnd)) {
            displayAlert(1);
            return;
        }
        if (startAppointmentDayToCheckInt < startOfBusinessWeek || startAppointmentDayToCheckInt > endOfBusinessWeek || endAppointmentDayToCheckInt < startOfBusinessWeek || endAppointmentDayToCheckInt > endOfBusinessWeek) {
            displayAlert(1);
            return;
        }

        Timestamp createdDateAdd = Timestamp.valueOf(LocalDateTime.now());
        String createdByAdd = "admin";
        Timestamp lastUpdateAdd = Timestamp.valueOf(LocalDateTime.now());
        String lastUpdatedByAdd = "admin";
        int customerIDAdd = Integer.parseInt(appointmentCustomerIDTF.getText());

        // Make sure that the new Appointment doesn't overlap with other appointments.
        ObservableList<Appointment> allAppointments = AppointmentQuery.getAllAppointments();
        for (Appointment appointment : allAppointments) {
            LocalDateTime startTimesToCheck = appointment.getStart();
            LocalDateTime endTimesToCheck = appointment.getEnd();
            int customerIDsToCheck = appointment.getCustomerID();
            if (customerIDAdd == customerIDsToCheck &&
                    (startLocalDateTimeToAdd.isEqual(startTimesToCheck) || startLocalDateTimeToAdd.isAfter(startTimesToCheck)) &&
                    (startLocalDateTimeToAdd.isEqual(endTimesToCheck) || startLocalDateTimeToAdd.isBefore(endTimesToCheck))) {
                displayAlert(2);
                return;
            }
            if (customerIDAdd == customerIDsToCheck &&
                    (endLocalDateTimeToAdd.isEqual(startTimesToCheck) || endLocalDateTimeToAdd.isAfter(startTimesToCheck)) && (endLocalDateTimeToAdd.isEqual(endTimesToCheck) ||
                            endLocalDateTimeToAdd.isBefore(endTimesToCheck))) {
                displayAlert(2);
                return;
            }
        }
        int userIDAdd = Integer.parseInt(appointmentUserIDTF.getText());
        String sqlInsert = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, " +
                "Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlInsert);
        ps.setInt(1, appointmentIDToAdd);
        ps.setString(2, titleAdd);
        ps.setString(3, descriptionAdd);
        ps.setString(4, locationAdd);
        ps.setString(5, typeAdd);
        ps.setTimestamp(6, Timestamp.valueOf(startLocalDateTimeToAdd));
        ps.setTimestamp(7, Timestamp.valueOf(endLocalDateTimeToAdd));
        ps.setTimestamp(8, createdDateAdd);
        ps.setString(9, createdByAdd);
        ps.setTimestamp(10, lastUpdateAdd);
        ps.setString(11, lastUpdatedByAdd);
        ps.setInt(12, customerIDAdd);
        ps.setInt(13, userIDAdd);
        ps.setInt(14, contactIDAdd);
        ps.execute();
        addAppointmentDataToTables();
        System.out.println("Appointment successfully added to MySQL Database!");
        clearData();
    }

    /**
     * Populates the test fields in that make up the form based on selection from the table view.
     * Makes sure the new Appointment's time doesn't overlap with other appointments.
     * Once user has updated any necessary information this function performs the update statement
     * @throws SQLException if exception has occurred
     */

    @FXML
    void onActionModifyAppointment(ActionEvent event) throws SQLException {

        // verify that there is a matching foreign key for customer ID and user ID
        ObservableList<Customer> allCustomerData = CustomersQuery.getAllCustomers();
        ObservableList<Integer> allCustomerIDs = FXCollections.observableArrayList();
        for (Customer customer : allCustomerData) {
            allCustomerIDs.add(customer.getCustomerID());
        }
        if (!allCustomerIDs.contains(Integer.parseInt(appointmentCustomerIDTF.getText()))) {
            displayAlert(5);
            return;
        }
        ObservableList<User> allUserData = UserQuery.getAllUsers();
        ObservableList<Integer> allUserIDs = FXCollections.observableArrayList();
        for (User user : allUserData) {
            allUserIDs.add(user.getUserId());
        }
        if (!allUserIDs.contains(Integer.parseInt(appointmentUserIDTF.getText()))) {
            displayAlert(6);
            return;
        }

        //Gather information of Appointment to be updated.
        int appointmentIDToUpdate = Integer.parseInt(appointmentIDTF.getText());
        String titleToUpdate = appointmentTitleTF.getText();
        String descriptionToUpdate = appointmentDescriptionTF.getText();
        String locationToUpdate = appointmentLocationTF.getText();
        String typeToUpdate = appointmentTypeTF.getText();
        String contactNameToUpdate = appointmentContactCB.getValue();
        int contactIDToUpdate = 0;
        ObservableList<Contact> contactsObservableList = ContactsQuery.getAllContacts();
        for (Contact contact : contactsObservableList) {
            if (contactNameToUpdate.equals(contact.getContactName())) {
                contactIDToUpdate = contact.getId();
            }
        }
        LocalDate startLocalDate = appointmentStartDP.getValue();
        LocalDate endLocalDate = appointmentEndDP.getValue();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startLocalTime = LocalTime.parse(appointmentStartCB.getValue(), timeFormatter);
        LocalTime endLocalTime = LocalTime.parse(appointmentEndCB.getValue(), timeFormatter);
        LocalDateTime startLocalDateTimeUpdate = LocalDateTime.of(startLocalDate, startLocalTime);
        LocalDateTime endLocalDateTimeUpdate = LocalDateTime.of(endLocalDate, endLocalTime);

        // Check if attempted appointment creation is happening outside business hours.
        // Check for the start time
        ZonedDateTime startLocalDTToZonedDT = ZonedDateTime.of(startLocalDateTimeUpdate, ZoneId.systemDefault());
        ZonedDateTime startZonedDTToZonedDTCheck = startLocalDTToZonedDT.withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalTime startTimeCheck = startZonedDTToZonedDTCheck.toLocalTime();
        DayOfWeek startAppointmentDayCheck = startZonedDTToZonedDTCheck.toLocalDate().getDayOfWeek();
        int startDayCheckInt = startAppointmentDayCheck.getValue();

        // Check for end time
        ZonedDateTime endLocalDTToZonedDT = ZonedDateTime.of(endLocalDateTimeUpdate, ZoneId.systemDefault());
        ZonedDateTime endZonedDTToZonedDTCheck = endLocalDTToZonedDT.withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalTime endTimeCheck = endZonedDTToZonedDTCheck.toLocalTime();
        DayOfWeek endAppointmentDayCheck = endZonedDTToZonedDTCheck.toLocalDate().getDayOfWeek();
        int endDayCheckInt = endAppointmentDayCheck.getValue();

        // Setting the variables for what business hours and days are
        LocalTime startOfBusinessHours = LocalTime.of(8, 0, 0);
        LocalTime endOfBusinessHours = LocalTime.of(22, 0, 0);
        int startOfWeekInt = DayOfWeek.MONDAY.getValue();
        int endOfWeekInt = DayOfWeek.FRIDAY.getValue();
        // Check to see if Start of Appointment is BEFORE Business Hours OR if it is AFTER Business Hours.
        if (startTimeCheck.isBefore(startOfBusinessHours) || startTimeCheck.isAfter(endOfBusinessHours) || endTimeCheck.isBefore(startOfBusinessHours) || endTimeCheck.isAfter(endOfBusinessHours)) {
           displayAlert(1);
            return;
        }
        if (startDayCheckInt < startOfWeekInt || startDayCheckInt > endOfWeekInt || endDayCheckInt < startOfWeekInt || endDayCheckInt > endOfWeekInt) {
            displayAlert(1);
            return;
        }
        Timestamp lastUpdatedUpdate = Timestamp.valueOf(LocalDateTime.now());
        String lastUpdatedByUpdate = "admin"; // Hardcoded Value: admin
        int customerIDUpdate = Integer.parseInt(appointmentCustomerIDTF.getText());

        // Make sure there are no overlapping appointment conflicts.
        ObservableList<Appointment> allAppointments = AppointmentQuery.getAllAppointments();
        int selectedAppointmentID = 0;
        if (apptAllTableTab.isSelected()) {
            selectedAppointmentID = apptAllTable.getSelectionModel().getSelectedItem().getAppointmentID();
        }
        if (apptMonthTableTab.isSelected()) {
            selectedAppointmentID = apptMonthTable.getSelectionModel().getSelectedItem().getAppointmentID();
        }
        if (apptWeekTableTab.isSelected()) {
            selectedAppointmentID = apptWeekTable.getSelectionModel().getSelectedItem().getAppointmentID();
        }
        for (Appointment appointment : allAppointments) {
            LocalDateTime startTimesCheck = appointment.getStart();
            LocalDateTime endTimesCheck = appointment.getEnd();
            int customerIDsCheck = appointment.getCustomerID();
            // Check to see if selected Appointment's CustomerIDs match and if the start and/or end overlap with an existing Appointment.
            if ((appointment.getAppointmentID() != selectedAppointmentID) && (customerIDUpdate == customerIDsCheck) && (startLocalDateTimeUpdate.isEqual(startTimesCheck) || startLocalDateTimeUpdate.isAfter(startTimesCheck)) && (startLocalDateTimeUpdate.isEqual(endTimesCheck) || startLocalDateTimeUpdate.isBefore(endTimesCheck))) {
                displayAlert(2);
                return;
            }
            if ((appointment.getAppointmentID() != selectedAppointmentID) && (customerIDUpdate == customerIDsCheck) && (endLocalDateTimeUpdate.isEqual(startTimesCheck) || endLocalDateTimeUpdate.isAfter(startTimesCheck)) && (endLocalDateTimeUpdate.isEqual(endTimesCheck) || endLocalDateTimeUpdate.isBefore(endTimesCheck))) {
                displayAlert(2);
                return;
            }
        }
        int userIDUpdate = Integer.parseInt(appointmentUserIDTF.getText());
        String sqlUpdate = "UPDATE appointments SET Appointment_ID = ?, Title = ?, Description = ?, " +
                "Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, " +
                "Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlUpdate);
        ps.setInt(1, appointmentIDToUpdate);
        ps.setString(2, titleToUpdate);
        ps.setString(3, descriptionToUpdate);
        ps.setString(4, locationToUpdate);
        ps.setString(5, typeToUpdate);
        ps.setTimestamp(6, Timestamp.valueOf(startLocalDateTimeUpdate));
        ps.setTimestamp(7, Timestamp.valueOf(endLocalDateTimeUpdate));
        ps.setTimestamp(8, lastUpdatedUpdate);
        ps.setString(9, lastUpdatedByUpdate);
        ps.setInt(10, customerIDUpdate);
        ps.setInt(11, userIDUpdate);
        ps.setInt(12, contactIDToUpdate);
        ps.setInt(13, appointmentIDToUpdate);
        ps.execute();
        addAppointmentDataToTables();
        System.out.println("Appointment successfully updated in MySQL Database!");
        clearData();
    }

    /**
     * Deletes Appointments from the Database based on Appointment ID.
     * This function also triggers notifications to let the user know that Appointments has been deleted.
     * @param event Clicking the Delete Appointment Button
     * @throws SQLException if exception has occurred
     */

    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws SQLException {
        String sqlDelete = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlDelete);
        int appointmentIDDelete = 0;
        String appointmentType = "";
        if (apptMonthTableTab.isSelected()) {
            appointmentIDDelete = apptMonthTable.getSelectionModel().getSelectedItem().getAppointmentID();
            appointmentType = apptMonthTable.getSelectionModel().getSelectedItem().getAppointmentType();
        }
        if (apptWeekTableTab.isSelected()) {
            appointmentIDDelete = apptWeekTable.getSelectionModel().getSelectedItem().getAppointmentID();
            appointmentType = apptWeekTable.getSelectionModel().getSelectedItem().getAppointmentType();
        }
        if (apptAllTableTab.isSelected()) {
            appointmentIDDelete = apptAllTable.getSelectionModel().getSelectedItem().getAppointmentID();
            appointmentType = apptAllTable.getSelectionModel().getSelectedItem().getAppointmentType();
        }
        ps.setInt(1, appointmentIDDelete);
        ps.execute();
        addAppointmentDataToTables();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dp = alert.getDialogPane();
        dp.setStyle("-fx-font-family:sans-serif");
        alert.setTitle("Appointment Deleted");
        alert.setHeaderText("Appointment with ID: " + appointmentIDDelete + "\nAppointment of Type: " + appointmentType + "\nhas been deleted from Database");
        alert.showAndWait();
        System.out.println("Appointment deleted from MySQL database");
        displayAlert(4);
        clearData();

    }


/*
    @FXML
    void searchPartsHandler(ActionEvent event) {
        try {
            Part foundPart = Inventory.lookupPart(Integer.parseInt(queryPartsTF.getText()));
            partTableView.getSelectionModel().select(foundPart);
            if (foundPart == null) {
                displayAlert(1);
            }
        } catch (Exception e) {
            String partName = queryPartsTF.getText();
            ObservableList<Part> parts = Inventory.lookupPart(partName);
            partTableView.setItems(parts);
            queryPartsTF.setText("");
            if (!(Inventory.partsFound)){
                displayAlert(1);
            }
        }
    }


    @FXML
    void searchProductsHandler(ActionEvent event) {
        try {
            Product foundProduct = Inventory.lookupProduct(Integer.parseInt(queryProductsTF.getText()));
            productTableView.getSelectionModel().select(foundProduct);
        } catch (Exception e) {
            String productName = queryProductsTF.getText();
            ObservableList<Product> products = Inventory.lookupProduct(productName);
            productTableView.setItems(products);
            queryProductsTF.setText("");
        }
    }*/



    /**
     * This switch statement is used to display a variety of alert and error messages.
     * @param alertType Alert selects a message based on case number.
     */
    private void displayAlert(int alertType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dp = alert.getDialogPane();
        dp.setStyle("-fx-font-family:sans-serif");
        DialogPane dp2 = alertInfo.getDialogPane();
        dp2.setStyle("-fx-font-family:sans-serif");

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Scheduling Conflict");
                alert.setContentText("Time selected is outside of business hours. Please schedule between 8AM and 10PM Monday - Friday EST.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Appointment Not Added");
                alert.setContentText("Selected time overlaps with existing appointment.");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Incomplete Data");
                alert.setContentText("Add necessary data to Save Appointment");
                alert.showAndWait();
                break;
            case 4:
                alertInfo.setTitle("Info");
                alertInfo.setHeaderText("Appointment Deleted");
                alertInfo.setContentText("Appointment has been deleted from Database");
                alertInfo.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Appointment not Updated");
                alert.setContentText("Matching Customer ID not found");
                alert.showAndWait();
                break;
            case 6:
                alert.setTitle("Error");
                alert.setHeaderText("Appointment not Updated");
                alert.setContentText("Matching User ID not found");
                alert.showAndWait();
                break;
        }
    }

    /**
     * Resets text fields and comboboxes after performing a Save or an Update.
     */
    public void clearData() {
        appointmentTitleTF.clear();
        appointmentDescriptionTF.clear();
        appointmentLocationTF.clear();
        appointmentIDTF.clear();
        appointmentTypeTF.clear();
        appointmentStartDP.setValue(null);
        appointmentEndDP.setValue(null);
        appointmentStartCB.setValue("");
        appointmentEndCB.setValue("");
        appointmentCustomerIDTF.clear();
        appointmentUserIDTF.clear();
        appointmentContactCB.setValue("");
    }


    /**
     * This method initializes the AppointmentMenuController and populates the Appointment Table View.
     * @param url The url is used to find the relative paths for the root object.
     * @param resourceBundle The resource bundle is used to localize the root object.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        apptAllAppointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptAllTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        apptAllDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        apptAllLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        apptAllContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        apptAllTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptAllStartDATCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptAllEndDATCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptAllCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptAllUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

        apptMonthAppointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptMonthTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        apptMonthDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        apptMonthLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        apptMonthContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        apptMonthTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptMonthStartDATCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptMonthEndDATCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptMonthCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptMonthUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

        apptWeekAppointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptWeekTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        apptWeekDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        apptWeekLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        apptWeekContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        apptWeekTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptWeekStartDATCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptWeekEndDATCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptWeekCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptWeekUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));


        ObservableList<Contact> allContactsObservableList = null;
        try {
            allContactsObservableList = ContactsQuery.getAllContacts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObservableList<String> allContactsNames = FXCollections.observableArrayList();
        allContactsObservableList.forEach(contacts -> allContactsNames.add(contacts.getContactName()));
        appointmentContactCB.setItems(allContactsNames);
        appointmentContactCB.setEditable(true);
        appointmentContactCB.getEditor().setEditable(false);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        ObservableList<String> appointmentTimesDuringDay = FXCollections.observableArrayList();
        LocalTime firstAppointmentLocalTime = LocalTime.MIN.plusHours(8);
        LocalTime lastAppointmentLocalTime = LocalTime.MAX.minusHours(1).minusMinutes(59);
        while (firstAppointmentLocalTime.isBefore(lastAppointmentLocalTime)) {
            appointmentTimesDuringDay.add(dateTimeFormatter.format(firstAppointmentLocalTime));
            firstAppointmentLocalTime = firstAppointmentLocalTime.plusMinutes(30);
        }
        appointmentStartCB.setItems(appointmentTimesDuringDay);
        appointmentStartCB.setEditable(true);
        appointmentStartCB.getEditor().setEditable(false);
        appointmentEndCB.setItems(appointmentTimesDuringDay);
        appointmentEndCB.setEditable(true);
        appointmentEndCB.getEditor().setEditable(false);
        try {
            addAppointmentDataToTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
