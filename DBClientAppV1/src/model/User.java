package model;

import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * The User class represents a user entity in the application.
 * It contains attributes and methods for managing user information.
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public class User implements Initializable {

    private int userId;
    private String userName;
    private String password;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    /**
     * Constructs a new User object with the specified attributes.
     *
     * @param userId       The unique identifier of the user.
     * @param userName     The username of the user.
     * @param password     The password of the user.
     * @param createdDate  The date and time when the user was created.
     * @param createdBy    The username of the creator.
     * @param lastUpdate   The date and time of the last update.
     * @param lastUpdatedBy The username of the last updater.
     */
    public User(int userId, String userName, String password, LocalDateTime createdDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets the user's unique identifier.
     *
     * @return The user's unique identifier.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user's unique identifier.
     *
     * @param userId The user's unique identifier to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    /**
     * Gets the user's username.
     *
     * @return The user's username.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the user's password.
     *
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password The user's password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the date and time when the user was created.
     *
     * @return The user's creation date and time.
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the date and time when the user was created.
     *
     * @param createdDate The user's creation date and time to set.
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the username of the user who created this user.
     *
     * @return The username of the creator.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the username of the user who created this user.
     *
     * @param createdBy The username of the creator to set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the date and time of the last update made to this user's information.
     *
     * @return The date and time of the last update.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the date and time of the last update made to this user's information.
     *
     * @param lastUpdate The date and time of the last update to set.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the username of the user who made the last update to this user's information.
     *
     * @return The username of the last updater.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the username of the user who made the last update to this user's information.
     *
     * @param lastUpdatedBy The username of the last updater to set.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets a string representation of the user, which is the user's username.
     *
     * @return The user's username as a string.
     */
    @Override
    public String toString(){
        return this.getUserName();
    }

    /**
     * Initializes the user object. This method is required by the Initializable interface.
     *
     * @param url            The location used to resolve relative paths.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
