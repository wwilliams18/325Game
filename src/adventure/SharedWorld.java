package adventure;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class SharedWorld {
    private final List<Loot> lootPool = Collections.synchronizedList(new ArrayList<>());
    private final Random random = new Random();
    private final ReentrantLock chestLock = new ReentrantLock();
    public SharedWorld() {
        lootPool.add(new Loot("Gold Coin Pouch",20));
        lootPool.add(new Loot("Silver Goblet",30));
        lootPool.add(new Loot("Emerald Ring",40));
        lootPool.add(new Loot("Dragon Ruby Fragment",60));
        lootPool.add(new Loot("Old Map Piece",15));
    }

    public synchronized Loot getRandomLoot() {
        Loot loot;
        if (!lootPool.isEmpty() && random.nextInt(10) < 6) {
            loot = lootPool.remove(random.nextInt(lootPool.size()));
            GameLogger.log("[World] Loot taken from pool: " + loot);
        } else {
            loot = Loot.randomLoot();
            GameLogger.log("[World] New loot appears: " + loot);
        }
        return loot;
    }

    public Optional<String> tryOpenChest(String who) {
        if (chestLock.tryLock()) {
            try {
                if (lootPool.isEmpty()) return Optional.empty();
                return Optional.of(lootPool.remove(random.nextInt(lootPool.size())).getName());
            } finally {
                chestLock.unlock();
            }
        } else {
            return Optional.empty();
        }
    }

    public int remainingLootCount() { return lootPool.size(); }
}
