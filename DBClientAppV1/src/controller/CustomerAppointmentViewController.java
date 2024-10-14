package controller;

import DAO.DBAppointments;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * The CustomerAppointmentViewController class is responsible for managing the customer and appointment view in the application.
 * It provides methods for initializing the user interface, handling user interactions, and displaying customer and appointment data.
 *
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public class CustomerAppointmentViewController implements Initializable {

    Stage stage;
    Parent scene;
    /**
     * Radio button for the default appointment view.
     */
    public RadioButton appDefault;
    /**
     * Toggle group for appointment view selection.
     */
    public ToggleGroup appView;
    /**
     * Radio button for the week appointment view.
     */
    public RadioButton appWeek;
    /**
     * Radio button for the month appointment view.
     */
    public RadioButton appMonth;
    @FXML
    private TableColumn<Customer, Integer> customerId;
    @FXML
    private TableColumn<Customer, String> customerName;
    @FXML
    private TableColumn<Customer, String> address;
    @FXML
    private TableColumn<Customer, String> postalCode;
    @FXML
    private TableColumn<Customer, String> phone;
    @FXML
    private TableColumn<Customer, Integer> divisionId;
    @FXML
    private TableColumn<Appointment, Integer> appIdCol;
    @FXML
    private TableColumn<Appointment, String>  titleCol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<Appointment, String> locationCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;
    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;
    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;
    @FXML
    private TableColumn<Appointment, Integer> userIdCol;
    @FXML
    private TableColumn<Appointment, Integer> contactIdCol;
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableView<Appointment> appTableView;

    private ObservableList<Appointment> allAppointments;
    private ObservableList<Appointment> filteredAppointments;
    LoginViewController loginController = new LoginViewController();
    private String userName;

    /**
     * Handles the "Log Out" action, navigating to the report view.
     *
     * @param event The action event triggered by the button click.
     * @throws IOException If an error occurs during navigation.
     */
    public void OnActionViewReports (ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Handles the action of exiting the program.
     *
     * @param event The event triggering the action.
     */
    public void OnActionLogOut(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Handles the "Add Customer" action, navigating to the add customer view.
     *
     * @param event The action event triggered by the button click.
     * @throws IOException If an error occurs during navigation.
     */
    @FXML
    public void OnActionAddCustomer(ActionEvent event)  throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomerView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Handles the "Update Customer" action, navigating to the update customer view.
     *
     * @param event The action event triggered by the button click.
     * @throws IOException If an error occurs during navigation.
     * @throws SQLException If a database error occurs during customer retrieval.
     */
    public void OnActionUpdateCustomer(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateCustomerView.fxml"));
        Parent root = loader.load();

        UpdateCustomerViewController modifyController = loader.getController();

        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer==null) {
            Alerts.showSaveConfirmation("Please select a customer.");
        } else {

            modifyController.setCustomer(selectedCustomer);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     * Handles the "Delete Customer" action, deleting a customer and associated appointments.
     *
     * @param actionEvent The action event triggered by the button click.
     * @throws SQLException If a database error occurs during customer or appointment deletion.
     */
    public void OnActionDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer==null) {
            Alerts.showSaveConfirmation("Please select an customer.");
        } else {
            ObservableList<Appointment> Appointments = DBAppointments.select();
            ObservableList<Appointment> associatedAppointments = FXCollections.observableArrayList();
            for(Appointment app: Appointments) {
                if (app.getCustomerId() == selectedCustomer.getCustomerId()) {
                    associatedAppointments.add(app);
                }
            }

            if (associatedAppointments.size()>0){
                    Alerts.showSaveConfirmation("Deleted customer's appointments first.");
            }else {
                DBCustomers.delete(selectedCustomer);
                Alerts.showSaveConfirmation("customer deleted successfully!");
                updateCustomerTableView();
            }
        }
    }

    /**
     * Handles the "Add Appointment" action, navigating to the add appointment view.
     *
     * @param event The action event triggered by the button click.
     * @throws IOException If an error occurs during navigation.
     */
    public void OnActionAddAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddAppointmentView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Handles the "Update Appointment" action, navigating to the update appointment view.
     *
     * @param event The action event triggered by the button click.
     * @throws IOException If an error occurs during navigation.
     * @throws SQLException If a database error occurs during appointment retrieval.
     */
    public void OnActionUpdateAppointment(ActionEvent event) throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateAppointmentView.fxml"));
        Parent root = loader.load();

        UpdateAppointmentViewController modifyController = loader.getController();

        Appointment selectedAppointment = appTableView.getSelectionModel().getSelectedItem();
        if(selectedAppointment==null) {
            Alerts.showSaveConfirmation("Please select an appointment.");
        } else {
            modifyController.setAppointment(selectedAppointment);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     * Handles the "Delete Appointment" action, deleting an appointment.
     *
     * @param actionEvent The action event triggered by the button click.
     * @throws SQLException If a database error occurs during appointment deletion.
     */
    public void OnActionDeleteAppointment(ActionEvent actionEvent) throws SQLException {

        Appointment selectedAppointment = appTableView.getSelectionModel().getSelectedItem();
        if(selectedAppointment==null) {
            Alerts.showSaveConfirmation("Please select an appointment.");
        } else {
            DBAppointments.delete(selectedAppointment);
            Alerts.showSaveConfirmation("Appointment ID " + selectedAppointment.getId() + " of type " + selectedAppointment.getType() + " was deleted successfully!");

            filteredAppointments.remove(selectedAppointment);
            updateAppointmentTableView();

        }
    }

    /**
     * Updates the customer table view with the latest data from the database.
     */
    private void updateCustomerTableView() {

        try {
            ObservableList<Customer> customers = DBCustomers.select();
            customerTableView.setItems(customers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the appointment table view with the latest data from the database.
     */
    private void updateAppointmentTableView() throws SQLException {

        allAppointments = DBAppointments.select();
        appTableView.setItems(filteredAppointments);
    }

    /**
     * Handles the "Default" view selection, displaying all appointments.
     *
     * @param actionEvent The action event triggered by the radio button selection.
     */
    public void OnActionDefault(ActionEvent actionEvent) {

        filteredAppointments.setAll(allAppointments);

    }

    /**
     * Handles the "Week" view selection, filtering appointments for the current week (Monday to Sunday).
     *
     * Lambda Expression Explanation:
     * In this method, a lambda expression is used to filter the list of appointments for the current week.
     * The lambda expression defines the filtering criteria based on the start date of each appointment.
     * It checks if the appointment's start date is after the start of the current week (Monday) minus one day and
     * before the end of the current week (Sunday) plus one day, effectively filtering appointments for the entire week.
     *
     * @param actionEvent The action event triggered by the radio button selection.
     */
    public void OnActionWeek(ActionEvent actionEvent) {

        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = now.with(DayOfWeek.SUNDAY);

        ObservableList<Appointment> weekAppointments = allAppointments
                .stream()
                .filter(appointment ->
                        appointment.getStart().toLocalDate().isAfter(startOfWeek.minusDays(1)) &&
                                appointment.getStart().toLocalDate().isBefore(endOfWeek.plusDays(1))
                )
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        filteredAppointments.setAll(weekAppointments);
    }


    /**
     * Handles the "Month" view selection, filtering appointments for the current month.
     *
     * Lambda Expression Explanation:
     * In this method, a lambda expression is used to filter the list of appointments for the current month.
     * The lambda expression defines the filtering criteria based on the start date of each appointment.
     * It checks if the appointment's start date corresponds to the same month as the current month.
     * This effectively filters appointments for the entire current month.
     *
     * @param actionEvent The action event triggered by the radio button selection.
     * @throws SQLException If a database error occurs during appointment retrieval.
     */
    public void OnActionMonth(ActionEvent actionEvent) throws SQLException {

        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(now);

        ObservableList<Appointment> monthAppointments = allAppointments
                .stream()
                .filter(appointment ->
                        YearMonth.from(appointment.getStart().toLocalDate()).equals(currentMonth)
                )
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        filteredAppointments.setAll(monthAppointments);

    }


    public void setUserName(String userName) {

        this.userName = userName;
    }

    /**
     * This method checks for upcoming appointments associated with the current user, where the time until the appointment
     * is 15 minutes or less. If any such appointments are found, a confirmation dialog is shown to the user with details
     * about the appointment. If there are no upcoming appointments within 15 minutes, a confirmation message is displayed.
     *
     * @throws SQLException If a database error occurs during data retrieval.
     */
    void alertUserAppointment() throws SQLException {
        String userName = this.userName;

        ObservableList<User> users = DBUsers.select();
        int userId = -1;
        for (User u : users) {
            if (u.getUserName().equals(userName)) {
                userId = u.getUserId();
            }
        }
        if (userId != -1) {
            ObservableList<Appointment> apps = DBAppointments.getAppointmentsByUserId(userId);
            boolean upcomingAppointments = false;

            for (Appointment app : apps) {
                LocalDateTime appointmentStartTime = app.getStart();

                LocalDateTime currentTime = LocalDateTime.now();
                long minutesUntilAppointment = ChronoUnit.MINUTES.between(currentTime, appointmentStartTime);

                if (minutesUntilAppointment >= 0 && minutesUntilAppointment <= 15) {
                    Alerts.showSaveConfirmation(
                            "Appointment ID " + app.getId() + " at " + appointmentStartTime + " is within 15 minutes."
                    );
                    upcomingAppointments = true;
                }
            }

            if (!upcomingAppointments) {
                Alerts.showSaveConfirmation("There are no upcoming appointments within 15 minutes.");
            }
        }
    }

    /**
     * Initializes the controller and sets up the initial state of the user interface.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<Customer> customers = DBCustomers.select();
            customerTableView.setItems(customers);

            customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            address.setCellValueFactory(new PropertyValueFactory<>("address"));
            postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            divisionId.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            allAppointments = DBAppointments.select();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        filteredAppointments = FXCollections.observableArrayList(allAppointments);
        appTableView.setItems(filteredAppointments);

        appIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));

        try {
            alertUserAppointment();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
