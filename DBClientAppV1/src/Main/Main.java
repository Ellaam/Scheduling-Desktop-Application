package Main;

import DAO.DBCountries;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * The Main class is the entry point of the application and contains the `main` method to start the program.
 *
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public class Main extends Application {

    /**
     * The start method initializes the JavaFX application, loads the login view, and displays it.
     *
     * @param primaryStage The primary stage for the JavaFX application.
     * @throws Exception If an error occurs during the initialization of the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
        primaryStage.setTitle("First Screen");
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();
    }

    /**
     * The main method is the entry point of the application.
     * It initializes the database connection, launches the JavaFX application, retrieves countries' data,
     * and closes the database connection after the application is closed.
     *
     * @param args Command-line arguments.
     * @throws SQLException If a database error occurs during data retrieval or closing the connection.
     */
    public static void main(String[] args) throws SQLException {

//      Locale.setDefault(new Locale("fr", "FR"));
        JDBC.openConnection();
        launch(args);
        DBCountries.select();
        JDBC.closeConnection();
    }
}