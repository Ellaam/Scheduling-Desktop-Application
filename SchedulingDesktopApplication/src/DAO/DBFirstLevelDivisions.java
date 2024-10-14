package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The DBFirstLevelDivisions class provides methods for interacting with the first-level divisions database table.
 * It allows for selecting, inserting, updating, and deleting division records associated with countries.
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public abstract class DBFirstLevelDivisions {


    /**
     * Inserts a new division into the database associated with a specific country.
     *
     * @param divisionName The name of the division to insert.
     * @param countryId    The ID of the country to which the division belongs.
     * @return The number of rows affected by the insert operation.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static int insert(String divisionName, int countryId) throws SQLException {
        String sql = "insert into first_level_divisions (Division, Country_ID) values (?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, divisionName);
        ps.setInt(2, countryId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Updates an existing division in the database associated with a specific country.
     *
     * @param divisionId   The ID of the division to update.
     * @param divisionName The updated name of the division.
     * @param countryId    The updated ID of the country to which the division belongs.
     * @return The number of rows affected by the update operation.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static int update(int divisionId, String divisionName, int countryId) throws SQLException {
        String sql = "update first_level_divisions set Division = ?, Country_ID = ? where Division_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, divisionName);
        ps.setInt(2, countryId);
        ps.setInt(3, divisionId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Deletes a division from the database.
     *
     * @param divisionId The ID of the division to delete.
     * @return The number of rows affected by the delete operation.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static int delete(int divisionId) throws SQLException {
        String sql = "delete from first_level_divisions where Division_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, divisionId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Retrieves a list of all divisions from the database.
     *
     * @return An ObservableList containing all the divisions retrieved from the database.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static ObservableList<FirstLevelDivision> select() throws SQLException {
        ObservableList<FirstLevelDivision> divisionList = FXCollections.observableArrayList();
        String sql = "select * from first_level_divisions";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int divsionId = rs.getInt("Division_ID");
            String countryName = rs.getString("Division");
            int countryIdFK = rs.getInt("Country_ID");
//        System.out.print(countryId + " | ");
//        System.out.print(countryName+"\n");
            FirstLevelDivision division = new FirstLevelDivision(divsionId, countryName, countryIdFK);
           // System.out.println(division);
            divisionList.add(division);
           // System.out.println(divisionList.size());
        }
        return divisionList;
    }

    /**
     * Retrieves a list of divisions associated with a specific country from the database.
     *
     * @param countryId The ID of the country for which divisions are to be retrieved.
     * @return An ObservableList containing divisions associated with the specified country.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static ObservableList<FirstLevelDivision> select(int countryId) throws SQLException {
        ObservableList<FirstLevelDivision> filteredDivisionList = FXCollections.observableArrayList();
        String sql = "select * from first_level_divisions where Country_ID=?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int divisionId = rs.getInt("Division_ID");
            String countryName = rs.getString("Division");
            int countryIdFK = rs.getInt("Country_ID");
            FirstLevelDivision division = new FirstLevelDivision(divisionId, countryName, countryIdFK);
            //System.out.println(division);
            filteredDivisionList.add(division);
//        System.out.print(countryId + " | ");
//        System.out.print(countryName+"\n");
        }
        return filteredDivisionList;
    }

    /**
     * Gets the ID of the country associated with a specific division.
     *
     * @param divisionId The ID of the division for which the associated country ID is to be retrieved.
     * @return The ID of the country associated with the specified division, or -1 if not found.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static int getCustomerCountryId(int divisionId) throws SQLException {
        int countryId = -1;
        ObservableList <FirstLevelDivision> allDivision = select();
        for(FirstLevelDivision division: allDivision){
            if(division.getDivisionId()==divisionId){
                countryId = division.getCountryId();
            }
        }
        return countryId;
    }

    /**
     * Retrieves a specific division from the database by its ID.
     *
     * @param divisionId The ID of the division to retrieve.
     * @return The FirstLevelDivision object with the specified ID, or null if not found.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static FirstLevelDivision getDivision(int divisionId) throws SQLException {
        FirstLevelDivision division = null;
        ObservableList <FirstLevelDivision> allDivision = select();
        for(FirstLevelDivision d: allDivision){
            if(d.getDivisionId()==divisionId){
                division = d;
            }
        }
        return division;

    }
}
