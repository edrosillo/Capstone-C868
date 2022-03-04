package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import helper.JDBC;
import helper.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * This class adds and controls all the functionality of the Login Screen.
 */

public class LoginController implements Initializable {

    @FXML
    private Text titleLbl;

    @FXML
    private Text userNameLbl;

    @FXML
    private Text passwordLbl;

    @FXML
    private TextField userNameTF;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private Button loginBtn;

    @FXML
    private Button resetBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Text userLocationLbl;

    /**
     * The stage object that will be used to hold the GUI and data of the new screens.
     */
    Stage stage;

    /**
     * The scene object that will be used to generate the new screens.
     */
    Parent scene;

    //Variables used in methods within Login Controller.
    boolean successLogin = false;
    String logInFailTitle = "Log-In Failed";
    String logInFailMessage ="Invalid username or password";
    String plsEnterUserNameTitle = "Error";
    String plsEnterUserNameMessage = "Please enter username";
    String plsEnterPasswordTitle = "Error";
    String plsEnterPasswordMessage = "Please enter password";

    /**
     * This method Exits the program.
     * @param event Exit button action.
     */
    @FXML
    void onActionExit(ActionEvent event) {
        JDBC.closeConnection();
        System.exit(0);
    }

    /**
     * Resets text fields after pressing the reset button.
     * @param event Clicking Reset Button
     */
    @FXML
    void clearLogin(ActionEvent event) {
        userNameTF.clear();
        passwordTF.clear();
    }

    /**
     * This method validates the Username and Password against data that is already in the Database.
     * If fed proper credentials the program will proceed to the Appointment Menu part of the program.
     * It triggers the appointment notification method which will tell users of they have any upcoming
     * appointments or not.
     * It also triggers the userActivity method which logs successful and failed log in attempts.
     * @throws IOException in case an exception is triggered
     * @param event Clicking the Submit Button
     */

    @FXML
    public void onActionLogin(ActionEvent event) throws IOException {
        try {
            if (userNameTF.getText().isEmpty()) {
                displayAlert(3);
                return;
            }
            if (passwordTF.getText().isEmpty()) {
                displayAlert(4);
                return;
            }
            String userName = userNameTF.getText();
            String password = passwordTF.getText();

            successLogin = UserQuery.validate(userName, password);

            if (!successLogin) {
                displayAlert(5);
            } else {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/AppointmentMenu.fxml"));
                scene.setStyle("-fx-font-family: 'SansSerif';");
                stage.setScene(new Scene(scene));
                stage.show();
                AppointmentMenuController login = new AppointmentMenuController();
                login.appointmentNotification();
            }
        }
        catch(Exception e){
            // print SQL exception information
            System.out.println(e.getMessage());
        }
        userActivity();
    }

    /**
     * This method sets checks the user's language and location settings.
     * It will translate the interface into French if user’s computer language setting = fr.
     * Headers, Buttons, Labels and Error Messages are translated into French.
     */
    public void checkLanguage() {
        ZoneId currentZone = ZoneId.systemDefault();
        userLocationLbl.setText("User Location: " + currentZone);
        Locale French = new Locale("fr", "FR");
        ResourceBundle rb = ResourceBundle.getBundle("main/Nat", Locale.FRENCH);
        if(Locale.getDefault().getLanguage().equals("fr")) {
            Locale.setDefault(French);
            titleLbl.setText((rb.getString("Appointment,Management,System")).replaceAll(",", " "));
            userNameLbl.setText((rb.getString("Username")).replaceAll(",", " "));
            passwordLbl.setText((rb.getString("password")).replaceAll(",", " "));
            //passwordTF.setPromptText(rb.getString("password").replaceAll(",", " "));
            loginBtn.setText(rb.getString("Submit"));
            resetBtn.setText(rb.getString("Reset"));
            exitBtn.setText(rb.getString("Exit"));
            int indexOfSeparation = (currentZone.toString()).indexOf("/");
            String printRegion = (currentZone.toString()).substring(0, indexOfSeparation);
            String printCountryFR;
            if (printRegion.equals("Pacific") || printRegion.equals("America") || printRegion.equals("Europe")) {
                printCountryFR = (rb.getString(printRegion));
                int indexOfEnd = (currentZone.toString()).length();
                String locationToPrint = (currentZone.toString()).substring(indexOfSeparation, indexOfEnd);
                userLocationLbl.setText((rb.getString("User,Location")).replaceAll(",", " ") +
                        ": " + printCountryFR + locationToPrint);
            }
            else {
                userLocationLbl.setText((rb.getString("User,Location")).replaceAll(",", " ") +
                        ": " + currentZone);
            }
            logInFailMessage = rb.getString("Invalid,username,or,password").replaceAll(",", " ");
            logInFailTitle = rb.getString("Log-In,Failed").replaceAll(",", " ");
            plsEnterUserNameMessage = rb.getString("Please,enter,username").replaceAll(",", " ");
            plsEnterUserNameTitle = rb.getString("Error").replaceAll(",", " ");
            plsEnterPasswordMessage = rb.getString("Please,enter,password").replaceAll(",", " ");
            plsEnterPasswordTitle = rb.getString("Error").replaceAll(",", " ");
        }
    }


    /**
     * This method used to create a log file of the user's activity.
     * which adds new entries as users log into the program.
     * @throws IOException in case an exception is triggered
     */
    public void userActivity() throws IOException {
        LocalDate loginDate = LocalDateTime.now().toLocalDate();
        Timestamp loginTimestamp = Timestamp.valueOf(LocalDateTime.now());
        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter outputFile = new PrintWriter(fileWriter);
        outputFile.print("Date: " + loginDate + " -- ");
        outputFile.print("Timestamp: " + loginTimestamp + " -- ");
        if (successLogin) {
            outputFile.print("Status: Log In Attempt Successful\n");
        }
        else {
            outputFile.print("Status: Log In Attempt Failed\n");
        }
        outputFile.close();
    }

    /**
     * This switch statement is used to display a variety of alert and error messages.
     * @param alertType Alert selects a message based on case number.
     */

    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        DialogPane dp = alert.getDialogPane();
        dp.setStyle("-fx-font-family:sans-serif");
        DialogPane dp2 = alertError.getDialogPane();
        dp2.setStyle("-fx-font-family:sans-serif");

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("User ID does not exist");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Incorrect Password entered");
                alert.showAndWait();
                break;
            case 3:
                alertError.setTitle(plsEnterUserNameTitle);
                alertError.setHeaderText(plsEnterUserNameMessage);
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle(plsEnterPasswordTitle);
                alertError.setHeaderText(plsEnterPasswordMessage);
                alertError.showAndWait();
                break;
            case 5:
                alertError.setTitle(logInFailTitle);
                alertError.setHeaderText(logInFailMessage);
                alertError.showAndWait();
                break;
            case 6:
                alertConfirmation.setTitle("Success");
                alertConfirmation.setHeaderText("Logging In");
                alertConfirmation.showAndWait();
                break;
        }
    }


    /**
     * This method initializes the MainController and checks the location of the user.
     * @param url The url is used to find the relative paths for the root object.
     * @param resourceBundle The resource bundle is used to localize the root object.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        checkLanguage();


    }

}
