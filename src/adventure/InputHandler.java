package adventure;

import java.util.Scanner;

public class InputHandler extends Thread {
    private final GameCharacter player;
    private volatile boolean running = true;
    public InputHandler(GameCharacter player) { this.player = player; }
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        GameLogger.log("[Player] You may type commands: attack / rest / status / quit");
        while (running && player.isAlive()) {
            System.out.print("> ");
            String cmd = sc.nextLine().trim().toLowerCase();
            switch (cmd) {
                case "attack" : player.manualAttack();
                synchronized (player) {
                    player.notify();
                    break;
                }
                case "rest" : player.rest();
                synchronized (player) {
                    player.notify();
                    break;
                }
                case "status" : player.printStatus();
                break;
                case "quit" :
                    player.stopCharacter();
                    synchronized (player) {
                        player.notify();
                    }
                default : System.out.println("Commands: attack, rest, status, quit");
            }
        }
        GameLogger.log("Exitting Session....");
    }
}
