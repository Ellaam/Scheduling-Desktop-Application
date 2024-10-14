package controller;

import DAO.DBCountries;
import DAO.DBCustomers;
import DAO.DBFirstLevelDivisions;
import helper.Alerts;
import helper.TimeUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static DAO.DBFirstLevelDivisions.getCustomerCountryId;


/**
 * The UpdateCustomerViewController class is responsible for managing the updating of customer information in the application.
 *
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public class UpdateCustomerViewController implements Initializable {

    Stage stage;
    Parent scene;
    @FXML
    public TextField customerIdTxt;
    @FXML
    public TextField customerNameTxt;
    @FXML
    public TextField customerAddressTxt;
    @FXML
    public TextField customerPostalTxt;
    @FXML
    public TextField customerPhoneTxt;
    @FXML
    public ComboBox<Country> customerCountryCombo;
    @FXML
    public ComboBox<FirstLevelDivision> customerDivisionCombo;
    private Customer customer;
    public Country selectedCountry;

    /**
     * Sets the provided customer's data in the form fields for updating.
     *
     * @param selectedCustomer The customer to be updated.
     * @throws SQLException If a database error occurs during data retrieval.
     */
    public void setCustomer(Customer selectedCustomer) throws SQLException {

        this.customer = selectedCustomer;
        if(selectedCustomer==null){
            return;
        }else {
            customerIdTxt.setText(String.valueOf(customer.getCustomerId()));
            customerNameTxt.setText(customer.getCustomerName());
            customerAddressTxt.setText(customer.getAddress());
            customerPostalTxt.setText(customer.getPostalCode());
            customerPhoneTxt.setText(customer.getPhone());
            int divisionId = customer.getDivisionId();
            int countryId = getCustomerCountryId(divisionId);
            customerCountryCombo.setValue(DBCountries.getCountry(countryId));
            customerDivisionCombo.setValue(DBFirstLevelDivisions.getDivision(selectedCustomer.getDivisionId()));
        }
    }

    /**
     * Handles the action to save the updated customer information to the database.
     *
     * @param event The action event triggered by the button click.
     * @throws SQLException If a database error occurs during the update.
     */
    public void OnActionSaveCustomer(ActionEvent event) throws SQLException, IOException {
        String customerName = customerNameTxt.getText();
        String address = customerAddressTxt.getText();
        String postalCode = customerPostalTxt.getText();
        String phone = customerPhoneTxt.getText();

        Country selectedCountry = customerCountryCombo.getSelectionModel().getSelectedItem();
        FirstLevelDivision selectedDivision = customerDivisionCombo.getSelectionModel().getSelectedItem();

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime lastUpdateUTC = TimeUtils.toUTC(currentTime);

        this.customer.setCustomerName(customerName);
        this.customer.setAddress(address);
        this.customer.setPostalCode(postalCode);
        this.customer.setPhone(phone);
        this.customer.setDivisionId(selectedDivision.getDivisionId());
        this.customer.setLastUpdate(LocalDateTime.now());

        if(Alerts.customerErrorHandling(customerName, address, postalCode, phone, selectedCountry, selectedDivision)) {
            DBCustomers.update(customer);
            Alerts.showSaveConfirmation("Customer updated successfully!");

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/CustomerAndAppointmentView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Handles the action to cancel the customer update and returns to the main customer and appointment view.
     *
     * @param event The action event triggered by the button click.
     * @throws IOException If an error occurs during navigation.
     */
    public void OnActionCancelCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerAndAppointmentView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Handles the action to navigate back to the main customer and appointment view.
     *
     * @param event The action event triggered by the button click.
     * @throws IOException If an error occurs during navigation.
     */
    public void OnActionBackCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerAndAppointmentView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Sets the available options for the "Country" ComboBox when updating a customer's information.
     *
     * This method initializes the "Country" ComboBox by populating it with a list of countries retrieved from the database.
     * It also sets a prompt text for the ComboBox. When a country is selected, the lambda expression within the listener
     * responds to the selection event by updating the available options in the "First-Level Division" ComboBox based on the selected country.
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
     * Initializes the controller and sets up the initial state of the user interface.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setCustomerCountryCombo();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
