package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DivisionsQuery {

    /**
     * @return Creates and Observable List of all Division data from database using a Select statement.
     */
    public static ObservableList<Division> getAllDivisions(){
        ObservableList<Division> divisionList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("COUNTRY_ID");
                Division division = new Division(divisionID, divisionName, countryID);
                divisionList.add(division);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return divisionList;
    }
/*
    *//** @param countryID the country id.
     * @return Returns observable list of all first level division within selected country.*//*
    public static ObservableList<Division> getDivisionsByCountry(int countryID){
        ObservableList<Division> divisionsFilteredByCountry = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int divisionID = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                int countryID = rs.getInt("COUNTRY_ID");
                Division division = new Division(divisionID, divisionName, countryID);
                divisionsFilteredByCountry.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionsFilteredByCountry;

    }*/



}
