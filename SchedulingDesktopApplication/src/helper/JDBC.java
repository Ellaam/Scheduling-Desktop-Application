package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * The JDBC class provides methods for managing a JDBC database connection.
 *  @author Elham Pazhakh
 *  JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection = null;  // Connection Interface

    /**
     * Opens a database connection and returns the Connection object.
     *
     * @return The opened Connection object.
     */
    public static Connection openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Retrieves the current database connection.
     *
     * @return The current database Connection object.
     */
    public static Connection getConnection(){
        return connection;
    }

    /**
     * Closes the current database connection.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
