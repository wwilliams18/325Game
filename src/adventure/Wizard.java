package adventure;

public class Wizard extends GameCharacter {
    public Wizard(String name, int levelOrHp, boolean isPlayer, SharedWorld world) { super(name, levelOrHp, isPlayer, world); }
    @Override protected void storylineFlavor() { GameLogger.log(name + " studies ancient tomes and hums with magic."); }
    @Override protected void classEvent() {
        int r = random.nextInt(100);
        if (r < 30) { GameLogger.log(name + " absorbs arcane surge! +12"); score += 12; }
        else if (r < 40) { GameLogger.log(name + " miscasts and loses -5 HP"); hp -= 5; }
    }
    @Override protected void passiveAction() {
        if (random.nextInt(100) < 50) {
            Loot l = world.getRandomLoot();
            collectLoot(l);
        }
    }
    @Override public void triggerFinalBoss() {
        GameLogger.log(name + " enters the crystalline spire — the SAPPHIRE DRAGON descends!");
        var won = fightEnemy(Enemy.finalBossWizard());
        if (won) {
            score += 200;
            GameLogger.log("\n=== WIZARD ENDING ===\nAnd so the wise Wizard, triumphing over the Sapphire Dragon, reached the long-hidden treasure and at last claimed the treasures of the world through mastery of arcane power.");
        } else {
            GameLogger.log("The Sapphire Dragon froze the Wizard...");
        }
        stopCharacter();
    }
}
