package com.mygdx.game;

public class HandGun extends Weapon
{
	public HandGun(String name, double dmg, double range, double amo, double loadAmo, double remainAmo, double angle, double bSpeed)
	{
		super(name, dmg, range, amo, loadAmo, remainAmo, angle,bSpeed);
		type = HANDGUN;
	}
	
	@Override
	public HandGun copy()
	{
		HandGun newWeapon = new HandGun(name, dmg, range, amo, loadAmo, remainAmo, angle, bSpeed);
		newWeapon.setImg(img);
		newWeapon.setType(type);
		newWeapon.setFireSound(fireSound, outAmo);
		return newWeapon;
	}
}
