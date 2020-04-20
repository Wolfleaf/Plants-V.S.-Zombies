package a10;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

public class Projectile extends Actor {

	private boolean isColliding;
	private BufferedImage projectileImage;

	public Projectile(Double startingPosition, Double initHitbox, BufferedImage img, int health, int coolDown, double speed, int attackDamage) 
	{
		super(startingPosition, initHitbox, img, health, coolDown, speed, attackDamage);
		isColliding = false;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Move if not colliding. Only moves in x.
	 */
	@Override
	public void move() 
	{
		if (!isColliding)
			shiftPosition(new Point2D.Double(getSpeed(), 0)); //Use positive numbers so it moves right
	}

	/**
	 * Set the collision status
	 * 
	 * @param collisionStatus
	 */
	public void setColliding(boolean collisionStatus) 
	{
		isColliding = collisionStatus;
	}

	/**
	 * Get the collision status.
	 * 
	 * @return
	 */
	public boolean getColliding() 
	{
		return isColliding;
	}

	/**
	 * Set collision status on this if it overlaps with other.
	 * 
	 * @param other
	 */
	public void setCollisionStatus(Actor other) 
	{
		if (other instanceof Zombie && this.isCollidingOther(other))
		{//only collides with zombies
			setColliding(true);
			this.changeHealth(-1000);
		}
	}

	/**
	 * Update the internal state of the Actor. This means reset the collision status
	 * to false and decrement the cool down counter.
	 */
	public void update() 
	{
		isColliding = false;
		decrementCooldown();
	}
	
	public void drawHealthBar(Graphics g) //Projectiles shouldn't have healthbars, so this overrides the default method so it doesn't draw it
	{
		/*
		Point2D.Double pos = this.getPosition();
		Point2D.Double box = this.getHitbox();
	    g.setColor(Color.BLACK);  
		g.drawRect((int)pos.getX(),(int) pos.getY() - 10, (int)box.getX(), 5);  
	    g.setColor(Color.RED);  
		g.fillRect((int)pos.getX(),(int) pos.getY() - 10, (int)(box.getX() * this.health / (double)this.fullHealth), 5);  
		*/
	}

	/**
	 * An attack means the two hotboxes are overlapping and the Actor is ready to
	 * attack again (based on its cooldown).
	 * 
	 * @param other
	 */
	@Override
	public void attack(Actor other) 
	{
		if (other instanceof Zombie) //ensures it only attacks zombies
		{
			super.attack(other);
		}
	}

}
