package helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * The TimeUtils class provides utility methods for working with date and time.
 *  @author Elham Pazhakh
 *  JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public class TimeUtils {

    /**
     * Converts a LocalDateTime object to UTC.
     *
     * @param localDateTime The LocalDateTime object to convert.
     * @return The equivalent LocalDateTime in UTC.
     */
    public static LocalDateTime toUTC(LocalDateTime localDateTime) {

        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime utcDateTime = zonedDateTime.withZoneSameInstant(utcZone);
        return utcDateTime.toLocalDateTime();
    }

    /**
     * Converts a UTC LocalDateTime to the system default timezone.
     *
     * @param utcDateTime The UTC LocalDateTime to convert.
     * @return The equivalent LocalDateTime in the system default timezone.
     */
    public static LocalDateTime toSystemDefault(LocalDateTime utcDateTime) {

        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime utcZonedDateTime = utcDateTime.atZone(utcZone);
        ZonedDateTime systemDefaultDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
        return systemDefaultDateTime.toLocalDateTime();
    }
}

//    public static Boolean appointmentOverlap(LocalDateTime start, LocalDateTime end, int customerId) throws SQLException {
//
//        boolean overlap = false;
//        ObservableList<Appointment> appointments = DBAppointments.selectByCustomerId(customerId);
//        for(Appointment app: appointments){
//            if((start.isBefore(app.getEnd()) || start.isEqual(app.getEnd())) &&
//                    (end.isAfter(app.getStart()) || end.isEqual(app.getStart()))){
//                overlap = true;
//            }
//        }
//        return overlap;
//    }
//}

//