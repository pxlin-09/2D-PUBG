package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Halmet {
	protected double durability;
	//discount the durability and return 
	//how much it damaged to the character
	public abstract double getDurability();
	
	public abstract double damageArmor(double damage); 
	
	public abstract void displaySelf(BitmapFont font, SpriteBatch batch);
}


class LevelOneHalmet extends Halmet{
	private final static double reductionRate = 0.3;

	public LevelOneHalmet() {
		durability = 80;
	}

	public LevelOneHalmet(double Durability) {
		this.durability = Durability;
	}

	public double damageArmor(double damage) {
		double old = durability;
		durability -= damage * reductionRate;
		durability = durability < 0 ? 0 : durability;
		return damage - (old - durability);
	}

	@Override
	public double getDurability() {
		return durability;
	}

	@Override
	public void displaySelf(BitmapFont font, SpriteBatch batch) {
		String armorDis = "LV1 HALMET: "+String.format("%.1f", durability);
        batch.begin();
		font.draw(batch, armorDis, 100, 90 );
		batch.end();
	}

}

class LevelTwoHalmet extends Halmet{
	private final static double reductionRate = 0.4;

	public LevelTwoHalmet() {
		durability = 150;
	}

	public LevelTwoHalmet(double Durability) {
		this.durability = Durability;
	}

	public double damageArmor(double damage) {
		double old = durability;
		durability -= damage * reductionRate;
		durability = durability < 0 ? 0 : durability;
		return damage - (old - durability);
	}

	@Override
	public double getDurability() {
		return durability;
	}

	@Override
	public void displaySelf(BitmapFont font, SpriteBatch batch) {
		String armorDis = "LV2 HALMET: "+String.format("%.1f", durability);
        batch.begin();
		font.draw(batch, armorDis, 100,90);
		batch.end();
	}

}


class LevelThreeHalmet extends Halmet{
	private final static double reductionRate = 0.55;

	public LevelThreeHalmet() {
		durability = 250;
	}

	public LevelThreeHalmet(double Durability) {
		this.durability = Durability;
	}

	public double damageArmor(double damage) {
		double old = durability;
		durability -= damage * reductionRate;
		durability = durability < 0 ? 0 : durability;
		return damage - (old - durability);
	}

	@Override
	public double getDurability() {
		return durability;
	}

	@Override
	public void displaySelf(BitmapFont font, SpriteBatch batch) {
		String armorDis = "LV3 HALMET: "+String.format("%.1f", durability);
		batch.begin();
		font.draw(batch, armorDis, 100, 90);
		batch.end();
		
	}

}

