package model;

import java.time.LocalDateTime;

public class Country {
    /**
     * Country ID
     */
    private int countryID;
    /**
     * Country Name
     */
    private String country;

    /**
     * Division Constructor
     * @param countryID Country ID
     * @param country Country Name
     */
    public Country(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    /**
     * @return Country ID
     */
    public int getCountryID() {
        return countryID;
    }
    /**
     * @return Country Name
     */
    public String getCountry() {
        return country;
    }

}
