package model;

import helper.DivisionsQuery;
import javafx.collections.ObservableList;


public class Division {
    /**
     * Division ID
     */
    private int divisionID;
    /**
     * Division Name
     */
    private String divisionName;
    /**
     * Country ID (FK)
     */
    public int countryID;

    /**
     * Division Constructor
     * @param divisionID Division ID
     * @param divisionName Division Name
     * @param countryID Country ID Foreign Key
     */
    public Division(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     * @return the division ID
     */
    public int getDivisionID() { return divisionID; }

    /**
     * @return the division name
     */
    public String getDivisionName() { return divisionName; }

    /**
     *
     * @return the country ID
     */
    public int getCountryID() { return countryID; }


    /** This method searches the first level division combobox for matching division ID of the customer selected.
     * @param divID The customer's division ID.
     * @return Returns the first level division with matching ID.*/
    public static Division getDivisionIdMatch(int divID) {
        ObservableList<Division> divisions = DivisionsQuery.getAllDivisions();

        Division division = null;

        for (Division firstLevelDivision : divisions) {
            if (firstLevelDivision.getDivisionID() != divID) {
            } else {
                division = firstLevelDivision;
                break;
            }

        }
        return division;
    }


}
