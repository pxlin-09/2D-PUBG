package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.audio.Sound;

public class ShotGun extends Weapon
{
	public ShotGun(String name, double dmg, double range, double amo, double loadAmo, double remainAmo, double angle, double bSpeed)
	{
		super(name, dmg, range, amo, loadAmo, remainAmo, angle, bSpeed);
		type = SHOTGUN;
	}
	@Override
	public ArrayList<Bullet> fire(Character shotFrom, Character user, int mapX, int mapY)
	{
		ArrayList<Bullet> bullets = new ArrayList<Bullet>();
		if(amo>0)
		{
			if(Math.abs(user.getSelf().x-mapX - shotFrom.getSelf().x) <= 500 && Math.abs(user.getSelf().y-mapY-shotFrom.getSelf().y) < 250)
			{
				fireSound.play();	// play the shooting sound if user
			}
			if(shotFrom.equals(user))
			{
				fireSound.play();	
			}
			amo--;
			Bullet b = new Bullet(dmg, bSpeed, angle-0.05,range,shotFrom);
			Bullet b2 = new Bullet(dmg, bSpeed, angle-0.025,range,shotFrom);
			Bullet b3 = new Bullet(dmg, bSpeed, angle,range,shotFrom);
			Bullet b4 = new Bullet(dmg, bSpeed, angle+0.025,range,shotFrom);
			Bullet b5 = new Bullet(dmg, bSpeed, angle+0.05,range,shotFrom);
			b.fly();
			b2.fly();
			b3.fly();
			b4.fly();
			b5.fly();
			bullets.add(b);
			bullets.add(b2);
			bullets.add(b3);
			bullets.add(b4);
			bullets.add(b5);
		}
		else
		{
			outAmo.play();
		}
		return bullets;
	}
	
	@Override
	public ShotGun copy()
	{
		ShotGun newWeapon = new ShotGun(name, dmg, range, amo, loadAmo, remainAmo, angle, bSpeed);
		newWeapon.setImg(img);
		newWeapon.setType(type);
		newWeapon.setFireSound(fireSound, outAmo);
		return newWeapon;
	}
}
