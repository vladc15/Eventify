package service;

import java.io.FileWriter;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static final String AUDIT_FILE = "audit.csv";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private FileWriter fileWriter;

    private static AuditService instance = null;

    private AuditService() {
        try {
            fileWriter = new FileWriter(AUDIT_FILE, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public void logAction(String action) {
        String timestamp = java.time.LocalDateTime.now().format(DATE_TIME_FORMATTER);
        String log = action + "," + timestamp;
        try {
            fileWriter.append(log);
            fileWriter.append("\n");
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
