package com.devices;

public class Headset {
	public String name;
	public Smartphone pairedPhone;
	
	public void pair(Smartphone smartphone) {
		if (pairedPhone != null) {
			System.out.println("Headset is already paired with something");
			return;
		}
		if (smartphone.bluetooth == false) {
			System.out.println("Device has no bluetooth module. Can't pair");
			return;
		}
		pairedPhone = smartphone;
		System.out.println(name + " has been successfully paired with " + smartphone.name);
		return;
	}
	
	public void unpair() {
		pairedPhone = null;
	}
	
	public String checkPairing() {
		if (pairedPhone != null)
			return "Headset " + name + " is paired with " + pairedPhone;
		else
			return "Headset " + name + " is free";
	}
}
