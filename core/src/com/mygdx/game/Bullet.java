package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bullet
{	
	protected double dmg;	// the damage the bullet does
	protected double speed;	// how fast the bullet flies
	protected double angle;	// the direction of bullet
	protected double range;	// how far the bullet can travel
	protected double flewDistance;	// how far the bullet already travelled
	protected boolean fly;	// true if bullet is still moving
	protected Texture img;	// img of bullet
	
	protected double x,y;	// position of bullet
	protected double inX, inY; // initial x, y
	
	private Character shotFrom;	// who shot the bullet
	
	public Bullet(double dmg, double speed, double angle, double range, Character shotFrom)	// constructor
	{
		this.dmg = dmg;
		this.speed = speed;
		this.angle = angle;
		this.range = range;
		this.shotFrom = shotFrom;
		fly = true;
	}
	
	public void setPos(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	public double getX() 
	{
		return x;
	}
	public double getY() 
	{
		return y;
	}
	public Texture getImg()
	{
		return img;
	}
	public boolean isFlying()
	{
		return fly;
	}
	public void setImg(Texture img)
	{
		this.img = img;
	}
	
	public void fly()	// move the bullet
	{
		if(fly)
		{
			x+=Math.cos(angle)*speed;	// find the x and y component
			y+=Math.sin(angle)*speed;
			flewDistance+=speed;	// move bullet according to speed
			if(flewDistance > range)	// stop moving at max range
			{
				fly = false;
			}
		}
	}
	
	public void checkHit(ArrayList<Character> characters, int mapX, int mapY, Character user)	// checks if bullet hits any character
	{
		for(Character c : characters)
		{
			if(!c.equals(shotFrom))	// cannot shot yourself
			{
				if(c.equals(user))	// check if bullet will hit user
				{
					if(c.getSelf().contains((float)x+mapX, (float)y+mapY))
					{
						fly = false;
						c.getHit(dmg, user, mapX, mapY, shotFrom);
					}
				}
				else if(c.getSelf().contains((float)x, (float)y))	// check if bullet will hit cpu
				{
					fly = false;
					c.getHit(dmg, user, mapX, mapY, shotFrom);
				}
			}
		}
	}
	
	public void drawSelf(SpriteBatch batch, int mapX, int mapY)	// draw bullet
	{
		TextureRegion imgR = new TextureRegion(img,0,0,img.getWidth(),img.getHeight()); // make a texture region to rotate when drawing
		batch.draw(imgR, (float)x-imgR.getRegionWidth()/2+mapX, (float)y-imgR.getRegionHeight()/2+mapY, (float)img.getWidth()/2, (float)img.getHeight()/2, (float)img.getWidth(), (float)img.getHeight(),(float)1,(float)1, (float)Math.toDegrees(angle));
	}
}
