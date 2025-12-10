WesLee Williams
CSC 345

Title: Cave Quest
Summary: Wealth, fame, power. 
One man is said to have acquired this and everything else the world has to offer
with his dying breath he sent the world into a frenzy
all of his treasure was located within a single cave.
A fool, many believed the man to be
and yet to this day, nearly ten years after his dying words
not a single adventurer has ever come back out with more than the clothes on their back.
Now you, adventurer, shall guide one of three people who wishes to challenge the cave,
the knight, who seeks a legendary sword,
the wizard, who seeks a tome of endless knowledge,
and the thief, who wishes for the riches and gold the cave has to offer. 
Now go forth, and lay claim to your spoils.

To run the program, all you need to do is use the Run function from within VS Code.

For Thread Synchronization we have it where each character extends thread and runs autonomously in parallel, with GameEngine starting
all threads and using join() to wait for completion.
SharedWorld uses Collections.synchronizedList() for the loot pool to allow thread-safe concurrent access without explicit locking overhead
ReentrantLock in SharedWorld makes it where only one character can open a chest at a time, while other characters will fail immediately.
GameLogger.log() and HighScoreManager.saveScores() use synchronized methods for safe concurrent writes to log/score files.

AI USAGE:
Had an issue early on where packages were not wanting to work AT ALL, where I would have something like Package main
and then get flagged saying I'm using the wrong package, I should be using Package main... Tried using AI to explain where I went wrong
and didn't even get an actual answer. 

Had it help write the code so that the Classes (Jobs? Characters? Whatever you want to call them) that didn't get picked
would run simultaniously with the player, of course it wasn't perfect and almost sent everything else into shock all over a single missing
value, the hp value.