package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;

public class Character
{
	public static final int PRIMARY = 1;
	public static final int SECONDARY = 2;
	
	private boolean alive;	// true if user hp>0
	private Weapon using;	// the gun character is using
	private Weapon primary;	// first weapon of character
	private Weapon secondary;	// second weapon of character
	private double angle;	// direction character is facing
	private double x,y;	// position of character
	private Texture img;	// img of character
	private double hp;	// character health
	
	// character armor and halmet
	private Armor armor;
	private Halmet halmet;
	
	// points the cpu will travel inbetween
	private double beginX;	
	private double beginY;
	private double endX;
	private double endY;
	
	// current point that the character is travelling towards
	private double targetX, targetY;
	private boolean target;	// the cpu will not move when they spot a target

	private Circle self; // radius of character image is 22
	
	private Sound deathSound;	// sound will play at death
	
	// this limits the fire rate of bots
	private long fireTime;	
	private long nextTime;
	
	private int kills; // number of kills
	
	// number of medical;
	private int firstAid;
	private int bandage;
	
	public Character()	// constructor
	{
		alive = true;
		primary = null;
		secondary = null;
		self = new Circle();
		hp = 100.0;
		angle = 0;
		kills = 0;
		fireTime = nextTime = 0;
		armor = null;
		
		firstAid = bandage = 6;
	}
	
	public Circle getSelf()
	{
		return self;
	}
	
	public boolean isAlive()
	{
		return alive;
	}
	
	public void setDeathSound(Sound deathSound)
	{
		this.deathSound = deathSound;
	}
	
	public void addFirstAid()
	{
		firstAid++;
	}
	
	public void addBandage()
	{
		bandage++;
	}
	
	public int getFirstAid()
	{
		return firstAid;
	}
	
	public int getBandage()
	{
		return bandage;
	}
	
	public void useMeds(double heal)	// regain heal amount of health
	{
		hp = hp+heal >100? 100 : hp+heal;
	}
	
	public void usefirstAid()
	{
		if(firstAid>0)
		{
			firstAid--;
			useMeds(FirstAid.getHeal());
		}
	}
	
	public void useBandage()
	{
		if(bandage>0)
		{
			bandage--;
			useMeds(Bandage.getHeal());
		}
	}

	public void getHit(double dmg, Character user, int mapX, int mapY , Character shotFrom)	// takes away health of character when shot by bullet
	{
		double bodyDmg = 0, headDmg = 0;
		if(alive)
		{
			Random rand = new Random();		
			bodyDmg = armor != null? armor.damageArmor(dmg) : dmg; // damage on body
			
			headDmg = rand.nextInt(5) == 0? dmg : 0;	// 20% chance to head shot
			headDmg = halmet != null? halmet.damageArmor(headDmg):headDmg;
			
			hp = hp - bodyDmg - headDmg;	// calculate total damage on body
			
			if(armor != null && armor.getDurability() <= 0) armor = null;	// no longer have the protection if it is no longer durable
			if(halmet != null && halmet.getDurability() <= 0) halmet = null;
			
			if(hp <= 0)
			{
				hp = 0;
				alive = false;
				if(Math.abs(user.getSelf().x-mapX - self.x) <= 500 && Math.abs(user.getSelf().y-mapY-self.y) < 250)
				{
					deathSound.play();	// will only play death sound if user can see it
				}
				if(this.equals(user))
				{
					deathSound.play();	// will only play death sound if user can see it
				}
				shotFrom.addKill();
			}
		}
	}

	public void getHitInStorm() {
		// when the character is in the storm, he loses 1 hp per sec
		if (alive) {
			hp-=0.005;			// 0.005*200 = 1 since 200 refreshes per sec
			if (hp == 0) {
				alive = false;
				deathSound.play();
			}
		}
	}
	
	public void setArmor(Armor armor)
	{
		if(armor.getDurability()>0) this.armor = armor;
	}
	
	public void setHalmet(Halmet halmet)
	{
		if(halmet.getDurability()>0) this.halmet = halmet;
	}
	
	public void setImg(Texture img)
	{
		this.img = img;
	}
	
	public void setPos(double x, double y)
	{
		self.set( (float) x, (float) y, (float) 22);
	}
	
	public void setUsing(int weapon)	// set the weapon for character to use
	{
		if(weapon == PRIMARY)
		{
			using = primary == null? using:primary;
		}
		else if(weapon == SECONDARY)
		{
			using = secondary == null? using:secondary;
		}
	}
	
	public Weapon getPrimary()
	{
		return primary;
	}
	
	public Weapon getSecondary()
	{
		return secondary;
	}
	
	public void setPrimary(Weapon primary)
	{
		this.primary = primary;
		using = primary;
	}
	
	public void setSecondary(Weapon secondary)
	{
		this.secondary = secondary;
	}
	
	public double getAngle()
	{
		return angle;
	}
	
	public void addKill()	// add a kill for character
	{
		kills++;
	}
	
	public int getKills()
	{
		return kills;
	}
		
	public double getX()
	{
		return self.x;
	}
	
	public double getY()
	{
		return self.y;
	}
	
	public double getImgW()
	{
		return img.getWidth();
	}
	
	public double getImgH()
	{
		return img.getHeight();
	}
	
	public Weapon getUsing()
	{
		return using;
	}
	
	public ArrayList<Weapon> getWeapons()	// return a list containing all the weapons that the character has
	{
		ArrayList<Weapon> weapons = new ArrayList<Weapon>();	// make a list of weapons
		
		// add each weapon in
		if(primary != null)
		{
			weapons.add(primary);
		}
		if(secondary != null)
		{
			weapons.add(secondary);
		}
		return weapons;
	}
	
	public ArrayList<Bullet> fire(Character user, int mapX, int mapY)	// fire a gun
	{
		return using == null? null:using.fire(this, user, mapX, mapY);	// fire the using gun
	}
	
	public void turn(int mouseX, int mouseY)	// rotate the character according to a point
	{
		// angle calculation is different according to where the point is
		if(mouseX == self.x)	// if point is directed above or below
		{
			angle = mouseY > self.y? Math.PI/2 : 3*Math.PI/2;
		}
		else if(mouseX >= self.x && mouseY >= self.y)	// if point is right and up
		{
			angle = Math.atan((mouseY-self.y)/(mouseX-self.x));
		}
		else if(mouseX <= self.x && mouseY >= self.y)	// if point is left and up
		{
			angle = Math.atan((mouseY-self.y)/(mouseX-self.x))+Math.PI;
		}
		else if(mouseX <=self.x && mouseY <= self.y)	// if point is left and down
		{
			angle = Math.atan((mouseY-self.y)/(mouseX-self.x))+Math.PI;
		}
		else if(mouseX >= self.x && mouseY <= self.y)	// if point is right and down
		{
			angle = Math.atan((mouseY-self.y)/(mouseX-self.x));
		}
	}
	
	public void setPath(double bX, double bY, double eX, double eY)	// set a path for character to travel
	{
		// set the x and y of the two points
		beginX = bX;
		beginY = bY;
		endX = eX;
		endY = eY;
		
		// first travel towards the end point
		targetX = eX;
		targetY = eY;
	}
	
	public void pathMove()	// travel between the points
	{
		if(!target)
		{
			turn((int)targetX, (int)targetY);	// rotate the character to face the direction of target location
			if(targetX == endX && targetY == endY)	// if target is end point
			{
				// will move until current location and target spot is within 2 pixels
				if(Math.abs(self.x - targetX) > 2) self.x+= 2*Math.cos(angle);	
				if(Math.abs(self.y - targetY) > 2) self.y+= 2*Math.sin(angle);
				if(Math.abs(self.x - targetX) < 2 && Math.abs(self.y - targetY) < 2)
				{
					// target becomes beginning point
					targetX = beginX;
					targetY = beginY;
				}
			}
			else	// if target is beginning point
			{
				// will move until current location and target spot is within 2 pixels
				if(Math.abs(self.x - targetX) > 2) self.x+= 2*Math.cos(angle);
				if(Math.abs(self.y - targetY) > 2) self.y+= 2*Math.sin(angle);
				if(Math.abs(self.x - targetX) < 2 && Math.abs(self.y - targetY) < 2)
				{
					// target becomes end point
					targetX = endX;
					targetY = endY;
				}
			}
		}	
	}
	
	public void displayStats(BitmapFont font, SpriteBatch batch, ShapeRenderer shaperender)
	{
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);	// allow alpha 
		
		// draw the rectangles, these are used as backgrounds to better display the words
		shaperender.begin(ShapeType.Filled);
        shaperender.setColor((float)0.3, (float)0.3, (float)0.3, (float)0.8);
        shaperender.rect(90, 30, 130, 30);
        shaperender.rect(90, 70, 130, 30);
        shaperender.rect(90, 110, 130, 30);
        shaperender.rect(240, 30, 130, 30);
        shaperender.rect(240, 70, 130, 30);
        shaperender.end();
        
        String hpDis = "HP: "+ (float) hp;	// health   
        batch.begin();
		font.draw(batch,hpDis,100,130);	// display health
		font.draw(batch,"BANDAGE: "+bandage,250,90);
		font.draw(batch,"FIRSTAID: "+firstAid,250,50);
		batch.end();
		
		if(armor!=null)
		{
			armor.displaySelf(font, batch);	// display armor statistics
		}
		else
		{
			batch.begin();
			font.draw(batch, "NO ARMOR", 100,50);	// display "NO ARMOR" if there is no armor
			batch.end();
		}
		
		if(halmet!=null)
		{
			halmet.displaySelf(font, batch);	// halmet statistics
		}
		else
		{
			batch.begin();
			font.draw(batch, "NO HALMET", 100, 90);	// display "NO HALMET" if there is no halmet
			batch.end();
		}
	}
	
	public void detectEnemy(ArrayList<Character> characters, Character user, int mapX, int mapY)	// check if there is any other character in shooting range
	{
		double posX = Double.NEGATIVE_INFINITY, posY = Double.NEGATIVE_INFINITY;
		for(Character c : characters)
		{
			if(!c.equals(this))	// if the character is not self
			{
				if(c.equals(user))	// if character is user
				{	
					posX = user.getSelf().x-mapX;	// get user's position in terms of on the map
					posY = user.getSelf().y-mapY;
				}
				else
				{
					posX = c.getSelf().x;	// get the character position on map
					posY = c.getSelf().y;
				}
			}
			
			double deltaX = Math.abs(self.x - posX);
			double deltaY = Math.abs(self.y-posY);
			if(Math.hypot(deltaX, deltaY) < c.getUsing().getRange())	// if another character is within shooting range
			{
				target = true; // enemy detected
				turn((int)posX, (int)posY);	// turn towards the character to shoot against
				break;
			}
			target = false; // if no character is in shooting range
		}
	}
	
	public ArrayList<Bullet> fireTarget(long pastTime, Character user, int mapX, int mapY)	// fire at the target
	{
		ArrayList<Bullet> bullets = new ArrayList<Bullet>();
		if(target)	// if there is a target
		{
			if(nextTime - fireTime <= 0)	// checks if the bot can fire
			{
				if(using.getAmo() > 0)
				{
					bullets = fire(user, mapX, mapY);
					
					// limit the frequency that the bot will fire at according to the weapon they are using
					fireTime = pastTime;
					nextTime = pastTime + using.getType()*100 + 400; 
				}
				else if(using.getRemainAmo() > 0)	// reload if out of amo
				{
					using.reload();
				}
				else if(using.equals(primary))
				{
					using = secondary;
				}
			}
			fireTime = pastTime;	// update time
		}
		return bullets;
	}
	public void drawSelf(SpriteBatch batch)	// draw the character
	{
		TextureRegion imgR = new TextureRegion(img,0,0,img.getWidth(),img.getHeight()); // make a texture region to rotate when drawing
		batch.draw(imgR, (float)self.x-imgR.getRegionWidth()/2, (float)self.y-imgR.getRegionHeight()/2, (float)img.getWidth()/2, (float)img.getHeight()/2, (float)img.getWidth(), (float)img.getHeight(),(float)1,(float)1, (float)Math.toDegrees(angle));
	}
	
	public void cpuDrawSelf(SpriteBatch batch, int mapX, int mapY)	// draw cpu 
	{

		TextureRegion imgR = new TextureRegion(img,0,0,img.getWidth(),img.getHeight()); // make a texture region to rotate when drawing
		batch.draw(imgR, (float)self.x-imgR.getRegionWidth()/2+mapX, (float)self.y-imgR.getRegionHeight()/2+mapY, (float)img.getWidth()/2, (float)img.getHeight()/2, (float)img.getWidth(), (float)img.getHeight(),(float)1,(float)1, (float)Math.toDegrees(angle));
	}
	
	public void CpudisplayStats(BitmapFont font, SpriteBatch batch, int mapX, int mapY)	// show the stats of each bot (display for debug only)
	{
        // get the hp of bot and display it
		String hpDis = "HP: "+String.format("%.1f",hp);     
		font.draw(batch,hpDis,self.x-15+mapX, self.y+25+mapY);
		
		// get the armor of bot and display it
		String armorDis= armor != null? "ARMOR: "+String.format("%.1f",armor.getDurability()) : "NO ARMOR";
		font.draw(batch,armorDis,self.x-15+mapX, self.y-25+mapY);
		
		// get the halmet of bot and display it
		String halmetDis = halmet != null? "HALMET: "+String.format("%.1f",halmet.getDurability()) : "NO HALMET";
		font.draw(batch,halmetDis,self.x-15+mapX, self.y-50+mapY);
	}

	public double getHp() {
		return hp;
	}

}
