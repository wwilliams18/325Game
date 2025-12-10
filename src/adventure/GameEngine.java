package adventure;

import java.util.List;

public class GameEngine {
    private final List<GameCharacter> characters;
    private final SharedWorld world;

    public GameEngine(List<GameCharacter> characters, SharedWorld world) {
        this.characters = characters;
        this.world = world;
    }

    public static java.util.List<GameCharacter> createAICharacters(GameCharacter playerChoice, SharedWorld world) {
        var classes = new java.util.ArrayList<String>(java.util.List.of("Knight","Wizard","Thief"));
        classes.remove(playerChoice.getClassName());
        var result = new java.util.ArrayList<GameCharacter>();
        for (var cls : classes) {
            switch (cls) {
                case "Knight" -> result.add(new Knight("AI Knight", 1, false, world));
                case "Wizard" -> result.add(new Wizard("AI Wizard", 1, false, world));
                case "Thief"  -> result.add(new Thief("AI Thief", 1, false, world));
            }
        }
        return result;
    }

    public void startGame() {
        System.out.println("\nStarting adventure...\n");
        characters.forEach(Thread::start);

        for (var c : characters) {
            try { c.join(); } catch (InterruptedException ignored) {}
        }

        System.out.println("\n=== Adventure Complete ===");
        HighScoreManager.saveScores(characters);
        HighScoreManager.printHighScores();
    }
}
