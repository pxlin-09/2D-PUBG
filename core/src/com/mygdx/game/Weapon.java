package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Weapon
{
	// this class will be the weapons of the game, there are 7 types of weapons
	public static final int HANDGUN = 0;
	public static final int SUBMACHINEGUN = 1;
	public static final int MACHINEGUN = 2;
	public static final int RIFLE = 3;
	public static final int SHOTGUN = 4;
	public static final int SNIPER = 5;
	
	// types of mag
	public static final int QUICK = 1;
	public static final int EXTEND = 2;
	
	protected String name;	// name of weapon
	protected int type;
	protected double dmg;	// on hit damage
	protected double range;	// range of weapon
	protected double amo;	// amo that can be used without reload
	protected double loadAmo;	// the amount that can be loaded
	protected double remainAmo;	// amo stored
	protected double angle;	// direction the weapon is pointing at
	protected double bSpeed; // bullet speed
	protected Texture img;	// image of gun
	
	// firing sound and sound when there is no ammo in mag
	protected Sound fireSound;
	protected Sound outAmo;
	
	protected int mag;	// type of mag for the weapon
	protected double x,y;	// position of weapon
	
	public Weapon(String name, double dmg, double range, double amo, double loadAmo, double remainAmo, double angle, double bSpeed)
	{
		this.name = name;
		this.dmg = dmg;
		this.range = range;
		this.amo = amo;
		this.loadAmo = loadAmo;
		this.remainAmo = remainAmo;
		this.angle = angle;
		this.bSpeed = bSpeed;
		
		mag = 0;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getType()
	{
		return type;
	}
	
	public void setType(int type)
	{
		this.type = type;
	}
	
	public double getRange()
	{
		return range;
	}
	public double getImgH()
	{
		return img.getHeight();
	}
	
	public double getImgW()
	{
		return img.getWidth();
	}
	
	public void setImg(Texture img)	
	{
		this.img = img;
	}
	
	public double getAmo()
	{
		return amo;
	}
	
	public double getLoadAmo()
	{
		return loadAmo;
	}
	
	public double getRemainAmo()
	{
		return remainAmo;
	}
	
	public void setRemain(double amo)
	{
		remainAmo = amo;
	}
	
	public void setPos(double x, double y)	// set position of weapon
	{
		this.x = x;
		this.y = y;
	}
	
	public void setAngle(double angle)
	{
		this.angle = angle;
	}
	
	public void setFireSound(Sound fireSound, Sound outAmo)
	{
		this.fireSound = fireSound;
		this.outAmo = outAmo;
	}
	
	public Sound getFire()
	{
		return fireSound;
	}
	
	public int getMag()
	{
		return mag;
	}
	
	public void setMag(int mag)
	{
		if(this.mag == EXTEND)	// if the mag that is already on the gun
		{
			loadAmo -= 10;
		}
		
		this.mag = mag;
		
		if(mag == EXTEND)
		{
			loadAmo += 10;	// extend mag allows weapon to reload 10 extra bullet
		}
	}
	
	public void reload()	//	reload weapon
	{
		if (amo<loadAmo && remainAmo > 0)	// if the mag is not full and there is amo left
		{
			double newAmo = amo+remainAmo > loadAmo? loadAmo : remainAmo+amo;	// reload the gun
			remainAmo = remainAmo - newAmo + amo;	// take away the loaded amo from remaining
			amo = newAmo;	// set new ammo in mag
		}
		
	}
	
	public void drawSelf(SpriteBatch batch)	// draw the weapon (for user)
	{
		TextureRegion imgR = new TextureRegion(img,0,0,img.getWidth(),img.getHeight()); // make a texture region to rotate when drawing
		batch.draw(imgR, (float)x-imgR.getRegionWidth()/2, (float)y-imgR.getRegionHeight()/2, (float)img.getWidth()/2, (float)img.getHeight()/2, (float)img.getWidth(), (float)img.getHeight(),(float)1,(float)1, (float)Math.toDegrees(angle));
	}
	
	public void drawSelf(SpriteBatch batch, int mapX, int mapY)	// draw the weapon (for cpu)
	{
		TextureRegion imgR = new TextureRegion(img,0,0,img.getWidth(),img.getHeight()); // make a texture region to rotate when drawing
		batch.draw(imgR, (float)x-imgR.getRegionWidth()/2+mapX, (float)y-imgR.getRegionHeight()/2+mapY, (float)img.getWidth()/2, (float)img.getHeight()/2, (float)img.getWidth(), (float)img.getHeight(),(float)1,(float)1, (float)Math.toDegrees(angle));
	}
	
	
	public void display(BitmapFont font, SpriteBatch batch, ShapeRenderer shaperender)	// display the gun name and the ammo amount
	{
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);	// allow alpha 
		shaperender.begin(ShapeType.Filled);
        shaperender.setColor((float)0.3, (float)0.3, (float)0.3, (float)0.8);
        shaperender.rect(890, 30, 80, 75);
        shaperender.end();
        batch.begin();
		font.draw(batch,name,900,75);	// display name of gun and ammo stats
		font.draw(batch, (int)amo+" / "+(int)remainAmo, 900,50);
		batch.end();
		
	}
	
	public ArrayList<Bullet> fire(Character shotFrom, Character user, int mapX, int mapY)	// shots a bullet
	{
		Random rand = new Random();
		ArrayList<Bullet> bullets = new ArrayList<Bullet>();
		if(amo>0)	// can only shoot a bullet if there is bullet(s) in the clip
		{
			if(Math.abs(user.getSelf().x-mapX - shotFrom.getSelf().x) <= 500 && Math.abs(user.getSelf().y-mapY-shotFrom.getSelf().y) < 250)
			{
				fireSound.play();	// play the shooting sound if user
			}
			if(shotFrom.equals(user))
			{
				fireSound.play();	
			}
			
			amo -= 1;
			double newAngle = type != SNIPER? angle + rand.nextDouble()/10-type/100 : angle; // add spread of bullets according to the gun
			Bullet b = new Bullet(dmg, bSpeed, newAngle,range, shotFrom);	// make the bullet
			b.fly();
			bullets.add(b);	
		}
		else	// if there is no bullets left
		{
			outAmo.play();
		}
		return bullets;
	}
	
	public Weapon copy() // return a clone of the class
	{
		Weapon newWeapon = new Weapon(name, dmg, range, amo, loadAmo, remainAmo, angle, bSpeed);
		newWeapon.setImg(img);	// copy the image, type, and sounds
		newWeapon.setType(type);
		newWeapon.setFireSound(fireSound, outAmo);
		return newWeapon;
	}
}
