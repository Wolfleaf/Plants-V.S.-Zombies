package a10;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class LifeguardSpawnButton extends JButton implements ActionListener {


	private static final long serialVersionUID = 1L;

	private int count;
	
	public LifeguardSpawnButton(String plantName) {
		super(plantName); 
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setText("place plant");
	}

	}
