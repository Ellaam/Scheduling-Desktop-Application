package controller;

import DAO.*;
import helper.TimeUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Country;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * The `ReportViewController` class is responsible for managing the reporting view in the application.
 *
 * @author Elham Pazhakh
 *  JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public class ReportViewController implements Initializable {

    Stage stage;
    Parent scene;
    public Label firstReportResult;
    public TableView<Appointment> filteredAppTableView;
    public TableColumn<Appointment, Integer> appIdCol;
    public TableColumn<Appointment, String> titleCol;
    public TableColumn<Appointment, String> descriptionCol;
    public TableColumn<Appointment, String> typeCol;
    public TableColumn<Appointment, LocalDateTime> startCol;
    public TableColumn<Appointment, LocalDateTime> endCol;
    public TableColumn<Appointment, Integer> customerIdCol;
    public ComboBox<Contact> pickContactCombo;
    public TableColumn<Appointment, String> locationCol;
    public Label secondReportResult;
    public ComboBox<String> appTypeReportOneCombo;
    public ComboBox<String> monthReportOneCombo;
    public Label numAppsReportOne;
    private Contact selectedContact;
    public TextField customerNameTxt;
    public TextField customerAddressTxt;
    public TextField customerPostalTxt;
    public TextField customerPhoneTxt;
    public TextField customerIdTxt;
    public ComboBox<Country> customerCountryCombo;
    public ComboBox<FirstLevelDivision> customerDivisionCombo;
    public Country selectedCountry;

    /**
     * Sets the available options for the "Type" ComboBox in the first report.
     *
     * @throws SQLException If a database error occurs during data retrieval.
     */
    public void setAppTypeReportOneCombo() throws SQLException {
        appTypeReportOneCombo.setPromptText("Type");
        ObservableList<String> types = FXCollections.observableArrayList();
        ObservableList<Appointment> appointments = DBAppointments.select();
        for (Appointment app: appointments) {
            types.add(app.getType());
        }
        appTypeReportOneCombo.setItems(types);
    }

    /**
     * Sets the available options for the "Month" ComboBox in the first report.
     */
    public void setMonthReportOneCombo() {
        monthReportOneCombo.setPromptText("Month");
        ObservableList<String> months = FXCollections.observableArrayList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );
        monthReportOneCombo.setItems(months);
    }

    /**
     * Handles the action to navigate back to the main customer and appointment view.
     *
     * @param event The action event triggered by the button click.
     * @throws IOException If an error occurs during navigation.
     */
    public void OnActionBackBtn(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerAndAppointmentView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Handles the action to view the first report's result and display it.
     *
     * This method retrieves the selected month and appointment type from ComboBoxes in the user interface.
     * It then queries the database to find matching appointments based on the selected month and type.
     * The lambda expression filters appointments based on their type and counts the matching appointments.
     *
     * @param event The action event triggered by the button click.
     */
    public void OnActionViewFirstReportResult(ActionEvent event) {

        String selectedMonth = monthReportOneCombo.getSelectionModel().getSelectedItem();
        String selectedType = appTypeReportOneCombo.getSelectionModel().getSelectedItem();

        if (selectedMonth != null && selectedType != null) {
            try {
                ObservableList<Appointment> matchingAppointments = DBAppointments.getAppointmentsByMonth(selectedMonth);
                long appointmentCount = matchingAppointments.stream()
                        .filter(appointment -> appointment.getType().equals(selectedType))
                        .count();

                firstReportResult.setText("Total " + selectedType + " Appointments in " + selectedMonth + ": " + appointmentCount);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the available options for the "Contact" ComboBox.
     *
     * @throws SQLException If a database error occurs during data retrieval.
     */
    public void setContactCombo() throws SQLException {
        pickContactCombo.setPromptText("Contact");
        ObservableList<Contact> contacts = DBContacts.select();
        pickContactCombo.setItems(contacts);
        pickContactCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedContact = newValue;
            }
        });
    }

    /**
     * Handles the action to set the filtered appointment table view based on the selected contact.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    public void OnActionSetTableView(ActionEvent actionEvent) {
        selectedContact = pickContactCombo.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            try {
                ObservableList<Appointment> appointments = DBAppointments.select(DBContacts.getContactId(String.valueOf(selectedContact)));
                filteredAppTableView.setItems(appointments);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Sets the available options for the "Country" ComboBox.
     *
     * @throws SQLException If a database error occurs during data retrieval.
     */
    public void setCustomerCountryCombo() throws SQLException {
        customerCountryCombo.setPromptText("Pick a country");
        ObservableList<Country> countries = DBCountries.select();
        customerCountryCombo.setItems(countries);
        customerCountryCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedCountry = newValue;
                try {
                    ObservableList<FirstLevelDivision> divisions = DBFirstLevelDivisions.select(selectedCountry.getCountryId());
                    customerDivisionCombo.setItems(divisions);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Handles the action to view the second report's result and display it.
     *
     * This method retrieves the selected division from a ComboBox in the user interface.
     * It then queries the database to count the number of customers belonging to the selected division.
     * The lambda expression filters the list of customers to include only those with the selected division ID and counts them.
     *
     * @param event The action event triggered by the button click.
     * @throws SQLException If a database error occurs during data retrieval.
     */
    public void OnActionViewSecondReportResult(ActionEvent event) throws SQLException {
        FirstLevelDivision division = customerDivisionCombo.getSelectionModel().getSelectedItem();
        long count = DBCustomers.select()
                .stream()
                .filter(customer -> customer.getDivisionId() == division.getDivisionId())
                .count();
        secondReportResult.setText("Total Customers: " + count);
    }

    /**
     * Initializes the controller and sets up the initial state of the user interface.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMonthReportOneCombo();
        try {
            setCustomerCountryCombo();
            setAppTypeReportOneCombo();
            setContactCombo();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        appIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(
                TimeUtils.toSystemDefault(cellData.getValue().getStart())
        ));

        endCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(
                TimeUtils.toSystemDefault(cellData.getValue().getEnd())
        ));

        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

    }
}
