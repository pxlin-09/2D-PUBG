package com.mygdx.game;

public class MachineGun extends Weapon
{	
	public MachineGun(String name, double dmg, double range, double amo, double loadAmo, double remainAmo, double angle, double bSpeed)
	{
		super(name, dmg, range, amo, loadAmo, remainAmo, angle, bSpeed);
		type = MACHINEGUN;
	}
	
	@Override
	public MachineGun copy()
	{
		MachineGun newWeapon = new MachineGun(name, dmg, range, amo, loadAmo, remainAmo, angle, bSpeed);
		newWeapon.setImg(img);
		newWeapon.setType(type);
		newWeapon.setFireSound(fireSound, outAmo);
		return newWeapon;
	}
}
