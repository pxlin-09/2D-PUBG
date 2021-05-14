package com.mygdx.game;

public class Rifle extends Weapon
{
	public Rifle(String name, double dmg, double range, double amo, double loadAmo, double remainAmo, double angle, double bSpeed)
	{
		super(name, dmg, range, amo, loadAmo, remainAmo, angle, bSpeed);
		type = RIFLE;
	}
	
	@Override
	public Rifle copy()
	{
		Rifle newWeapon = new Rifle(name, dmg, range, amo, loadAmo, remainAmo, angle, bSpeed);
		newWeapon.setImg(img);
		newWeapon.setType(type);
		newWeapon.setFireSound(fireSound, outAmo);
		return newWeapon;
	}
}
