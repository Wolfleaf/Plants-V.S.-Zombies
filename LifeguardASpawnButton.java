package a10;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class LifeguardASpawnButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;

	private String plantNameA;
	private BufferedImage plantAImage;
	private boolean buttonClicked = false;
	private static int plantAPrice = 50;

	public void setPlantAPrice(int price) {
		plantAPrice = price;
	}

	public int getPrice() {
		return plantAPrice;
	}

	public LifeguardASpawnButton() {
		super("Lifeguard 1 Cost: " + plantAPrice);
		addActionListener(this);

		try {
			plantAImage = ImageIO.read(new File("src/a10/GameSprites/PersonA.png"));
		} catch (IOException e) {
			System.out.println("A file was not found");
			System.exit(0);
		}
	}

	public LifeguardA placePlantA(int yPos, int xPos) 
	{
		LifeguardA plant = new LifeguardA(new Point2D.Double(xPos - 20, yPos - 20),
				new Point2D.Double(plantAImage.getWidth(), plantAImage.getHeight()), plantAImage, 200, 30, 1);
		setText("Plant A Cost: " + plantAPrice);
		return plant;
	}

	public void resetText() {
		setText("Lifeguard 1 Cost: " + plantAPrice);
	}
	
	public void insufficientFunds() {
		setText("Insufficient Funds");
	}
	
	public void buttonWasClicked() {
		buttonClicked = false;
	}
	
	public void MouseExited(MouseEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setText("place Lifeguard 1");
		buttonClicked = true;

	}

	public boolean wasButtonClicked() {
		return buttonClicked;
	}

}
