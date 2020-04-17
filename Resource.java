package a10;

public class Resource {

	private static int resourceAmount = 0;
	private static int coolDownCounter;
	private static int coolDown = 500;

	public int getCoolDown() {
		return coolDownCounter / 50;
	}

	public void resetCoolDown() {
		coolDownCounter = coolDown;
	}

	public void decrementCoolDown() {
		coolDownCounter--;
	}

	public void update() {
		decrementCoolDown();
	}

	public boolean readyForAction() {
		if (coolDownCounter <= 0)
			return true;
		return false;
	}

	public int getResource() {
		return resourceAmount;
	}

	public void setResource(int resourceSet) {
		resourceAmount = resourceSet;
	}

	public void addResource(int resourceAdd) {
		resourceAmount += resourceAdd;
	}

	public String toString() {
		return "" + getResource();
	}

	public static void main(String args[]) {
	}
}
