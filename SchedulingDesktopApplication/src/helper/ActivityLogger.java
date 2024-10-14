package helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * The ActivityLogger class provides methods for logging user login activities.
 * It allows logging successful and unsuccessful login attempts along with timestamps.
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public class ActivityLogger {
    private static final String LOG_FILE_PATH = "login_activity.txt";

    /**
     * Logs a user login activity.
     *
     * @param username    The username of the user attempting to log in.
     * @param successful  A boolean indicating whether the login attempt was successful.
     */
    public static void log(String username, boolean successful) {
        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = timestamp.format(formatter);

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(LOG_FILE_PATH, true)))) {
            String logEntry = String.format("[%s] User '%s' logged in %s%n",
                    formattedTimestamp, username, (successful ? "successfully" : "unsuccessfully"));
            writer.println(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
