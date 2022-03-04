package model;

public class Contact {
    /**
     * Contact ID
     */
    public int contactID;
    /**
     * Contact Name
     */
    public String contactName;
    /**
     * Contact Email
     */
    public String contactEmail;

    /**
     * Contact Constructor
     * @param contactID Contact ID
     * @param contactName Contact Name
     * @param contactEmail Contact Email
     */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * @return Contact ID
     */
    public int getId() { return contactID; }
    /**
     * @return Contact name
     */
    public String getContactName() { return contactName; }
    /**
     * @return Contact Email
     */
    public String getContactEmail() { return contactEmail; }
}
