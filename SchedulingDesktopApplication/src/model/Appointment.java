package model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The Appointment class represents an appointment entity.
 * It contains attributes and methods for managing appointment information.
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public class Appointment {
   private int id;
   private String title;
   private String description;
   private String location;
   private String type;
   private LocalDateTime start;
   private LocalDateTime end;
   private LocalDateTime createdDate;
   private String createdBy;
   private LocalDateTime lastUpdated;
   private String lastUpdatedBy;
   private int customerId;
   private int userId;
   private int contactId;

    /**
     * Constructs a new Appointment object with the specified attributes.
     *
     * @param id           The unique identifier of the appointment.
     * @param title        The title of the appointment.
     * @param description  The description of the appointment.
     * @param location     The location of the appointment.
     * @param type         The type of the appointment.
     * @param start        The start date and time of the appointment.
     * @param end          The end date and time of the appointment.
     * @param createdDate  The date and time when the appointment was created.
     * @param createdBy    The user who created the appointment.
     * @param lastUpdated  The date and time when the appointment was last updated.
     * @param lastUpdatedBy The user who last updated the appointment.
     * @param customerId   The unique identifier of the customer associated with the appointment.
     * @param userId       The unique identifier of the user associated with the appointment.
     * @param contactId    The unique identifier of the contact associated with the appointment.
     */
   public Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createdDate, String createdBy, LocalDateTime lastUpdated, String lastUpdatedBy, int customerId, int userId, int contactId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * Gets the unique identifier of the appointment.
     *
     * @return The appointment's unique identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the appointment.
     *
     * @param id The appointment's unique identifier to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the title of the appointment.
     *
     * @return The appointment's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the appointment.
     *
     * @param title The appointment's title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the appointment.
     *
     * @return The appointment's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the appointment.
     *
     * @param description The appointment's description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the location of the appointment.
     *
     * @return The appointment's location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the appointment.
     *
     * @param location The appointment's location to set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the type of the appointment.
     *
     * @return The appointment's type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the appointment.
     *
     * @param type The appointment's type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the start date and time of the appointment.
     *
     * @return The start date and time of the appointment.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets the start date and time of the appointment.
     *
     * @param start The start date and time of the appointment to set.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Gets the end date and time of the appointment.
     *
     * @return The end date and time of the appointment.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets the end date and time of the appointment.
     *
     * @param end The end date and time of the appointment to set.
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Gets the date and time when the appointment was created.
     *
     * @return The appointment's creation date and time.
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the date and time when the appointment was created.
     *
     * @param createdDate The appointment's creation date and time to set.
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the user who created the appointment.
     *
     * @return The user who created the appointment.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the user who created the appointment.
     *
     * @param createdBy The user who created the appointment to set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the date and time when the appointment was last updated.
     *
     * @return The appointment's last update date and time.
     */
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Sets the date and time when the appointment was last updated.
     *
     * @param lastUpdated The appointment's last update date and time to set.
     */
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Gets the user who last updated the appointment.
     *
     * @return The user who last updated the appointment.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the user who last updated the appointment.
     *
     * @param lastUpdatedBy The user who last updated the appointment to set.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets the unique identifier of the customer associated with the appointment.
     *
     * @return The customer's unique identifier.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the unique identifier of the customer associated with the appointment.
     *
     * @param customerId The unique identifier of the customer to set.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the unique identifier of the user associated with the appointment.
     *
     * @return The user's unique identifier.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier of the user associated with the appointment.
     *
     * @param userId The unique identifier of the user to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the unique identifier of the contact associated with the appointment.
     *
     * @return The contact's unique identifier.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the unique identifier of the contact associated with the appointment.
     *
     * @param contactId The unique identifier of the contact to set.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment appointment = (Appointment) o;
        return id == appointment.id; // Use a unique identifier for comparison (e.g., the appointment ID).
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Use the same unique identifier for the hash code.
    }

    /**
     * Returns a string representation of the appointment.
     *
     * @return The title of the appointment.
     */
    @Override
    public String toString(){
        return this.getTitle();
    }
}






