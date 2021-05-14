package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public abstract class Armor
{
	private int durability;

	//discount the durability and return 
	//how much it damaged to the character
	public abstract double getDurability();
	
	public abstract double damageArmor(double damage); 
	
	public abstract void displaySelf(BitmapFont font, SpriteBatch batch);
	
}

class LevelOneArmor extends Armor 
{
	private final static double reductionRate = 0.3;
	private double durability;

	public LevelOneArmor() {
		durability = 200;
	}

	public LevelOneArmor(int Durability) {
		this.durability = Durability;
	}

	public double getDurability() {
		return durability;
	}

	public double damageArmor(double damage) {
		double old = durability;
		durability -= damage * reductionRate;
		durability = durability < 0 ? 0 : durability;
		return damage - (old - durability);
	}
	
	public void displaySelf(BitmapFont font, SpriteBatch batch)
	{   
        String armorDis = "LV1 ARMOR: "+String.format("%.1f", durability);
        
        batch.begin();
		font.draw(batch, armorDis, 100,50);
		batch.end();
	}
}

class LevelTwoArmor extends Armor
{
	private final static double reductionRate = 0.4;
	private double durability;

	public LevelTwoArmor() {
		durability = 220;
	}

	public LevelTwoArmor(int Durability) {
		this.durability = Durability;
	}

	public double getDurability() {
		return durability;
	}

	public double damageArmor(double damage) {
		double old = durability;
		durability -= damage * reductionRate;
		durability = durability < 0 ? 0 : durability;
		return damage - (old - durability);
	}
	
	public void displaySelf(BitmapFont font, SpriteBatch batch)
	{
        String armorDis = "LV2 ARMOR: "+String.format("%.1f", durability);
        
        batch.begin();
		font.draw(batch, armorDis, 100,50);
		batch.end();
	}
}

class LevelThreeArmor extends Armor
{
	private final static double reductionRate = 0.55;
	private double durability;

	public LevelThreeArmor() {
		durability = 250;
	}

	public LevelThreeArmor(int Durability) {
		this.durability = Durability;
	}

	public double getDurability() {
		return durability;
	}

	public double damageArmor(double damage) {
		double old = durability;
		durability -= damage * reductionRate;
		durability = durability < 0 ? 0 : durability;
		return damage - (old - durability);
	}
	
	public void displaySelf(BitmapFont font, SpriteBatch batch)
	{ 
        String armorDis = "LV3 ARMOR: "+String.format("%.1f", durability);
        
        batch.begin();
		font.draw(batch, armorDis, 100,50);
		batch.end();
	}
}
