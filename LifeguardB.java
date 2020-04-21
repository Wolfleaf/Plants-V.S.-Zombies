package a10;

import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

public class LifeguardB extends Lifeguard
{
	private int projectileCooldown;

	public LifeguardB(Double startingPosition, Double initHitbox, BufferedImage img, int health, int coolDown,
			int attackDamage, int projectileCooldown_) 
	{
		super(startingPosition, initHitbox, img, health, coolDown, attackDamage);
		projectileCooldown = projectileCooldown_;
	}
	
	public int getCooldown() 
	{
		return projectileCooldown;
	}
	
	public void setCooldown(int newCooldown)
	{
		this.projectileCooldown = newCooldown;
	}
	
	public void update() 
	{
		decrementCooldown();
		projectileCooldown--;
	}

}
