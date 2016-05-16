package com.devices;

public class Charger {
	public String type;
	private Boolean broken = false;
	
	public Boolean isBroken() {
		return broken;
	}
	
	public void breakCharger() {
		broken = true;
		System.out.println("Congratulations! Charger is broken now");
		return;
	}
}
