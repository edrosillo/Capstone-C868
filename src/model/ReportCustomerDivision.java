package model;

import java.util.ArrayList;

public class ReportCustomerDivision {
    /**
     * Division Name
     */
    public String division;
    /**
     * List of Customers
     */
    public ArrayList<String> customerList;

    /**
     * Report Customers By Division Constructor
     * @param division Division
     * @param customerList the customer list to set
     */
    public ReportCustomerDivision(String division, ArrayList<String> customerList) {
        this.division = division;
        this.customerList = customerList;
    }

    /**
     * @return Division Name
     */
    public String getDivision() {
        return division;
    }

    /**
     * @return Customer List
     */
    public ArrayList<String> getCustomerList() {
        return customerList;
    }


}
