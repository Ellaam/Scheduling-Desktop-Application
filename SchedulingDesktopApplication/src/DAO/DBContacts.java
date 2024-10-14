package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The DBContacts class provides methods for interacting with the contacts database table.
 * It allows for selecting, retrieving, inserting, updating, and deleting contact records.
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public abstract class DBContacts {

    /**
     * Selects and retrieves all contacts from the database.
     *
     * @return An ObservableList containing all the contacts retrieved from the database.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static ObservableList<Contact> select() throws SQLException {
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        String sql = "select * from contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contact contact = new Contact(contactId, contactName, contactEmail);
            contactList.add(contact);
        }
        return contactList;
    }

    /**
     * Retrieves the contact associated with a given contact ID.
     *
     * @param contactId The ID of the contact to retrieve the name for.
     * @return The Contact object containing the contact name, or null if not found.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static Contact getContactById(int contactId) throws SQLException {
         Contact contact = null;
        ObservableList<Contact> contacts = DBContacts.select();
        for( Contact c: contacts){
            if(c.getContactId() ==contactId){
                contact = c;
            }
        }
        return contact;
    }

    /**
     * Retrieves the contact ID associated with a given contact name.
     *
     * @param contactName The name of the contact to retrieve the ID for.
     * @return The contact ID, or -1 if the contact name is not found.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static int getContactId(String contactName) throws SQLException {
        int id = -1;
        ObservableList<Contact> contacts = select();
        for(Contact c: contacts){
            if(c.getContactName().equals(contactName)){
                id = c.getContactId();
                break;
            }
        }
        return id;
    }

    public int insert() {
        return 0;
    }


    public int update() {
        return 0;
    }


    public int delete() {
        return 0;
    }
}
