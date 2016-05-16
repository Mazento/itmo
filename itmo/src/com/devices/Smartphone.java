package com.devices;

public class Smartphone {
	public String name;
	public String os;
	public String chargerType;
	public Integer batteryCapacity; // mAh
	public Integer batteryLeft = 0;
	public Double internalMemory; // Gb
	public Double usedMemory = 0.0; // Gb
	public Boolean bluetooth = false;
	
	public Smartphone() {
	}
	
	public Smartphone(String deviceName) {
		this.name = deviceName;
	}
	
	public void call(String phoneNumber, Integer callDuration) {
		Double drainRate = 0.07;
		if (!checkBattery(drainRate, callDuration)) {
			System.out.println("Error: not enough battery");
			return;
		}
		if (phoneNumber.length() == 0)
			System.out.println("Phone field is empty");
		else if (phoneNumber.length() > 15)
			System.out.println("Phone number is too long. The limit is 15 symbols");
		else
			System.out.println("Called " + phoneNumber + ". Talktime: " + callDuration + " seconds");
		drainBattery(drainRate, callDuration);
		return;
	}
	
	public void downloadData(Double dataSize) {
		Double drainRate = 0.05;
		Integer downloadDuration = (int)Math.round(dataSize / 0.003);
		if (!checkBattery(drainRate, downloadDuration)) {
			System.out.println("Error: not enough battery");
			return;
		}
		if (internalMemory == null)
			System.out.println("Error: memory capacity is not specified!");
		else if ((usedMemory + dataSize) >= internalMemory)
			System.out.println("Cannot download data, no more space");
		else {
			usedMemory += dataSize;
			Double freeSpace = internalMemory - usedMemory;
			System.out.println("Data has been downloaded. Free space: " + freeSpace + "Gb");
		}
		drainBattery(drainRate, downloadDuration);
		return;
	}
	
	public Double getFreeMemory() {
		return internalMemory - usedMemory;
	}
	
	public void acceptData(Smartphone device, Double dataSize) {
		Double drainRate = 0.05;
		Integer transferDuration = (int)Math.round(dataSize / 0.00037);
		if (!checkBattery(drainRate, transferDuration)) {
			System.out.println("Error: not enough battery");
			return;
		}
		if (bluetooth == false) {
			System.out.println("Target device " + name + " has no bluetooth module. Abort");
			return;
		}
		if (device.bluetooth == false) {
			System.out.println("Source device " + device.name + " has no bluetooth module. Abort");
			return;
		}
		if (getFreeMemory() <= dataSize) {
			System.out.println("No free space on target device");
			return;
		}
		if (device.usedMemory < dataSize) {
			System.out.println("Can't send more data than the source device has. Abort");
			return;
		}
		usedMemory += dataSize;
		System.out.println("Success! Data has been sent");
		drainBattery(drainRate, transferDuration);
		return;
	}
	
	public void drainBattery(Double drainRate, Integer time) {
		batteryLeft -= (int)Math.round(drainRate * time);
		if (batteryLeft < 0)
			batteryLeft = 0;
		return;
	}
	
	// check if battery has enough charge
	public Boolean checkBattery(Double drainRate, Integer time) {
		if (batteryLeft - Math.round(drainRate * time) > 0)
			return true;
		return false;
	}
	
	public void chargeDevice(Charger charger, Integer time) {
		Double chargeRate = 0.5;
		if (charger.isBroken()) {
			System.out.println("Charger is broken. You can't use it anymore");
			return;
		}
		if (charger.type != chargerType) {
			System.out.println("Wrong charger");
			return;
		}
		batteryLeft += (int)Math.round(chargeRate * time);
		if (batteryLeft > batteryCapacity)
			batteryLeft = batteryCapacity;
	}
	
	public String getDescription() {
		String result;
		Double batteryPercent = (double)Math.round(batteryLeft * 10000 / batteryCapacity) / 100;
		result = name + " runs on " + os + " operation system. ";
		result += batteryPercent + "% battery left. ";
		result += "Free memory: " + getFreeMemory() + "Gb";
		return result;
	}
}
