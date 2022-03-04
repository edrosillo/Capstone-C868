package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//private static final String loginQuery = "SELECT * FROM users WHERE email_id = ? and password = ?";


public abstract class UserQuery {

    /**
     * This method is retrieves all the data from the Users table in the Database using a select statement.
     * @throws SQLException if exception has occurred
     */
    public static void select() throws SQLException {
        String sql = "SELECT * FROM users"; //SQL
        PreparedStatement ps = JDBC.connection.prepareStatement(sql); //Create Prepared Statement
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            ListManager.addUser(new User(userId, userName, password));
        }
    }

    /**
     * This method is used to validate the information provided in the Log In Screen against the data in the Database.
     * @param userName Username to be validated
     * @param password Password to be validated
     * @throws SQLException if exception has occurred
     * @return A boolean value to see wether or not the provided credentials are saved in the Database
     */
    public static boolean validate(String userName, String password) throws SQLException {
        try{
            String sql = "SELECT * FROM users WHERE User_Name = ? and Password = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql) ;
            {
                ps.setString(1, userName);
                ps.setString(2, password);

                System.out.println(ps);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception e) {
            // print SQL exception information
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * @return Creates and Observable List of all User data from database using a Select statement timestamps are then changed to local time
     * @throws SQLException if exception has occurred
     */
    public static ObservableList<User> getAllUsers() throws SQLException {
        ObservableList<User> usersObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users"; //SQL
        PreparedStatement ps = JDBC.connection.prepareStatement(sql); //Create Prepared Statement
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");
            User user = new User(userID, userName, userPassword);
            usersObservableList.add(user);
        }
        return usersObservableList;
    }


}
