package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The DBUsers class provides methods for interacting with the users database table.
 * It allows for selecting and retrieving user records.
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public abstract class DBUsers {

    /**
     * Retrieves a list of all user records from the database.
     *
     * @return An ObservableList containing all the user records retrieved from the database.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static ObservableList<User> select() throws SQLException {
        ObservableList<User> userList = FXCollections.observableArrayList();
        String sql = "select * from users";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String pass = rs.getString("Password");
            Timestamp createdDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            LocalDateTime createdDateTime = (createdDate != null) ? createdDate.toLocalDateTime() : null;
            LocalDateTime lastUpdateDateTime = (lastUpdate != null) ? lastUpdate.toLocalDateTime() : null;
//        System.out.print(countryId + " | ");
//        System.out.print(countryName+"\n");
            User user = new User(userId, userName, pass, createdDateTime, createdBy, lastUpdateDateTime, lastUpdatedBy);
            //System.out.println(user);
            userList.add(user);
           // System.out.println(userList.size());
        }
        return userList;
    }

    /**
     * Retrieves a specific user by their ID from the database.
     *
     * @param userId The ID of the user to retrieve.
     * @return The User object with the specified ID, or null if not found.
     * @throws SQLException If an SQL exception occurs while accessing the database.
     */
    public static User getUserById(int userId) throws SQLException {
        User user = null;
        ObservableList<User> users = select();
        for(User u: users){
            if(u.getUserId() ==userId){
                user = u;
            }
        }
        return user;
    }

    public static int insert() {
        return 0;
    }

    public int update() {
        return 0;
    }

    public int delete() {
        return 0;
    }
}
