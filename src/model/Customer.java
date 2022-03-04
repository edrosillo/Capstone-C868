package model;

public class Customer {
    /**
     * Customer ID
     */
    private int customerID;
    /**
     * Customer Name
     */
    private String customerName;
    /**
     * Customer Address
     */
    private String address;
    /**
     * Customer Postal Code
     */
    private String postalCode;
    /**
     * Customer Phone
     */
    private String phone;
    /**
     * Division ID (FK)
     */
    public int divisionID;


    /**
     * Customer Constructor
     * @param customerID Customer ID
     * @param customerName Customer Name
     * @param address Customer Address
     * @param postalCode Customer Postal Code
     * @param phone Customer Phone Number
     * @param divisionID Division ID Foreign Key
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phone, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
    }

    /**
     * @return customerID
     */
    public int getCustomerID() { return customerID; }
    /**
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }
    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }
    /**
     * @return postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }
    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * @return divisionID
     */
    public int getDivisionID() { return divisionID; }
}
