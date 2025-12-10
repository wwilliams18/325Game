package adventure;

import java.util.Random;

public class Enemy {
    private final String name;
    private int hp;
    private final int attackPower;
    private static final Random R = new Random();

    public Enemy(String name, int hp, int attackPower) {
        this.name = name;
        this.hp = hp;
        this.attackPower = attackPower;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getAttackPower() { return attackPower; }

    public void takeDamage(int d) { hp = Math.max(0, hp - d); }
    public boolean isAlive() { return hp > 0; }

    public static Enemy randomEnemy() {
        int roll = R.nextInt(3);
        return switch (roll) {
            case 0 -> new Enemy("Goblin", 30 + R.nextInt(16), 4 + R.nextInt(4));
            case 1 -> new Enemy("Skeleton", 35 + R.nextInt(20), 5 + R.nextInt(5));
            default -> new Enemy("Giant Rat", 20 + R.nextInt(12), 3 + R.nextInt(3));
        };
    }

    public static Enemy finalBossKnight() { return new Enemy("Ruby Dragon",160,24); }
    public static Enemy finalBossThief() { return new Enemy("Emerald Dragon",150,20); }
    public static Enemy finalBossWizard() { return new Enemy("Sapphire Dragon",170,28); }

    @Override public String toString() { return name + " (HP:" + hp + " ATK:" + attackPower + ")"; }
}
