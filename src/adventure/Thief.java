package adventure;

public class Thief extends GameCharacter {
    public Thief(String name, int levelOrHp, boolean isPlayer, SharedWorld world) { super(name, levelOrHp, isPlayer, world); }
    @Override protected void storylineFlavor() { GameLogger.log(name + " slips into shadows, fingers ready."); }
    @Override protected void classEvent() {
        int r = random.nextInt(100);
        if (r < 35) { GameLogger.log(name + " pickpockets a merchant! +10"); score += 10; }
        else if (r < 55) { GameLogger.log(name + " disarms a trap and pockets valuables! +8"); score += 8; }
    }
    @Override protected void passiveAction() {
        var taken = world.tryOpenChest(name);
        taken.ifPresent(item -> { GameLogger.log(name + " quietly takes: " + item); score += 25; });
    }
    @Override public void triggerFinalBoss() {
        GameLogger.log(name + " slips into the emerald vault — the EMERALD DRAGON stirs!");
        var won = fightEnemy(Enemy.finalBossThief());
        if (won) {
            score += 200;
            GameLogger.log("\n=== THIEF ENDING ===\nAnd so the cunning Thief, having bested the Emerald Dragon, reached the long-hidden treasure and finally secured the treasures of the world for their own shadowy legend.");
        } else {
            GameLogger.log("The Emerald Dragon devoured the Thief...");
        }
        stopCharacter();
    }
}
