package a10;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class ResourceCoolDownLabel extends JLabel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private Timer timer;
	
	public ResourceCoolDownLabel() 
	{
		super("0"); 
		timer = new Timer(50, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Resource resource = new Resource();
		setText("Time Until Next Resource: " + resource.getCoolDown());
		
	}

}
