package adventure;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class GameLogger {
    private static final String LOG_FILE = "logs.txt";
    public static synchronized void log(String text) {
        System.out.println(text);
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write("[" + LocalDateTime.now() + "] " + text + "\n");
        } catch (IOException ignored) {}
    }
}
