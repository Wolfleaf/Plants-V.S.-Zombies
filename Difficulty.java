package a10;

public class Difficulty {

	private static int difficulty = 999;
	private static double coolDownCounterSpawn;
	private static double coolDownSpawn = 1000;
	private static int coolDownCounterDifficulty;
	private static int coolDownDifficulty = 10;
	
	public void setDifficulty(int difficulty_) {
		difficulty = difficulty_;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public void decrementSpawnCoolDown() {
		coolDownCounterSpawn --;
	}
	
	public void resetSpawnCoolDown() 
	{
		coolDownCounterSpawn = coolDownSpawn; 
	}
	
	public boolean readyForActionSpawn() {
		if (coolDownCounterSpawn <= 0)
			return true;
		return false;
	}
	
	public void decrementDifficultyCoolDown() {
		coolDownCounterSpawn --;
	}
	
	public void resetDifficultyCoolDown() {
		coolDownCounterDifficulty = coolDownDifficulty; 
	}
	
	public int getCoolDownCounterDifficulty()
	{
		return coolDownCounterDifficulty;
	}
	
	public void changeCoolDownSpawn()
	{
		coolDownSpawn *= 0.75;
	}
	
	public boolean readyForActionDifficulty() 
	{
		if (coolDownCounterDifficulty <= 0)
			return true;
		return false;
	}
	
	public String toString() {
		return ("" + (100 - this.getDifficulty()));
	}
}
