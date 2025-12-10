package adventure;

import java.util.List;
import java.util.Scanner;

public class AdventureGame {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("======== MEDIEVAL ADVENTURE (Java 21) ==========");
        System.out.println("Choose your class:");
        System.out.println("1) Knight");
        System.out.println("2) Wizard");
        System.out.println("3) Thief");
        System.out.print("Enter choice (1-3): ");

        int choice = 1;
        try { choice = Integer.parseInt(sc.nextLine().trim()); } catch (Exception ignored) {}

        SharedWorld world = new SharedWorld();

        GameCharacter player;
        if (choice == 1) player = new Knight("You (Knight)", 1, true, world);
        else if (choice == 2) player = new Wizard("You (Wizard)", 1, true, world);
        else player = new Thief("You (Thief)", 1, true, world);

        List<GameCharacter> characters = GameEngine.createAICharacters(player, world);
        characters.add(player);

        // start input handler
        InputHandler ih = new InputHandler(player);
        ih.start();

        // start engine
        GameEngine engine = new GameEngine(characters, world);
        engine.startGame();

        sc.close();
    }
}
