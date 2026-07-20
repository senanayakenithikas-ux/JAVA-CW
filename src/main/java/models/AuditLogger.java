package models;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLogger {

    private static final String logFile = "Audit_log.txt";

    public static void log (String  action, String itemCode, int quantity) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = now.format(formatter);
        String logEntry = "Time: " +time + " | " + "Action: " + action+ " | " + "Code: " + itemCode + " | " + "Qty: " + quantity;
        try (FileWriter fileWriter = new FileWriter(logFile, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println(logEntry);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
