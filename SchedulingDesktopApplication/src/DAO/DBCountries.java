package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The DBCountries class provides methods for interacting with the countries database table.
 * It allows for selecting, inserting, updating, and deleting country records.
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public abstract class DBCountries {

    /**
     * Inserts a new country into the database.
     *
     * @param countryName The name of the country to insert.
     * @return The number of rows affected by the insert operation.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static int insert(String countryName) throws SQLException {
        String sql = "insert into countries (Country) values(?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, countryName);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Updates an existing country in the database.
     *
     * @param countryId   The ID of the country to update.
     * @param countryName The new name for the country.
     * @return The number of rows affected by the update operation.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static int update(int countryId, String countryName) throws SQLException {
        String sql = "update countries set Country = ? where Country_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, countryName);
        ps.setInt(2, countryId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Deletes a country from the database.
     *
     * @param countryId The ID of the country to delete.
     * @return The number of rows affected by the delete operation.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static int delete(int countryId) throws SQLException {
        String sql = "delete from countries where Country_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, countryId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Retrieves a list of all countries from the database.
     *
     * @return An ObservableList containing all the countries retrieved from the database.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static ObservableList<Country> select() throws SQLException {
        ObservableList<Country> countryList = FXCollections.observableArrayList();
        String sql = "select * from countries";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int countryId = rs.getInt("Country_ID");
              String countryName = rs.getString("Country");
            Country country = new Country(countryId, countryName);
            countryList.add(country);
        }
        return countryList;
    }

    /**
     * Retrieves a specific country from the database by its ID.
     *
     * @param countryId The ID of the country to retrieve.
     * @return The Country object with the specified ID, or null if not found.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static ObservableList<Country> select(int countryId) throws SQLException {
        ObservableList<Country> filteredCountryList = FXCollections.observableArrayList();
        String sql = "select * from countries where Country_ID=?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int country_id = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Country country = new Country(countryId, countryName);
            filteredCountryList.add(country);
        }
        return filteredCountryList;
    }

    /**
     * Retrieves a specific country from the database by its ID.
     *
     * @param countryId The ID of the country to retrieve.
     * @return The Country object with the specified ID, or null if not found.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static Country getCountry(int countryId) throws SQLException {
            Country country = null;
            ObservableList<Country> countries = select();
            for(Country c: countries){
                if(c.getCountryId()==countryId){
                    country = c;
                }
            }
            return country;
    }
}
