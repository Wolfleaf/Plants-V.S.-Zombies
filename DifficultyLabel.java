package a10;

import java.awt.Color;

import javax.swing.JLabel;

public class DifficultyLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	public void updateDifficultyLabel() {
		Difficulty difficulty = new Difficulty();
		setText("Current Difficulty: " + difficulty.toString());
		//this.setForeground(Color.white);
	}

}
