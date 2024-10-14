package model;

import java.time.LocalDateTime;

/**
 * The Customer class represents a customer entity in the application.
 * It contains attributes and methods for managing customer information.
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public class Customer {

    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int divisionId;


    /**
     * Constructs a new Customer object with the specified attributes.
     *
     * @param customerId     The unique identifier of the customer.
     * @param customerName   The name of the customer.
     * @param address        The address of the customer.
     * @param postalCode     The postal code of the customer's location.
     * @param phone          The phone number of the customer.
     * @param createdDate    The date and time when the customer was created.
     * @param createdBy      The user who created the customer record.
     * @param lastUpdate     The date and time of the last update to the customer record.
     * @param lastUpdatedBy  The user who last updated the customer record.
     * @param divisionId     The division ID associated with the customer.
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phone, LocalDateTime createdDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
    }

    /**
     * Gets the date and time when the customer was created.
     *
     * @return The created date and time.
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the date and time when the customer was created.
     *
     * @param createdDate The created date and time to set.
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the user who created the customer record.
     *
     * @return The user who created the customer record.
     */
    public String getCreatedBy() {
        return "script";
    }

    /**
     * Sets the user who created the customer record.
     *
     * @param createdBy The user who created the customer record.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the date and time of the last update to the customer record.
     *
     * @return The last update date and time.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the date and time of the last update to the customer record.
     *
     * @param lastUpdate The last update date and time to set.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the user who last updated the customer record.
     *
     * @return The user who last updated the customer record.
     */
    public String getLastUpdatedBy() {
        return "script";
    }

    /**
     * Sets the user who last updated the customer record.
     *
     * @param lastUpdatedBy The user who last updated the customer record.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets the division ID associated with the customer.
     *
     * @return The division ID.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the division ID associated with the customer.
     *
     * @param divisionId The division ID to set.
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Gets the unique identifier of the customer.
     *
     * @return The customer's ID.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the unique identifier of the customer.
     *
     * @param customerId The customer's ID to set.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the name of the customer.
     *
     * @return The customer's name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the name of the customer.
     *
     * @param customerName The customer's name to set.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the address of the customer.
     *
     * @return The customer's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer.
     *
     * @param address The customer's address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the postal code of the customer's location.
     *
     * @return The postal code.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code of the customer's location.
     *
     * @param postalCode The postal code to set.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the phone number of the customer.
     *
     * @return The customer's phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the customer.
     *
     * @param phone The customer's phone number to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets a string representation of the customer, which is the customer's name.
     *
     * @return The customer's name as a string.
     */
    @Override
    public String toString(){
        return this.getCustomerName();
    }
}
