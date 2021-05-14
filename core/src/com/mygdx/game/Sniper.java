package com.mygdx.game;

public class Sniper extends Weapon
{
	public Sniper(String name, double dmg, double range, double amo, double loadAmo, double remainAmo, double angle,double bSpeed)
	{
		super(name, dmg, range, amo, loadAmo, remainAmo, angle, bSpeed);
		type = SNIPER;
	}
	
	@Override
	public Sniper copy()
	{
		Sniper newWeapon = new Sniper(name, dmg, range, amo, loadAmo, remainAmo, angle, bSpeed);
		newWeapon.setImg(img);
		newWeapon.setType(type);
		newWeapon.setFireSound(fireSound, outAmo);
		return newWeapon;
	}
}
