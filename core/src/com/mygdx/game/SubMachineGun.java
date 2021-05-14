package com.mygdx.game;

public class SubMachineGun extends Weapon
{
	public SubMachineGun(String name, double dmg, double range, double amo, double loadAmo, double remainAmo, double angle, double bSpeed)
	{
		super(name, dmg, range, amo, loadAmo, remainAmo, angle, bSpeed);
		type = SUBMACHINEGUN;
	}
	
	@Override
	public SubMachineGun copy()
	{
		SubMachineGun newWeapon = new SubMachineGun(name, dmg, range, amo, loadAmo, remainAmo, angle, bSpeed);
		newWeapon.setImg(img);
		newWeapon.setType(type);
		newWeapon.setFireSound(fireSound, outAmo);
		return newWeapon;
	}
}
