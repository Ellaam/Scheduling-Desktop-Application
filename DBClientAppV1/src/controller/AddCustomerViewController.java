package controller;

import DAO.DBCountries;
import DAO.DBCustomers;
import DAO.DBFirstLevelDivisions;
import helper.Alerts;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

/**
 * The AddCustomerViewController class is responsible for managing the customer creation process in the application's
 * user interface. It provides methods for setting up and controlling various UI elements, handling user interactions,
 * and saving customer information to the database.
 *
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public class AddCustomerViewController implements Initializable {
    Stage stage;
    Parent scene;
    /**
     * Text field for customer name input.
     */
    public TextField customerNameTxt;
    /**
     * Text field for customer address input.
     */
    public TextField customerAddressTxt;
    /**
     * Text field for customer postal code input.
     */
    public TextField customerPostalTxt;
    /**
     * Text field for customer phone number input.
     */
    public TextField customerPhoneTxt;
    /**
     * Text field for customer ID display.
     */
    public TextField customerIdTxt;
    /**
     * Combo box for selecting the customer's country.
     */
    public ComboBox<Country> customerCountryCombo;
    /**
     * Combo box for selecting the customer's first-level division (region).
     */
    public ComboBox<FirstLevelDivision> customerDivisionCombo;
    /**
     * The selected country from the customerCountryCombo.
     */
    public Country selectedCountry;


    /**
     * Initializes the customerCountryCombo in the UI, populating it with country data from the database. It also
     * handles the selection change event to dynamically load divisions based on the selected country.
     *
     * @throws SQLException If a database error occurs during country data retrieval.
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
     * Handles the "Save Customer" button action, validating user input and saving the customer to the database.
     *
     * @param event The action event triggered by the button click.
     * @throws SQLException If a database error occurs during customer insertion.
     */
    public void OnActionSaveCustomer(ActionEvent event) throws SQLException, IOException {

        Country selectedCountry = customerCountryCombo.getSelectionModel().getSelectedItem();
        FirstLevelDivision selectedDivision = customerDivisionCombo.getSelectionModel().getSelectedItem();

        String customerName = customerNameTxt.getText();
        String customerAddress = customerAddressTxt.getText();
        String customerPostal = customerPostalTxt.getText();
        String customerPhone = customerPhoneTxt.getText();

        if(Alerts.customerErrorHandling(customerName, customerAddress, customerPostal, customerPhone, selectedCountry, selectedDivision)) {
            Customer customer = new Customer(0, customerName, customerAddress, customerPostal, customerPhone, LocalDateTime.now(), "script", LocalDateTime.now(), "script", selectedDivision.getDivisionId());
            DBCustomers.insert(customer);
            Alerts.showSaveConfirmation("Customer added successfully!");
            clearFields();
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
    public void OnActionCancelCustomer(ActionEvent event) throws IOException {
        clearFields();
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerAndAppointmentView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Clears input fields and resets combo boxes to their default states.
     */
    public void clearFields(){
        customerNameTxt.clear();
        customerAddressTxt.clear();
        customerPostalTxt.clear();
        customerPhoneTxt.clear();
        customerCountryCombo.getSelectionModel().clearSelection();
        customerDivisionCombo.getSelectionModel().clearSelection();
    }

    /**
     * Handles the "Back" button action, navigating back to the previous screen.
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
     * Initializes the controller, setting up various UI components and populating combo boxes with data.
     *
     * @param url            The location used to resolve relative paths.
     * @param resourceBundle The resource bundle containing localized strings.
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
