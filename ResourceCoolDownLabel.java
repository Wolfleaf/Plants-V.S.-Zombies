package a10;

import java.awt.Color;

import javax.swing.JLabel;

public class ResourceCoolDownLabel extends JLabel {
	
	private static int coolDownCounter;
	private static int coolDown = 50;
	private static final long serialVersionUID = 1L;

	public void updateResourceCoolDownLabel() {
		Resource resource = new Resource();
		setText("Time Until Next Resource: " + resource.getCoolDown());	
		this.setForeground(Color.white);
	}
	
	public boolean readyForAction() {
		if (coolDownCounter <= 0)
			return true;
		return false;
	}
	
	public void resetCoolDown() {
		coolDownCounter = coolDown;
	}
	
	public void decrementCoolDown() {
		coolDownCounter --;
	}

}
