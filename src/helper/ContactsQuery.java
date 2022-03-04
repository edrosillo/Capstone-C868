package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsQuery {

    /**
     * @return Creates an Observable List of all contact data from database using a Select statement
     * @throws SQLException if exception has occurred
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> allContactsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contact contact = new Contact(contactID, contactName, contactEmail);
            allContactsObservableList.add(contact);
    }
        return allContactsObservableList;
}

}
