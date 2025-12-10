package adventure;

public class Knight extends GameCharacter {
    public Knight(String name, int levelOrHp, boolean isPlayer, SharedWorld world) { super(name, levelOrHp, isPlayer, world); }
    @Override protected void storylineFlavor() { GameLogger.log(name + " dons shining armor and vows valor."); }
    @Override protected void classEvent() {
        int r = random.nextInt(100);
        if (r < 30) { GameLogger.log(name + " performs a Heavy Strike! +15"); score += 15; }
        else if (r < 50) { GameLogger.log(name + " aids a villager, gains honor +10"); score += 10; }
    }
    @Override protected void passiveAction() {
        var taken = world.tryOpenChest(name);
        taken.ifPresent(item -> { GameLogger.log(name + " lifted from the chest: " + item); score += 20; });
    }
    @Override public void triggerFinalBoss() {
        GameLogger.log(name + " marches to the volcanic keep — the RUBY DRAGON awaits!");
        var won = fightEnemy(Enemy.finalBossKnight());
        if (won) {
            score += 200;
            GameLogger.log("\n=== KNIGHT ENDING ===\nAnd so the brave Knight, having defeated the Ruby Dragon, reached the long-hidden treasure and finally claimed the treasures of the world.");
        } else {
            GameLogger.log("The Ruby Dragon bested the Knight...");
        }
        stopCharacter();
    }
}
