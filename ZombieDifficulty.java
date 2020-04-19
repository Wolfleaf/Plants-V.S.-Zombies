package a10;

public class ZombieDifficulty {

	private static int difficulty = 999;
	private static int coolDownCounterSpawn;
	private static int coolDownSpawn = 1000;
	private static int coolDownCounterDifficulty;
	private static int coolDownDifficulty = 100000;
	
	public void setDifficulty(int difficulty_) {
		difficulty = difficulty_;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public void decrementSpawnCoolDown() {
		coolDownCounterSpawn --;
	}
	
	public void resetSpawnCoolDown() {
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
	
	public boolean readyForActionDifficulty() {
		if (coolDownCounterDifficulty <= 0)
			return true;
		return false;
	}
	
	public String toString() {
		return ("" + (100 - this.getDifficulty()));
	}
}
