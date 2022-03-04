package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.time.LocalTime;
import java.util.ArrayList;

public abstract class ListManager {

    /**
     * Observable List of all User data from database.
     */
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();

    /**
     * Adds user data to the Observable List of all User
     * @param user User to be added to the list
     */
    public static void addUser(User user) { allUsers.add(user);}


}
