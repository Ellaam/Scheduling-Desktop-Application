package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.stream.Collectors;


/**
 * The DBAppointments class provides database operations related to appointments.
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public abstract class DBAppointments {

    /**
     * Inserts a new appointment into the database.
     *
     * @param appointment The appointment to insert.
     * @return The number of rows affected by the insertion.
     * @throws SQLException If a database error occurs.
     */
    public static int insert(Appointment appointment) throws SQLException {
        String sql = "insert into appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, appointment.getId());
        ps.setString(2, appointment.getTitle());
        ps.setString(3, appointment.getDescription());
        ps.setString(4, appointment.getLocation());
        ps.setString(5, appointment.getType());
        ps.setTimestamp(6, Timestamp.valueOf(appointment.getStart()));
        ps.setTimestamp(7, Timestamp.valueOf(appointment.getEnd()));
        ps.setTimestamp(8, Timestamp.valueOf(appointment.getCreatedDate()));
        ps.setString(9,appointment.getCreatedBy());
        ps.setTimestamp(10, Timestamp.valueOf(appointment.getLastUpdated()));
        ps.setString(11,appointment.getLastUpdatedBy());
        ps.setInt(12, appointment.getCustomerId());
        ps.setInt(13, appointment.getUserId());
        ps.setInt(14, appointment.getContactId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Updates an existing appointment in the database.
     *
     * @param appointment The appointment to update.
     * @return The number of rows affected by the update.
     * @throws SQLException If a database error occurs.
     */
    public static int update(Appointment appointment) throws SQLException {
        String sql = "UPDATE appointments SET " +
                "Title = ?, " +
                "Description = ?, " +
                "Location = ?, " +
                "Type = ?, " +
                "Start = ?, " +
                "End = ?, " +
                "Last_Update = ?, " +
                "Contact_ID = ?, " +
                "User_ID = ?, " +
                "Customer_ID = ? " +
                "WHERE Appointment_ID = ?"; // Add more conditions as needed

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
        ps.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
        ps.setTimestamp(7, Timestamp.valueOf(appointment.getLastUpdated()));
        ps.setInt(8, appointment.getContactId());
        ps.setInt(9, appointment.getUserId());
        ps.setInt(10, appointment.getCustomerId()); // Additional condition value
        ps.setInt(11, appointment.getId());

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Deletes an appointment from the database.
     *
     * @param appointment The appointment to delete.
     * @return The number of rows affected by the deletion.
     * @throws SQLException If a database error occurs.
     */
    public static int delete(Appointment appointment) throws SQLException {
        String sql = "delete from appointments where Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, appointment.getId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Retrieves a list of all appointments from the database.
     *
     * @return An ObservableList of Appointment objects.
     * @throws SQLException If a database error occurs.
     */
    public static ObservableList<Appointment> select() throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String sql = "select * from appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int appointmentId = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            Timestamp createdDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdated = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
//        System.out.print(countryId + " | ");
//        System.out.print(countryName+"\n");
            LocalDateTime startDate = (start != null) ? start.toLocalDateTime() : null;
            LocalDateTime endDate = (end != null) ? end.toLocalDateTime(): null;
            LocalDateTime createdDateTime = (createdDate != null) ? createdDate.toLocalDateTime() : null;
            LocalDateTime lastUpdateDateTime = (lastUpdated != null) ? lastUpdated.toLocalDateTime() : null;

            Appointment appointment = new Appointment(appointmentId, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, startDate, endDate, createdDateTime, createdBy, lastUpdateDateTime, lastUpdatedBy,customerId, userId, contactId);
            //System.out.println(appointment);
            appointmentList.add(appointment);
            //System.out.println(appointmentList.size());
        }
        return appointmentList;
    }

    /**
     * Retrieves a list of appointments associated with a specific contact.
     *
     * @param contactId The ID of the contact.
     * @return An ObservableList of Appointment objects associated with the contact.
     * @throws SQLException If a database error occurs.
     */
    public static ObservableList<Appointment> select(int contactId) throws SQLException {
        ObservableList<Appointment> filteredAppointmentList = FXCollections.observableArrayList();
        String sql = "select * from appointments where Contact_ID=?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int appointmentId = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            Timestamp createdDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
             Timestamp lastUpdated = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int cId = rs.getInt("Contact_ID");
//        System.out.print(countryId + " | ");
//        System.out.print(countryName+"\n");
            LocalDateTime startDate = (start != null) ? start.toLocalDateTime() : null;
            LocalDateTime endDate = (end != null) ? end.toLocalDateTime() : null;
            LocalDateTime createdDateTime = (createdDate != null) ? createdDate.toLocalDateTime() : null;
            LocalDateTime lastUpdateDateTime = (lastUpdated != null) ? lastUpdated.toLocalDateTime() : null;
            Appointment appointment = new Appointment(appointmentId, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, startDate, endDate, createdDateTime, createdBy, lastUpdateDateTime, lastUpdatedBy,customerId, userId, cId);
            //System.out.println(appointment);
            filteredAppointmentList.add(appointment);
            //System.out.println(filteredAppointmentList.size());
        }
        return filteredAppointmentList;
    }

    /**
     * Retrieves a list of appointments associated with a specific customer.
     *
     * @param customerId The ID of the contact.
     * @return An ObservableList of Appointment objects associated with the contact.
     * @throws SQLException If a database error occurs.
     */
    public static ObservableList<Appointment> selectByCustomerId(int customerId) throws SQLException {
        ObservableList<Appointment> filteredAppointmentList = FXCollections.observableArrayList();
        String sql = "select * from appointments where Customer_ID=?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int appointmentId = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            Timestamp createdDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdated = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int contactId = rs.getInt("Contact_ID");
            int userId = rs.getInt("User_ID");
            int cId = rs.getInt("Customer_ID");
//        System.out.print(countryId + " | ");
//        System.out.print(countryName+"\n");
            LocalDateTime startDate = (start != null) ? start.toLocalDateTime() : null;
            LocalDateTime endDate = (end != null) ? end.toLocalDateTime() : null;
            LocalDateTime createdDateTime = (createdDate != null) ? createdDate.toLocalDateTime() : null;
            LocalDateTime lastUpdateDateTime = (lastUpdated != null) ? lastUpdated.toLocalDateTime() : null;
            Appointment appointment = new Appointment(appointmentId, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, startDate, endDate, createdDateTime, createdBy, lastUpdateDateTime, lastUpdatedBy,customerId, userId, cId);
            //System.out.println(appointment);
            filteredAppointmentList.add(appointment);
            //System.out.println(filteredAppointmentList.size());
        }
        return filteredAppointmentList;
    }

    /**
     * Retrieves a Contact object by its name.
     *
     * @param contactName The name of the contact.
     * @return The Contact object with the specified name, or null if not found.
     * @throws SQLException If a database error occurs.
     */
    public static Contact getContactByName(String contactName) throws SQLException {
        Contact contact = null;
        ObservableList<Contact> contacts = DBContacts.select();
        for(Contact c: contacts){
            if(c.getContactName().equals(contactName)){
                contact = c;
            }
        }
        return contact;
    }

    /**
     * Retrieves an appointment list by user ID.
     *
     * @param userId The ID of the user.
     * @return The list of appointments with that user ID.
     * @throws SQLException If a database error occurs.
     */
    public static ObservableList<Appointment> getAppointmentsByUserId(int userId) throws SQLException {

        ObservableList<Appointment> appointments = DBAppointments.select();
        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
        for(Appointment app: appointments){
            if(app.getUserId()==userId){
                filteredAppointments.add(app);
            }
        }
        return filteredAppointments;
    }

    /**
     * Retrieves a list of appointments for a specific month.
     *
     * @param selectedMonth The selected month (e.g., "JANUARY").
     * @return An ObservableList of Appointment objects for the selected month.
     * @throws SQLException If a database error occurs.
     */
    public static ObservableList<Appointment> getAppointmentsByMonth(String selectedMonth) throws SQLException {
        // Mapping the selected month to its corresponding Month enum
        Month month = Month.valueOf(selectedMonth.toUpperCase());

        // Getting the user's time zone
        ZoneId userTimeZone = ZoneId.systemDefault();

        // Getting the current date in the user's time zone
        ZonedDateTime userNow = ZonedDateTime.now(userTimeZone);

        // Getting the first and last dates of the selected month in the user's time zone
        ZonedDateTime firstDayOfMonth = userNow.withMonth(month.getValue()).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        ZonedDateTime lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusSeconds(1);

        // Converting start and end times to UTC for querying
        LocalDateTime startOfMonthUTC = firstDayOfMonth.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
        LocalDateTime endOfMonthUTC = lastDayOfMonth.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();

        // Fetching appointments that match the selected month
        ObservableList<Appointment> matchingAppointments = select()
                .stream()
                .filter(appointment ->
                        appointment.getStart().isAfter(startOfMonthUTC) &&
                                appointment.getStart().isBefore(endOfMonthUTC))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        FXCollections::observableArrayList));

        return matchingAppointments;
    }

}
