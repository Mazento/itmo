package com.devices;

public class Cat {
	public String name;
	public Boolean inpudent;
	
	public void chewCharger(Charger charger) {
		if (inpudent == true)
			charger.breakCharger();
		else
			System.out.println(name + " is too nice to break the charger");
		return;
	}
}
