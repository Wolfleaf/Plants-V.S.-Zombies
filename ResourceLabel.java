package a10;

import java.awt.Color;

import javax.swing.JLabel;

public class ResourceLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	public void updateResourceLabel() {
		Resource resource = new Resource();
		setText("Current Resource: " + resource.toString());
		this.setForeground(Color.white);
	}

}
