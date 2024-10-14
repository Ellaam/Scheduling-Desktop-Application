package controller;

import DAO.DBUsers;
import helper.ActivityLogger;
import helper.Alerts;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class controls the login functionality and user interface for the application.
 * It allows users to log in, verifies their credentials, and logs login attempts.
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public class LoginViewController implements Initializable {


    public Button loginButton;
    public Label usernameLabel;
    public Label passwordLabel;
    Stage stage;
    Parent scene;
    public PasswordField loginPassword;
    @FXML
    public TextField loginUserName;
    public Label loginTimeZone;
    private ActivityLogger activityLogger;
    private ResourceBundle bundle;


    /**
     * Displays the user's time zone in the user interface.
     * The time zone is determined by the system's default time zone.
     */
    public void setUserTimeZone(){

        ZoneId userZone = ZoneId.systemDefault();
        ZonedDateTime zdt = ZonedDateTime.now(userZone);
        loginTimeZone.setText(userZone.toString());
    }


    /**
     * Retrieves a ResourceBundle containing language-specific translations based on the user's locale.
     *
     * This method determines the user's language and country from their system's default locale, and then constructs
     * a specific Locale object for that language and country. It uses this Locale to load an appropriate ResourceBundle
     * that contains translations for the user interface elements, such as labels, error messages, and button texts.
     *
     * @return A ResourceBundle containing translations based on the user's locale.
     */
    private ResourceBundle getResourceBundle() {
        Locale userLocale = Locale.getDefault();
        String language = userLocale.getLanguage();
        String country = userLocale.getCountry();
        String bundleName;

        if (language.equals("fr") && country.equals("FR")) {
            bundleName = "LoginBundle_fr_FR";
        } else {
            bundleName = "LoginDefaultBundle";
        }

        return ResourceBundle.getBundle("languages/" + bundleName, userLocale);
    }

    /**
     * Updates the user interface elements with localized text retrieved from the ResourceBundle.
     *
     * This method fetches localized text values from the ResourceBundle and uses them to update various user interface
     * elements such as labels, buttons, and error messages. It ensures that the text displayed to the user is in their
     * preferred language, as determined by the application's ResourceBundle.
     */
    private void updateUIElements() {
        String loginButtonLabel = bundle.getString("loginButtonLabel");
        String userNameLabel = bundle.getString("usernameLabel");
        String passWordLabel = bundle.getString("passwordLabel");

        loginButton.setText(loginButtonLabel);
        usernameLabel.setText(userNameLabel);
        passwordLabel.setText(passWordLabel);

    }


    /**
     * Handles the "Login" action when the user attempts to log in.
     *
     * @param event The action event triggered by the login button.
     * @throws SQLException If a database error occurs during user retrieval.
     * @throws IOException If an error occurs during navigation.
     */
    public void OnActionLogin(ActionEvent event) throws SQLException, IOException {

        String userName = loginUserName.getText();
        String passWord = loginPassword.getText();

        ObservableList<User> users = DBUsers.select();
        boolean loginSuccessful = false;

        for(User user: users){
            if(user.getUserName().equals(userName) && user.getPassword().equals(passWord)){
                loginSuccessful = true;
                log(userName, true);
                navigateToNextView(userName, event);
            }
        }
        if (!loginSuccessful) {
            log(userName, false);
            String invalidCredentialsError = bundle.getString("wrongUsernameOrPasswordMessage");
            Alerts.showSaveConfirmation(invalidCredentialsError);
        }
    }

    /**
     * Logs a login attempt, indicating whether it was successful or not.
     *
     * @param userName The username used for the login attempt.
     * @param isSuccess A boolean flag indicating if the login was successful.
     */
    private void log(String userName, boolean isSuccess) {
        String status = isSuccess ? "Successful" : "Failed";
        String logMessage = String.format("Login attempt by user '%s' at %s: %s", userName, getCurrentTimestamp(), status);
        activityLogger.log(logMessage, isSuccess);
    }

    private String getCurrentTimestamp() {
        ZoneId userZone = ZoneId.systemDefault();
        ZonedDateTime zdt = ZonedDateTime.now(userZone);
        return zdt.toString();
    }

    /**
     * Navigates to the next view and passes the username to the next view's controller.
     *
     * @param userName The username to pass to the next view.
     * @param event    The action event associated with the navigation.
     * @throws IOException If an error occurs during navigation.
     */
    private void navigateToNextView(String userName, ActionEvent event) throws IOException, SQLException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomerAndAppointmentView.fxml"));
        Parent root = loader.load();

        CustomerAppointmentViewController controller = loader.getController();
        controller.setUserName(userName);
        controller.alertUserAppointment();
        stage.setScene(new Scene(root));
        stage.show();
    }


    /**
     * Initializes the controller and sets up the initial state of the user interface.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        activityLogger = new ActivityLogger();
        setUserTimeZone();
        bundle = getResourceBundle();
        updateUIElements();
    }
}
