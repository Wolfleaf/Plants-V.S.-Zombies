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

public class PlantBSpawnButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;

	private String plantNameB;
	private BufferedImage plantBImage;
	private boolean buttonClicked = false;
	private static int plantBPrice = 100;

	public void setPlantBPrice(int price) {
		plantBPrice = price;
	}

	public int getPrice() {
		return plantBPrice;
	}

	public PlantBSpawnButton() {
		super("Plant B Cost: " + plantBPrice);
		addActionListener(this);

		try {
			plantBImage = ImageIO.read(new File("src/a10/GameSprites/PersonB.png"));
		} catch (IOException e) {
			System.out.println("A file was not found");
			System.exit(0);
		}
	}

	public PlantB placePlantB(int yPos, int xPos) {
		PlantB plant = new PlantB(new Point2D.Double(xPos - 20, yPos - 20),
				new Point2D.Double(plantBImage.getWidth(), plantBImage.getHeight()), plantBImage, 50, 30, 0, 200);
		return plant;
	}
	
	public void resetText() {
		setText("Plant B Cost: " + plantBPrice);
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
		setText("place plant B");
		buttonClicked = true;

	}

	public boolean wasButtonClicked() {
		return buttonClicked;
	}

}
