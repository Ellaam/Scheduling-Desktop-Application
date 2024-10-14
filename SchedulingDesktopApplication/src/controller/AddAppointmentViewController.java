package controller;

import DAO.DBAppointments;
import DAO.DBContacts;
import DAO.DBCustomers;
import DAO.DBUsers;
import helper.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The AppointmentController class is responsible for managing the appointment creation and modification process
 * in the application's user interface. It provides methods for setting up and controlling various UI elements,
 * handling user interactions, and saving appointment information to the database.
 *
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public class AddAppointmentViewController implements Initializable {

    Stage stage;
    Parent scene;
    public ComboBox<LocalTime> startCombo;
    public ComboBox<LocalTime> endCombo;
    @FXML
    private ComboBox<Contact> contactCombo;
    @FXML
    private ComboBox<User> UserCombo;
    @FXML
    private ComboBox<Customer> customerCombo;
    @FXML
    private TextField appointmentIdTxt;
    @FXML
    private TextField titleTxt;
    @FXML
    private TextField descriptionTxt;
    @FXML
    private TextField locationTxt;
    @FXML
    private TextField typeTxt;
    @FXML
    private DatePicker datePicker;


    /**
     * Initializes the contact combo box in the UI, populating it with contact data from the database.
     *
     * @throws SQLException If a database error occurs during contact data retrieval.
     */
    public void setContactCombo() throws SQLException {
        contactCombo.setPromptText("Pick a contact");
        ObservableList<Contact> contacts = DBContacts.select();
        contactCombo.setItems(contacts);
        contactCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Contact selectedContact = newValue;
            }
        });
    }

    /**
     * Initializes the user combo box in the UI, populating it with user data from the database.
     *
     * @throws SQLException If a database error occurs during user data retrieval.
     */
    public void setUserCombo() throws SQLException {
        UserCombo.setPromptText("Pick a user");
        ObservableList<User> users = DBUsers.select();
        UserCombo.setItems(users);
        UserCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                User selectedUser = newValue;
            }
        });
    }

    /**
     * Initializes the customer combo box in the UI, populating it with customer data from the database.
     *
     * @throws SQLException If a database error occurs during customer data retrieval.
     */
    public void setCustomerCombo() throws SQLException {
        customerCombo.setPromptText("Pick a customer");
        ObservableList<Customer> customers = DBCustomers.select();
        customerCombo.setItems(customers);
        customerCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Customer selectedCustomer = newValue;
            }
        });
    }

    /**
     * Initializes the start time combo box in the UI, providing a list of available appointment start times
     * within business hours.
     */
    public void setStartCombo() {
        startCombo.setPromptText("Pick appointment's start");

        ZoneId userTimeZone = ZoneId.systemDefault();
        ZoneId estZone = ZoneId.of("America/New_York");

        ZonedDateTime startEst = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), estZone);
        ZonedDateTime endEst = ZonedDateTime.of(LocalDate.now(), LocalTime.of(21, 30), estZone);

        ZonedDateTime startUserTime = startEst.withZoneSameInstant(userTimeZone);
        ZonedDateTime endUserTime = endEst.withZoneSameInstant(userTimeZone);

        LocalTime businessHoursStartUser = startUserTime.toLocalTime();
        LocalTime businessHoursEndUser = endUserTime.toLocalTime();

        List<LocalTime> availableHours = new ArrayList<>();
        LocalTime currentHour = businessHoursStartUser;

        while (true) {
            availableHours.add(currentHour);
            if (currentHour.equals(businessHoursEndUser)) {
                break;
            }
            currentHour = currentHour.plusMinutes(30);

            if (currentHour.equals(LocalTime.MIDNIGHT)) {
                currentHour = LocalTime.of(0, 0);
            }
        }

        ObservableList<LocalTime> hours = FXCollections.observableArrayList(availableHours);
         startCombo.setItems(hours);
    }


    /**
     * Initializes the end time combo box in the UI, providing a list of available appointment end times
     * within business hours.
     */
    public void setEndCombo(){
        endCombo.setPromptText("Pick appointment's end");


        ZoneId userTimeZone = ZoneId.systemDefault();
        ZoneId estZone = ZoneId.of("America/New_York");

        ZonedDateTime startEst = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 30), estZone);
        ZonedDateTime endEst = ZonedDateTime.of(LocalDate.now(), LocalTime.of(22, 0), estZone);

        ZonedDateTime startUserTime = startEst.withZoneSameInstant(userTimeZone);
        ZonedDateTime endUserTime = endEst.withZoneSameInstant(userTimeZone);

        LocalTime businessHoursStartUser = startUserTime.toLocalTime();
        LocalTime businessHoursEndUser = endUserTime.toLocalTime();

        List<LocalTime> availableHours = new ArrayList<>();
        LocalTime currentHour = businessHoursStartUser;

        while (true) {
            availableHours.add(currentHour);
            if (currentHour.equals(businessHoursEndUser)) {
                break;
            }
            currentHour = currentHour.plusMinutes(30);

            if (currentHour.equals(LocalTime.MIDNIGHT)) {
                currentHour = LocalTime.of(0, 0);
            }
        }

        ObservableList<LocalTime> hours = FXCollections.observableArrayList(availableHours);
        endCombo.setItems(hours);
    }

    /**
     * Handles the "Save Appointment" button action, validating user input and saving the appointment to the database.
     *
     * @param event The action event triggered by the button click.
     * @throws SQLException If a database error occurs during the appointment insertion.
     */
    public void OnActionSaveAppointment(ActionEvent event) throws SQLException, IOException {

        Contact selectedContact = contactCombo.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            Alerts.showSaveConfirmation("Please select a contact.");
            return;
        }

        User selectedUser = UserCombo.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            Alerts.showSaveConfirmation("Please select a User.");
            return;
        }

        Customer selectedCustomer = customerCombo.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alerts.showSaveConfirmation("Please select a Customer.");
            return;
        }

        LocalDate selectedDate = datePicker.getValue();
        if (datePicker.getValue() == null) {
            Alerts.showSaveConfirmation("Please select a date.");
            return;
        }

        LocalTime startTime = startCombo.getValue();
        if (startTime == null) {
            Alerts.showSaveConfirmation("Please select the start time.");
            return;
        }

        LocalTime endTime = endCombo.getValue();
        if (endTime == null) {
            Alerts.showSaveConfirmation("Please select the end time.");
            return;
        }

        LocalDateTime start = LocalDateTime.of(selectedDate, startTime);
        LocalDateTime end = LocalDateTime.of(selectedDate, endTime);

        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String type = typeTxt.getText();
        if (appointmentOverlap(start, end, selectedCustomer.getCustomerId())) {
            Alerts.showSaveConfirmation("Time slot clash! Please choose a different time.");
        } else if (Alerts.AppointmentErrorHandling(title, description, location, type) && Alerts.appointmentTimingError(start, end)){
            Appointment appointment = new Appointment(0, title, description, location, type, start, end, LocalDateTime.now(), "script", LocalDateTime.now(), "script", selectedCustomer.getCustomerId(), selectedUser.getUserId(), selectedContact.getContactId());
            DBAppointments.insert(appointment);
            Alerts.showSaveConfirmation("Appointment added successfully!");
            //clearFields();
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/CustomerAndAppointmentView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * Handles the "Cancel" button action, clearing input fields and navigating back to the previous screen.
     *
     * @param event The action event triggered by the button click.
     * @throws IOException If an error occurs during navigation.
     */
    public void OnActionCancelAppointment(ActionEvent event) throws IOException {
        clearFields();
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerAndAppointmentView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Handles the "Back" button action, navigating back to the previous screen.
     *
     * @param event The action event triggered by the button click.
     * @throws IOException If an error occurs during navigation.
     */
    public void OnActionBackAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerAndAppointmentView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Clears input fields and resets combo boxes to their default states.
     */
    public void clearFields(){
        titleTxt.clear();
        descriptionTxt.clear();
        locationTxt.clear();
        typeTxt.clear();
        contactCombo.getSelectionModel().clearSelection();
        UserCombo.getSelectionModel().clearSelection();
        startCombo.getSelectionModel().clearSelection();
        endCombo.getSelectionModel().clearSelection();
        datePicker.setValue(null);
    }

    /**
     * Checks if a new appointment overlaps with existing appointments for a specific customer.
     *
     * @param start      The start time of the new appointment.
     * @param end        The end time of the new appointment.
     * @param customerId The ID of the customer for whom to check appointment overlaps.
     * @return True if the new appointment overlaps with any existing appointments for the customer, otherwise false.
     * @throws SQLException If a database error occurs during data retrieval.
     */

    public static Boolean appointmentOverlap(LocalDateTime start, LocalDateTime end, int customerId) throws SQLException {
        boolean overlap = false;
        ObservableList<Appointment> appointments = DBAppointments.selectByCustomerId(customerId);

        for (Appointment app : appointments) {
            LocalDateTime appStart = app.getStart();
            LocalDateTime appEnd = app.getEnd();

            if ((start.isEqual(appStart) || end.isEqual(appEnd)) ||
                    (start.isAfter(appStart) && start.isBefore(appEnd)) ||
                    (end.isAfter(appStart) && end.isBefore(appEnd)) ||
                    (start.isBefore(appStart) && end.isAfter(appEnd)))
            {
                overlap = true;
                break;
            }
        }

        return overlap;
    }


    /**
     * Initializes the controller, setting up various UI components and populating combo boxes with data.
     *
     * @param url            The location used to resolve relative paths.
     * @param resourceBundle The resource bundle containing localized strings.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setContactCombo();
            setUserCombo();
            setCustomerCombo();
            setStartCombo();
            setEndCombo();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
