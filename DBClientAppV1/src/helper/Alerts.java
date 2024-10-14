package helper;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.*;

import java.time.LocalDateTime;


/**
 * The Alerts class provides static methods for displaying alert dialogs in JavaFX applications.
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public class Alerts {

    /**
     * Displays an information alert dialog with the specified message.
     *
     * @param message The message to be displayed in the alert.
     */
    public static void showSaveConfirmation(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static boolean appointmentTimingError(LocalDateTime start, LocalDateTime end){
        boolean works = true;
        if(start.isEqual(end)||start.isAfter(end)){
            showSaveConfirmation("Start cannot be after or same as end.");
            works = false;
        }
        return works;
    }


    public static boolean customerErrorHandling(String name, String address, String postalCode, String phone, Country selectedCountry, FirstLevelDivision selectedDivision) {
        boolean works = true;
        StringBuilder errorMessage = new StringBuilder();

        if (name.isEmpty()) {
            errorMessage.append("Name cannot be empty.\n");
            works = false;
        }

        if (address.isEmpty()) {
            errorMessage.append("Address cannot be empty.\n");
            works = false;
        }

        if (postalCode.isEmpty()) {
            errorMessage.append("Postal code cannot be empty.\n");
            works = false;
        }

        if (phone.isEmpty()) {
            errorMessage.append("Phone cannot be empty.\n");
            works = false;
        }

        if (selectedCountry == null) {
            errorMessage.append("Please select a country.\n");
            works = false;
        }

        if (selectedDivision == null) {
            errorMessage.append("Please select a division.\n");
            works = false;
        }

        if (!works) {
            showSaveConfirmation(errorMessage.toString());
        }

        return works;
    }

    public static boolean AppointmentErrorHandling(String title, String description, String location, String type) {


        StringBuilder errorMessage = new StringBuilder();
        boolean works = true;

        if (title.isEmpty()) {
            errorMessage.append("Title cannot be empty.\n");

        }

        if (description.isEmpty()) {
            errorMessage.append("Description cannot be empty.\n");

        }

        if (type.isEmpty()) {
            errorMessage.append("Type code cannot be empty.\n");

        }

        if (location.isEmpty()) {
            errorMessage.append("Location cannot be empty.\n");

        }

        if (errorMessage.length() > 0) {
            Alerts.showSaveConfirmation(errorMessage.toString());
            works = false;
        }

        System.out.println("Error: " + errorMessage);
        return works;
    }

}
