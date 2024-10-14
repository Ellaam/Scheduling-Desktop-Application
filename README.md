# Scheduling-Desktop-Application

## Objective

This desktop application allows a global consulting company to manage customer appointments across multiple time zones. It features user authentication, customer management, and scheduling, with support for English and French languages.



### Key Features

- **User Login & Localization**: Automatically adjusts to the user's time zone and language (English/French).
- **Customer & Appointment Management**: Add, update, and delete customer records and appointments.
- **Error Handling**: Alerts users to upcoming appointments and conflicts (e.g., overlapping or out-of-business hours).
- **Reports**: Generates monthly appointment statistics and detailed schedules for contacts.


### Skills Demonstrated

- **JavaFX for UI**: Built a user-friendly interface for managing customer and appointment data.
- **Java Date/Time API**: Handled time zones and appointment scheduling with localization.
- **MySQL Database Integration**: Connected to a MySQL database for persistent data storage.
- **Multi-language Support**: Implemented English and French language interfaces.
- **Error Handling**: Validated data input and managed exceptions to ensure smooth user experience.


### Technologies Used

- **Java 17**: Core development language.
- **JavaFX**: GUI framework for desktop applications.
- **MySQL**: Backend database.
- **IntelliJ IDEA**: Development environment.


### How to Run

1. Open the project in IntelliJ IDEA.
2. Ensure JDK 17.0.8 is installed:
   - Configure the project to use JDK 17.0.8 under `Project Structure`.
3. Add MySQL Connector and JavaFX libraries:
   - Use Maven or download manually and add them to the classpath.
4. Edit VM options:
   - Go to `Run` > `Edit Configurations` and add:
     ```
     --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
     ```
5. Set up JavaFX:
   - Ensure JavaFX is correctly set up in your project settings.
6. Build and run the project.


### Screenshots
1. Main Dashboard:
<img src="https://github.com/user-attachments/assets/2564ac49-5823-4af0-9722-3f2249725f85" alt="mainDashboard" width="600"/>


2. Reports:
<img src="https://github.com/user-attachments/assets/0d53a412-61d3-4027-9450-9b25d52e9198" alt="Reports" width="600"/>

