package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 * The DBCustomers class provides methods for interacting with the customers database table.
 * It allows for selecting, inserting, updating, and deleting customer records.
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public abstract class DBCustomers {

    /**
     * Inserts a new customer into the database.
     *
     * @param customer The Customer object to insert.
     * @return The number of rows affected by the insert operation.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static int insert(Customer customer) throws SQLException {
        String sql = "insert into customers (Customer_ID, Customer_Name, Address, Postal_Code,Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customer.getCustomerId());
        ps.setString(2, customer.getCustomerName());
        ps.setString(3, customer.getAddress());
        ps.setString(4, customer.getPostalCode());
        ps.setString(5, customer.getPhone());
        ps.setTimestamp(6, Timestamp.valueOf(customer.getCreatedDate()));
        ps.setString(7, customer.getCreatedBy());
        ps.setTimestamp(8, Timestamp.valueOf(customer.getLastUpdate()));
        ps.setString(9, customer.getLastUpdatedBy());
        ps.setInt(10, customer.getDivisionId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Updates an existing customer in the database.
     *
     * @param customer The Customer object with updated information.
     * @return The number of rows affected by the update operation.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static int update(Customer customer) throws SQLException{

        String sql = "UPDATE customers SET " +
                "Customer_Name = ?, " +
                "Address = ?, " +
                "Postal_Code = ?, " +
                "Phone = ?, " +
                "Last_Update = ?, " +
                "Division_ID = ? " +
                "WHERE Customer_ID = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, customer.getCustomerName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setTimestamp(5, Timestamp.valueOf(customer.getLastUpdate()));
        ps.setInt(6, customer.getDivisionId());
        ps.setInt(7, customer.getCustomerId());

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Deletes a customer from the database.
     *
     * @param customer The Customer object to delete.
     * @return The number of rows affected by the delete operation.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static int delete(Customer customer) throws SQLException {
        String sql = "delete from customers where Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customer.getCustomerId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Retrieves a list of all customers from the database.
     *
     * @return An ObservableList containing all the customers retrieved from the database.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static ObservableList<Customer> select() throws SQLException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        String sql = "select * from customers";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int customerId = rs.getInt("Customer_ID");
            String countryName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            Timestamp createdDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");
//        System.out.print(countryId + " | ");
//        System.out.print(countryName+"\n");
            LocalDateTime createdDateTime = (createdDate != null) ? createdDate.toLocalDateTime() : null;
            LocalDateTime lastUpdateDateTime = (lastUpdate != null) ? lastUpdate.toLocalDateTime() : null;

            Customer customer = new Customer(customerId, countryName, address, postalCode, phone, createdDateTime, createdBy, lastUpdateDateTime, lastUpdatedBy, divisionId);
            customerList.add(customer);

        }
        return customerList;
    }

    /**
     * Retrieves a specific customer from the database by their ID.
     *
     * @param customerId The ID of the customer to retrieve.
     * @return The Customer object with the specified ID, or null if not found.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static Customer select(int customerId) throws SQLException {

        Customer selected = null;
        String sql = "select * from customers where Customer_ID=?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int customerIdd = rs.getInt("Customer_ID");
            String countryName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            Timestamp createdDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");
//        System.out.print(countryId + " | ");
//        System.out.print(countryName+"\n");
            LocalDateTime createdDateTime = (createdDate != null) ? createdDate.toLocalDateTime() : null;
            LocalDateTime lastUpdateDateTime = (lastUpdate != null) ? lastUpdate.toLocalDateTime() : null;
            Customer customer = new Customer(customerIdd, countryName, address, postalCode, phone, createdDateTime, createdBy, lastUpdateDateTime, lastUpdatedBy, divisionId);
            System.out.println(customer);
            //filteredCustomerList.add(customer);
            //System.out.println(filteredCustomerList.size());
            selected = customer;
        }
        return selected;
    }


    /**
     * Retrieves a specific customer from the database by their ID.
     *
     * @param customerId The ID of the customer to retrieve.
     * @return The Customer object with the specified ID, or null if not found.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static Customer getCustomerById(int customerId) throws SQLException {
        Customer customer = null;
        ObservableList<Customer> customers = select();
        for(Customer c: customers){
            if(c.getCustomerId() ==customerId){
                customer = c;
            }
        }
        return customer;
    }

}
