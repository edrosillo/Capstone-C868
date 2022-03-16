package main;

import helper.AppointmentQuery;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

/**
 *
 * This is the Main Class for an Inventory Management application that is designed to manage
 * an inventory of parts and products containing of parts.
 *
 */

public class Main extends Application {

    /**
     * The start method creates the first screen in the application.
     * It generates FXML stage and loads the GUI and information of the initial scene.
     * @param primaryStage This will be the GUI and information the user will see.
     */

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        root.setStyle("-fx-font-family: 'SansSerif';");
        primaryStage.setTitle("Droid Depot Appointment Management System");
        primaryStage.setScene(new Scene(root, 880, 607));
        primaryStage.show();
    }

    /**
     * The main method is the entry point to the application. It launches the Java application.
     * @param args The arguments are the functions that open and close the connection to the Database.
     */

    public static void main(String[] args) {
        //Locale.setDefault(new Locale("fr")); //French Locale for Testing purposes.
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}