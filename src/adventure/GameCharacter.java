package adventure;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public abstract class GameCharacter extends Thread {
    protected String name;
    protected int hp;
    protected int score = 0;
    protected boolean isPlayer;
    protected volatile boolean running = true;
    protected SharedWorld world;
    protected Random random = new Random();
    protected List<Loot> inventory = new CopyOnWriteArrayList<>();

    public GameCharacter(String name, int levelOrHp, boolean isPlayer, SharedWorld world) {
        this.name = name;
        // support level-based constructors: if levelOrHp <= 20 treat as level else HP
        if (levelOrHp <= 20) {
            this.hp = 80 + levelOrHp * 10;
        } else {
            this.hp = levelOrHp;
        }
        this.isPlayer = isPlayer;
        this.world = world;
    }

@Override
public void run() {
    storylineFlavor();

    while (running && hp > 0) {

        if (isPlayer) {
            // Wait for player command
            synchronized (this) {
                try {
                    wait(); // InputHandler resumes us
                } catch (InterruptedException ignored) {}
            }
        } else {
            // AI autonomous behavior
            passiveAction();
            try { Thread.sleep(1500 + random.nextInt(1500)); } catch (InterruptedException ignored) {}
        }

        if (!running || hp <= 0) break;

        // World progression ONLY after action
        randomTreasureEvent();
        if (random.nextInt(100) < 35) randomEncounter();  // Don't trigger every turn

        if (score >= 120) {
            triggerFinalBoss();
            running = false;
        }
    }

    // Ensure message prints exactly once
    System.out.println("\n=== " + name + " has ended their journey. ===");
}
    private boolean finalBossTriggered = false;

    /* Loot */
    protected void randomTreasureEvent() {
        if (random.nextInt(100) < 25) {
            Loot loot = world.getRandomLoot();
            collectLoot(loot);
        }
    }
    protected void collectLoot(Loot loot) {
        if (loot == null) return;
        if (loot.take()) {
            inventory.add(loot);
            score += loot.getScoreValue();
            GameLogger.log(name + " collects " + loot + " (Score: " + score + ")");
        }
    }

    /* Encounters */
    protected void randomEncounter() {
        if (random.nextInt(100) < 35) {
            Enemy e = Enemy.randomEnemy();
            GameLogger.log(name + " encounters a " + e.getName());
            fightEnemy(e);
        }
    }

    public boolean fightEnemy(Enemy enemy) {
        if (enemy == null) return false;
        GameLogger.log("[COMBAT] " + name + " vs " + enemy.getName());
        try { Thread.sleep(200); } catch (InterruptedException ignored) {}
        while (enemy.getHp() > 0 && hp > 0) {
            int dmg = 5 + random.nextInt(10);
            enemy.takeDamage(dmg);
            GameLogger.log(name + " hits " + enemy.getName() + " for " + dmg + " (Enemy HP: " + enemy.getHp() + ")");
            if (enemy.getHp() <= 0) break;
            int inc = enemy.getAttackPower() + random.nextInt(5);
            hp -= inc;
            GameLogger.log(enemy.getName() + " hits " + name + " for " + inc + " (HP: " + hp + ")");
            try { Thread.sleep(180); } catch (InterruptedException ignored) {}
        }
        boolean won = enemy.getHp() <= 0 && hp > 0;
        if (won) {
            int award = 50 + enemy.getAttackPower() * 5;
            score += award;
            GameLogger.log(name + " defeated " + enemy.getName() + " and gains " + award + " score (Total: " + score + ")");
            if (random.nextInt(100) < 40) {
                Loot drop = Loot.randomLoot();
                collectLoot(drop);
            }
        } else {
            GameLogger.log(name + " was defeated by " + enemy.getName());
        }
        return won;
    }

    /* Player actions */
    public void manualAttack() {
        int add = 5 + random.nextInt(8);
        score += add;
        GameLogger.log(name + " trains and gains +" + add + " score (Total: " + score + ")");
    }
    public void rest() {
        int healed = 10;
        hp = Math.min(9999, hp + healed);
        GameLogger.log(name + " rests and recovers " + healed + " HP (HP: " + hp + ")");
    }
    public void printStatus() {
        String inv = inventory.isEmpty() ? "[empty]" : inventory.stream().map(Loot::getName).collect(Collectors.joining(", "));
        GameLogger.log("\n=== STATUS: " + name + " ===\nClass: " + getClassName() + "\nHP: " + hp + "\nScore: " + score + "\nInventory: " + inv + "\n====================");
    }
    public void stopCharacter() { running = false; GameLogger.log(name + " has been stopped."); }

    /* Abstract hooks */
    protected abstract void storylineFlavor();
    protected abstract void classEvent();
    protected abstract void passiveAction();
    public abstract void triggerFinalBoss();
    public String getClassName() { return getClass().getSimpleName(); }
    public int getScore() { return score; }
    public String getPlayerName() { return name; }
    public boolean isCurrentlyAlive() { return hp > 0; }
}
