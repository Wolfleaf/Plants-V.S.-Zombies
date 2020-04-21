package a10;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainFile extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private Timer timer; // Creates the timer that will actually run the game (creating fps)
	private ArrayList<Actor> actors; // All Sprites, like Plants and Zombies, will go in this ArrayList

	private int difficultyCooldown = 2500;
	
	// All Images that will be used for the game:
	BufferedImage plantImage; // Maybe these images should be in those classes, but easy to change here.
	BufferedImage zombieImageA;
	BufferedImage zombieImageB;
	BufferedImage projectileImage;
	BufferedImage backgroundImage;

	// Layout of the game:
	int numRows;
	int numCols;
	int cellSize;

	private static Difficulty difficulty = new Difficulty();

	// Resource used to buy plants, and the labels to represent it
	private static Resource resource = new Resource();
	private static ResourceLabel resourceLabel = new ResourceLabel();
	private static ResourceCoolDownLabel resourceCoolDownLabel = new ResourceCoolDownLabel();

	// Plant spawner buttons
	private static LifeguardASpawnButton plantA = new LifeguardASpawnButton();
	private static LifeguardBSpawnButton plantB = new LifeguardBSpawnButton();

	// Difficulty label
	private static DifficultyLabel difficultyLabel = new DifficultyLabel();

	private JFrame app; // the main game

	/**
	 * Setup the game
	 */
	public MainFile(JFrame app) {

		super(); // inherits all it needs from JPanel
		this.app = app;
		app.addMouseListener(this);

		Random chance = new Random(); // used for random rows and columns

		// Define some quantities of the scene
		numRows = 5; // 5 rows top to bottom
		numCols = 7; // 7 columns left to right
		cellSize = 75; // size of each cell

		resource.setResource(175); // starting resource

		difficulty.setDifficulty(99); // starting difficulty

		setPreferredSize(new Dimension(50 + (numCols + 1) * (cellSize), 100 + numRows * cellSize));

		actors = new ArrayList<>(); // creates an arraylist to store all the sprites

		// Load all images into the game
		try {
			zombieImageA = ImageIO.read(new File("src/a10/Game-Sprites/CrabA.png"));
			zombieImageB = ImageIO.read(new File("src/a10/Game-Sprites/CrabB.png"));
			projectileImage = ImageIO.read(new File("src/a10/Game-Sprites/Rock.png"));
			backgroundImage = ImageIO.read(new File("src/a10/Other-Pictures/Background.png"));
		} catch (IOException e) {
			System.out.println("A file was not found");
			System.exit(0);
		}

		// The timer updates the game each time it goes.
		// Get the javax.swing Timer, not from util.
		timer = new Timer(5, this); // 5 is how fast the timer will go, lower is faster
		timer.start();
	}

	/***
	 * Implement the paint method to draw the plants
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this);
		for (Actor actor : actors) {
			actor.draw(g, 0);
			actor.drawHealthBar(g);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int xPos = e.getX();
		int yPos = e.getY();

		int gridXPos = (xPos / 75) * 75;
		int gridYPos = (yPos / 75) * 75;
		boolean spaceOccupied = false;

		for (Actor actor : actors) 
		{
			if (!(actor instanceof Projectile))
			{
				if (actor.isCollidingPoint(new Point2D.Double(xPos, yPos))) 
				{
					spaceOccupied = true;
				}
			}
		}

		if (plantA.wasButtonClicked() == true && resource.getResource() >= plantA.getPrice()
				&& spaceOccupied == false) {
			LifeguardA placedPlantA = plantA.placePlantA(gridYPos, gridXPos);
			actors.add(placedPlantA);
			resource.addResource(-50);
			plantA.resetText();
			plantA.buttonWasClicked();
		}

		if (plantA.wasButtonClicked() == true && resource.getResource() <= plantA.getPrice())

		{
			plantA.insufficientFunds();
			plantA.buttonWasClicked();
		}

		if (plantB.wasButtonClicked() == true && resource.getResource() >= plantB.getPrice()
				&& spaceOccupied == false) {

			Lifeguard placedPlantB = plantB.placePlantB(gridYPos, gridXPos);
			actors.add(placedPlantB);
			actors.add(placedPlantB);
			resource.addResource(-100);
			plantB.resetText();
			plantB.buttonWasClicked();
		}

		if (plantB.wasButtonClicked() == true && resource.getResource() <= plantB.getPrice())

		{
			plantB.insufficientFunds();
			plantB.buttonWasClicked();
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	/**
	 * 
	 * This is triggered by the timer. It is the game loop of this test.
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// decrement cool down timer for difficulty and update label
		difficulty.decrementSpawnCoolDown();
		difficultyLabel.updateDifficultyLabel();
		difficultyCooldown--;

		// update labels and decrement cool downs for the resource
		resourceLabel.updateResourceLabel();
		resourceCoolDownLabel.decrementCoolDown();
		resource.decrementCoolDown();

		// slowing down how often the resourceCoolDownLabel updates to make it readable
		if (resourceCoolDownLabel.readyForAction() == true) {
			resourceCoolDownLabel.updateResourceCoolDownLabel();
			resourceCoolDownLabel.resetCoolDown();
		}

		// adds resource on a cool down
		if (resource.readyForAction() == true) {
			resource.addResource(25);
			resource.resetCoolDown();
		}

		// chance to spawn Zombie
		Random chance = new Random();
		int chanceToAppear = chance.nextInt(1000);
		if (chanceToAppear <= difficulty.getDifficulty() && difficulty.readyForActionSpawn() == true) // decides if it will add a zombie or not
		{
			Random randomRow = new Random();
			int randomRowYPos = (randomRow.nextInt(5)) * 75;
			int randomRowXPos = (randomRow.nextInt(4)) * 75;
			if (chanceToAppear % 2 == 0 || chanceToAppear % 3 == 0)
			{
				CrabA newZombie = new CrabA(new Point2D.Double(8 * 75 + 55, randomRowYPos + 55),
						new Point2D.Double(zombieImageA.getWidth(), zombieImageA.getHeight()), zombieImageA, 100, 30, -0.5, 1);
				actors.add(newZombie);
				System.out.println("Spawned Zombie type A");
			}
			else
			{
				CrabB newZombie = new CrabB(new Point2D.Double(3 * 75 + 55 + randomRowXPos, randomRowYPos + 55),
						new Point2D.Double(zombieImageB.getWidth(), zombieImageB.getHeight()), zombieImageB, 100, 30, -0.8, 1);
				actors.add(newZombie);
				System.out.println("Spawned Zombie type B");
			}

			difficulty.resetSpawnCoolDown();
		}

		// Increase difficulty
		if (difficultyCooldown <= 0)
		{
			difficulty.setDifficulty(difficulty.getDifficulty() - 1);
			difficulty.changeCoolDownSpawn();
			System.out.println("Difficulty has been changed to: " + (100 - difficulty.getDifficulty()));
			difficultyCooldown = 2500;
		}

		// Increment their cooldowns and reset collision status
		for (Actor actor : actors) {
			actor.update();
		}

		// Try to attack
		for (Actor actor : actors) {
			for (Actor other : actors) {
				actor.attack(other);
			}
		}

		// Remove plants and zombies with low health
		ArrayList<Actor> nextTurnActors = new ArrayList<>();
		for (Actor actor : actors) {
			if (actor.isAlive()) {
				nextTurnActors.add(actor);
			} else {
				actor.removeAction(actors); // any special effect or whatever on removal
			}
		}

		// Check for collisions between zombies and plants and set collision status
		for (Actor actor : actors) {
			for (Actor other : actors) {
				actor.setCollisionStatus(other);
			}
		}

		// Move the actors.
		for (Actor actor : actors) {
			actor.move(); // only moves if it is not colliding
		}

		for (Actor actor : actors) {
			if (actor.getCooldown() <= 0) {
				Projectile projectile = new Projectile(
						new Point2D.Double((int) Math.round(actor.getPosition().getX()),
								(int) Math.round(actor.getPosition().getY())),
						new Point2D.Double(projectileImage.getWidth(), projectileImage.getHeight()), projectileImage, 1,
						1, 5, 5);
				nextTurnActors.add(projectile);
				actor.setCooldown(100);
			}
		}

		for (Actor actor : actors) {
			if (actor.getPosition().getX() < 0) {
				System.out.println("You died"); // shows that the program is actually working
				timer.stop();
				app.dispose();
				System.exit(0);
			}
			if (actor.getPosition().getX() > 1920) {
				actor.changeHealth(-1000);
			}
		}

		actors = nextTurnActors;

		// Redraw the new scene
		repaint();
	}

	/**
	 * Make the game and run it
	 * 
	 * @param args
	 */
	public static void main(String[] args) // actually runs the game, does not do the game loop however
	{
		// Schedule a job for the event-dispatching thread:
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				System.out.println("Running Crabs V.S. Lifeguards");
				System.out.println("Created by Guyan Cool and Alexander Thomsen");
				JFrame app = new JFrame("Crabs V.S. Lifeguards"); // Name of the Program
				app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exits (Disposes and Exits (use DISPOSE_ON_CLOSE
																	// for only Dispose) of the JPanel when pressing the
																	// exit button
				MainFile panel = new MainFile(app);

				panel.add(plantA);
				panel.add(plantB);
				panel.add(resourceLabel);
				panel.add(resourceCoolDownLabel);
				panel.add(difficultyLabel);
				app.setContentPane(panel);
				app.pack();
				app.setVisible(true); // allows you to actually see the window

			}
		});

	}

}
