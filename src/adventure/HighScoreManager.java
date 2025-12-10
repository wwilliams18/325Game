package adventure;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class HighScoreManager {
    private static final String FILE = "highscores.txt";

    public static synchronized void saveScores(List<GameCharacter> chars) {
        try (FileWriter fw = new FileWriter(FILE, true)) {
            for (var c : chars) fw.write(c.getName() + "," + c.getScore() + "\n");
        } catch (IOException e) { System.out.println("Save error: "+e.getMessage()); }
    }

    public static void printHighScores() {
        System.out.println("\n=== HIGH SCORES ===");
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            br.lines()
              .map(l -> l.split(","))
              .sorted((a,b) -> Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1])))
              .limit(10)
              .forEach(arr -> System.out.println(arr[0] + " — " + arr[1]));
        } catch (Exception e) {
            System.out.println("No highscores yet.");
        }
    }
}
