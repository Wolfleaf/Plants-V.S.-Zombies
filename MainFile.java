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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainFile extends JPanel implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	
	private Timer timer; //Creates the timer that will actually run the game (creating fps)
	private ArrayList<Actor> actors; // All Sprites, like Plants and Zombies, will go in this ArrayList
	
	//All Images that will be used for the game:
	BufferedImage plantImage; // Maybe these images should be in those classes, but easy to change here.
	BufferedImage zombieImage;
	BufferedImage projectileImage;
	BufferedImage backgroundImage;
	
	//Layout of the game:
	int numRows;
	int numCols;
	int cellSize;
	
	private JFrame app; //the main game

	/**
	 * Setup of the game
	 */
	public MainFile(JFrame app) 
	{
		super(); //inherits all it needs from JPanel
		this.app = app;
		
		Random chance = new Random(); //used for random rows and columns

		// Define some quantities of the scene
		numRows = 5; //5 rows from top to bottom
		numCols = 8; //7 columns from left to right
		cellSize = 75; //size of each cell
		setPreferredSize(new Dimension(50 + numCols * cellSize, 100 + numRows * cellSize));

		actors = new ArrayList<>(); //creates the arrayList to store all sprites

		// Load all images into the game
		try 
		{
			plantImage = ImageIO.read(new File("src/a10/Animal-Icons/frog-icon.png"));
			zombieImage = ImageIO.read(new File("src/a10/Animal-Icons/chihuahua-icon.png"));
			projectileImage = ImageIO.read(new File("src/a10/Animal-Icons/crab-icon.png"));
			backgroundImage = ImageIO.read(new File("src/a10/Other-Pictures/Temp-Background.png"));
		} 
		catch (IOException e) 
		{
			System.out.println("A file was not found");
			System.exit(0);
		}

		// Starting Plants (Creates 1 Plant that will go on each row)
		// The + 5 after each position to to center the sprite in the center of the row
		Plant plant = new Plant(new Point2D.Double(2 * 75 + 55, 0 + 55), new Point2D.Double(plantImage.getWidth(), plantImage.getHeight()), plantImage, 100, 30, 1);
		actors.add(plant);
		Plant plant2 = new Plant(new Point2D.Double(2 * 75 + 55, 75 + 55), new Point2D.Double(plantImage.getWidth(), plantImage.getHeight()), plantImage, 100, 30, 1);
		actors.add(plant2);
		Plant plant3 = new Plant(new Point2D.Double(2 * 75 + 55, 150 + 55), new Point2D.Double(plantImage.getWidth(), plantImage.getHeight()), plantImage, 100, 30, 1);
		actors.add(plant3);
		Plant plant4 = new Plant(new Point2D.Double(2 * 75 + 55, 225 + 55), new Point2D.Double(plantImage.getWidth(), plantImage.getHeight()), plantImage, 100, 30, 1);
		actors.add(plant4);
		Plant plant5 = new Plant(new Point2D.Double(2 * 75 + 55, 300 + 55), new Point2D.Double(plantImage.getWidth(), plantImage.getHeight()), plantImage, 100, 30, 1);
		actors.add(plant5);

		// Starting Zombie(s)
		int chanceToAppear = chance.nextInt(100);
		if (chanceToAppear >= 50) //decides if it will add a zombie or not
		{
			Random randomRow = new Random();
			int randomRowYPos = (randomRow.nextInt(5)) * 75;
			Zombie zombie3 = new Zombie(new Point2D.Double(7 * 75 + 55, randomRowYPos + 55), new Point2D.Double(zombieImage.getWidth(), zombieImage.getHeight()), zombieImage, 100, 30, -0.5, 1);
			actors.add(zombie3);
		}
		
		// Make a zombie in a random row
		Random randomRow = new Random();
		int randomRowYPos = (randomRow.nextInt(5)) * 75;
		Zombie zombie2 = new Zombie(new Point2D.Double(7 * 75 + 55, randomRowYPos + 55), new Point2D.Double(zombieImage.getWidth(), zombieImage.getHeight()), zombieImage, 100, 30, -0.5, 1);
		actors.add(zombie2);

		//Create Projectiles
		Projectile projectile = new Projectile(new Point2D.Double(2 * 75 + 55, randomRowYPos + 55), new Point2D.Double(projectileImage.getWidth(), projectileImage.getHeight()), projectileImage, 1, 1, 5, 50);
		actors.add(projectile);
		
		// The timer updates the game each time it goes.
		// Get the javax.swing Timer, not from util.
		timer = new Timer(5, this); //5 is how fast the timer will go, lower is faster
		timer.start();
		}
	

	/***
	 * Implement the paint method to draw the plants
	 */
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this);
		for (Actor actor : actors) {
			actor.draw(g, 0);
			actor.drawHealthBar(g);
		}
	}

	/**
	 * 
	 * This is triggered by the timer. It is the game loop of this test.
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// This method is getting a little long, but it is mostly loop code
		// Increment their cooldowns and reset collision status
		for (Actor actor : actors) 
		{
			actor.update();
		}

		// Try to attack
		for (Actor actor : actors) 
		{
			for (Actor other : actors) 
			{
				actor.attack(other);
			}
		}

		// Remove plants and zombies with low health
		ArrayList<Actor> nextTurnActors = new ArrayList<>();
		for (Actor actor : actors) 
		{
			if (actor.isAlive()) 
			{
				nextTurnActors.add(actor);
			}
			else 
			{
				actor.removeAction(actors); // any special effect or whatever on removal
			}
		}
		
		actors = nextTurnActors;

		// Check for collisions between zombies and plants and set collision status
		for (Actor actor : actors) 
		{
			for (Actor other : actors) 
			{
				actor.setCollisionStatus(other);
			}
		}

		// Move the actors.
		for (Actor actor : actors) 
		{
			actor.move(); // only moves if it is not colliding
		}
		
		for (Actor actor : actors) 
		{
			if (actor.getPosition().getX() < 0)
			{
				System.out.println("You died oof"); //shows that the program is actually working
				timer.stop();
				app.dispose();
				return;

			}
		}
		
		
		

		// Redraw the new scene
		repaint();
	}
	
	/**
	 * Make the game and run it
	 * 
	 * @param args
	 */
	public static void main(String[] args) //actually runs the game, does not do the game loop however
	{
		// Schedule a job for the event-dispatching thread:
		javax.swing.SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{
				System.out.println("Running Plants V.S. Zombies");
				System.out.println("Created by Guyan Cool and Alexander Thomsen");
				JFrame app = new JFrame("Plants V.S. Zombies"); //Name of the Program
				app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exits (Disposes and Exits (use DISPOSE_ON_CLOSE for only Dispose) of the JPanel when pressing the exit button
				MainFile panel = new MainFile(app);
				PlantSpawnButton plantA = new PlantSpawnButton("Plant A");
				PlantSpawnButton plantB = new PlantSpawnButton("Plant B");
				JLabel resourceLabel = new ResourceLabel();
				JLabel resourceCoolDownLabel = new ResourceCoolDownLabel();
				panel.add(plantA);
				panel.add(plantB);
				panel.add(resourceLabel);
				panel.add(resourceCoolDownLabel);
				app.setContentPane(panel);
				app.pack();
				app.setVisible(true); //allows you to actually see the window
			}
		});

	}

}
