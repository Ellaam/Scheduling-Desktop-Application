package model;

/**
 * The Contact class represents a contact entity.
 * It contains attributes and methods for managing contact information.
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public class Contact {

    private int contactId;
    private String contactName;
    private String contactEmail;

    /**
     * Constructs a new Contact object with the specified attributes.
     *
     * @param contactId    The unique identifier of the contact.
     * @param contactName  The name of the contact.
     * @param contactEmail The email address of the contact.
     */
    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Gets the unique identifier of the contact.
     *
     * @return The contact's unique identifier.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the unique identifier of the contact.
     *
     * @param contactId The contact's unique identifier to set.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    /**
     * Gets the name of the contact.
     *
     * @return The contact's name.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Gets the email address of the contact.
     *
     * @return The contact's email address.
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Sets the email address of the contact.
     *
     * @param contactEmail The contact's email address to set.
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * Gets a string representation of the contact, which is the contact's name.
     *
     * @return The contact's name as a string.
     */
    @Override
    public String toString(){
        return this.getContactName();
    }
}
