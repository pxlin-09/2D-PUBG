package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Medical {
	
	public static final int FIRSTAID = 0;
	public static final int BANDAGE = 1;

	/*protected String name;
	protected int type;
	protected static double heal;
	protected static double useTime;
	protected double effectiveTime;
	protected Texture img;
	protected double angle;
	protected int num;
	
	protected double x,y;
	
	public Medical(String name, double heal, double useTime, double effectiveTime,
			Texture img, double angle, int num) {
		this.name = name;
		this.heal = heal;
		this.useTime = useTime;
		this.effectiveTime = effectiveTime;
		this.img = img;
		this.angle = angle;
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public static double getHeal() {
		return heal;
	}

	public static double getUseTime() {
		return useTime;
	}

	public double getEffectiveTime() {
		return effectiveTime;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public Texture getImg() {
		return img;
	}
	
	public void drawSelf(SpriteBatch batch)
	{
		TextureRegion imgR = new TextureRegion(img,0,0,img.getWidth(),img.getHeight()); // make a texture region to rotate when drawing
		batch.draw(imgR, (float)x-imgR.getRegionWidth()/2, (float)y-imgR.getRegionHeight()/2);
	}
	
	public void drawSelf(SpriteBatch batch, int mapX, int mapY)
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
        shaperender.rect(890, 30, 70, 75);
        shaperender.end();
        batch.begin();
		font.draw(batch,name,900,75);
		font.draw(batch, num + "", 900,50);
		batch.end();
		
	}*/
	
}

class FirstAid {
	private static int heal = 75;
	private static int useTime = 8;
	
	public FirstAid()
	{
		//
	}
	
	public static int getHeal()
	{
		return heal;
	}
	
	public static int getUseTime()
	{
		return useTime;
	}
	
	/*public FirstAid(String name, int heal, double useTime,
			double effectiveTime, Texture img, double angle, int num) {
		super(name, heal, useTime, effectiveTime, img, angle, num);
		name = "first aid";
		heal = 75;
		useTime = 8;
		effectiveTime = 1;
		type = FIRSTAID;
	}*/
	

}

class Bandage {
	private static int heal = 10;
	private static int useTime = 4;
	
	public Bandage()
	{
		//
	}
	
	public static int getHeal()
	{
		return heal;
	}
	
	public static int getUseTime()
	{
		return useTime;
	}
	/*public Bandage(String name, double heal, double useTime, double effectiveTime,
			Texture img, double angle, int num) {
		super(name, heal, useTime, effectiveTime, img, angle, num);
		name = "Bandage";
		heal = 10;
		useTime = 4;
		effectiveTime = 1;
		type = BANDAGE;
	}*/

}
