package adventure;

import java.util.concurrent.atomic.AtomicInteger;

public class Loot {
    private final String name;
    private final int scoreValue;
    private final AtomicInteger quantity = new AtomicInteger(1);

    public Loot(String name, int scoreValue) {
        this.name = name;
        this.scoreValue = scoreValue;
    }

    public String getName() { return name; }
    public int getScoreValue() { return scoreValue; }

    public boolean take() {
        int cur = quantity.get();
        if (cur > 0 && quantity.compareAndSet(cur, cur-1)) return true;
        return false;
    }

    @Override
    public String toString() { return name + " (+" + scoreValue + " pts)"; }

    public static Loot randomLoot() {
        int roll = (int)(Math.random()*5);
        switch (roll) {
            case 0: return new Loot("Gold Coins",10);
            case 1: return new Loot("Silver Ring",15);
            case 2: return new Loot("Jeweled Goblet",25);
            case 3: return new Loot("Ancient Relic",35);
            default: return new Loot("Dragon Ruby Fragment",60);
        }
    }
}
