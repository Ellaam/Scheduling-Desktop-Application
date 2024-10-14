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

import static DAO.DBContacts.getContactById;
import static DAO.DBCustomers.getCustomerById;
import static DAO.DBUsers.getUserById;

/**
 * Controller for the appointment update view. Handles updating existing appointments.
 *
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public class UpdateAppointmentViewController implements Initializable {

    Stage stage;
    Parent scene;
    public ComboBox<LocalTime> endCombo;
    public ComboBox<LocalTime> startCombo;
    public TextField titleTxt;
    public TextField descriptionTxt;
    public TextField locationTxt;
    public TextField typeTxt;
    public TextField appointmentIdTxt;
    @FXML
    private ComboBox<Contact> contactCombo;
    @FXML
    private ComboBox<User> UserCombo;
    @FXML
    private ComboBox<Customer> customerCombo;
    @FXML
    private DatePicker datePicker;
    private static Appointment appointment;
    private LocalDate selectedDate;


    /**
     * Sets the appointment to be updated and populates the fields with its details.
     *
     * @param selectedAppointment The appointment to be updated.
     * @throws SQLException If there is an issue accessing the database.
     */
    public void setAppointment(Appointment selectedAppointment) throws SQLException {

        appointment = selectedAppointment;

        if(selectedAppointment==null){
            return;
        }else {
            appointmentIdTxt.setText(String.valueOf(selectedAppointment.getId()));
            titleTxt.setText(selectedAppointment.getTitle());
            descriptionTxt.setText(selectedAppointment.getDescription());
            locationTxt.setText(selectedAppointment.getLocation());
            typeTxt.setText(selectedAppointment.getType());

            startCombo.setValue(selectedAppointment.getStart().toLocalTime());
            endCombo.setValue(selectedAppointment.getEnd().toLocalTime());
            datePicker.setValue(selectedAppointment.getStart().toLocalDate());

            contactCombo.setValue(getContactById(selectedAppointment.getContactId()));
            UserCombo.setValue(getUserById(selectedAppointment.getUserId()));
            customerCombo.setValue(getCustomerById(selectedAppointment.getCustomerId()));
            System.out.println("inside setAppointment: " + appointment);
        }
    }

    /**
     * Navigates back to the main appointment view.
     *
     * @param event The action event.
     * @throws IOException If there is an issue with loading the view.
     */
    public void OnActionBackAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerAndAppointmentView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Cancels the appointment update and navigates back to the main appointment view.
     *
     * @param event The action event.
     * @throws IOException If there is an issue with loading the view.
     */
    public void OnActionCancelAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerAndAppointmentView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     * Updates the appointment information in the database.
     *
     * @param event The action event.
     * @throws SQLException If there is an issue accessing the database.
     */
    public void OnActionSaveAppointment(ActionEvent event) throws SQLException, IOException {

        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String type = typeTxt.getText();

        Contact selectedContact = contactCombo.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            Alerts.showSaveConfirmation("Please select a contact.");
            return;
        }

        User selectedUser = UserCombo.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            Alerts.showSaveConfirmation("Please select a user.");
            return;
        }

        Customer selectedCustomer = customerCombo.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alerts.showSaveConfirmation("Please select a customer.");
            return;
        }

        selectedDate = datePicker.getValue();
        if (datePicker.getValue() == null) {
            Alerts.showSaveConfirmation("Please select a date.");
            return;
        }

        LocalTime startTime = startCombo.getValue();
        LocalTime endTime = endCombo.getValue();

        LocalDateTime start = LocalDateTime.of(selectedDate, startTime);
        LocalDateTime end = LocalDateTime.of(selectedDate, endTime);

        appointment.setTitle(title);
        appointment.setDescription(description);
        appointment.setLocation(location);
        appointment.setType(type);
        appointment.setContactId(selectedContact.getContactId());
        appointment.setUserId(selectedUser.getUserId());
        appointment.setCustomerId(selectedCustomer.getCustomerId());
        appointment.setStart(start);
        appointment.setEnd(end);
        appointment.setLastUpdated(LocalDateTime.now());

        if (updatedAppointmentOverlap(start, end, selectedCustomer.getCustomerId())) {
            Alerts.showSaveConfirmation("Time slot clash! Please choose a different time.");
        } else if(Alerts.AppointmentErrorHandling(title, description, location, type) && Alerts.appointmentTimingError(start, end)){

            DBAppointments.update(appointment);

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/CustomerAndAppointmentView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Initializes the combo box for selecting a contact.
     *
     * @throws SQLException If there is an issue accessing the database.
     */
    public void setContactCombo() throws SQLException {
        contactCombo.setPromptText("Pick a contact");
        ObservableList<Contact> contacts = DBContacts.select();
        contactCombo.setItems(contacts);
        contactCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Contact selectedContact = newValue;
                System.out.println("Selected country: " + selectedContact);
                System.out.println(selectedContact.getContactId());
            }
        });
    }

    /**
     * Initializes the combo box for selecting a user.
     *
     * @throws SQLException If there is an issue accessing the database.
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
     * Initializes the combo box for selecting a customer.
     *
     * @throws SQLException If there is an issue accessing the database.
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
     * Checks if an updated appointment overlaps with existing appointments for a specific customer. This method
     * is used when updating an existing appointment to ensure that the updated appointment does not overlap with
     * any other appointments for the same customer.
     *
     * @param start      The updated start time of the appointment.
     * @param end        The updated end time of the appointment.
     * @param customerId The ID of the customer for whom to check appointment overlaps.
     * @return True if the updated appointment overlaps with any existing appointments for the customer, otherwise false.
     * @throws SQLException If a database error occurs during data retrieval.
     */
    public static Boolean updatedAppointmentOverlap(LocalDateTime start, LocalDateTime end, int customerId) throws SQLException {
        boolean overlap = false;
        ObservableList<Appointment> appointments = DBAppointments.selectByCustomerId(customerId);
        appointments.remove(appointment);

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
     * Initializes the controller.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
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
