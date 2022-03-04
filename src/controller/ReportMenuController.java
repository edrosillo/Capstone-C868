package controller;

import helper.AppointmentQuery;
import helper.ContactsQuery;
import helper.CustomersQuery;
import helper.DivisionsQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * This class adds and controls all the functionality of the Report Menu.
 */

public class ReportMenuController implements Initializable {
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

    // Report 1 - Display Appointments Totals by Type and Month
    @FXML
    Tab totalApptsTab;
    // // Report View 1a - Display Appointments by Type
    @FXML
    TableView<ReportAppointmentType> appointmentsByTypeTable;
    @FXML
    TableColumn<ReportAppointmentType, String> totApptsTypeCol;
    @FXML
    TableColumn<ReportAppointmentType, Integer> totApptsTotalCol;

    // Report View 1b - Display Appointments by Month
    @FXML
    TableView<ReportAppointmentByMonth> appointmentsByMonthTable;
    @FXML
    TableColumn<ReportAppointmentByMonth, String> apptsByMonMonthCol;
    @FXML
    TableColumn<ReportAppointmentByMonth, Integer> apptsByMonTotalCol;

    // Report View 2 - Display Appointments by Contact
    @FXML
    Tab appointmentsByContactTab;
    @FXML
    TableView<Appointment> appointmentsByContactTable;
    @FXML
    TableColumn<Appointment, Integer> apptsByContactIDCol;
    @FXML
    TableColumn<Appointment, String> apptsByContactTitleCol;
    @FXML
    TableColumn<Appointment, String> apptsByContactTypeCol;
    @FXML
    TableColumn<Appointment, String> apptsByContactDescriptionCol;
    @FXML
    TableColumn<Appointment, LocalDateTime> apptsByContactStartCol;
    @FXML
    TableColumn<Appointment, LocalDateTime> apptsByContactEndCol;
    @FXML
    TableColumn<Appointment, Integer> apptsByContactCustomerIDCol;

    @FXML
    Label contactText;
    @FXML
    ComboBox<String> contactSelectCB;

    // Report View 3 - Show Customers by Division
    @FXML Tab customerDivisionTab;
    @FXML TableView<ReportCustomerDivision> customerDivisionTable;
    @FXML TableColumn<ReportCustomerDivision, String> custbyDivDivisionCol;
    @FXML TableColumn<ReportCustomerDivision, ArrayList<String>> custbyDivCustomersCol;


    /**
     * @param event on radio button press - switch from AppointmentForm to CustomerForm.
     * @throws IOException in case an exception is triggered
     */
    public void goToCustomerRB(ActionEvent event) throws IOException {
        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        scene.setStyle("-fx-font-family: 'SansSerif';");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @param event When the Radio Button is clicked the scene will change to Appointments Menu.
     * @throws IOException in case an exception is triggered
     */
    public void goToAppointmentRB(ActionEvent event) throws IOException {
        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentMenu.fxml"));
        scene.setStyle("-fx-font-family: 'SansSerif';");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    // Reports 1a and 1b
    // Report 1a
    /**
     * Gathers the different Types of Appointments, ignores any duplicate types.
     * Checks the frequency in which each Type appears, creates instance of the class and sets the list for tableview
     * @throws SQLException if exception has occurred
     */
    public void addAppointmentTypesTotals() throws SQLException {
        String typeToSet;
        int totalToSet;
        ObservableList<Appointment> allAppointmentData = AppointmentQuery.getAllAppointments();
        ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
        allAppointmentData.forEach(appointments -> appointmentTypes.add(appointments.getAppointmentType()));
        ObservableList<String> appointmentTypesNoDuplicates = FXCollections.observableArrayList();
        for (Appointment appointment : allAppointmentData) {
            String type = appointment.getAppointmentType();
            if (!appointmentTypesNoDuplicates.contains(type)) {
                appointmentTypesNoDuplicates.add(type);
            }
        }
        ObservableList<ReportAppointmentType> appointmentReportTypes = FXCollections.observableArrayList();
        for (String type : appointmentTypesNoDuplicates) {
            int total = Collections.frequency(appointmentTypes, type);
            typeToSet = type;
            totalToSet = total;
            ReportAppointmentType appointmentType = new ReportAppointmentType(typeToSet, totalToSet);
            appointmentReportTypes.add(appointmentType);
        }
        appointmentsByTypeTable.setItems(appointmentReportTypes);
    }


    // Report 1b
    /**
     * Generates a list of Months in which Appointments are scheduled, any duplicates are ignored.
     * Calculates the occurrence of  each month, creates an instance of class and sets the list for tableview
     * @throws SQLException if some form of exception is triggered
     */
    public void addAppointmentMonthsTotals() throws SQLException {
        String monthToSet;
        int totalToSet;
        ObservableList<Appointment> allAppointmentData = AppointmentQuery.getAllAppointments();
        ObservableList<Month> appointmentMonths = FXCollections.observableArrayList();
        for (Appointment appointment : allAppointmentData) {
            Month month = appointment.getStart().getMonth();
            appointmentMonths.add(month);
        }
        ObservableList<Month> appointmentMonthsNoDuplicates = FXCollections.observableArrayList();
        for (Month month : appointmentMonths) {
            if (!appointmentMonthsNoDuplicates.contains(month)) {
                appointmentMonthsNoDuplicates.add(month);
            }
        }
        ObservableList<ReportAppointmentByMonth> reportAppointmentMonths = FXCollections.observableArrayList();
        for (Month month : appointmentMonthsNoDuplicates) {
            int total = Collections.frequency(appointmentMonths, month);
            monthToSet = month.name();
            totalToSet = total;
            ReportAppointmentByMonth monthVar = new ReportAppointmentByMonth(monthToSet, totalToSet);
            reportAppointmentMonths.add(monthVar);
        }
        System.out.println(reportAppointmentMonths.size());
        appointmentsByMonthTable.setItems(reportAppointmentMonths);
    }

    /**
     * Combines above functions in order for the tables in this first tab get populated with the pertinent information.
     * @throws SQLException if some form of exception is triggered
     */
    public void addTypeMonthTotalAppointments() throws SQLException {
        addAppointmentTypesTotals();
        addAppointmentMonthsTotals();
    }

    // Report 2
    /**
     * The first step for this tab to work is to ake the selection Combobox and label visible.
     * This populates the Contact Names into the Combobox.
     * @throws SQLException if exception has occurred
     */
    public void addAppointmentsByContact() throws SQLException {
        contactText.setVisible(true);
        contactSelectCB.setVisible(true);
        ObservableList<Contact> allContacts = ContactsQuery.getAllContacts();
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for (Contact contacts : allContacts) {
            String contactName = contacts.getContactName();
            contactNames.add(contactName);
        }
        contactSelectCB.setItems(contactNames);
    }

    /**
     * Uses the selected name from the Combobox to find all the matching Appointments connected to that Customer.
     * @throws SQLException if exception has occurred
     */
    public void addContactAppointmentData() throws SQLException {
        int selectedContactID = 0;
        Appointment selectedAppointmentData;
        String selectedContactName = contactSelectCB.getSelectionModel().getSelectedItem();
        if (selectedContactName != null) {

            ObservableList<Contact> allContactData = ContactsQuery.getAllContacts();
            for (Contact contact : allContactData) {
                if (selectedContactName.equals(contact.getContactName())) {
                    selectedContactID = contact.getId();
                }
            }
            ObservableList<Appointment> allAppointmentData = AppointmentQuery.getAllAppointments();
            ObservableList<Appointment> appointmentDataForSelectedContact = FXCollections.observableArrayList();
            for (Appointment appointment : allAppointmentData) {
                if (selectedContactID == appointment.getContactID()) {
                    selectedAppointmentData = appointment;
                    appointmentDataForSelectedContact.add(selectedAppointmentData);
                }
            }
            appointmentsByContactTable.setItems(appointmentDataForSelectedContact);
        }
    }

    // Report View 3 - Show Customers by Division
    /**
     * Report 3 - Customer by Division
     * This method matches Division ID with Customer_Division ID in order to populate the table and display which Customer is located in which Division.
     * This type of report is important so company management can view which Division contains the most valuable Customers,
     * and in which Division the company should invest more in order to gain more customers.
     *  @throws SQLException if exception has occurred
     */
    public void addCustomersByDivision() throws SQLException {
        String divisionSet ;
        ArrayList<String> customerListSet;
        ReportCustomerDivision record;
        String previousDivisionName = "";

        ObservableList<Division> allDivisionData = DivisionsQuery.getAllDivisions();
        ObservableList<Customer> allCustomerData = CustomersQuery.getAllCustomers();
        ObservableList<ReportCustomerDivision> observableListToSet = FXCollections.observableArrayList();

        for (Division division : allDivisionData) {
            customerListSet = new ArrayList<>();
            for (Customer customer : allCustomerData) {
                if (division.getDivisionID() == customer.divisionID) {
                    divisionSet = division.getDivisionName();
                    customerListSet.add(customer.getCustomerName());
                    record = new ReportCustomerDivision(divisionSet, customerListSet);
                    if (!record.getDivision().equals(previousDivisionName)) {
                        observableListToSet.add(record);
                        previousDivisionName = record.getDivision();
                    }
                }
            }
        }
        customerDivisionTable.setItems(observableListToSet);
    }

    /**
     * This method initializes the MainController and populates the all table views required for the different reports.
     * @param url The url is used to find the relative paths for the root object.
     * @param resourceBundle The resource bundle is used to localize the root object.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Initial Status of Label and Combobox
        contactText.setVisible(false);
        contactSelectCB.setVisible(false);
        contactSelectCB.setEditable(true);
        contactSelectCB.getEditor().setEditable(false);

        // Report View 1a - Display Appointments by Type
        totApptsTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        totApptsTotalCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));

        // Report View 1b - Display Appointments by Month
        apptsByMonMonthCol.setCellValueFactory(new PropertyValueFactory<>("appointmentMonth"));
        apptsByMonTotalCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTotalMonth"));

        // Report View 2 - Display Appointments by Contact
        apptsByContactIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptsByContactTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        apptsByContactTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptsByContactDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        apptsByContactStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptsByContactEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptsByContactCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        // Report View 3 - Show Customers by Division
        custbyDivDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        custbyDivCustomersCol.setCellValueFactory(new PropertyValueFactory<>("customerList"));
    }

}

