// Tools.java
// Tools object is an object that has a lot of convenient tools that
// helps to construct other functions
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.util.Timer;

public class Tools{
	private int graphHeight, graphWidth;            // it is screen size
	private boolean  leftClicked;     // record if a key is pressed

	public Tools(int width, int height) {
		graphHeight = height;
		graphWidth = width;
	}

	public int getMouseX() {
		return Gdx.input.getX();
	}

	public int getMouseY() {
		// it edits the mouse's y value so it starts from the down left.
		return graphHeight - Gdx.input.getY();
	}

	public boolean leftClick() {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			leftClicked = true;
			return false;
		}

		if (leftClicked) {
			leftClicked = false;
			return true;
		}

		return false;
	}

	public Sprite loadSprite(String st, float width, float height) {
		// load a single sprite
		Sprite sp = new Sprite(new Texture(st));
		sp.setSize(width, height);
		return sp;
	}

	public Sprite[] loadSprites(String[] stl, float width, float height) {
		// load a sprite array to use.
		int amount = stl.length;
		Sprite[] textures = new Sprite[amount];

		for (int i = 0; i < amount; i++) {
            textures[i] = loadSprite(stl[i], width, height);
		}

		return textures;
	}

    public Texture[] loadTextures(String[] stl, int width, int height) {
	    // half leaned online
        int amount = stl.length;
        Texture[] textures = new Texture[amount];

        for (int i = 0; i < amount; i++) {
			textures[i] = loadTexture(stl[i], width, height);
        }

        return textures;
    }

    public Texture loadTexture(String st, int width, int height) {
		return new Texture(getPixmap(st, width, height));
	}

    public Pixmap[] getPixmaps(String[] stl, int width, int height) {
	    // it gets a bunch of Pixmaps with the same size
        int amount = stl.length;
        Pixmap[] pixmaps = new Pixmap[amount];

        for (int i = 0; i < amount; i++) {
            pixmaps[i] = getPixmap(stl[i], width, height);
        }

        return pixmaps;
    }

    public Pixmap getPixmap(String st, int width, int height) {
        Pixmap pixRaw = new Pixmap(Gdx.files.internal(st));
        Pixmap pixGood = new Pixmap(width, height, Pixmap.Format.RGB888);
        pixGood.drawPixmap(pixRaw,
                0, 0, pixRaw.getWidth(), pixRaw.getHeight(),
                0, 0, width, height);
        pixRaw.dispose();
        return pixGood;
    }

	public boolean checkSpriteCollision(Sprite sp1, Sprite sp2) {
		// check if two sprites collide.
		Rectangle r1 = sp1.getBoundingRectangle();
		Rectangle r2 = sp2.getBoundingRectangle();
		if (r1.overlaps(r2)) {
			return true;
		}
		return false;
	}

	public boolean mouseIsInRange(Sprite p) {
		// check if mouse is in range.
		if (p.getBoundingRectangle().contains(getMouseX(), getMouseY())) {
			return true;
		}
		return false;
	}

	public int randomIndex(int length) {
		// get the length of a list and output a random index.
		return (int)(Math.random()*length);
	}

	public void timeWait (int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			System.out.println("Sleep failed.");
		}
	}
}