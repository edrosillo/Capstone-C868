package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CountriesQuery {

    /**Retrieves a list of all countries from countries database using a Select statement.
     * @return Returns array list of all country from database.
     */

    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> countryList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);;
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                Country c = new Country(countryID, country);
                countryList.add(c);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return countryList;
    }

}
