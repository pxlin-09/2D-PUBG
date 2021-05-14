// MyGdxGame.java
// this is the main file that connects all other classes and run the game
// Game Name: Mission Possible Express
// developed by Yanbo Hou, Alex Lin, Lizhuo You
// game type: 2d pubg, surviving on an island and gun fight with AIs.

// features:
/*
general feature:
1. Multiple classes which increased 
2. Multiple methods to reduce the length of the code and make the code clean and nice.
3. clean structure.
4. clean field names.
*/
/*
Map features:
1. randomly computer generated map, with uncertainty for the user to explore.
2. it has a mini map display so user can easily understand where they are. Also, it tells user where the safe zone is
    so the user can survive with ease.
3. with seamless mysterious storm that appears both on the real map and mini map
4. 3 mysterious storm with 3 different locations and sizes. The mysterious storms are randomly generated as well.
5. obstacles are randomly generated and only on good grounds such as dirt or grass.
6. the movement of the user is SUPER-FLUENT, there is a seamless direction changing.
7. User's speed is depending on the user's location so 
 */
/*
Character features:
1. User¡¯s character will be in the Center of the displaying screen
2. All the bot characters will be displayed relatively to the user¡¯s position
3. Bot characters have setPath and movePath method that allows it to move on the map automatically
4. The method detectEnemy allows the bots to detect other characters that are in shooting range and will fire at them
5. When a character is hit by a bullet, there is a 20% chance that the shot will be a headshot where the damage will
    double. If the user has armour and/or helmet, depending on the level of it, damage will be reduced accordingly.

Weapons
1. There are 6 types of weapons: handgun sub machine gun, machine gun, rifle, shotgun, and sniper.
    Some of those support multi fire, and others only fire once per mouse click
2. When in game, the user gets two random weapons to start with, the user can switch back and forth between weapon
:
by
    clicking numbers 1 and 2 on the keyboard
3. When a weapon is fired, there will be a percent spread on the bullets according to the type of gun. Sniper guns will
    have no spread, and shot guns will fire 5 bullets per shot.
4. To reload the gun, the user needs to click r using keyboard. When reloading, a timer will be set. Once the timer
    ends, the gun will reload. While the timer is on, switching weapons or using meds will cancel the reload in process.

Medication
1. There are two types of medication, first aid and bandage, each has its own time requirement to use, and amount
    that it can heal.
2. The user can use meds only when health is below 100, the user can use it by clicking 3 or 4 on the keyboard.
3. When using meds, a timer will be set, and words ¡°using meds...¡± will be displayed on the screen. If the user
    choose to switch weapons, it will cancel the healing process.

Sound
1. Every character will make a sound upon death. However, the sound will only be played when the dying character is
    in sight of the character.
2. Same thing as to weapon sounds. When a weapon is firing bullets, there is a unique sound for every type of weapon.
    When the gun is out of ammo and user still fired it, an out of ammo sound will be played. User can here firing
    sounds from other characters when they are in sight.

Displaying near enemies
1. Since there are 99 bots in the map and the map is huge, exclamation marks are used to point at near enemies. When
    the user approaches bots that are within 1500 pixels, exclamation marks will be drawn. These exclamation
    marks will not be drawn once the bots are displayed on the screen or more then 1500 pixels away from user.
 */
 /*
intro features:
1. after showing the loading page, the screen will be darkening
out and then switch to the next page
2. there is a dynamic loading circle which will rotates for one
round in 1.2 second
3. there is a dynamic loading line which will loading in a random amount 
 */
package com.mygdx.game;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.TimeUtils;


public class MyGdxGame extends ApplicationAdapter implements InputProcessor,
		TextInputListener {
	// general global fields
	private SpriteBatch batch;

	private Tools tools;
	private int game_runningTime;
	private long timeStart, timeNow; // ensure the fluency of the game
	private boolean horizontalMoveL, horizontalMoveR, horizontalMoveFinal; // 3
																			// move
																			// fields
																			// to
																			// let
																			// user
																			// freely
																			// and
																			// smoothly
																			// move
	private boolean verticalMoveU, verticalMoveD, verticalMoveFinal;

	private ShapeRenderer shaperender;

	private Texture youWin;
	private Texture youLost;

	// intro fields
	private int mx, my; // mouse x, mouse y
	private String username;
	private User u;
	private Menu m;
	private ArrayList<String> text;
	private Sprite bg, start, loading, menu, login; // sprite used in Intro
	private boolean gameStart, windowOpen, loadGame, inMenu; // all the switches
																// that control
																// which part is
																// shown
	private int direction; // 0 -- to intro, 1 -- to game
	private double loadingPercent; // how much loading is complete
	private BitmapFont Menu_fontT, Menu_font; // font used in menu
	private ShapeRenderer shapeRenderer;

	// map fields
	private MapDesign map_MapDesign;
	private int map_x, map_y;
	private Texture map_mapBase;
	private Texture map_mapObstacles;

	private int map_mysteriousStorm_stage; // four stages 0-4, from 0 storm to 3
											// storms.
	private double map_mysteriousStorm_radius;
	private Texture map_coverT0;

	private int map_mysteriousStorm_centerX1;
	private int map_mysteriousStorm_centerY1;
	private Texture map_coverT1;

	private int map_mysteriousStorm_centerX2;
	private int map_mysteriousStorm_centerY2;
	private Texture map_coverT2;

	private int map_mysteriousStorm_centerX3;
	private int map_mysteriousStorm_centerY3;
	private Texture map_coverT3;

	private int map_miniCoverBlinking;
	private Texture map_coverTM1;
	private Texture map_coverTM2;
	private Texture map_coverTM3;
	private Texture map_coverTM1_safezone;
	private Texture map_coverTM2_safezone;
	private Texture map_coverTM3_safezone;

	// Character fields
	private Scanner inFile;
	private Random rand = new Random();

	private boolean reload = false;
	private boolean healing = false;
	private int medicine = -1;
	private int mouseX = 0, mouseY = 0;

	private Texture bulletImg, CharacterImg, dangerImg;
	private Texture[] gunImgs, CharacterImgs;

	private Character user, cpu1;

	private Weapon gun, gun2;
	private Weapon[][] weapons;

	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	private BitmapFont font;
	private long pastTime = 0, desTime = 0, waitTime = 0, fireTime = 0;
	private float prevTime = 0;

	private ArrayList<Character> Characters = new ArrayList<Character>();
	private Sound combatSound, handGunSound, subMachineGunSound,
			machineGunSound, rifleSound, shotGunSound, sniperSound,
			outAmoSound;
	private Sound deathSound;

	// alex-------------------------------------------------------------------------

	public void map_minimapMysteriousStorm(Texture cover, Texture safezone) {
		// it is for map_minimapDisplay
		// the alpha blinks to show its emergent

		// put the cover first
		if (map_miniCoverBlinking < 100) {
			map_miniCoverBlinking++;
		} else if (map_miniCoverBlinking < 250) {
			map_miniCoverBlinking++;
			batch.begin();
			batch.draw(cover, 0, 300);
			batch.end();
		} else {
			map_miniCoverBlinking = 0;
		}

		// draw safe zone
		batch.begin();
		batch.draw(safezone, 0, 300);
		batch.end();
	}

	public void map_minimapDisplay() {
		// it draws a mini map on top left corner to indicate player's location
		// everything comparing to real map should have a 1/60 ratio

		// draw a frame around the mini display
		shaperender.begin(ShapeRenderer.ShapeType.Filled);
		shaperender.setColor(Color.LIGHT_GRAY);
		shaperender.rect(0, 298, 203, 203);
		shaperender.end();

		batch.begin();
		batch.draw(map_mapBase, 0, 300, 200, 200); // display size = 12000/60
		batch.draw(map_mapObstacles, 0, 300, 200, 200);
		batch.end();

		shaperender.begin(ShapeRenderer.ShapeType.Filled);
		shaperender.setColor(Color.RED);
		shaperender.circle(8 - map_x / 60, 305 - map_y / 60, 4);
		shaperender.end();

		// show safe zone for mysterious storm
		if (map_mysteriousStorm_stage == 1) {
			map_minimapMysteriousStorm(map_coverTM1, map_coverTM1_safezone); // for
																				// radius
																				// r
																				// =
																				// 3000/60
		} else if (map_mysteriousStorm_stage == 2) {
			map_minimapMysteriousStorm(map_coverTM2, map_coverTM2_safezone); // for
																				// radius
																				// r
																				// =
																				// 1000/60
		} else if (map_mysteriousStorm_stage == 3) {
			map_minimapMysteriousStorm(map_coverTM3, map_coverTM3_safezone); // for
																				// radius
																				// r
																				// =
																				// 300/60
		}
	}

	public Texture map_coverConstructor(boolean hasHole, int coverWidth,
			int coverHeight, Color color, int circleX, int circleY,
			int circleRadius) {
		Pixmap cover = new Pixmap(coverWidth, coverHeight,
				Pixmap.Format.RGBA8888);
		Texture coverT = new Texture(coverWidth, coverHeight,
				Pixmap.Format.RGBA8888);
		cover.setBlending(Pixmap.Blending.SourceOver);
		cover.setColor(color); // purple
		cover.fill();
		if (hasHole) {
			cover.setBlending(Pixmap.Blending.None);
			cover.setColor(new Color(1, 1, 1, 0));
			cover.fillCircle(circleX, circleY, circleRadius);
		}
		coverT.draw(cover, 0, 0);
		return coverT;
	}

	public Texture map_safezoneConstructor(int coverWidth, int coverHeight,
			Color color, int circleX, int circleY, int circleRadius) {
		Pixmap cover = new Pixmap(coverWidth, coverHeight,
				Pixmap.Format.RGBA8888);
		Texture coverT = new Texture(coverWidth, coverHeight,
				Pixmap.Format.RGBA8888);
		cover.setBlending(Pixmap.Blending.None);
		cover.setColor(new Color(1, 1, 1, 0));
		cover.fill();
		cover.setBlending(Pixmap.Blending.SourceOver);
		cover.setColor(color);
		cover.drawCircle(circleX, circleY, circleRadius);
		coverT.draw(cover, 0, 0);
		return coverT;
	}

	public void setMap_mysteriousStormSetUp() {
		// these are the setups of mysterious storm.
		map_mysteriousStorm_stage = 0;

		// pure cover that is used to extend the size of the map
		map_coverT0 = map_coverConstructor(false, 12000, 12000, new Color(
				(float) 138 / 255, (float) 43 / 255, (float) 226 / 255, .3f),
				0, 0, 0);

		// for mysterious storm stage 1
		map_mysteriousStorm_centerX1 = (int) (Math.random() * 500 + 6000);
		map_mysteriousStorm_centerY1 = (int) (Math.random() * 500 + 6000);
		map_coverT1 = map_coverConstructor(true, 12000, 12000, new Color(
				(float) 138 / 255, (float) 43 / 255, (float) 226 / 255, .3f),
				map_mysteriousStorm_centerX1, map_mysteriousStorm_centerY1,
				5500);

		// for mysterious storm stage 2
		map_mysteriousStorm_centerX2 = map_mysteriousStorm_centerX1
				+ (int) (Math.random() * 1000 - 500);
		map_mysteriousStorm_centerY2 = map_mysteriousStorm_centerY1
				+ (int) (Math.random() * 1000 - 500);
		map_coverT2 = map_coverConstructor(true, 12000, 12000, new Color(
				(float) 138 / 255, (float) 43 / 255, (float) 226 / 255, .3f),
				map_mysteriousStorm_centerX2, map_mysteriousStorm_centerY2,
				2500);

		// for mysterious storm stage 3
		map_mysteriousStorm_centerX3 = map_mysteriousStorm_centerX2
				+ (int) (Math.random() * 400 - 200);
		map_mysteriousStorm_centerY3 = map_mysteriousStorm_centerY2
				+ (int) (Math.random() * 400 - 200);
		map_coverT3 = map_coverConstructor(true, 12000, 12000, new Color(
				(float) 138 / 255, (float) 43 / 255, (float) 226 / 255, .3f),
				map_mysteriousStorm_centerX3, map_mysteriousStorm_centerY3, 800);

		// for mini map, there are 3 covers for three stages and a blinking
		// intiger to light it blink
		// everything should be reduced to 1/60 as before
		map_miniCoverBlinking = 0;

		map_coverTM1 = map_coverConstructor(true, 200, 200, new Color(1, 0, 0,
				.4f), map_mysteriousStorm_centerX1 / 60,
				map_mysteriousStorm_centerY1 / 60, 50);
		map_coverTM1_safezone = map_safezoneConstructor(200, 200, new Color(
				(float) 0.75, (float) 0.75, (float) 0.75, 1),
				map_mysteriousStorm_centerX1 / 60,
				map_mysteriousStorm_centerY1 / 60, 50);

		map_coverTM2 = map_coverConstructor(true, 200, 200, new Color(1, 0, 0,
				.4f), map_mysteriousStorm_centerX2 / 60,
				map_mysteriousStorm_centerY2 / 60, 17);
		map_coverTM2_safezone = map_safezoneConstructor(200, 200, new Color(
				(float) 0.75, (float) 0.75, (float) 0.75, 1),
				map_mysteriousStorm_centerX2 / 60,
				map_mysteriousStorm_centerY2 / 60, 17);

		map_coverTM3 = map_coverConstructor(true, 200, 200, new Color(1, 0, 0,
				.4f), map_mysteriousStorm_centerX3 / 60,
				map_mysteriousStorm_centerY3 / 60, 5);
		map_coverTM3_safezone = map_safezoneConstructor(200, 200, new Color(
				(float) 0.75, (float) 0.75, (float) 0.75, 1),
				map_mysteriousStorm_centerX3 / 60,
				map_mysteriousStorm_centerY3 / 60, 5);
	}

	public void map_mysteriousStormUpdate() {
		// it updates and draws the storm
		// it randomly manages and draws mysterious storms which kills player in
		// the game
		// the first initial radius is 5500, which decays till 3000, then it
		// decays from 2500 to 1000, then from 800 to 30

		// set different stages of the storm
		if (game_runningTime < 2000) { // after 10s, the mysterious storm should
										// appear
			game_runningTime++;
		} else if (game_runningTime == 2000) {
			map_mysteriousStorm_stage = 1;
			game_runningTime++;
			map_mysteriousStorm_radius = 4000; // first initial radius
			// changeAIPosition(map_mysteriousStorm_centerX1,
			// map_mysteriousStorm_centerY1, 3000);
			System.out.println("Mysterious storm: 1st stage");
		} else if (game_runningTime == 6006) { // 10s before the mysterious
												// storm's second stage
			map_mysteriousStorm_stage = 2;
			map_mysteriousStorm_radius = 2500; // second initial radius
			game_runningTime++;
			changeAIPosition(map_mysteriousStorm_centerX2,
					map_mysteriousStorm_centerY2, 1000);
			System.out.println("Mysterious storm: 2nd stage");
		} else if (game_runningTime == 7006) { // another 5s before the
												// mysterious storm's final
												// stage
			map_mysteriousStorm_stage = 3;
			map_mysteriousStorm_radius = 800; // third radius
			game_runningTime++;
			changeAIPosition(map_mysteriousStorm_centerX2,
					map_mysteriousStorm_centerY2, 300);
			System.out.println("Mysterious storm: appear 3rd stage");
		}

		// update and draw storm
		if (map_mysteriousStorm_stage > 0) {
			if (map_mysteriousStorm_stage == 1) {
				if (map_mysteriousStorm_radius > 3000.1) {
					map_mysteriousStorm_radius -= 0.1; // 125s to completely
														// decay
				} else {
					game_runningTime += 1;
				}
				map_mysteriousStormDraw(5500, map_coverT1);
			} else if (map_mysteriousStorm_stage == 2) {
				if (map_mysteriousStorm_radius > 1000.1) {
					map_mysteriousStorm_radius -= 0.1; // 75s to completely
														// decay
				} else {
					game_runningTime += 1;
				}
				map_mysteriousStormDraw(2500, map_coverT2);
			} else if (map_mysteriousStorm_stage == 3) {
				if (map_mysteriousStorm_radius > 300.1) {
					map_mysteriousStorm_radius -= 2; // 25s to completely decay
				} else {
					game_runningTime += 1;
				}
				map_mysteriousStormDraw(800, map_coverT3);
			}
		}
	}

	public void losingBloodInMysteriousStorm() {
		// when the user is the the mysterious storm, he slowly loses hp.
		if (map_mysteriousStorm_stage == 1) {
			double d = Math.pow(map_mysteriousStorm_centerX1 + map_x - 500, 2)
					+ Math.pow(map_mysteriousStorm_centerY1 + map_y - 250, 2);
			if (d > Math.pow(map_mysteriousStorm_radius, 2)) {
				user.getHitInStorm();
			}
		} else if (map_mysteriousStorm_stage == 2) {
			double d = Math.pow(map_mysteriousStorm_centerX2 + map_x - 500, 2)
					+ Math.pow(map_mysteriousStorm_centerY2 + map_y - 250, 2);
			if (d > Math.pow(map_mysteriousStorm_radius, 2)) {
				user.getHitInStorm();
			}
		} else if (map_mysteriousStorm_stage == 3) {
			double d = Math.pow(map_mysteriousStorm_centerX3 + map_x - 500, 2)
					+ Math.pow(map_mysteriousStorm_centerY3 + map_y - 250, 2);
			if (d > Math.pow(map_mysteriousStorm_radius, 2)) {
				user.getHitInStorm();
			}
		}
	}

	public void changeAIPosition(int centerX, int centerY, int radius) {
		// let all AI moves to safe zone by reset their walking path
		for (Character c : Characters) {
			if (!c.equals(user)) {
				c.setPath(centerX + tools.randomIndex(radius / 3), centerY
						- tools.randomIndex(radius / 3),
						centerX - tools.randomIndex(radius / 3), centerY
								+ tools.randomIndex(radius / 3));
			}
		}
	}

	public void map_mysteriousStormDraw(int initialRadius, Texture cover) {
		// this method draws mysterious storm based on given information
		int changeOfRadius = (int) (initialRadius - map_mysteriousStorm_radius);
		int increaseMove = 12000 - changeOfRadius * 2; // for moving right/up
		int decreaseMove = -12000 + changeOfRadius * 2; // for moving
														// left/bottom

		// draw center cover first
		batch.draw(cover, map_x + changeOfRadius, map_y + changeOfRadius,
				12000 - changeOfRadius * 2, 12000 - changeOfRadius * 2);

		// around center cover, draw 8 surrounding cover
		for (int i = 0; i < 2; i++) {
			// left and top left
			batch.draw(map_coverT0, map_x + changeOfRadius + decreaseMove,
					map_y + changeOfRadius + i * increaseMove,
					12000 - changeOfRadius * 2, 12000 - changeOfRadius * 2);
			// right and bottom right
			batch.draw(map_coverT0, map_x + changeOfRadius + increaseMove,
					map_y + changeOfRadius + i * decreaseMove,
					12000 - changeOfRadius * 2, 12000 - changeOfRadius * 2);
			// top and top right
			batch.draw(map_coverT0, map_x + changeOfRadius + i * increaseMove,
					map_y + changeOfRadius + increaseMove,
					12000 - changeOfRadius * 2, 12000 - changeOfRadius * 2);
			// bottom and bottom left
			batch.draw(map_coverT0, map_x + changeOfRadius + i * decreaseMove,
					map_y + changeOfRadius + decreaseMove,
					12000 - changeOfRadius * 2, 12000 - changeOfRadius * 2);
		}
	}

	public void move(int speed) {
		// it detects moving direction and move the graphics
		// when both opposite directions are pressed, go as the second one
		// check w key
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (map_y - speed >= -11500
					&& map_MapDesign.validPosition(-map_x + 500,
							-(map_y - speed) + 250)) {
				if (!verticalMoveFinal) {
					verticalMoveU = true;
					map_y -= speed;
					if (verticalMoveD) {
						verticalMoveD = false;
						verticalMoveFinal = true;
					}
				} else if (verticalMoveU) {
					map_y -= speed;
				}
			}
		} else {
			verticalMoveFinal = false;
			verticalMoveU = false;
		}

		// check s key
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			if (map_y + speed <= 0
					&& map_MapDesign.validPosition(-map_x + 500,
							-(map_y + speed) + 250)) {
				if (!verticalMoveFinal) {
					verticalMoveD = true;
					map_y += speed;
					if (verticalMoveU) {
						verticalMoveU = false;
						verticalMoveFinal = true;
					}
				} else if (verticalMoveD) {
					map_y += speed;
				}
			}
		} else {
			verticalMoveFinal = false;
			verticalMoveD = false;
		}

		// check d key
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (map_x - speed >= -11000
					&& map_MapDesign.validPosition(-(map_x - speed) + 500,
							-map_y + 250)) {
				if (!horizontalMoveFinal) {
					horizontalMoveR = true;
					map_x -= speed;
					if (horizontalMoveL) {
						horizontalMoveL = false;
						horizontalMoveFinal = true;
					}
				} else if (horizontalMoveR) {
					map_x -= speed;
				}
			}
		} else {
			horizontalMoveFinal = false;
			horizontalMoveR = false;
		}

		// check a key
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (map_x + speed <= 0
					&& map_MapDesign.validPosition(-(map_x + speed) + 500,
							-map_y + 250)) {
				if (!horizontalMoveFinal) {
					horizontalMoveL = true;
					map_x += speed;
					if (horizontalMoveR) {
						horizontalMoveR = false;
						horizontalMoveFinal = true;
					}
				} else if (horizontalMoveL) {
					map_x += speed;
				}
			}
		} else {
			horizontalMoveFinal = false;
			horizontalMoveL = false;
		}
	}

	// --------------------------------------------------------------------------------------------------
	public Texture resize(Texture srcTexture, int newWidth, int newHeight) // This
																			// method
																			// resizes
																			// a
																			// texture
																			// by
																			// changing
																			// it
																			// into
																			// a
																			// pixmap,
																			// resize,
																			// then
																			// convert
																			// back
																			// to
																			// texture
	{
		if (!srcTexture.getTextureData().isPrepared()) {
			srcTexture.getTextureData().prepare();
		}
		Pixmap srcPixmap = srcTexture.getTextureData().consumePixmap();
		Pixmap newPixmap = new Pixmap(newWidth, newHeight,
				srcPixmap.getFormat()); // create new pixmap with desired size

		newPixmap.drawPixmap(
				srcPixmap, // copy picture from source pixmap to new pixmap
				0, 0, srcPixmap.getWidth(), srcPixmap.getHeight(), 0, 0,
				newPixmap.getWidth(), newPixmap.getHeight());

		Texture newTexture = new Texture(newPixmap); // the output texture
		srcPixmap.dispose();
		newPixmap.dispose();
		return newTexture;
	}

	public void drawBullets(ArrayList<Bullet> bullets) // This method draws the
														// bullets
	{
		for (Bullet b : bullets) {
			b.drawSelf(batch, map_x, map_y);
		}
	}

	public void flyBullets(ArrayList<Bullet> bullets) // This method takes the
														// list of active
														// bullets and makes
														// them move
	{
		ArrayList<Bullet> deletes = new ArrayList<Bullet>(); // store all the
																// bullets that
																// are not
																// flying and
																// delete it
		for (Bullet b : bullets) {
			b.checkHit(Characters, map_x, map_y, user);
			if (!b.isFlying()) // store the ones that no longer fly
			{
				deletes.add(b);
			} else {
				b.fly(); // move active bullets
			}
		}
		for (Bullet b : deletes) // the ones not flying will be deleted
		{
			bullets.remove(b);
		}
	}

	public void clearBodies(ArrayList<Character> Characters) {
		ArrayList<Character> deletes = new ArrayList<Character>(); // store
																		// all
																		// the
																		// Characters
																		// that
																		// are
																		// not
																		// alive
																		// and
																		// delete
																		// it
		for (Character c : Characters) {
			if (!c.isAlive()) // store the ones that no longer alive
			{
				deletes.add(c);
			}

		}
		for (Character c : deletes) // the ones not alive will be deleted
		{
			Characters.remove(c);
		}
	}

	public Weapon dataToWeapon(String[] data, int type) // This method takes the
														// data of string and
														// make a weapon from it
	{
		String name;
		double dmg, range, amo, loadAmo, remainAmo, angle, bSpeed; // the
																	// variable
																	// for each
																	// piece of
																	// information

		// takes the information
		name = data[0];
		dmg = Double.parseDouble(data[1]);
		range = Double.parseDouble(data[2]);
		amo = Double.parseDouble(data[3]);
		loadAmo = Double.parseDouble(data[4]);
		remainAmo = Double.parseDouble(data[5]);
		angle = 0;
		bSpeed = Double.parseDouble(data[6]);

		// returns the weapon according to its type
		if (type == Weapon.HANDGUN)
			return new HandGun(name, dmg, range, amo, loadAmo, remainAmo,
					angle, bSpeed);
		if (type == Weapon.SUBMACHINEGUN)
			return new SubMachineGun(name, dmg, range, amo, loadAmo, remainAmo,
					angle, bSpeed);
		if (type == Weapon.MACHINEGUN)
			return new MachineGun(name, dmg, range, amo, loadAmo, remainAmo,
					angle, bSpeed);
		if (type == Weapon.RIFLE)
			return new Rifle(name, dmg, range, amo, loadAmo, remainAmo, angle,
					bSpeed);
		if (type == Weapon.SHOTGUN)
			return new ShotGun(name, dmg, range, amo, loadAmo, remainAmo,
					angle, bSpeed);
		else
			return new Sniper(name, dmg, range, amo, loadAmo, remainAmo, angle,
					bSpeed);
	}

	public Weapon[][] weaponToArray(Scanner file) // takes the loaded text file
													// and make an array of
													// weapons
	{
		int total = Integer.parseInt(file.nextLine()); // the amount of types
		String[] data; // the information of each weapon

		Weapon[][] weapons = new Weapon[total][]; // makes the array

		// loads each line into an permitive array and use the dataToWeapon
		// method to make the weapon
		// Load combat weapons
		// --------------------------------------------------

		// Load hand gun weapons
		// --------------------------------------------------
		HandGun[] handGuns = new HandGun[Integer.parseInt(file.nextLine())];
		for (int i = 0; i < handGuns.length; i++) {
			data = file.nextLine().split(",");
			handGuns[i] = (HandGun) dataToWeapon(data, Weapon.HANDGUN);
			handGuns[i].setFireSound(handGunSound, outAmoSound);
		}
		weapons[0] = handGuns;

		// Load sub-machine gun weapons
		// --------------------------------------------------
		SubMachineGun[] subMachineGuns = new SubMachineGun[Integer
				.parseInt(file.nextLine())];
		for (int i = 0; i < subMachineGuns.length; i++) {
			data = file.nextLine().split(",");
			subMachineGuns[i] = (SubMachineGun) dataToWeapon(data,
					Weapon.SUBMACHINEGUN);
			subMachineGuns[i].setFireSound(subMachineGunSound, outAmoSound);
		}
		weapons[1] = subMachineGuns;

		// Load machine gun weapons
		// ------------04--------------------------------------
		MachineGun[] machineGuns = new MachineGun[Integer.parseInt(file
				.nextLine())];
		for (int i = 0; i < machineGuns.length; i++) {
			data = file.nextLine().split(",");
			machineGuns[i] = (MachineGun) dataToWeapon(data, Weapon.MACHINEGUN);
			machineGuns[i].setFireSound(machineGunSound, outAmoSound);

		}
		weapons[2] = machineGuns;

		// Load rifle weapons --------------------------------------------------
		Rifle[] rifles = new Rifle[Integer.parseInt(file.nextLine())];
		for (int i = 0; i < rifles.length; i++) {
			data = file.nextLine().split(",");
			rifles[i] = (Rifle) dataToWeapon(data, Weapon.RIFLE);
			rifles[i].setFireSound(rifleSound, outAmoSound);

		}
		weapons[3] = rifles;

		// Load shot gun weapons
		// --------------------------------------------------
		ShotGun[] shotGuns = new ShotGun[Integer.parseInt(file.nextLine())];
		for (int i = 0; i < shotGuns.length; i++) {
			data = file.nextLine().split(",");
			shotGuns[i] = (ShotGun) dataToWeapon(data, Weapon.SHOTGUN);
			shotGuns[i].setFireSound(shotGunSound, outAmoSound);

		}
		weapons[4] = shotGuns;

		// Load sniper weapons
		// --------------------------------------------------
		Sniper[] snipers = new Sniper[Integer.parseInt(file.nextLine())];
		for (int i = 0; i < snipers.length; i++) {
			data = file.nextLine().split(",");
			snipers[i] = (Sniper) dataToWeapon(data, Weapon.SNIPER);
			snipers[i].setFireSound(sniperSound, outAmoSound);

		}
		weapons[5] = snipers;

		return weapons;
	}

	public Texture[] loadGunImgs() // this method loads all the weapon pictures
	{
		Texture[] gunImg = new Texture[17];
		Texture p92Img, p18cImg, p1911Img, mp5Img, uziImg, vectorImg, m60Img, m4a1Img, ak47Img, scarImg, augImg, s12kImg, s186Img, kar98Img, awmImg;

		try // get each image
		{
			p92Img = new Texture("p92.png");
			p92Img = resize(p92Img, 60, 16);

			p18cImg = new Texture("p18c.png");
			p18cImg = resize(p18cImg, 57, 23);

			p1911Img = new Texture("p1911.png");
			p1911Img = resize(p1911Img, 61, 20);

			mp5Img = new Texture("mp5.png");
			mp5Img = resize(mp5Img, 96, 25);

			uziImg = new Texture("uzi.png");
			uziImg = resize(uziImg, 70, 30);

			vectorImg = new Texture("vector.png");
			vectorImg = resize(vectorImg, 73, 16);

			m60Img = new Texture("m60.png");
			m60Img = resize(m60Img, 119, 40);

			m4a1Img = new Texture("m4a1.png");
			m4a1Img = resize(m4a1Img, 95, 26);

			ak47Img = new Texture("ak47.png");
			ak47Img = resize(ak47Img, 96, 23);

			scarImg = new Texture("scar.png");
			scarImg = resize(scarImg, 90, 90);

			augImg = new Texture("aug.png");
			augImg = resize(augImg, 104, 30);

			s12kImg = new Texture("s12k.png");
			s12kImg = resize(s12kImg, 100, 130);

			s186Img = new Texture("s186.png");
			s186Img = resize(s186Img, 100, 130);

			kar98Img = new Texture("kar98.png");
			kar98Img = resize(kar98Img, 150, 10);

			awmImg = new Texture("awm.png");
			awmImg = resize(awmImg, 150, 30);

			// store the images into the array
			gunImg[0] = p92Img;
			gunImg[1] = p18cImg;
			gunImg[2] = p1911Img;
			gunImg[3] = mp5Img;
			gunImg[4] = uziImg;
			gunImg[5] = vectorImg;
			gunImg[6] = m60Img;
			gunImg[7] = m4a1Img;
			gunImg[8] = ak47Img;
			gunImg[9] = scarImg;
			gunImg[10] = augImg;
			gunImg[11] = s12kImg;
			gunImg[12] = s186Img;
			gunImg[13] = kar98Img;
			gunImg[14] = awmImg;
		}

		catch (Exception e) {
			System.out.println("gun images not found");
			System.exit(-1);
		}

		return gunImg;

	}

	public double getAngle(double x1, double y1, double x2, double y2) {
		double angle = 0;
		if (x2 == x1) {
			angle = x2 > x1 ? Math.PI / 2 : 3 * Math.PI / 2;
		} else if (x2 >= x1 && y2 >= y1) {
			angle = Math.atan((y2 - y1) / (x2 - x1));
		} else if (x2 <= x1 && y2 >= y1) {
			angle = Math.atan((y2 - y1) / (x2 - x1)) + Math.PI;
		} else if (x2 <= x1 && y2 <= y1) {
			angle = Math.atan((y2 - y1) / (x2 - x1)) + Math.PI;
		} else if (x2 >= x1 && y2 <= y1) {
			angle = Math.atan((y2 - y1) / (x2 - x1));
		}
		return angle;
	}

	public void showDanger() {
		double posX = 0, posY = 0;
		double userX = user.getSelf().x - map_x, userY = user.getSelf().y
				- map_y;
		double angle;
		float imgX, imgY;
		TextureRegion imgR = new TextureRegion(dangerImg, 0, 0,
				dangerImg.getWidth(), dangerImg.getHeight()); // make a texture
																// region to
																// rotate when
																// drawing
		for (Character c : Characters) {
			if (!c.equals(user)) {
				posX = c.getSelf().x;
				posY = c.getSelf().y;

				// if enemy if within 1500 pixel and is not seen in the screen
				if (Math.hypot(posY - userY, posX - userX) <= 1500
						&& (Math.abs(posY - userY) > 250 || Math.abs(posX
								- userX) > 500)) {
					angle = getAngle(userX, userY, posX, posY);
					imgX = (float) (userX + Math.cos(angle) * 240) + map_x;
					imgY = (float) (userY + Math.sin(angle) * 240) + map_y;

					batch.draw(imgR, imgX, imgY,
							(float) dangerImg.getWidth() / 2,
							(float) dangerImg.getHeight() / 2,
							(float) dangerImg.getWidth(),
							(float) dangerImg.getHeight(), (float) 1,
							(float) 1, (float) Math.toDegrees(angle));
				}

			}
		}
	}

	public static Weapon randWeapon(Weapon[][] weapons, Random rand,
			Texture[] gunImgs) // gets a random weapon from the array
	{
		int index1 = rand.nextInt(weapons.length); // get indexes from random
		int index2 = rand.nextInt(weapons[index1].length);

		return weapons[index1][index2].copy();

	}

	public static void setWeaponPos(Character user) // sets the weapon's
														// position according to
														// the user's
	{
		if (user.getWeapons() != null) {
			// for every weapon that the user has
			for (Weapon w : user.getWeapons()) {
				// sets angle and position
				w.setAngle(user.getAngle());
				w.setPos(
						user.getX() + user.getImgW() / 2.5
								* Math.cos(user.getAngle()),
						user.getY() + user.getImgH() / 2.5
								* Math.sin(user.getAngle()));
			}
		}
	}

	public void cpuRandArmor(Character c) {
		int random = rand.nextInt(4);
		if (random == 1) {
			c.setArmor(new LevelOneArmor());
		} else if (random == 2) {
			c.setArmor(new LevelTwoArmor());
		} else if (random == 3) {
			c.setArmor(new LevelThreeArmor());
		}
	}

	// ///////////////////////////////////////////ALEX
	// variations\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public void Character_loadFiles() {
		// for render
		try // load sound effects
		{
			combatSound = Gdx.audio.newSound(Gdx.files
					.internal("audio/combatSound.mp3"));
			handGunSound = Gdx.audio.newSound(Gdx.files
					.internal("audio/handGunSound.mp3"));
			subMachineGunSound = Gdx.audio.newSound(Gdx.files
					.internal("audio/subMachineGunSound.mp3"));
			machineGunSound = Gdx.audio.newSound(Gdx.files
					.internal("audio/machineGunSound.mp3"));
			rifleSound = Gdx.audio.newSound(Gdx.files
					.internal("audio/rifleSound.mp3"));
			shotGunSound = Gdx.audio.newSound(Gdx.files
					.internal("audio/shotGunSound.mp3"));
			sniperSound = Gdx.audio.newSound(Gdx.files
					.internal("audio/sniperSound.mp3"));
			outAmoSound = Gdx.audio.newSound(Gdx.files
					.internal("audio/outAmo.mp3"));
			deathSound = Gdx.audio.newSound(Gdx.files
					.internal("audio/deathSound.mp3"));
		} catch (Exception e) {
			System.out.println("mp3 files not found");
			System.exit(-1);
		}

		try // load image of the bullet and user
		{
			bulletImg = new Texture("bullets.png");
			bulletImg = resize(bulletImg, 30, 3);

			dangerImg = new Texture("dangerPic.png");
			dangerImg = resize(dangerImg, 25, 25);

			CharacterImg = new Texture("Characters1.png");
			CharacterImg = resize(CharacterImg, 120, 120);
			CharacterImgs[0] = CharacterImg;

			CharacterImg = new Texture("Characters2.png");
			CharacterImg = resize(CharacterImg, 120, 120);
			CharacterImgs[1] = CharacterImg;

			CharacterImg = new Texture("Characters3.png");
			CharacterImg = resize(CharacterImg, 120, 120);
			CharacterImgs[2] = CharacterImg;

			CharacterImg = new Texture("Characters4.png");
			CharacterImg = resize(CharacterImg, 120, 120);
			CharacterImgs[3] = CharacterImg;

			CharacterImg = new Texture("Characters5.png");
			CharacterImg = resize(CharacterImg, 120, 120);
			CharacterImgs[4] = CharacterImg;
		} catch (Exception e) // error when image does not load
		{
			System.out.println("bullet/user image not found");
			System.exit(-1);
		}

		try // load the text file for the weapons
		{
			inFile = new Scanner(new BufferedReader(new FileReader(
					"Weapons.txt")));
			weapons = weaponToArray(inFile);
		} catch (FileNotFoundException e) // error in loading text file
		{
			System.out.println("Txt file not found");
			System.exit(-1);
		}
	}

	public void Character_userSetup() {
		// creates a Character for user
		user = new Character();
		user.setPos(500, 250);
		user.setImg(CharacterImgs[rand.nextInt(CharacterImgs.length)]);

		LevelTwoArmor a1 = new LevelTwoArmor();
		user.setArmor(a1);

		// get two random weapons for user
		gun = randWeapon(weapons, rand, gunImgs);
		gun.setRemain(gun.getAmo() * 20);

		gun2 = randWeapon(weapons, rand, gunImgs);
		gun2.setRemain(gun2.getAmo() * 20);

		// set primary and secondary weapon for user
		user.setPrimary(gun);
		user.setSecondary(gun2);
		user.setDeathSound(deathSound);
		// user.getHit(-10000000, user, map_x, map_y, user);
		setWeaponPos(user);
		Characters.add(user);
	}

	public void Character_AISetup() {
		// set up other Characters(AI)
		for (int i = 0; i < 99; i++) {
			gun = randWeapon(weapons, rand, gunImgs);
			gun.setRemain(gun.getAmo() * 20);

			gun2 = randWeapon(weapons, rand, gunImgs);
			gun2.setRemain(gun2.getAmo() * 20);

			cpu1 = new Character();
			int x = rand.nextInt(10000) + 1000;
			int y = rand.nextInt(10000) + 1000;
			cpu1.setPos(x, y);
			cpu1.setImg(CharacterImgs[rand.nextInt(CharacterImgs.length)]);
			cpu1.setPrimary(gun);
			cpu1.setSecondary(gun2);
			cpu1.setDeathSound(deathSound);
			cpu1.setPath(cpu1.getX() - 100, cpu1.getY() - 100,
					cpu1.getX() + 100, cpu1.getY() + 200);
			setWeaponPos(cpu1);
			cpuRandArmor(cpu1);
			Characters.add(cpu1);
		}
	}

	public void Character_drawCharacters() {
		// for render
		// draw bullets, all Characters and weapons
		batch.begin();
		for (Character c : Characters) {

			if (c.equals(user)) {
				c.getUsing().drawSelf(batch);
				c.drawSelf(batch);
			} else {
				c.pathMove();
				setWeaponPos(c);
				c.detectEnemy(Characters, user, map_x, map_y);
				ArrayList<Bullet> newB = c.fireTarget(pastTime, user, map_x,
						map_y);
				for (Bullet b : newB) {
					b.setImg(bulletImg);
					b.setPos(c.getSelf().x + 100 * Math.cos(c.getAngle()),
							c.getSelf().y + 100 * Math.sin(c.getAngle()));
					bullets.add(b);
				}
				c.getUsing().drawSelf(batch, map_x, map_y);
				c.cpuDrawSelf(batch, map_x, map_y);
				c.CpudisplayStats(font, batch, map_x, map_y);
			}
		}

		drawBullets(bullets);
		String pos = String.format("%f , %f", user.getSelf().x - map_x,
				user.getSelf().y - map_y);
		//font.draw(batch, pos, user.getSelf().x-30, user.getSelf().y-20) ;

		showDanger();
		batch.end();
	}

	public void Character_drawGamingData() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); // allow
																			// alpha
		shaperender.begin(ShapeRenderer.ShapeType.Filled);
		shaperender
				.setColor((float) 0.3, (float) 0.3, (float) 0.3, (float) 0.8);
		shaperender.rect(880, 450, 100, 25);
		shaperender.rect(910, 415, 70, 25);
		shaperender.end();
		user.getUsing().display(font, batch, shaperender);
		user.displayStats(font, batch, shaperender);
		batch.begin();
		if (reload) {
			if (desTime > pastTime) // checks if reload time is over
			{
				float t = desTime - pastTime; // time remaining
				String strTime = String.format("%.2f", t / 1000); // use only 2
																	// decimal
																	// places
				font.draw(batch, strTime, 900, 100); // display remaining time
			} else // reload gun
			{
				user.getUsing().reload();
				reload = false;
			}
		}

		font.draw(batch, "ALIVE: " + Characters.size(), 900, 470);
		font.draw(batch, "KILLS: " + user.getKills(), 920, 435);
		batch.end();

		if (healing) {
			shaperender.begin(ShapeRenderer.ShapeType.Filled);
			shaperender.setColor((float) 0.3, (float) 0.3, (float) 0.3,
					(float) 0.8);
			shaperender.rect((float) user.getX() - 50,
					(float) user.getY() - 50, (float) 100, (float) 25);
			shaperender.end();
			batch.begin();
			font.draw(batch, "USING MEDS...", (float) user.getX() - 45,
					(float) user.getY() - 35);
			if (desTime > pastTime) // checks if heal time is over
			{
				float t = desTime - pastTime; // time remaining
				String strTime = String.format("%.2f", t / 1000); // use
				// only
				// 2
				// decimal
				// places
				font.draw(batch, strTime, 900, 100); // display remaining
				// time
			} else // start heal
			{
				if (medicine == Medical.BANDAGE) {
					user.useBandage();
				} else {
					user.usefirstAid();
				}
				healing = false;
			}
			batch.end();
		}
	}

	private void loadSprites() {
		// load all sprites used in intro
		menu = tools.loadSprite(m.getMenu(), 1000, 500);
		login = tools.loadSprite(m.getLogin(), 180, 80);
		login.setCenter(500, 85);
		bg = tools.loadSprite(m.getBg(), 1000, 500);
		start = tools.loadSprite(m.getStart(), 70, 70);
		start.setCenter(865, 85);
		loading = tools.loadSprite(m.getLoading(), 1000, 500);
	}

	// /////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	// alex--------------------------------------------------------------------------------------------------

	private void game_resetGame() {
		// it reloads all the data to generate a new game
		// general fields
		map_x = 0; // start from bottom left corner on the map
		map_y = 0;
		horizontalMoveL = false;
		horizontalMoveR = false;
		horizontalMoveFinal = false;
		verticalMoveU = false;
		verticalMoveD = false;
		verticalMoveFinal = false;

		game_runningTime = 0; // check how long the games has run

		timeStart = System.currentTimeMillis();

		// map fields
		map_MapDesign = new MapDesign();
		map_mapBase = map_MapDesign.getMapTexture();
		map_mapObstacles = map_MapDesign.getObstacleTexture();

		setMap_mysteriousStormSetUp();

		// Character fields
		Gdx.input.setInputProcessor(this);
		gunImgs = loadGunImgs();
		CharacterImgs = new Texture[5];

		Character_loadFiles();

		// sets the image for each gun
		int counter = 0;
		for (Weapon[] ws : weapons) {
			for (Weapon w : ws) // goes through each weapon
			{
				w.setImg(gunImgs[counter]); // sets the image
				counter++;
			}
		}

		Character_userSetup();

		// sets the font
		font = new BitmapFont();
		font.getData().setScale(0.9f);
		font.setColor(1, 1, 1, 1);

		Character_AISetup();
	}

	@Override
	public void create() {
		tools = new Tools(1000, 500); // set up tools
		Gdx.graphics.setWindowedMode(1000, 500); // reset size.
		batch = new SpriteBatch();
		shaperender = new ShapeRenderer();
		youWin = tools.loadTexture("youWin.jpg", 1000, 500);
		youLost = tools.loadTexture("youLost.jpg", 1000, 500);


		// intro fields
		m = new Menu();
		m.init();
		loadSprites();
		mx = tools.getMouseX();
		my = tools.getMouseY();
		text = m.showIntro();
		Menu_font = new BitmapFont(Gdx.files.internal("font1.fnt"));
		Menu_font.setColor(Color.WHITE);
		Menu_fontT = new BitmapFont(Gdx.files.internal("font2.fnt"));
		Menu_fontT.getData().setScale(2.2F);

		username = "";
		loadingPercent = 0;
		shapeRenderer = new ShapeRenderer();
		windowOpen = false;
		gameStart = false;
		loadGame = false;
		inMenu = true;
		timeStart = System.currentTimeMillis();

		game_resetGame();
	}

	@Override
	public void render() {
		if (gameStart) { // the real game
			timeNow = System.currentTimeMillis();
			if (timeNow - timeStart > 5) { // only after 5 ms the graph will
											// refresh.
				timeStart = timeNow;

				move(map_MapDesign.getMovingSpeed(-map_x + 500, -map_y + 250));

				// map base and mysterious storm
				batch.begin();
				batch.draw(map_mapBase, map_x, map_y);
				batch.draw(map_mapObstacles, map_x, map_y);
				map_mysteriousStormUpdate();
				batch.end();

				losingBloodInMysteriousStorm();

				// Characters and Character gaming data
				pastTime = TimeUtils.millis();

				Character_drawCharacters();

				flyBullets(bullets); // move the bullets
				clearBodies(Characters);
				holdKey();

				// draw map obstacles so tree's can cover user.
				batch.begin();
				batch.draw(map_mapObstacles, map_x, map_y);
				batch.end();

				// draw data after obstacles so data wont be covered.
				Character_drawGamingData();

				// mini map
				map_minimapDisplay();

				if (game_runningTime == 8000) { // draw youWin to show it is the
												// end the game
					batch.begin();
					batch.draw(youWin, 0, 0);
					batch.end();
				} else if (game_runningTime > 8000) {
					tools.timeWait(2000);
					game_resetGame();
					gameStart = false;
					loadGame = false;
					inMenu = true;
				}

				if (!user.isAlive()) {
					batch.begin();
					batch.draw(youLost, 0, 0);
					batch.end();
					game_runningTime = 9000;	// finish game
				}
			}
		} else if (loadGame) { // the loading page
			timeNow = System.currentTimeMillis();
			batch.begin();
			loading.draw(batch); // page background

			Menu_font.getData().setScale(1F);
			if (direction == 0) { // this shows that before this page is
									// inMenu
				Menu_font.draw(batch, "Let me introduce the game, " + username,
						50, Gdx.graphics.getHeight() - 50);
			} else { // after this will be the game
				Menu_font.draw(batch, "Welcome to the Island, " + username, 50,
						Gdx.graphics.getHeight() - 50);
			}
			batch.end();

			if (loadingPercent < 1) { // draw the loading line
				batch.begin();
				Menu_font.draw(batch, "Loading... ", 50, 85);
				batch.end();
				shapeRenderer.begin(ShapeType.Filled);
				shapeRenderer.setColor(Color.RED);

				loadingPercent += loadingPercent < 0.998 ? Math.random() / 500
						: 1 - loadingPercent;
				shapeRenderer.circle(50, 45, 5);
				shapeRenderer.rect(50, 40, (float) (loadingPercent * 900), 10);
				shapeRenderer.circle((float) (50 + (loadingPercent * 900)), 45,
						5);

				double value = (timeNow - timeStart) / 100;
				double radian = Math.toRadians(30);
				shapeRenderer.setColor(Color.WHITE);
				for (int i = 0; i < 12; i++) { // loading circle
					shapeRenderer
							.circle((float) (190 + 15 * Math.cos(radian
									* (i + value))),
									(float) (70 + 15 * Math.sin(radian
											* (i + value))), i / 3);
				}
				shapeRenderer.end();
			}

			if (0.99 < loadingPercent && loadingPercent < 1) { // reset the
																// time
																// after
																// loading
				timeStart = timeNow;
			}

			// after loading, darken the screen, after 3 s, move to the next
			// page
			if (loadingPercent == 1) {
				double time = (timeNow - timeStart) / 1000;
				if (time > 3) {
					if (direction == 0) {
						loadGame = false; // go to the intro
					} else {
						gameStart = true; // or game start
					}
					timeStart = timeNow;
					loadingPercent = 0;
					loading.setColor(2F, 2F, 2F, 1F); // set the color back
					font.setColor(Color.WHITE);
				} else {
					float t = (float) (time * 0.1);
					Color cl = loading.getColor();
					// darken the screen
					loading.setColor(new Color(cl.r - cl.r / 30 * t, cl.g
							- cl.g / 30 * t, cl.b - cl.b / 30 * t, 1F));
					Color cf = font.getColor();
					Menu_font.setColor(new Color(cf.r - cf.r / 30 * t, cf.g
							- cf.g / 30 * t, cf.b - cf.b / 30 * t, 1F));
				}
			}

		} else if (inMenu) {
			batch.begin();
			menu.draw(batch); // menu background
			Menu_fontT.draw(batch, "Mission Possible Express", 13,
					Gdx.graphics.getHeight() - 40); // big title
			if (windowOpen) { // if the login window is open
				timeStart = timeNow;
			} else {
				if (username.equals("")) { // if the user did not login,
					login.draw(batch); // draw the login symbol
					if (tools.mouseIsInRange(login)) {
						batch.end();
						shapeRenderer.begin(ShapeType.Line); // draw a
																// rectangle
																// around
						for (float i = 0; i < 5; i += 0.5) {
							shapeRenderer.rect(410 - i, 45 - i, 180 + 2 * i,
									80 + 2 * i);
						}
						shapeRenderer.setColor(Color.RED);
						shapeRenderer.end();

						batch.begin();
						if (tools.leftClick() && username.equals("")
								&& !windowOpen) { // when player click on
													// login
							Gdx.input.getTextInput(this, "Login", "",
									"username"); // show the window
							if (!username.equals("")) {
								windowOpen = true; // mark window is open
							}
						}
					}
				} else { // if there is a user name, close this page, move
							// to
							// loadGame
					inMenu = false;
					direction = 0;
					loadGame = true;
				}
			}
			batch.end();
		} else { // this is where the intro page get called
			timeNow = System.currentTimeMillis();
			batch.begin();
			bg.draw(batch); // background of the page
			long value = (((timeNow - timeStart) / 1500) < text.size()) ? ((timeNow - timeStart) / 1500)
					: text.size(); // how many lines should show be on the
									// screen depends on time past
			Menu_font.getData().setScale(0.8f);

			// draw those lines
			for (int i = 0; i < value; i++) {
				Menu_font.draw(batch, text.get(i), 450,
						Gdx.graphics.getHeight() - 100 - i * 50);
			}

			// after drawing all the lines, show the start button
			if ((timeNow - timeStart) / 1500 > text.size()) {
				start.draw(batch);

				if (tools.mouseIsInRange(start)) {
					batch.end();
					shapeRenderer.begin(ShapeType.Line);
					// if touched, draw a circle around
					for (float i = 30; i < 35; i += 0.5) {
						shapeRenderer.circle(865, 85, i, Color.alpha(1));
					}
					shapeRenderer.setColor(Color.RED);
					shapeRenderer.end();
					batch.begin();
					// click on the button and leads to the loadGame and
					// real game after
					if (tools.leftClick()) {
						loadGame = true;
						timeStart = timeNow; // reset time
						direction = 1; // to the real game
					}
				}
			}
			batch.end();

		}

	}

	@Override
	public void dispose() {
		// dispose to release memory
		batch.dispose();
		map_mapBase.dispose();
		map_coverT0.dispose();
		map_coverT1.dispose();
		map_coverT2.dispose();
		map_coverT3.dispose();
		map_coverTM1.dispose();
		map_coverTM1_safezone.dispose();
		map_coverTM2.dispose();
		map_coverTM2_safezone.dispose();
		map_coverTM3.dispose();
		map_coverTM3_safezone.dispose();
	}

	public void holdKey() {
		fireTime = pastTime; // check time
		mouseX = Gdx.input.getX(); // turn the user according to the mouse
									// position
		mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		user.turn(mouseX, mouseY);
		setWeaponPos(user);
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)
				&& fireTime > waitTime && !reload) // if the wait time to fire
													// is over
		{
			int type = user.getUsing().getType(); // type of weapon that is
													// being used
			waitTime = fireTime + type * 75; // wait time is dependent on the
												// type of the gun
			if (type == Weapon.SUBMACHINEGUN || type == Weapon.MACHINEGUN
					|| type == Weapon.RIFLE) // only these weapons support
												// automatic fire
			{
				ArrayList<Bullet> newB = user.fire(user, map_x, map_y);

				// sets the image for the bullet, sets position, and add it to
				// the list of bullets that are fired
				for (Bullet b : newB) {
					b.setImg(bulletImg);
					b.setPos(user.getSelf().x + 100 * Math.cos(user.getAngle())
							- map_x,
							user.getSelf().y + 100 * Math.sin(user.getAngle())
									- map_y);
					bullets.add(b);
				}
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {

		return false;

	}

	@Override
	public boolean keyUp(int keycode) {
		// clicking numbers allow user to change weapons

		if (keycode == Input.Keys.NUM_1) {
			user.setUsing(user.PRIMARY);
			reload = false;
		}
		if (keycode == Input.Keys.NUM_2) {
			user.setUsing(user.SECONDARY);
			reload = false;
		}

		if (keycode == Input.Keys.NUM_3) {
			if (user.getFirstAid() > 0 && !healing && user.getHp() < 100) {
				healing = true;
				reload = false;
				desTime = (long) (pastTime + FirstAid.getUseTime() * 1000);
				medicine = Medical.FIRSTAID;
			}
		}

		if (keycode == Input.Keys.NUM_4) {
			if (user.getBandage() > 0 && !healing && user.getHp() < 100) {
				healing = true;
				reload = false;
				desTime = (long) (pastTime + Bandage.getUseTime() * 1000);
				medicine = Medical.BANDAGE;
			}
		}
		// clicking r allows user to reload gun
		if (keycode == Input.Keys.R && !reload) {
			// you can only reload if there is room to reload and amo left to
			// reload
			if (user.getUsing().getAmo() < user.getUsing().getLoadAmo()
					&& user.getUsing().getRemainAmo() > 0) {
				if (user.getUsing() != null)
					;
				{
					double ammo = user.getUsing().getMag() == Weapon.EXTEND ? user
							.getUsing().getLoadAmo() - 10 : user.getUsing()
							.getLoadAmo();
					System.out.println(ammo);
					desTime = user.getUsing().getMag() == Weapon.QUICK ? (long) (pastTime + ammo * 40)
							: (long) (pastTime + ammo * 70); // making the
																// reload time
																// proportion to
																// loadAmo of
																// the gun
					reload = true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean keyTyped(char Character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// mouse click allows user to fire weapon
		if (button == Input.Buttons.LEFT && !reload) {
			int type = user.getUsing().getType(); // type of weapon that is
													// being used
			if (type == Weapon.HANDGUN || type == Weapon.SHOTGUN
					|| type == Weapon.SNIPER) {
				ArrayList<Bullet> newB = user.fire(user, map_x, map_y);

				// sets the image for the bullet, sets position, and add it to
				// the list of bullets that are fired
				for (Bullet b : newB) {
					b.setImg(bulletImg);
					b.setPos(user.getSelf().x + 100 * Math.cos(user.getAngle())
							- map_x,
							user.getSelf().y + 100 * Math.sin(user.getAngle())
									- map_y);
					bullets.add(b);
				}
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// moving the mouse allows the user to change angle
		mouseX = Gdx.input.getX();
		mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		user.turn(mouseX, mouseY);
		setWeaponPos(user);
		return false;
	}

	@Override
	public boolean scrolled(float a, float b) {
		// TODO Auto-generated method stub
		return false;
	}

	public void input(String text) {
		// see if the user existed before, if not, create a new one
		username = text;
		boolean flag = true;
		// based on the username see if he has records before
		for (int i = 0; i < m.getUsers().size(); i++) {
			if (m.getUsers().get(i).getUsername().equals(username)) {
				u = m.getUsers().get(i);
				flag = false;
				break;
			}
		}
		if (flag) {
			m.getUsers().add(new User(username));
		}
		m.saveUser();
		// after get the username, close window
		//
		windowOpen = false;
		timeStart = timeNow;
	}

	@Override
	public void canceled() {
		username = "";
		windowOpen = false; // mark window is closed
	}
}